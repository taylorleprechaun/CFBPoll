import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

//This class is to hold the code related to pulling in data from files
public class DataGrabber {

	//Gets team names from FBS.txt and stores in a map
	public static Map generateTeams(Map<String, Team> teams) {
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

		return teams;
	}

	//Gets team score and updates record + opponents
	public static void getTeamResults(Map<String, Team> teams, String date) throws InterruptedException {
		try {
			//Read from UCLA page html file
			BufferedReader dataReader = new BufferedReader(new FileReader(".\\rsc\\scores\\2019 College Football - " + date + ".html"));
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

					//Handle SJSU name properly because the accent messes with things now for some reason
					if (home.contains("San Jos")) {
						home = "San Jose St";
					} else if (away.contains("San Jos")) {
						away = "San Jose St";
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

	//Pull stats for each team
	public static void getTeamStats(Map<String, Team> teams, String date) {
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
					if (startRow && currentCell.getCellTypeEnum()== CellType.BLANK) {
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
}
