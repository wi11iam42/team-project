package use_case.logout;

public interface LogoutUserDataAccessInterface {
    String getCurrentUsername();

    void setCurrentUsername(String username);
}
