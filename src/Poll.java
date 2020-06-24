import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*	Info

	Final points are from the formula and then divided by the team with the most
		so that #1 has 1.000 and the rest is relative to them

	Sources:
	Scores - http://prwolfe.bol.ucla.edu/cfootball/scores.htm
	Stats - https://www.sports-reference.com/cfb/years/2018-team-defense.html
			https://www.sports-reference.com/cfb/years/2018-team-offense.html
 */

public class Poll {

	public static void main(String[] args) throws InterruptedException, IOException {
		//Stuff to figure out which poll we want to view
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		seasonDir();
		System.out.println("\n\n> Season?");
		String season = br.readLine();
//		String season = "2013";

		weakDir(season);
		System.out.println("\n\n> Week?");
		String week = br.readLine();
//		String week = "NCG";
		System.out.println("\n\n");


		if (week.length() == 1) {
			week = "0" + week;
		}
		String confFile = "FBS-Conf-" + season;

		//Hold teams and their names for lookup
		Map<String, Team> teams = new HashMap<>();

		//Gets FBS teams from that season's team list
		DataBuilder.generateTeams(teams, confFile);
		//Pulls scores (and calculates record) of teams from sports-reference score sheet
		DataBuilder.getTeamResults(teams, season, week);
		//Pulls stats from sports-reference stat sheets
		DataBuilder.getTeamStats(teams, season, week);

		//Calculates SoS using basic and BCS-like formula
		calcTeamWeightedSoS(teams);

		//Print poll
		if (args[0].equals("Full")) {
			PollPrinter.printTeamData(teams, "Full");
		} else if (args[0].equals("T25")) {
			PollPrinter.printTeamData(teams, "T25");
		} else if (args[0].equals("RedditT25")) {
			PollPrinter.printReddit(teams, "25");
		} else if (args[0].equals("RedditFull")) {
			PollPrinter.printReddit(teams, "Full");
		}

		//Print info for an individual team
//		PollPrinter.printTeam(teams, "Florida");
	}

	private static void seasonDir() {
		File folder = new File(".\\rsc\\scores\\");
		File[] listOfFiles = folder.listFiles();
		System.out.println("> Seasons available:");
		for (int ii = 0; ii < listOfFiles.length; ii++) {
			if (listOfFiles[ii].isDirectory()) {
				String entry = listOfFiles[ii].getName();
				System.out.print(entry + "\t");
				if (entry.length() < 4) {
					System.out.print("\t");
				}
			}
			if (ii % 7 == 6) {
				System.out.print("\n");
			}
		}
	}

	private static void weakDir(String season) {
		File folder = new File(".\\rsc\\scores\\" + season);
		File[] listOfFiles = folder.listFiles();
		System.out.println("\n> Weeks available:");
		for (int ii = 0; ii < listOfFiles.length; ii++) {
			if (listOfFiles[ii].isFile()) {
				String entry = listOfFiles[ii].getName();
				entry = entry.substring(7,entry.length()-5);
				System.out.print(entry + "\t");
				if (entry.length() < 4) {
					System.out.print("\t");
				}
			}
			if (ii % 7 == 6) {
				System.out.print("\n");
			}
		}
	}

	//Calculate regular and Weighted SoS for each team
	public static void calcTeamWeightedSoS(Map<String, Team> teams) {
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
}