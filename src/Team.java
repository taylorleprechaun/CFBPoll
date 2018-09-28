import java.util.ArrayList;

public class Team {
	private String name;
	private int wins, losses, games, MoV, PF, PA;
	private double YF, YA;
	private double SoS, SoV, pct;
	private ArrayList<String> opponents;
	private ArrayList<String> results;
	
	public Team(String name) {
		wins = 0; losses = 0; games = 0;
		MoV = 0; PF = 0; PA = 0;
		YF = 0; YA = 0;
		this.name = name;
		this.opponents = new ArrayList<String>();
		this.results = new ArrayList<String>();
	}
	
	public double calculateRank() {
		double rank = 0.0;
		
		//PCT 		= 20%
		//SoS 		= 20%
		//SoV 		= 10%
		//YF/Game/350	= 12.5%
		//YA/Game/250	= 12.5%
		//PF/Game/24	= 12.5%
		//PA/Game/24	= 12.5%
		
		rank =	((pct*100)*.20
				+ (SoS*100)*.20
				+ (SoV*100)*.10
				+ (MoV/games)*.10
				)/60;
		
		return rank;
	}
	
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
		this.pct = (double)wins/(games);
	}
	public int getWins() {
		return wins;
	}
	public int getLosses() {
		return losses;
	}
	public int getGames() {
		return games;
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
	public void setMoV(int PF, int PA) {
		this.MoV += PF-PA;
		this.PF = PF;
		this.PA = PA;
	}
	public double getAvgMoV() {
		return (double)(MoV)/(games);
	}
	public double getPct() {
		return pct;
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
