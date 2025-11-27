package test;

import static org.junit.jupiter.api.Assertions.*;

import entity.User;
import interface_adapter.GameSelect.GameSelectController;
import interface_adapter.GameSelect.GameSelectState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameSelectTest {

    private GameSelectController gsc;
    private GameSelectState state;

    @BeforeEach
    void setUp() {
        User user = new User("Bob",0,0,0,"psps");
        gsc = new GameSelectController();
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
            gsc.execute(state);
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
            gsc.execute(state);
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
            gsc.execute(state);
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








    }
