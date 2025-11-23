package app;

import data_access.SportsAPIDataAccess;
import entity.User;
import view.MainMenuFrame;

public class Main {
    public static void main(String[] args) {
        SportsAPIDataAccess data = new SportsAPIDataAccess();

        // data.fetchOdds();
        data.readdata();
        User user = new User("BillChen", 1525.00, 15, 8,"123456");
        new MainMenuFrame(user);
        //test_push
    }
}
