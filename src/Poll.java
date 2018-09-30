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

/*	Philosophy

	Gives credit for W/L against FCS but does not count towards SoV or So
	Formula uses win percent, SoS, SoV, MoV (capped at 21), YF, and YA

	Final points are from the formula and then divided by the team with the most
		so that #1 has 1.000 and the rest is relative to them

	Sources:
	Scores - http://prwolfe.bol.ucla.edu/cfootball/scores.htm
	Stats - https://www.sports-reference.com/cfb/years/2018-team-defense.html
		https://www.sports-reference.com/cfb/years/2018-team-offense.html
 */

//TODO: save game by game MoV to team and use to weight wins by opponent
//TODO: 	rather than by schedule
//TODO: use more stats (TO margin, Yards/Play, Pts/Drive

public class Poll {
	public static String date = "20180930";

	public static void main(String[] args) throws InterruptedException {
		//Hold teams and their names for lookup
		Map<String, Team> teams = new HashMap<String, Team>();

		//Gets FBS teams from FBS.txt
		generateTeams(teams);
		//Pulls scores (and calculates record) of teams from UCLA page
		getTeamResults(teams);
		//Calculates SoS and SoV for each team using scores and records
		setTeamSoSSoV(teams);
		//Pulls stats from sports-reference
		getTeamStats(teams);
		//Prints results, calculates score and points for each team using rank formula
		printTeamData(teams);
		
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

					//Update team records + MoV
					if (teams.containsKey(home)) {
						teams.get(home).setRecord(homeResult);
						teams.get(home).updateOpponents(away);
						teams.get(home).setMoV(scoreHome, scoreAway);
					}
					if (teams.containsKey(away)) {
						teams.get(away).setRecord(awayResult);
						teams.get(away).updateOpponents(home);
						teams.get(away).setMoV(scoreAway, scoreHome);
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

	//Calculate SoS and SoV for each team
	public static void setTeamSoSSoV(Map<String, Team> teams) {
		//Use iterator to loop through teams
		Iterator it = teams.entrySet().iterator();
		
		//Iterate through teams
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Team currTeam = (Team) pair.getValue();

			//Use opponents (name) and results (W/L)
			ArrayList<String> opponents = currTeam.getOpponents();
			ArrayList<String> results = currTeam.getResults();
			
			//Calc SoS and SoV
			/*
				SoS is record of opponents
				SoV is record of opponents beaten without your win against them

				Team A, B, and C play each other.  A beats B, B beats C, A beats C.
				Records (A: 2-0, B: 1-1, C: 0-2)
				A's SoS is 1-1 + 0-2 = 1-3 = .333
				A's SoV is 1-0 + 0-1 = 1-1 = .500
					Since they beat both B and C you remove a loss from each of B and C
			 */
			int SoSGames = 0, SoSWins = 0, SoVGames = 0, SoVWins = 0;		
			for (int ii = 0; ii < opponents.size(); ii++) {
				if (teams.containsKey(opponents.get(ii))) {
					Team tempTeam = teams.get(opponents.get(ii));
					SoSGames += tempTeam.getWins() + tempTeam.getLosses();
					SoSWins += tempTeam.getWins();
					if (results.get(ii).equals("W")) {
						SoVWins += tempTeam.getWins();
						SoVGames += tempTeam.getWins() + tempTeam.getLosses() - 1;
					}
				}
			}
			double SoS = (double) SoSWins/SoSGames;
			currTeam.setSoS(SoS);
			
			double SoV = (double) SoVWins/SoVGames;
			currTeam.setSoV(SoV);
			if (SoVWins==0) {
				currTeam.setSoV(0);
			}
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

		//TODO: turn into an all stats function and pass the sheet and column to get that stat
		//YF are in column 15 of the offense sheet
		for (int ii = 0; ii < offenseSheet.size(); ii++) {
			if (teams.containsKey(offenseSheet.get(ii).get(1))) {
				Team team = teams.get(offenseSheet.get(ii).get(1));
				team.setYF(Double.parseDouble(offenseSheet.get(ii).get(14)));
			}	
		}
		//YA are in column 15 of defense sheet
		for (int ii = 0; ii < defenseSheet.size(); ii++) {
			if (teams.containsKey(defenseSheet.get(ii).get(1))) {
				Team team = teams.get(defenseSheet.get(ii).get(1));
				team.setYA(Double.parseDouble(defenseSheet.get(ii).get(14)));
			}	
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
		ArrayList<ArrayList<String>> spreadsheet = new ArrayList<ArrayList<String>>();
		
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
				ArrayList<String> currRow = new ArrayList<String>();
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
	public static void printTeamData(Map<String, Team> teams) {
		Iterator it = teams.entrySet().iterator();
		ArrayList<String> names = new ArrayList<String>();
		
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
		
		//Print name and record
		System.out.println("Number\tTeam\t\t\t\tScore\tPoints\tWins\tLosses\tPct\t\tSoS\t\tSoV\t\tAvgMoV\tYF\t\tYA");
		System.out.println("==========================================================================================================");
		for (int ii = 0; ii < names.size(); ii++) {
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

			DecimalFormat dec0 = new DecimalFormat("#0.000");
			//Ranking Score and Points
			System.out.print(dec0.format(currTeam.calculateRank()/leader) + "\t");
			System.out.print(dec0.format(currTeam.calculateRank()) + "\t");
			
			//Wins and Losses
			System.out.print(currTeam.getWins() + "\t\t" + currTeam.getLosses());
			
			DecimalFormat dec1 = new DecimalFormat("#0.0000");
			//Win Pct
			System.out.print("\t\t" + dec1.format(currTeam.getPCT()));
			//SoS and SoV
			System.out.print("\t" + dec1.format(currTeam.getSoS()) + "\t" + dec1.format(currTeam.getSoV()));

			DecimalFormat dec2 = new DecimalFormat("#0.00");
			//MoV
			System.out.print("\t" + dec2.format(currTeam.getAvgMoV()));
			//YF
			System.out.print("\t" + dec2.format(currTeam.getYF()));
			//YA
			System.out.print("\t" + dec2.format(currTeam.getYA()));

			//End line
			System.out.println();
		}
	}
}