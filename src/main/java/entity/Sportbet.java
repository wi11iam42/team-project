package entity;

/*
 * This file is the Sportbet Object
 * which we will use in the sports bet frame.
*/

public class Sportbet { private String id;
    private String sport;
    private String team1;
    private double team1price;
    private String team2;
    private double team2price;
    private String selection;
    private String status;
    private double stake;
    private double payout;
    private boolean betwon;
    private User user;

    public Sportbet(String id, String sport, String team1, String team2,
                    double team1price, double team2price, String status) {
        this.id = id;
        this.sport = sport;
        this.team1 = team1;
        this.team2 = team2;
        this.team1price = team1price;
        this.team2price = team2price;
        this.status = status;

        this.stake = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSport() {
        return sport;
    }
    // public void setSport(String sport) { this.sport = sport; }

    public String getTeam1() {
        return team1;
    }
    // public void setTeam1(String team1) { this.team1 = team1; }

    public String getTeam2() {
        return team2;
    }
    // public void setTeam2(String team2) { this.team2 = team2; }

    public double getTeam1price() {
        return team1price;
    }
    // public void setTeam1price(double team1price) { this.team1price = team1price; }

    public double getTeam2price() {
        return team2price;
    }
    // public void setTeam2price(double team2price) { this.team2price = team2price; }

    public boolean getBetwon() {
        return betwon;
    }

    public void setBetwon(boolean won) {
        this.betwon = won;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSelection() {
        return this.selection;
    }

    /**
     * Sets the selected team for this bet.
     * The selection is only updated if the provided team matches
     * one of the teams in the bet.
     *
     * @param team the team being selected
     */
    public void setSelection(String team) {
        if (!team.equals(this.team1) && !team.equals(this.team2)) {
            return;
        }
        else {
            this.selection = team;
        }
    }

    public double getStake() {
        return stake;
    }

    public void setStake(double stake) {
        this.stake = stake;
    }

    public double getPayout() {
        return this.payout;
    }

    public void setPayout(double payout) {
        this.payout = payout;
    }

    @Override
    public String toString() {
        final String res = "Sport: " + this.getSport() + ", Teams: " + this.getTeam1()
                + " vs " + this.getTeam2() + ", Odds: " + this.getTeam1price() + "/" + this.getTeam2price()
                + "selected team: " + this.getSelection() + ", Stake: " + this.getStake();
        String result = "";
        String spacer = "";
        for (int i = 0; i <= 170 - res.length(); i++) {
            spacer += " ";
        }
        result += "Sport: " + this.getSport() + ", Teams: " + this.getTeam1()
                + " vs " + this.getTeam2() + ", Odds: " + this.getTeam1price() + "/" + this.getTeam2price()
                + spacer + "\n Selected team: " + this.getSelection() + ",   Stake: " + this.getStake()
                + ",   Payout: " + this.getPayout() + " Status: " + this.getStatus();
        if (this.getStatus().equals("completed")) {
            if (this.betwon) {
                result += ",  Result: +" + this.getPayout();
            }
            else {
                result += ",  Result: -" + this.getStake();
            }
        }
        return result;
    }
}
