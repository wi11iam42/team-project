package usecase.MainMenu;

import java.util.List;

public class MainMenuOutputData {

    private final List<String> processedItems;
    private final int count;

    public MainMenuOutputData(List<String> processedItems, int count) {
        this.processedItems = processedItems;
        this.count = count;
    }

    public List<String> getProcessedItems() {
        return processedItems;
    }

    public int getCount() {
        return count;
    }
}
