import java.util.ArrayList;

public class Stats {
    private double games, points;
    private double completions, passAttempts, percent, passYards, passTD;
    private double rushAttempts, rushYards, rushAvg, rushTD;
    private double plays, totalYards, yardsPerPlay;
    private double passFirsts, rushFirsts, penaltyFirsts, totalFirsts;
    private double penalties, penaltyYards;
    private double fumbles, ints, totalTO;

    public Stats (ArrayList<Double> statList) {
        this.games = statList.get(0);
        this.points = statList.get(1);
        this.completions = statList.get(2);
        this.passAttempts = statList.get(3);
        this.percent = statList.get(4);
        this.passYards = statList.get(5);
        this.passTD = statList.get(6);
        this.rushAttempts = statList.get(7);
        this.rushYards = statList.get(8);
        this.rushAvg = statList.get(9);
        this.rushTD = statList.get(10);
        this.plays = statList.get(11);
        this.totalYards = statList.get(12);
        this.yardsPerPlay = statList.get(13);
        this.passFirsts = statList.get(14);
        this.rushFirsts = statList.get(15);
        this.penaltyFirsts = statList.get(16);
        this.totalFirsts = statList.get(17);
        this.penalties = statList.get(18);
        this.penaltyYards = statList.get(19);
        this.fumbles = statList.get(20);
        this.ints = statList.get(21);
        this.totalTO = statList.get(22);
    }

    public double getGames() {
        return games;
    }

    public void setGames(double games) {
        this.games = games;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getCompletions() {
        return completions;
    }

    public void setCompletions(double completions) {
        this.completions = completions;
    }

    public double getPassAttempts() {
        return passAttempts;
    }

    public void setPassAttempts(double passAttempts) {
        this.passAttempts = passAttempts;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getPassYards() {
        return passYards;
    }

    public void setPassYards(double passYards) {
        this.passYards = passYards;
    }

    public double getPassTD() {
        return passTD;
    }

    public void setPassTD(double passTD) {
        this.passTD = passTD;
    }

    public double getRushAttempts() {
        return rushAttempts;
    }

    public void setRushAttempts(double rushAttempts) {
        this.rushAttempts = rushAttempts;
    }

    public double getRushYards() {
        return rushYards;
    }

    public void setRushYards(double rushYards) {
        this.rushYards = rushYards;
    }

    public double getRushAvg() {
        return rushAvg;
    }

    public void setRushAvg(double rushAvg) {
        this.rushAvg = rushAvg;
    }

    public double getRushTD() {
        return rushTD;
    }

    public void setRushTD(double rushTD) {
        this.rushTD = rushTD;
    }

    public double getPlays() {
        return plays;
    }

    public void setPlays(double plays) {
        this.plays = plays;
    }

    public double getTotalYards() {
        return totalYards;
    }

    public void setTotalYards(double totalYards) {
        this.totalYards = totalYards;
    }

    public double getYardsPerPlay() {
        return yardsPerPlay;
    }

    public void setYardsPerPlay(double yardsPerPlay) {
        this.yardsPerPlay = yardsPerPlay;
    }

    public double getPassFirsts() {
        return passFirsts;
    }

    public void setPassFirsts(double passFirsts) {
        this.passFirsts = passFirsts;
    }

    public double getRushFirsts() {
        return rushFirsts;
    }

    public void setRushFirsts(double rushFirsts) {
        this.rushFirsts = rushFirsts;
    }

    public double getPenaltyFirsts() {
        return penaltyFirsts;
    }

    public void setPenaltyFirsts(double penaltyFirsts) {
        this.penaltyFirsts = penaltyFirsts;
    }

    public double getTotalFirsts() {
        return totalFirsts;
    }

    public void setTotalFirsts(double totalFirsts) {
        this.totalFirsts = totalFirsts;
    }

    public double getPenalties() {
        return penalties;
    }

    public void setPenalties(double penalties) {
        this.penalties = penalties;
    }

    public double getPenaltyYards() {
        return penaltyYards;
    }

    public void setPenaltyYards(double penaltyYards) {
        this.penaltyYards = penaltyYards;
    }

    public double getFumbles() {
        return fumbles;
    }

    public void setFumbles(double fumbles) {
        this.fumbles = fumbles;
    }

    public double getInts() {
        return ints;
    }

    public void setInts(double ints) {
        this.ints = ints;
    }

    public double getTotalTO() {
        return totalTO;
    }

    public void setTotalTO(double totalTO) {
        this.totalTO = totalTO;
    }
}
