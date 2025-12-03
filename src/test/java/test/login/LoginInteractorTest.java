package test.login;

import dataaccess.InMemoryUserDataAccess;
import entity.UserFactory;
import entity.User;
import org.junit.jupiter.api.Test;
import usecase.login.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        LoginInputData inputData = new LoginInputData("William", "1234");
        LoginUserDataAccessInterface userDataAccess = new InMemoryUserDataAccess();

        User user = UserFactory.create("William", 0, 0, 0, "1234");
        userDataAccess.save(user);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData loginOutputData) {
                assertEquals("William", loginOutputData.getUsername());
                assertEquals("William", userDataAccess.getCurrentUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignupView() {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userDataAccess, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() {
        LoginInputData inputData = new LoginInputData("William", "wrong");
        LoginUserDataAccessInterface userDataAccess = new InMemoryUserDataAccess();

        User user = UserFactory.create("William", 0, 0 ,0, "1234");
        userDataAccess.save(user);

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData loginOutputData) {
                fail("Use case success is unexpected");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"William\".", error);
            }

            @Override
            public void switchToSignupView() {
                fail("Use case failure is unexpected.");
            }

        };

            LoginInputBoundary interactor = new LoginInteractor(userDataAccess, failurePresenter);
            interactor.execute(inputData);

        }

            @Test
            void failureUserDoesNotExist() {
                LoginInputData inputData = new LoginInputData("William", "1234");
                InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess();

                LoginOutputBoundary loginPresenter = new LoginOutputBoundary() {
                    @Override
                    public void prepareSuccessView(LoginOutputData loginOutputData) {
                        fail("Use case success is unexpected");
                    }

                    @Override
                    public void prepareFailView(String error) {
                        assertEquals("William: Account does not exist.", error);
                    }

                    @Override
                    public void switchToSignupView() {
                        fail("Use case failure is unexpected.");
                    }
                };
                LoginInputBoundary interactor = new LoginInteractor(userDataAccess, loginPresenter);
                interactor.execute(inputData);
            }
            @Test
            void switchToSignupView() {
                LoginUserDataAccessInterface userDataAccess = new InMemoryUserDataAccess();

                final boolean[] called = {false};

                LoginOutputBoundary switchPresenter = new LoginOutputBoundary() {
                    @Override
                    public void prepareSuccessView(LoginOutputData loginOutputData) {
                    }

                    @Override
                    public void prepareFailView(String error) {
                    }

                    @Override
                    public void switchToSignupView() {
                        called[0] = true;
                    }
                };
                LoginInputBoundary interactor = new LoginInteractor(userDataAccess, switchPresenter);
                interactor.switchToSignupView();

                assertTrue(called[0]);
            }
}
