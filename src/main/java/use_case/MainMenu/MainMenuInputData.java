package use_case.MainMenu;

import java.util.List;

public class MainMenuInputData {
    private final List<String> rawMenuItems;

    public MainMenuInputData(List<String> rawMenuItems) {
        this.rawMenuItems = rawMenuItems;
    }

    public List<String> getRawMenuItems() {
        return rawMenuItems;
    }
}
