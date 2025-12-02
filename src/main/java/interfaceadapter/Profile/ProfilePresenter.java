package interfaceadapter.Profile;

import usecase.profile.ProfileOutputBoundary;
import usecase.profile.ProfileOutputData;

public class ProfilePresenter implements ProfileOutputBoundary {

    private ProfileViewModel viewModel;

    @Override
    public void prepareSuccessView(ProfileOutputData data) {
        viewModel = new ProfileViewModel(
                data.getUsername(),
                data.getBalance(),
                data.getBets(),
                data.getGamesPlayed());
    }

    public ProfileViewModel getViewModel() {
        return viewModel;
    }
}
