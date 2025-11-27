package data_access;

import entity.Sportbet;
import entity.User;
import entity.UserFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SportbetFileDataAccessObject {

    private static final String HEADER =
            "username,betId,sport,team1,team1price,team2,team2price," +
                    "selection,stake,payout,status,betwon";
    private static final FileUserDataAccessObject userDAO =
            new FileUserDataAccessObject("users.csv", new UserFactory());

    private final File csvFile;

    public SportbetFileDataAccessObject(String csvPath) {
        this.csvFile = new File(csvPath);

        if (!csvFile.exists() || csvFile.length() == 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                writer.write(HEADER);
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void saveBet(String username, Sportbet bet) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true))) {
            String line = String.format("%s,%s,%s,%s,%f,%s,%f,%s,%f,%f,%s,%b",
                    username,
                    bet.getId(),
                    bet.getSport(),
                    bet.getTeam1(),
                    bet.getTeam1price(),
                    bet.getTeam2(),
                    bet.getTeam2price(),
                    bet.getSelection(),
                    bet.getStake(),
                    bet.getPayout(),
                    bet.getStatus(),
                    bet.getBetwon());
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Sportbet> loadBetsForUser(String username) {
        ArrayList<Sportbet> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();
            String row;

            while ((row = reader.readLine()) != null) {
                String[] col = row.split(",");

                if (col.length < 12) {
                    continue;
                }

                if (!col[0].equals(username)) {
                    continue;
                }
                User user = userDAO.get(col[0]);
                String betId = col[1];
                String sport = col[2];
                String team1 = col[3];
                double team1price = Double.parseDouble(col[4]);
                String team2 = col[5];
                double team2price = Double.parseDouble(col[6]);

                Sportbet bet = new Sportbet(betId, sport, team1, team2,
                        team1price, team2price, col[10]);

                bet.setSelection(col[7]);
                bet.setStatus(col[10]);
                bet.setUser(user);
                bet.setStake(Double.parseDouble(col[8]));
                bet.setPayout(Double.parseDouble(col[9]));
                bet.setBetwon(Boolean.parseBoolean(col[11]));

                result.add(bet);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
