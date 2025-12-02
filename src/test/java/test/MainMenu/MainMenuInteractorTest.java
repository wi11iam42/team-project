package test.MainMenu;

import org.junit.jupiter.api.Test;
import usecase.MainMenu.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainMenuInteractorTest {

    private static class FakePresenter implements MainMenuOutputBoundary {
        MainMenuOutputData received;
        boolean called = false;

        @Override
        public void present(MainMenuOutputData data) {
            this.received = data;
            this.called = true;
        }
    }

    @Test
    public void testExecuteNormal() {
        FakePresenter presenter = new FakePresenter();
        MainMenuInteractor interactor = new MainMenuInteractor(presenter);

        List<String> items = Arrays.asList("Wallet", "Profile", "Profile", "SportBet");
        MainMenuInputData input = new MainMenuInputData(items);

        interactor.execute(input);

        assertTrue(presenter.called);
        assertEquals(Arrays.asList("Profile", "SportBet", "Wallet"), presenter.received.getProcessedItems());
        assertEquals(3, presenter.received.getCount());
    }

    @Test
    public void testExecuteWithInvalidValues() {
        FakePresenter presenter = new FakePresenter();
        MainMenuInteractor interactor = new MainMenuInteractor(presenter);

        List<String> items = Arrays.asList("", " ", null);
        MainMenuInputData input = new MainMenuInputData(items);

        interactor.execute(input);

        assertTrue(presenter.called);
        assertEquals(0, presenter.received.getProcessedItems().size());
        assertEquals(0, presenter.received.getCount());
    }
}
