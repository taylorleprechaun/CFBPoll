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
 * 
 *  Gives credit for W/L against FCS but does not count towards SoV or So
 * 
 * 
 * 
 *	Sources
 *	Scores - http://prwolfe.bol.ucla.edu/cfootball/scores.htm
 *	Stats - sports-reference
 */

//TODO: Fix team names in stat sheets

public class Poll {
	public static void main(String[] args) throws InterruptedException {
		Map<String, Team> teams = new HashMap<String, Team>();
		generateTeams(teams);
		getTeamResults(teams);		
		setTeamSoSSoV(teams);
		getTeamStats(teams);
		printTeamData(teams);
		
	}

	public static void generateTeams(Map<String, Team> teams) {
		try {
			BufferedReader teamReader = new BufferedReader(new FileReader("FBS.txt"));
			String str;
			while ((str = teamReader.readLine()) != null) {
				Team newTeam = new Team(str);
				teams.put(str, newTeam);
			}
			
			teamReader.close();
		} catch (IOException e) {
		}
	}
	
	public static void getTeamResults(Map<String, Team> teams) throws InterruptedException {
		try {
			BufferedReader dataReader = new BufferedReader(new FileReader("2018 College Football - 20180926.htm"));
			String str;
			
			while ((str = dataReader.readLine()) != null) {
				//Starts with a date
				if (Character.isDigit(str.charAt(0))) {
					
					//Get teams
					String away = "", home = "", tempScore = "";
					//Away team
					for (int ii = 10; !Character.isSpaceChar(str.charAt(ii)); ii++) {
						away += str.charAt(ii);
						if (Character.isSpaceChar(str.charAt(ii+1)) && !Character.isSpaceChar(str.charAt(ii+2))) {
							away += " ";
							ii++;
						}
					}
					//Home team
					for (int ii = 41; !Character.isSpaceChar(str.charAt(ii)); ii++) {
						home += str.charAt(ii);
						if (Character.isSpaceChar(str.charAt(ii+1)) && !Character.isSpaceChar(str.charAt(ii+2))) {
							home += " ";
							ii++;
						}
					}
					
					//Make sure it contains eligible teams
					if (!teams.containsKey(home) && !teams.containsKey(away)) {
						continue;
					}
					
					//Get scores
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
					
//					System.out.println(away + " - " + scoreAway + " @ " + home + " - " + scoreHome);
					
					//Update team records + MoV
					if (teams.containsKey(home)) {
						teams.get(home).setRecord(homeResult);
						teams.get(home).updateOpponents(away);
						teams.get(home).setMoV(scoreHome, scoreAway);
//						System.out.println("\t\t" + home + " (" + teams.get(home).getWins() + ", " + teams.get(home).getLosses() + ")");
					}
					if (teams.containsKey(away)) {
						teams.get(away).setRecord(awayResult);
						teams.get(away).updateOpponents(home);
						teams.get(away).setMoV(scoreAway, scoreHome);
//						System.out.println("\t\t" + away + " (" + teams.get(away).getWins() + ", " + teams.get(away).getLosses() + ")");
					}
					
					
//					Thread.sleep(2000);

				//End of the data
				} else if (str.equals("</PRE>")) {
					break;
				}
			}
			dataReader.close();
		} catch (IOException e) {
		}
	}

	public static void setTeamSoSSoV(Map<String, Team> teams) {
		Iterator it = teams.entrySet().iterator();
		
		//Iterate through teams
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Team currTeam = (Team) pair.getValue();
			
			ArrayList<String> opponents = currTeam.getOpponents();
			ArrayList<String> results = currTeam.getResults();
			
			//Calc SoS and SoV
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
	
	public static void getTeamStats(Map<String, Team> teams) {
		File offense = new File("C:\\Users\\c46659\\workspace\\CFBPoll\\TeamO.xlsx");
		File defense = new File("C:\\Users\\c46659\\workspace\\CFBPoll\\TeamD.xlsx");
		ArrayList<ArrayList<String>> offenseSheet = getSpreadsheet(offense);
		ArrayList<ArrayList<String>> defenseSheet = getSpreadsheet(defense);
		
		fixNames(offenseSheet);
		fixNames(defenseSheet);
		
		//YF
		for (int ii = 0; ii < offenseSheet.size(); ii++) {
			if (teams.containsKey(offenseSheet.get(ii).get(1))) {
				Team team = teams.get(offenseSheet.get(ii).get(1));
				team.setYF(Double.parseDouble(offenseSheet.get(ii).get(14)));
			}	
		}
		//YA
		for (int ii = 0; ii < defenseSheet.size(); ii++) {
			if (teams.containsKey(defenseSheet.get(ii).get(1))) {
				Team team = teams.get(defenseSheet.get(ii).get(1));
				team.setYA(Double.parseDouble(defenseSheet.get(ii).get(14)));
			}	
		}
	}
	
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
			
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				ArrayList<String> currRow = new ArrayList<String>();
				boolean startRow = true;
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					
					if (startRow && currentCell.getCellTypeEnum()==CellType.BLANK) {
						break;
					} else if (startRow && !Character.isDigit(currentCell.getStringCellValue().charAt(0))) {
						break;
					} else {
						startRow = false;
					}
					
					currRow.add(currentCell.getStringCellValue());
				}
				if (!currRow.isEmpty()) {
					spreadsheet.add(currRow);
				}
			}
			
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		
		return spreadsheet;
	}
	
	public static void printTeamData(Map<String, Team> teams) {
		Iterator it = teams.entrySet().iterator();
		ArrayList<String> names = new ArrayList<String>();
		
		//Get list of team names from map
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			String tempName = ((Team) pair.getValue()).getName();
			names.add(tempName);
		}
		
		//Sort alphabetical
//		names.sort(String::compareToIgnoreCase);
		
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
		
		
		//Print name and record
		System.out.println("Number\tTeam\t\t\tPoints\tWins\tLosses\tPct\tSoS\tSoV\tAvgMoV\tYF\tYA");
		System.out.println("=====================================================================================================");
		for (int ii = 0; ii < names.size(); ii++) {
			Team currTeam = teams.get(names.get(ii));
			
			//Number + Name
			System.out.print((ii+1) + ":\t" + currTeam.getName());
			//Tabbing for name length
			if (currTeam.getName().length() < 8) {
				System.out.print("\t\t\t");
			} else if (currTeam.getName().length() < 16) {
				System.out.print("\t\t");
			} else if (currTeam.getName().length() < 24) {
				System.out.print("\t");
			}

			DecimalFormat dec0 = new DecimalFormat("#0.000");
			//Ranking Points
			System.out.print(dec0.format(currTeam.calculateRank()) + "\t");
			
			//Wins and Losses
			System.out.print(currTeam.getWins() + "\t" + currTeam.getLosses());
			
			DecimalFormat dec1 = new DecimalFormat("#0.0000");
			//Win Pct
			System.out.print("\t" + dec1.format(((double)currTeam.getWins())/(currTeam.getGames())));
			//SoS and SoV
			System.out.print("\t" + dec1.format(currTeam.getSoS()) + "\t" + dec1.format(currTeam.getSoV()));

			DecimalFormat dec2 = new DecimalFormat("#0.0");
			//MoV
			System.out.print("\t" + dec2.format(currTeam.getAvgMoV()));
			//YF
			System.out.print("\t" + dec2.format(currTeam.getYF()));
			//YA
			System.out.print("\t" + dec2.format(currTeam.getYA()));
			
			System.out.println();
		}
	}
}



/*
String textURL = "http://prwolfe.bol.ucla.edu/cfootball/scores.htm";
URL url = new URL(textURL);
URLConnection con = url.openConnection();
InputStream is = con.getInputStream();
BufferedReader br = new BufferedReader(new InputStreamReader(is));
String line = null;
while ((line = br.readLine()) != null) {
	System.out.println(line);
}
*/