package interface_adapter.GameSelect;

import entity.User;

/**
 * The state for the Login View Model.
 */
public class GameSelectState {
    private User user;
    private String game = "None ";
    private float stakes = 0;

    public GameSelectState(User user){
        this.user = user;
    }

    public String getGame() {
        return game;
    }
    public User getUser() {
        return user;
    }
    public float getStakes() {
        return stakes;
    }

    public void setGame(String game){
        this.game = game;
    }
    public void setStakes(float stakes){
        this.stakes = stakes;
    }
    public void setUser(User user) {this.user = user;}


}
