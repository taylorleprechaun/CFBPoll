import java.util.ArrayList;

public class Team {
	private String name;
	private int wins, losses, games, MoV, PF, PA;
	private double YF, YA;
	private double SoS, SoV, PCT;
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

	//Calculates the teams rank in the system
	public double calculateRank() {
		double rank = 0.0;

		//Components of final rank
		double pctEffect, sosvEffect, movEffect, yfEffect, yaEffect;

		//Effect of Win percent (tiered)
		if (PCT == 1.0) {
			pctEffect = 100;
		} else if (PCT >= .75 && PCT < 1.0) {
			pctEffect = 75;
		} else if (PCT >= .5 && PCT < .75) {
			pctEffect = 50;
		} else if (PCT >= .25 && PCT < .5) {
			pctEffect = 25;
		} else {
			pctEffect = 0;
		}

		//Effect of SoS and SoV
		sosvEffect = SoV*SoS*100;

		//Effect of MoV, reward of up to 21
		movEffect = (Math.min(21.0, MoV)/21);

		//Effect of YF (tiered)
		if (YF >= 450) {
			yfEffect = 100;
		} else if (YF >= 400 && YF < 450) {
			yfEffect = 80;
		} else if (YF >= 350 && YF < 400) {
			yfEffect = 60;
		} else if (YF >= 300 && YF < 350) {
			yfEffect = 40;
		} else if (YF >= 250 && YF < 300) {
			yfEffect = 20;
		} else {
			yfEffect = 0;
		}

		//Effect of YA (tiered) with penalties for giving up too many yards/game
		if (YA <= 250) {
			yaEffect = 100;
		} else if (YA <= 300 && YA > 250) {
			yaEffect = 75;
		} else if (YA <= 350 && YA > 300) {
			yaEffect = 50;
		} else if (YA <= 400 && YA > 350) {
			yaEffect = 25;
		} else if (YA <= 450 && YA > 400) {
			yaEffect = 0;
		} else if (YA <= 500 && YA > 450) {
			yaEffect = -25;
		} else {
			yaEffect = -50;
		}

		//Krabby Patty secret formula
		rank = 	pctEffect*.3
				+ sosvEffect*.25
				+ movEffect*.2
				+ yfEffect*.1
				+ yaEffect*.1;

		return rank/95;
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
