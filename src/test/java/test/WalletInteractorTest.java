package test;

import dataaccess.UserDataAccessInterface;
import entity.User;
import org.junit.Test;
import usecase.Wallet.WalletInputData;
import usecase.Wallet.WalletInteractor;
import usecase.Wallet.WalletOutputBoundary;
import usecase.Wallet.WalletOutputData;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WalletInteractorTest {

    private static class FakeUserDAO implements UserDataAccessInterface {
        private final Map<String, User> users = new HashMap<>();
        private User lastSaved = null;
        private int saveCount = 0;

        void add(User u) {
            users.put(u.getUsername(), u);
        }

        @Override
        public User get(String username) {
            return users.get(username);
        }

        @Override
        public boolean existsByName(String username) {
            return users.containsKey(username);
        }

        @Override
        public void save(User user) {
            lastSaved = user;
            saveCount++;
        }
    }

    private static class FakePresenter implements WalletOutputBoundary {
        public WalletOutputData success;
        public String fail;

        @Override
        public void prepareSuccessView(WalletOutputData outputData) {
            this.success = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.fail = errorMessage;
        }
    }

    private User newUser(String name, double balance) {
        return new User(name, balance, 0, 0, "hash");
    }

    // ============================================
    //                  TESTS
    // ============================================

    @Test
    public void deposit_valid_amount_success() {
        FakeUserDAO dao = new FakeUserDAO();
        FakePresenter presenter = new FakePresenter();
        WalletInteractor interactor = new WalletInteractor(dao, presenter);

        User u = newUser("alice", 100);
        dao.add(u);

        WalletInputData input = new WalletInputData("alice", "50");
        interactor.deposit(input);

        assertNotNull(presenter.success);
        assertEquals("alice", presenter.success.getUsername());
        assertEquals(150.0, presenter.success.getBalance(), 1e-6);
        assertEquals("Deposited 50.0", presenter.success.getMessage());

        assertEquals(150.0, u.getBalance(), 1e-6);
        assertEquals(1, dao.saveCount);
    }

    @Test
    public void withdraw_valid_success() {
        FakeUserDAO dao = new FakeUserDAO();
        FakePresenter presenter = new FakePresenter();
        WalletInteractor interactor = new WalletInteractor(dao, presenter);

        User u = newUser("bob", 200);
        dao.add(u);

        WalletInputData input = new WalletInputData("bob", "80");
        interactor.withdraw(input);

        assertNotNull(presenter.success);
        assertEquals("bob", presenter.success.getUsername());
        assertEquals(120.0, presenter.success.getBalance(), 1e-6);
        assertEquals("Withdrew 80.0", presenter.success.getMessage());

        assertEquals(120.0, u.getBalance(), 1e-6);
        assertEquals(1, dao.saveCount);
    }

    @Test
    public void withdraw_insufficient_funds() {
        FakeUserDAO dao = new FakeUserDAO();
        FakePresenter presenter = new FakePresenter();
        WalletInteractor interactor = new WalletInteractor(dao, presenter);

        User u = newUser("eve", 30);
        dao.add(u);

        WalletInputData input = new WalletInputData("eve", "100");
        interactor.withdraw(input);

        assertNull(presenter.success);
        assertEquals("Insufficient funds!", presenter.fail);
        assertEquals(30.0, u.getBalance(), 1e-6);
        assertEquals(0, dao.saveCount);
    }

    @Test
    public void invalid_number_input() {
        FakeUserDAO dao = new FakeUserDAO();
        FakePresenter presenter = new FakePresenter();
        WalletInteractor interactor = new WalletInteractor(dao, presenter);

        WalletInputData input = new WalletInputData("alice", "abc");
        interactor.deposit(input);

        assertNull(presenter.success);
        assertEquals("Invalid number.", presenter.fail);
        assertEquals(0, dao.saveCount);
    }

    @Test
    public void non_positive_amount() {
        FakeUserDAO dao = new FakeUserDAO();
        FakePresenter presenter = new FakePresenter();
        WalletInteractor interactor = new WalletInteractor(dao, presenter);

        WalletInputData zero = new WalletInputData("alice", "0");
        interactor.deposit(zero);

        assertEquals("Please enter a positive amount.", presenter.fail);
        assertEquals(0, dao.saveCount);

        presenter.fail = null;

        WalletInputData neg = new WalletInputData("alice", "-5");
        interactor.deposit(neg);

        assertEquals("Please enter a positive amount.", presenter.fail);
        assertEquals(0, dao.saveCount);
    }
}
