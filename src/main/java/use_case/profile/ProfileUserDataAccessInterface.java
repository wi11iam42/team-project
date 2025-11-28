package use_case.profile;

import entity.User;

public interface ProfileUserDataAccessInterface {
    User get(String username);
}

