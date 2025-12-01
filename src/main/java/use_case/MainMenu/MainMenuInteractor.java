package use_case.MainMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenuInteractor {

    private final MainMenuOutputBoundary presenter;

    public MainMenuInteractor(MainMenuOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void execute(MainMenuInputData inputData) {
        List<String> items = new ArrayList<>(inputData.getRawMenuItems());
        items.removeIf(s -> s == null || s.trim().isEmpty());
        Collections.sort(items);

        List<String> unique = new ArrayList<>();
        for (String s : items) {
            if (!unique.contains(s)) {
                unique.add(s);
            }
        }

        MainMenuOutputData outputData = new MainMenuOutputData(unique, unique.size());
        presenter.present(outputData);
    }
}
