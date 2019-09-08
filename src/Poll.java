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
	public static String date = "20190908";

	public static void main(String[] args) throws InterruptedException {
		//Hold teams and their names for lookup
		Map<String, Team> teams = new HashMap<>();

		//Gets FBS teams from FBS.txt
		DataGrabber.generateTeams(teams);
		//Pulls scores (and calculates record) of teams from UCLA page
		DataGrabber.getTeamResults(teams, date);
		//Pulls stats from sports-reference
		DataGrabber.getTeamStats(teams, date);
		//Calculates SoS using basic and BCS-like formula
		calcTeamBCSSoS(teams);


		//Print poll
		if (args[0].equals("Full")) {
			PollPrinter.printTeamData(teams, "Full");
		} else if (args[0].equals("T25")) {
			PollPrinter.printTeamData(teams, "T25");
		} else if (args[0].equals("Reddit")) {
			PollPrinter.printReddit(teams);

		//Predict scores between two teams
		} else if (args[0].equals("vs")) {
			Predictor predictor = new Predictor(teams.get("Florida"), teams.get("Michigan"));
			predictor.calcPrediction();
			System.out.println("------------------------------");
			predictor.setTeams(teams.get("Texas"), teams.get("Georgia"));
			predictor.calcPrediction();
			System.out.println("------------------------------");
			predictor.setTeams(teams.get("Alabama"), teams.get("Clemson"));
			predictor.calcPrediction();
		}

		//Print info for an individual team
//		printTeam(teams, "Florida");
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
}