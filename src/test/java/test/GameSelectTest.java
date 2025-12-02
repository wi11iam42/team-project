package test;

import static org.junit.jupiter.api.Assertions.*;

import entity.User;
import interfaceadapter.GameSelect.GameSelectPresenter;
import interfaceadapter.GameSelect.GameSelectState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.GameSelect.GameSelectInteractor;
import usecase.GameSelect.GameSelectOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameSelectTest {

    private GameSelectInteractor interactor;
    private GameSelectPresenter presenter;
    private GameSelectState state;

    @BeforeEach
    void setUp() {
        User user = new User("Bob",0,0,0,"psps");
        presenter = new GameSelectPresenter();
        presenter.prepareView(user);
        interactor = new GameSelectInteractor(user, presenter);
        state = new GameSelectState(user);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Close any leftover windows to keep tests isolated
        SwingUtilities.invokeAndWait(() -> {
            for (Window w : Window.getWindows()) {
                if (w.isDisplayable()) w.dispose();
            }
        });
    }

    @Test
    void testBlackJack() throws Exception {
        // Capture existing windows
        Set<Window> before = new HashSet<>(Arrays.asList(Window.getWindows()));

        // Call the void method that should open a frame on the EDT
        SwingUtilities.invokeAndWait(() -> {
            // replace UiClass::openMainWindow with your method under test
            state.setGame("BlackJack");
            interactor.execute(state.getGame(), state.getStakes());
        });

        // Give Swing a moment to show windows (invokeAndWait already helps)
        SwingUtilities.invokeAndWait(() -> { /* no-op to flush EDT */ });

        // Capture windows after
        Set<Window> after = new HashSet<>(Arrays.asList(Window.getWindows()));

        // Find newly added windows
        after.removeAll(before);
        assertFalse(after.isEmpty(), "No new window was opened");

        // Optionally assert one of them is a visible JFrame
        boolean foundVisibleFrame = after.stream()
                .anyMatch(w -> w instanceof JFrame && w.isShowing());
        assertTrue(foundVisibleFrame, "Expected a visible JFrame to be opened");

    }

    @Test
    void testMines() throws Exception {
        // Capture existing windows
        Set<Window> before = new HashSet<>(Arrays.asList(Window.getWindows()));

        // Call the void method that should open a frame on the EDT
        SwingUtilities.invokeAndWait(() -> {
            // replace UiClass::openMainWindow with your method under test
            state.setGame("Mines");
            interactor.execute(state.getGame(), state.getStakes());
        });

        // Give Swing a moment to show windows (invokeAndWait already helps)
        SwingUtilities.invokeAndWait(() -> { /* no-op to flush EDT */ });

        // Capture windows after
        Set<Window> after = new HashSet<>(Arrays.asList(Window.getWindows()));

        // Find newly added windows
        after.removeAll(before);
        assertFalse(after.isEmpty(), "No new window was opened");

        // Optionally assert one of them is a visible JFrame
        boolean foundVisibleFrame = after.stream()
                .anyMatch(w -> w instanceof JFrame && w.isShowing());
        assertTrue(foundVisibleFrame, "Expected a visible JFrame to be opened");

    }

    @Test
    void testBackButton() throws Exception {
        // Capture existing windows
        Set<Window> before = new HashSet<>(Arrays.asList(Window.getWindows()));

        // Call the void method that should open a frame on the EDT
        SwingUtilities.invokeAndWait(() -> {
            // replace UiClass::openMainWindow with your method under test
            state.setGame("MainMenu");
            interactor.execute(state.getGame(), state.getStakes());
        });

        // Give Swing a moment to show windows (invokeAndWait already helps)
        SwingUtilities.invokeAndWait(() -> { /* no-op to flush EDT */ });

        // Capture windows after
        Set<Window> after = new HashSet<>(Arrays.asList(Window.getWindows()));

        // Find newly added windows
        after.removeAll(before);
        assertFalse(after.isEmpty(), "No new window was opened");

        // Optionally assert one of them is a visible JFrame
        boolean foundVisibleFrame = after.stream()
                .anyMatch(w -> w instanceof JFrame && w.isShowing());
        assertTrue(foundVisibleFrame, "Expected a visible JFrame to be opened");

    }

    @Test
    void testPresenter() {
        GameSelectState theState = presenter.getViewModel().getState();
        assertSame(state.getGame(), theState.getGame(), "Expected games to match");
        assertEquals(state.getStakes(), theState.getStakes(), "Expected stakes to match");
        assertSame(state.getUser(), theState.getUser(), "Expected users to match");
    }

    @Test
    void testInteractor() {
        GameSelectOutputBoundary thePresenter = interactor.getPresenter();
        User theUser = interactor.getUser();
        assertSame(thePresenter, presenter, "Expected presenters to match");
        assertSame(state.getUser(), theUser, "Expected users to match");
    }

    }
