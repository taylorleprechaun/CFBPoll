import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

//This class is used to print the poll in the various formats I use
public class PollPrinter {
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
				double team1Rank = teams.get(names.get(jj)).calculateRank();
				double team2Rank = teams.get(names.get(jj+1)).calculateRank();

				//This bit handles early season shenanigans with teams playing only FCS opponents
				int teamWins = teams.get(names.get(jj)).getWins();
				int teamLosses = teams.get(names.get(jj)).getLosses();
				if (teamWins == 1 && teamLosses == 0) {
					String temp = names.get(jj);
					names.set(jj, names.get(jj+1));
					names.set(jj+1, temp);

				} else if (team1Rank < team2Rank) {
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

		double averageSoS = 0, averageWeightedSoS = 0;

		//Header to print out select stats
		System.out.println("Number\tTeam\t\t\t\tScore\tPoints\t\tW\tL\tPct\t\tSoS\t\tWeighted\tAvgMoV\tYF\t\tYA\t\tTO Margin\tYPPO\tYPPD");
		System.out.println("====================================================================================================================================");
		for (int ii = 0; ii < count; ii++) {
			Team currTeam = teams.get(names.get(ii));

			averageWeightedSoS += currTeam.getWeightedSoS();
			averageSoS += currTeam.getSoS();

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
			//YPPO
			System.out.print("\t\t" + dec1.format(currTeam.getOffenseStats().getYardsPerPlay()));
			//YPPD
			System.out.print("\t\t" + dec1.format(currTeam.getDefenseStats().getYardsPerPlay()));
			//End line
			System.out.println();
		}

		DecimalFormat dec4 = new DecimalFormat("#0.0000");
		System.out.println("\n\nMisc info:");
		System.out.println("Average SoS: " + dec4.format(averageSoS/129));
		System.out.println("Average Weighted SoS: " + dec4.format(averageWeightedSoS/129));
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
				double team1Rank = teams.get(names.get(jj)).calculateRank();
				double team2Rank = teams.get(names.get(jj+1)).calculateRank();

				//This bit handles early season shenanigans with teams playing only FCS opponents
				int teamWins = teams.get(names.get(jj)).getWins();
				int teamLosses = teams.get(names.get(jj)).getLosses();
				if (teamWins == 1 && teamLosses == 0) {
					String temp = names.get(jj);
					names.set(jj, names.get(jj+1));
					names.set(jj+1, temp);

				} else if (team1Rank < team2Rank) {
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
