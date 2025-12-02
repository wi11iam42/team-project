package interfaceadapter.GameSelect;

import view.BlackjackView;
import view.MainMenuFrame.MainMenuFrame;
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
