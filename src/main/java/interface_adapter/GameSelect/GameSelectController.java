package interface_adapter.GameSelect;

import entity.User;
import view.BlackjackView;
import view.MainMenuFrame;
import view.MinesView;

/**
 * The controller for the Login Use Case.
 */
public class GameSelectController {

    public void execute(GameSelectState state) {
        if(state.getGame().equals("BlackJack")){
            new BlackjackView(state.getUser());
        }
        if(state.getGame().equals("Mines")){
            new MinesView(state.getUser());
        }
        if(state.getGame().equals("MainMenu")){
            new MainMenuFrame(state.getUser());
        }
    }
}
