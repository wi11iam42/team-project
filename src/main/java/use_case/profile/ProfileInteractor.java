package use_case.profile;

import entity.User;

public class ProfileInteractor implements ProfileInputBoundary {

    private final ProfileUserDataAccessInterface userDAO;
    private final ProfileOutputBoundary presenter;

    public ProfileInteractor(ProfileUserDataAccessInterface userDAO,
                             ProfileOutputBoundary presenter) {
        this.userDAO = userDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(ProfileInputData inputData) {
        User user = userDAO.get(inputData.getUsername());

        ProfileOutputData out = new ProfileOutputData(
                user.getUsername(),
                user.getBalance(),
                user.getTotalBets(),
                user.getGamesPlayed());

        presenter.prepareSuccessView(out);
    }
}
