import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/*	Info

	Final points are from the formula and then divided by the team with the most
		so that #1 has 1.000 and the rest is relative to them

	Sources:
	Scores - http://prwolfe.bol.ucla.edu/cfootball/scores.htm
	Stats - https://www.sports-reference.com/cfb/years/2018-team-defense.html
			https://www.sports-reference.com/cfb/years/2018-team-offense.html
 */

//TODO: clean up reused blocks into their own functions

public class Poll {
	public static String date = "20181104";

	public static void main(String[] args) throws InterruptedException {
		//Hold teams and their names for lookup
		Map<String, Team> teams = new HashMap<>();

		//Gets FBS teams from FBS.txt
		generateTeams(teams);
		//Pulls scores (and calculates record) of teams from UCLA page
		getTeamResults(teams);
		//Pulls stats from sports-reference
		getTeamStats(teams);
		//Calculates SoS using basic and BCS-like formula
		calcTeamBCSSoS(teams);


		//Print poll
		if (args[0].equals("Full")) {
			printTeamData(teams, "Full");
		} else if (args[0].equals("T25")) {
			printTeamData(teams, "T25");
		} else if (args[0].equals("Reddit")) {
			printReddit(teams);
		}

		//Print info for an individual team
//		printTeam(teams, "Florida");
	}

	//Gets team names from FBS.txt and stores in a map
	public static void generateTeams(Map<String, Team> teams) {
		try {
			//Read from file
			BufferedReader teamReader = new BufferedReader(new FileReader(".\\rsc\\FBS.txt"));
			String str;
			while ((str = teamReader.readLine()) != null) {
				//Create team using the team name and add to map
				Team newTeam = new Team(str);
				teams.put(str, newTeam);
			}
			
			teamReader.close();
		} catch (IOException e) {
		}
	}

	//Gets team score and updates record + opponents
	public static void getTeamResults(Map<String, Team> teams) throws InterruptedException {
		try {
			//Read from UCLA page html file
			BufferedReader dataReader = new BufferedReader(new FileReader(".\\rsc\\scores\\2018 College Football - " + date + ".html"));
			String str;

			while ((str = dataReader.readLine()) != null) {
				//If not an empty line and starts with a date
				if (!str.isEmpty() && Character.isDigit(str.charAt(0))) {
					String away = "", home = "", tempScore = "";
					//Away team
					for (int ii = 10; !Character.isSpaceChar(str.charAt(ii)); ii++) {
						away += str.charAt(ii);
						//Make sure to get full name until it reaches multiple spaces in a row
						if (Character.isSpaceChar(str.charAt(ii+1)) && !Character.isSpaceChar(str.charAt(ii+2))) {
							away += " ";
							ii++;
						}
					}
					//Home team
					for (int ii = 41; !Character.isSpaceChar(str.charAt(ii)); ii++) {
						home += str.charAt(ii);
						//Make sure to get full name until it reaches multiple spaces in a row
						if (Character.isSpaceChar(str.charAt(ii+1)) && !Character.isSpaceChar(str.charAt(ii+2))) {
							home += " ";
							ii++;
						}
					}

					//Make sure it contains eligible teams
					if (!teams.containsKey(home) && !teams.containsKey(away)) {
						//If neither team is FBS then skip and do next line
						continue;
					}

					int scoreAway, scoreHome;
					//Away score
					for (int ii = 38; ii < 40; ii++) {
						if (Character.isSpaceChar(str.charAt(ii))) {
							continue;
						}
						tempScore += str.charAt(ii);
					}
					scoreAway = Integer.parseInt(tempScore);
					tempScore = "";
					//Home score
					for (int ii = 69; ii < 71; ii++) {
						if (Character.isSpaceChar(str.charAt(ii))) {
							continue;
						}
						tempScore += str.charAt(ii);
					}
					scoreHome = Integer.parseInt(tempScore);

					//1 = home win, 0 = road win
					int homeResult = (scoreHome>scoreAway)? 1:0;
					int awayResult = (scoreHome<scoreAway)? 1:0;

					//Update team records
					if (teams.containsKey(home)) {
						teams.get(home).setRecord(homeResult);
						//Don't add FCS teams to opponent list
						if (teams.containsKey(away)) {
							teams.get(home).updateOpponents(away);
							teams.get(home).updateResults(homeResult);
						}
					}
					if (teams.containsKey(away)) {
						teams.get(away).setRecord(awayResult);
						//Don't add FCS teams to opponent list
						if (teams.containsKey(home)) {
							teams.get(away).updateOpponents(home);
							teams.get(away).updateResults(awayResult);
						}
					}

				//</PRE> denotes end of the scores so stop checking once we hit that
				} else if (str.equals("</PRE>")) {
					break;
				}
			}
			dataReader.close();
		} catch (IOException e) {
		}
	}

	//Calculate regular and BCS-like SoS for each team
	public static void calcTeamBCSSoS(Map<String, Team> teams) {
		//Hold the numbers
		Map<String, Integer> allTeamWins = new HashMap<>();
		Map<String, Integer> allTeamGames = new HashMap<>();

		//Get team wins and games from all teams into new maps
		Iterator it = teams.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			String tempName = ((Team) pair.getValue()).getName();
			Team tempTeam = teams.get(tempName);
			allTeamWins.put(tempName, tempTeam.getWins());
			allTeamGames.put(tempName, (int)Math.round(tempTeam.getOffenseStats().getGames()));
		}

		//Calculate each team's SoS
		it = teams.entrySet().iterator();
		while (it.hasNext()) {
			//Get a team
			Map.Entry pair = (Map.Entry)it.next();
			String tempName = ((Team) pair.getValue()).getName();
			Team tempTeam = teams.get(tempName);

			//Get their opponents
			ArrayList<String> opponents = tempTeam.getOpponents();

			//Calculate SoS
			double teamWins = 0;
			double teamGames = 0;
			double opponentWins = 0;
			double opponentGames = 0;
			for (int ii = 0; ii < opponents.size(); ii++) {
				String opponentName = opponents.get(ii);
				teamWins += allTeamWins.get(opponentName);
				teamGames += allTeamGames.get(opponentName);
				//And now we calculate the opponent SoS
				Team opponentTeam = teams.get(opponentName);
				ArrayList<String> oppOpponents = opponentTeam.getOpponents();
				for (int jj = 0; jj < oppOpponents.size(); jj++) {
					String oppOpponentName = oppOpponents.get(jj);
					opponentWins += allTeamWins.get(oppOpponentName);
					opponentGames += allTeamGames.get(oppOpponentName);
				}
			}
			tempTeam.setSoS(teamWins/teamGames);
			tempTeam.setOppSoS(opponentWins/opponentGames);
		}

	}

	//Pull stats for each team
	public static void getTeamStats(Map<String, Team> teams) {
		//Stat files turned into 2D string arrays
		File offense = new File(".\\rsc\\stats\\TeamO - " + date + ".xlsx");
		File defense = new File(".\\rsc\\stats\\TeamD - " + date + ".xlsx");
		ArrayList<ArrayList<String>> offenseSheet = getSpreadsheet(offense);
		ArrayList<ArrayList<String>> defenseSheet = getSpreadsheet(defense);

		//Team names vary across sources.  Making consistent with UCLA page
		fixNames(offenseSheet);
		fixNames(defenseSheet);

		//Get team stats for offense
		for (int ii = 0; ii < offenseSheet.size(); ii++) {
			ArrayList<Double> offenseStats = new ArrayList<>();
			Team team = teams.get(offenseSheet.get(ii).get(1));
			for (int jj = 2; jj < offenseSheet.get(jj).size(); jj++) {
				double t = Double.parseDouble(offenseSheet.get(ii).get(jj));
				offenseStats.add(Double.parseDouble(offenseSheet.get(ii).get(jj)));
			}
			team.setOffenseStats(new Stats(offenseStats));
		}

		//Get team stats for defense
		for (int ii = 0; ii < defenseSheet.size(); ii++) {
			ArrayList<Double> defenseStats = new ArrayList<>();
			Team team = teams.get(defenseSheet.get(ii).get(1));
			for (int jj = 2; jj < defenseSheet.get(jj).size(); jj++) {
				defenseStats.add(Double.parseDouble(defenseSheet.get(ii).get(jj)));
			}
			team.setDefenseStats(new Stats(defenseStats));
		}
	}

	//Big group of if statements fixing names in the stat tables
	public static void fixNames(ArrayList<ArrayList<String>> sheet) {
		for (int ii = 0; ii < sheet.size(); ii++) {
			for (int jj = 0; jj < sheet.get(ii).size(); jj++) {
				String cell = sheet.get(ii).get(jj);
				if (cell.contains("State") && !cell.equals("Penn State") && !cell.equals("Ohio State")) {
					String repl = cell.replace("State", "St");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("(")) {
					String repl = cell.replace("(", "");
					repl = repl.replace(")", "");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Jose")) {
					String repl = cell.replace("Jose", "Jos�");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Charlotte")) {
					String repl = cell.replace("Charlotte", "UNC-Charlotte");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Ole Miss")) {
					String repl = cell.replace("Ole Miss", "Mississippi");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("UCF")) {
					String repl = cell.replace("UCF", "Central Florida");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("USC")) {
					String repl = cell.replace("USC", "Southern Cal");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Hawaii")) {
					String repl = cell.replace("Hawaii", "Hawai`i");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("UAB")) {
					String repl = cell.replace("UAB", "Alabama-Birmingham");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Texas Christian")) {
					String repl = cell.replace("Texas Christian", "TCU");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Southern Mississippi")) {
					String repl = cell.replace("Southern Mississippi", "Southern Miss");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Florida International")) {
					String repl = cell.replace("Florida International", "Florida Int'l");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Pitt")) {
					String repl = cell.replace("Pitt", "Pittsburgh");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Ohio") && !cell.equals("Ohio State")) {
					String repl = cell.replace("Ohio", "Ohio U.");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.equals("Louisiana")) {
					String repl = cell.replace("Louisiana", "Louisiana-Lafayette");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("UTSA")) {
					String repl = cell.replace("UTSA", "Texas-San Antonio");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Bowling Green St")) {
					String repl = cell.replace("Bowling Green St", "Bowling Green");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Texas St")) {
					String repl = cell.replace("Texas St", "Texas St-San Marcos");
					sheet.get(ii).set(jj, repl);
				}
				cell = sheet.get(ii).get(jj);
				if (cell.contains("Monroe")) {
					String repl = "Louisiana-Monroe";
					sheet.get(ii).set(jj, repl);
				}
			}
		}
	}
	
	//Get spreadsheet from file
	public static ArrayList<ArrayList<String>> getSpreadsheet(File file) {
		ArrayList<ArrayList<String>> spreadsheet = new ArrayList<>();
		
		try {
			//Apache POI setup
			FileInputStream excelFile = new FileInputStream(file);
			Workbook wb = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = wb.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			//Loop through XLSX file
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				ArrayList<String> currRow = new ArrayList<>();
				boolean startRow = true;
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();

					//If the first cell is blank, skip the row
					if (startRow && currentCell.getCellTypeEnum()==CellType.BLANK) {
						break;
					//If the first cell isn't a number, skip the row
					} else if (startRow && !Character.isDigit(currentCell.getStringCellValue().charAt(0))) {
						break;
					//Otherwise it is a valid stat row
					} else {
						startRow = false;
					}

					//Add to arraylist
					currRow.add(currentCell.getStringCellValue());
				}
				//If arraylist has data in it, add to 2D one
				if (!currRow.isEmpty()) {
					spreadsheet.add(currRow);
				}
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		
		return spreadsheet;
	}

	//Print team rankings with some stats
	public static void printTeamData(Map<String, Team> teams, String type) {
		Iterator it = teams.entrySet().iterator();
		ArrayList<String> names = new ArrayList<>();
		
		//Get list of team names from map
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			String tempName = ((Team) pair.getValue()).getName();
			names.add(tempName);
		}

		//Sort by Points
		for (int ii = 0; ii < names.size()-1; ii++) {
			for (int jj = 0; jj < names.size()-ii-1; jj++) {
				if (teams.get(names.get(jj)).calculateRank() < teams.get(names.get(jj+1)).calculateRank()) {
					String temp = names.get(jj);
					names.set(jj, names.get(jj+1));
					names.set(jj+1, temp);
				}
			}
		}

		//To weight all teams against
		double leader = teams.get(names.get(0)).calculateRank();

		//Print full list or top 25
		int count = 0;
		if (type.equals("Full")) {
			count = names.size()-1;
		} else if (type.equals("T25")) {
			count = 25;
		}

		//Header to print out select stats
		System.out.println("Number\tTeam\t\t\t\tScore\tPoints\t\tW\tL\tPct\t\tSoS\t\tWeighted\tAvgMoV\tYF\t\tYA\t\tTO Margin");
		System.out.println("=====================================================================================================================");
		for (int ii = 0; ii < count; ii++) {
			Team currTeam = teams.get(names.get(ii));
			
			//Number
			System.out.print((ii+1) + ":\t");
			if ((ii+1) < 100) {
				System.out.print("\t");
			}

			//Name
			System.out.print(currTeam.getName());
			//Tabbing for name length
			if (currTeam.getName().length() < 4) {
				System.out.print("\t\t\t\t\t");
			} else if (currTeam.getName().length() < 8) {
				System.out.print("\t\t\t\t");
			} else if (currTeam.getName().length() < 12) {
				System.out.print("\t\t\t");
			} else if (currTeam.getName().length() < 16) {
				System.out.print("\t\t");
			} else if (currTeam.getName().length() < 20) {
				System.out.print("\t");
			}

			DecimalFormat dec1 = new DecimalFormat("#0.0");
			DecimalFormat dec2 = new DecimalFormat("#0.00");
			DecimalFormat dec3 = new DecimalFormat("#0.000");
			DecimalFormat dec4 = new DecimalFormat("#0.0000");

			//Ranking Score and Points
			System.out.print(dec4.format(currTeam.calculateRank()/leader) + "\t");
			System.out.print(dec3.format(currTeam.calculateRank()) + "\t\t");
			
			//Wins and Losses
			System.out.print(currTeam.getWins() + "\t" + currTeam.getLosses());

			//Win Pct
			System.out.print("\t" + dec4.format(currTeam.getPCT()));
			//SoS and SoV
			System.out.print("\t" + dec4.format(currTeam.getSoS()) + "\t" + dec4.format(currTeam.getWeightedSoS()));
			//MoV
			System.out.print("\t\t" + dec2.format(currTeam.getOffenseStats().getPoints() - currTeam.getDefenseStats().getPoints()));
			//YF
			System.out.print("\t" + dec1.format(currTeam.getOffenseStats().getTotalYards()));
			//YA
			System.out.print("\t" + dec1.format(currTeam.getDefenseStats().getTotalYards()));
			//TO Margin
			System.out.print("\t" + dec2.format(currTeam.getDefenseStats().getTotalTO()-currTeam.getOffenseStats().getTotalTO()));
			//End line
			System.out.println();
		}
	}

	//Print in Reddit table format for copy paste
	public static void printReddit(Map<String, Team> teams) {
		Iterator it = teams.entrySet().iterator();
		ArrayList<String> names = new ArrayList<>();

		//Get list of team names from map
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			String tempName = ((Team) pair.getValue()).getName();
			names.add(tempName);
		}

		//Sort by Points
		for (int ii = 0; ii < names.size()-1; ii++) {
			for (int jj = 0; jj < names.size()-ii-1; jj++) {
				if (teams.get(names.get(jj)).calculateRank() < teams.get(names.get(jj+1)).calculateRank()) {
					String temp = names.get(jj);
					names.set(jj, names.get(jj+1));
					names.set(jj+1, temp);
				}
			}
		}

		//To weight all teams against
		double leader = teams.get(names.get(0)).calculateRank();

		System.out.println("Rank| Team | Score | Record\n---|---|---|---");

		for (int ii = 0; ii < 25; ii++) {
			Team currTeam = teams.get(names.get(ii));

			//Ranking + Name
			System.out.print((ii+1) + " | " + currTeam.getName() + " | ");

			DecimalFormat dec4 = new DecimalFormat("#0.0000");
			//Ranking Score
			System.out.print(dec4.format(currTeam.calculateRank()/leader) + " | ");

			//Record
			System.out.println(currTeam.getWins() + "-" + currTeam.getLosses());
		}
	}

	//Print info for one team
	private static void printTeam(Map<String, Team> teams, String teamName) {
		Team team = teams.get(teamName);
		System.out.println(teamName + "\n==============");
		for (int ii = 0; ii < team.getOpponents().size(); ii++) {
			System.out.println(team.getOpponents().get(ii) + " " + team.getResults().get(ii));
		}
		DecimalFormat dec4 = new DecimalFormat("#0.0000");
		System.out.println("SoS: " + dec4.format(team.getSoS()) + "\nWeighted: " + dec4.format(team.getWeightedSoS()));
	}
}