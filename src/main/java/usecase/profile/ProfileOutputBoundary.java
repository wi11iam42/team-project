package usecase.profile;

public interface ProfileOutputBoundary {
    /**
     * Prepares the view for a successful profile retrieval or update.
     *
     * @param data the output data containing the profile information to be displayed
     */
    void prepareSuccessView(ProfileOutputData data);
}
