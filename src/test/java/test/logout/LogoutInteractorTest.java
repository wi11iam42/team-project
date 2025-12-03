package test.logout;

import dataaccess.InMemoryUserDataAccess;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import usecase.logout.*;

import static org.junit.jupiter.api.Assertions.*;

class LogoutInteractorTest {

    @Test
    void successTest() {
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess();
        UserFactory userFactory = new UserFactory();
        User user = userFactory.create("William", 0, 0 ,0, "1234");
        userDataAccess.save(user);
        userDataAccess.setCurrentUsername("William");

        LogoutOutputBoundary successPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData data) {
                assertEquals("William", data.getUsername());

                assertNull(userDataAccess.getCurrentUsername());
            }
        };

        LogoutInputBoundary interactor =
                new LogoutInteractor(userDataAccess, successPresenter);
        interactor.execute();

        assertNull(userDataAccess.getCurrentUsername());
    }
}
