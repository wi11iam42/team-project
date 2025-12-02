package usecase.profile;

public interface ProfileInputBoundary {

    /**
     * Executes the profile use case with the provided input data.
     *
     * @param inputData the data for a user's profile information
     */
    void execute(ProfileInputData inputData);
}
