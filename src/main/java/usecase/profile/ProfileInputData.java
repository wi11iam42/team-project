package usecase.profile;

public class ProfileInputData {
    private final String username;

    public ProfileInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
