package usecase.MainMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenuInteractor {

    private final MainMenuOutputBoundary presenter;

    public MainMenuInteractor(MainMenuOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Processes the main menu input data by cleaning, sorting, and removing duplicates
     * from the raw list of menu items.
     * @param inputData  containing menu items
     */
    public void execute(MainMenuInputData inputData) {
        final List<String> items = new ArrayList<>(inputData.getRawMenuItems());
        items.removeIf(str -> str == null || str.trim().isEmpty());
        Collections.sort(items);

        final List<String> unique = new ArrayList<>();
        for (String s : items) {
            if (!unique.contains(s)) {
                unique.add(s);
            }
        }

        final MainMenuOutputData outputData = new MainMenuOutputData(unique, unique.size());
        presenter.present(outputData);
    }
}
