import java.util.ArrayList;

//This class is the Team object which contains all of a team's information
public class Team {
	private String name, conference, division;
	private int wins, losses, games;
	private double SoS, oppSoS, PCT;
	private ArrayList<String> opponents;
	private ArrayList<String> results;
	private Rank teamRank;
	private Stats offenseStats;
	private Stats defenseStats;

	public Team(String name) {
		wins = 0; losses = 0; games = 0;
		this.name = name;
		this.opponents = new ArrayList<>();
		this.results = new ArrayList<>();
	}

	//Calculates the teams rank in the system
	public double calculateRank() {
		this.teamRank = new Rank(PCT, offenseStats, defenseStats, SoS, oppSoS, this);
		return teamRank.calculateRank();
	}

	//Getters and Setters
	public String getName() {
		return name;
	}
	public void setRecord(int result) {
		//Update record
		if (result==1) {
			this.wins++;
		} else if (result==0) {
			this.losses++;
		}
		this.games++;
		this.PCT = (double)wins/(games);
	}
	public int getWins() {
		return wins;
	}
	public int getLosses() {
		return losses;
	}
	public double getSoS() {
		return SoS;
	}
	public void setSoS(double soS) {
		this.SoS = soS;
	}
	public void updateOpponents(String opponent) {
		opponents.add(opponent);
	}
	public ArrayList<String> getOpponents() {
		return opponents;
	}
	public void updateResults(int result) {
		//Update record
		if (result==1) {
			results.add("W");
		} else if (result==0) {
			results.add("L");
		}
	}
	public ArrayList<String> getResults() {
		return results;
	}
	public double getPCT() {
		return PCT;
	}
	public double getWeightedSoS() {
		return Rank.calculateWeightedSoS(SoS, oppSoS);
	}
	public void setOppSoS(double oppSoS) {
		this.oppSoS = oppSoS;
	}
	public void setOffenseStats(Stats offenseStats) {
		this.offenseStats = offenseStats;
	}
	public Stats getOffenseStats() {
		return offenseStats;
	}
	public void setDefenseStats(Stats defenseStats) {
		this.defenseStats = defenseStats;
	}
	public Stats getDefenseStats() {
		return defenseStats;
	}
	public void setConference(String conference, String division) {
		this.conference = conference;
		this.division = division;
	}
	public String getConference() {
		return conference;
	}
	public String getDivision() {
		return  division;
	}

}
