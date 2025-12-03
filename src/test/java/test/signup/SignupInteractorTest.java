package test.signup;

import dataaccess.InMemoryUserDataAccess;
import entity.UserFactory;
import entity.User;
import org.junit.jupiter.api.Test;
import usecase.signup.*;

import static org.junit.jupiter.api.Assertions.*;


class SignupInteractorTest {

    @Test
    void sucessTest() {
        SignupInputData inputData = new SignupInputData("William", "1234", "1234");
        SignupUserDataAccessInterface userDataAccess = new InMemoryUserDataAccess();

        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                assertEquals("William", outputData.getUsername());
                assertTrue(userDataAccess.existsByName("William"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
            @Override
            public void switchToLoginView() {

            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userDataAccess, successPresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureUserExists() {
        SignupUserDataAccessInterface userDataAccess = new InMemoryUserDataAccess();
        UserFactory userFactory = new UserFactory();

        User existingUser = userFactory.create("William", 0, 0, 0, "1234");
        userDataAccess.save(existingUser);

        SignupInputData inputData = new SignupInputData("William", "1234", "1234");

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("User already exists.", error);
            }

            @Override
            public void switchToLoginView() {}
        };
        SignupInputBoundary interactor = new SignupInteractor(userDataAccess, failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatch() {
        SignupUserDataAccessInterface userDataAccess = new InMemoryUserDataAccess();

        SignupInputData inputData = new SignupInputData("William", "1234", "6789");

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Passwords don't match.", error);
            }

            @Override
            public void switchToLoginView() {}
        };

        SignupInputBoundary interactor = new SignupInteractor(userDataAccess, failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyPassword() {
        SignupUserDataAccessInterface userDataAccess = new InMemoryUserDataAccess();

        SignupInputData inputData = new SignupInputData("William", "", "");

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("New password cannot be empty", error);
            }

            @Override
            public void switchToLoginView() {}
        };

        SignupInputBoundary interactor = new SignupInteractor(userDataAccess, failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyUsername() {
        SignupUserDataAccessInterface userDataAccess = new InMemoryUserDataAccess();

        SignupInputData inputData = new SignupInputData("", "1234", "1234");

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Username cannot be empty", error);
            }

            @Override
            public void switchToLoginView() {}
        };

        SignupInputBoundary interactor = new SignupInteractor(userDataAccess, failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void switchToLoginView() {
        SignupUserDataAccessInterface userDataAccess = new InMemoryUserDataAccess();
        UserFactory userFactory = new UserFactory();
        final boolean[] called = {false};

        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {

            @Override
            public void prepareSuccessView(SignupOutputData outputData) {}

            @Override
            public void prepareFailView(String error) {}

            @Override
            public void switchToLoginView() {
                called[0] = true;
            }
        };
        SignupInputBoundary interactor = new SignupInteractor(userDataAccess, successPresenter, userFactory);
        interactor.switchToLoginView();

        assertTrue(called[0]);

    }
}
