package test.Profile;

import entity.User;
import org.junit.jupiter.api.Test;
import usecase.profile.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileInteractorTest {

    private static class FakeUserDAO implements ProfileUserDataAccessInterface {
        private final User fakeUser;
        private String lastRequestedUsername = null;

        FakeUserDAO(User user) {
            this.fakeUser = user;
        }

        @Override
        public User get(String username) {
            this.lastRequestedUsername = username;
            return fakeUser;
        }
    }

    private static class FakePresenter implements ProfileOutputBoundary {
        ProfileOutputData receivedOutput;
        boolean called = false;

        @Override
        public void prepareSuccessView(ProfileOutputData data) {
            this.called = true;
            this.receivedOutput = data;
        }
    }

    @Test
    void testProfileInteractorExecute() {
        User fakeUser = new User("bill", 0.0, 0, 0, "1234");

        FakeUserDAO dao = new FakeUserDAO(fakeUser);
        FakePresenter presenter = new FakePresenter();
        ProfileInteractor interactor = new ProfileInteractor(dao, presenter);

        ProfileInputData input = new ProfileInputData("bill");
        interactor.execute(input);

        assertEquals("bill", dao.lastRequestedUsername);
        assertTrue(presenter.called);

        ProfileOutputData out = presenter.receivedOutput;

        assertNotNull(out);
        assertEquals("bill", out.getUsername());
        assertEquals(0.0, out.getBalance());
        assertEquals(0, out.getBets());
        assertEquals(0, out.getGamesPlayed());
    }
}
