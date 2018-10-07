import java.util.ArrayList;

public class Team {
	private String name;
	private int wins, losses, games, MoV, PF, PA;
	private double YF, YA;
	private double SoS, SoV, PCT;
	private ArrayList<String> opponents;
	private ArrayList<String> results;
	private ArrayList<Integer> margins;
	private Rank teamRank;
	
	public Team(String name) {
		wins = 0; losses = 0; games = 0;
		MoV = 0; PF = 0; PA = 0;
		YF = 0; YA = 0;
		this.name = name;
		this.teamRank = new Rank();
		this.opponents = new ArrayList<String>();
		this.results = new ArrayList<String>();
		this.margins = new ArrayList<Integer>();
	}

	//Calculates the teams rank in the system
	public double calculateRank() {
		return teamRank.calculateRank(PCT, YF, YA, SoS, SoV, MoV, PF, PA);
	}

	//Getters and Setters
	public String getName() {
		return name;
	}
	public void setRecord(int result) {
		//Update record
		if (result==1) {
			this.wins++;
			this.results.add("W");
		} else if (result==0) {
			this.losses++;
			this.results.add("L");
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
	public double getSoV() {
		return SoV;
	}
	public void setSoV(double soV) {
		this.SoV = soV;
	}
	public void updateOpponents(String opponent) {
		opponents.add(opponent);
	}
	public ArrayList<String> getOpponents() {
		return opponents;
	}
	public ArrayList<String> getResults() {
		return results;
	}
	public ArrayList<Integer> getMargins() {
		return margins;
	}
	public void setMoV(int PF, int PA) {
		this.margins.add(PF-PA);
		this.MoV += PF-PA;
		this.PF = PF;
		this.PA = PA;
	}
	public double getAvgMoV() {
		return (double)(MoV)/(games);
	}
	public double getPCT() {
		return PCT;
	}
	public void setYF(double YF) {
		this.YF = YF;
	}
	public double getYF() {
		return YF;
	}
	public void setYA(double YA) {
		this.YA = YA;
	}
	public double getYA() {
		return YA;
	}
}
