package data_access;

import entity.User;
import entity.UserFactory;
import use_case.login.LoginUserDataAccessInterface;
import use_case.profile.ProfileUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class FileUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ProfileUserDataAccessInterface,
        UserDataAccessInterface,
        LogoutUserDataAccessInterface{
// Add back in change password and logout?

    private static final String HEADER = "username,password,balance,totalBets,gamesPlayed";

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();

    private String currentUsername;

    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) {

        csvFile = new File(csvPath);
        headers.put("username", 0);
        headers.put("password", 1);
        headers.put("balance", 2);
        headers.put("totalBets", 3);
        headers.put("gamesPlayed", 4);

        if (csvFile.length() == 0) {
            save();
        }
        else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();

                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("header should be%n: %s%n but was:%n%s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String username = String.valueOf(col[headers.get("username")]);
                    final String password = String.valueOf(col[headers.get("password")]);
                    double balance = 0;
                    int totalBets = 0;
                    int gamesPlayed = 0;

                    if (col.length >= 3) {
                        balance = Double.parseDouble(col[headers.get("balance")]);
                        totalBets = Integer.parseInt(col[headers.get("totalBets")]);
                        gamesPlayed = Integer.parseInt(col[headers.get("gamesPlayed")]);
                    }



                    final User user = userFactory.create(username, balance, totalBets, gamesPlayed, password);
                    accounts.put(username, user);
                }
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void save() {
        final BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user: accounts.values()) {
                final String line = String.format("%s,%s,%f,%d,%d",
                        user.getUsername(),
                        user.getPasswordHash(),
                        user.getBalance(),
                        user.getBets(),
                        user.getGamesPlayed());
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        accounts.put(user.getUsername(), user);
        this.save();
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

//    @Override
//    public void changePassword(User user) {
//        // Replace the User object in the map
//        accounts.put(user.getName(), user);
//        save();
//    }
}
