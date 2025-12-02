package interfaceadapter.Profile;

import usecase.profile.ProfileInputBoundary;
import usecase.profile.ProfileInputData;

public class ProfileController {

    private final ProfileInputBoundary interactor;

    public ProfileController(ProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadProfile(String username) {
        ProfileInputData data = new ProfileInputData(username);
        interactor.execute(data);
    }
}
