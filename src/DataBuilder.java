import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DataBuilder {
	//Gets team names from FBS.txt and stores in a map
	public static Map generateTeams(Map<String, Team> teams, String confFile) {
		try {
			//Read from file
			BufferedReader teamReader = new BufferedReader(new FileReader(".\\rsc\\teams\\" + confFile + ".txt"));
			String str;
			while ((str = teamReader.readLine()) != null) {
				//Create team using the team name and add to map
				String[] info = str.split("\\|");
				//0 is team name, 1 is conference, 2 is division
//				System.out.println(info[0] + "-" + info[1] + "-" + info[2]);
				Team newTeam = new Team(info[0]);
				teams.put(info[0], newTeam);
				newTeam.setConference(info[1], info[2]);
			}

			teamReader.close();
		} catch (IOException e) {
		}

		return teams;
	}

	//Get schedule+results for each team
	public static void getTeamResults(Map<String, Team> teams, String season, String week) {
		season = season.substring(0,4);
		File scoresSheet = new File(".\\rsc\\scores\\" + season + "\\" + season + " - " + week + ".xlsx");
		//Turn spreadsheet into arraylist
		ArrayList<ArrayList<String>> scoreSheet = getScoresSheet(scoresSheet);

		for (int ii = 1; ii < scoreSheet.size(); ii++) {	//Skip first row (headers)
			//Get the names of each winner+loser from score sheet
			Team winner, loser;
			String winnerName = scoreSheet.get(ii).get(4).trim();
			String loserName = scoreSheet.get(ii).get(7).trim();

//			System.out.println(winnerName + " - " + loserName);

			//If both are FBS teams then add the opponent to them and give them a W/L for that week
			if (teams.containsKey(winnerName) && teams.containsKey(loserName)) {
				winner = teams.get(winnerName);
				loser = teams.get(loserName);

				winner.updateOpponents(loserName);
				winner.updateResults(1);
				winner.setRecord(1);

				loser.updateOpponents(winnerName);
				loser.updateResults(0);
				loser.setRecord(0);
			//If FBS beats FCS just give FBS team a win with no opponents
			} else if (teams.containsKey(winnerName) && !teams.containsKey(loserName)) {
				winner = teams.get(winnerName);
				winner.setRecord(1);
			//If FCS beats FBS just give FBS team a loss with no opponents
			} else if (!teams.containsKey(winnerName) && teams.containsKey(loserName)) {
				loser = teams.get(loserName);
				loser.setRecord(0);
			}
		}
	}

	//Get stats for each team
	public static void getTeamStats(Map<String, Team> teams, String season, String week) {
		//Stat files turned into 2D string arrays
		File offense = new File(".\\rsc\\stats\\" + season + "\\" + "TeamO - " + season + " - " + week + ".xlsx");
		File defense = new File(".\\rsc\\stats\\" + season + "\\" + "TeamD - " + season + " - " + week + ".xlsx");
		ArrayList<ArrayList<String>> offenseSheet = getStatSheet(offense);
		ArrayList<ArrayList<String>> defenseSheet = getStatSheet(defense);

		//Find teams who haven't played a game yet
		ArrayList<String> removeList = new ArrayList<>();
		for (Map.Entry<String, Team> entry : teams.entrySet()) {
			Team team = entry.getValue();
			int wins = team.getWins(), losses = team.getLosses();
			if (wins+losses == 0) {
				removeList.add(entry.getKey());
			}
		}

		//Remove them from the teams map because we won't rank them
		for (int ii = 0; ii < removeList.size(); ii++) {
			teams.remove(removeList.get(ii));
		}

		//Get team stats for offense
		for (int ii = 0; ii < offenseSheet.size(); ii++) {
			//Fix name before making their stats
			String name = fixNames(offenseSheet.get(ii).get(1));
			ArrayList<Double> offenseStats = new ArrayList<>();
			Team team = teams.get(name);
//			System.out.println(name);
			for (int jj = 2; jj < offenseSheet.get(jj).size(); jj++) {
				offenseStats.add(Double.parseDouble(offenseSheet.get(ii).get(jj)));
			}
			team.setOffenseStats(new Stats(offenseStats));
		}

		//Get team stats for defense
		for (int ii = 0; ii < defenseSheet.size(); ii++) {
			//Fix name before making their stats
			String name = fixNames(defenseSheet.get(ii).get(1));
			ArrayList<Double> defenseStats = new ArrayList<>();
			Team team = teams.get(name);
			for (int jj = 2; jj < defenseSheet.get(jj).size(); jj++) {
				defenseStats.add(Double.parseDouble(defenseSheet.get(ii).get(jj)));
			}
			team.setDefenseStats(new Stats(defenseStats));
		}
	}

	//Get scores from score spreadsheet before a certain date
	public static ArrayList<ArrayList<String>> getScoresSheet(File file) {
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
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();

					//If cell is a string we want to get rid of the team's rank at the start (if they have one)
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						String temp = currentCell.getStringCellValue();
						if (temp.charAt(0) == '(') {
							for (int ii = 0; ii < temp.length(); ii++) {
								if (temp.charAt(ii) == ')') {
									temp = temp.substring(ii+2);
									//Check if the name needs to be fixed
//									System.out.println(temp);
									temp = fixNames(temp);
									currRow.add(temp);
									break;
								}
							}
							continue;
						}
					}

					//Otherwise just make the cell a string
					currentCell.setCellType(CellType.STRING);

					//And add it to the arraylist (after fixing the name)
					currRow.add(fixNames(currentCell.getStringCellValue()));
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

	//Get stats from stat spreadsheet
	public static ArrayList<ArrayList<String>> getStatSheet(File file) {
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

					//Add to arraylist (after fixing name if we need to)
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

	//Big group of if statements fixing names in the stat tables
	public static String fixNames(String name) {
		String repl = name;
		if (name.contains("(")) {
			repl = name.replace("(", "");
			repl = repl.replace(")", "");
		}
		if (name.contains("Ole Miss")) {
			repl = name.replace("Ole Miss", "Mississippi");
		}
		if (name.contains("UCF")) {
			repl = name.replace("UCF", "Central Florida");
		}
		if (name.contains("Southern California")) {
			repl = "USC";
		}
		if (name.contains("UAB")) {
			repl = name.replace("UAB", "Alabama-Birmingham");
		}
		if (name.equals("Southern Mississippi")) {
			repl = "Southern Miss";
		}
		if (name.contains("Florida International")) {
			repl = name.replace("Florida International", "Florida Int'l");
		}
		if (name.contains("Pitt")) {
			repl = name.replace("Pitt", "Pittsburgh");
		}
		if (name.contains("Ohio") && !name.equals("Ohio State")) {
			repl = name.replace("Ohio", "Ohio U.");
		}
		if (name.equals("Louisiana")) {
			repl = name.replace("Louisiana", "Louisiana-Lafayette");
		}
		if (name.contains("Bowling Green State")) {
			repl = name.replace("Bowling Green State", "Bowling Green");
		}
		if (name.equals("LSU")) {
			repl = name.replace("LSU", "Louisiana State");
		}
		if (name.equals("Southern Methodist")) {
			repl = "SMU";
		}
		if (name.contains("Pitt")) {
			repl = "Pittsburgh";
		}
		if (name.contains("El Paso")) {
			repl = "UTEP";
		}
		if (name.contains("Las Vegas")) {
			repl = "UNLV";
		}
		if (name.contains("Middle Tennessee")) {
			repl = "MTSU";
		}
		if (name.contains("Antonio")) {
			repl = "UTSA";
		}

		return repl;
	}
}
