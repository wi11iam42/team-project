package data_access;

import entity.Sportbet;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SportsAPIDataAccess {
    private OkHttpClient client = new OkHttpClient();
    private String apiKey = "0c5ba9bf08780b2ed18e605b84f07565";
    public static ArrayList<Sportbet> allbets = new ArrayList<>();

    public void fetchOdds() {
        String sport = "icehockey_nhl";
        String url = "https://api.the-odds-api.com/v4/sports/" + sport +
                "/odds?regions=us&markets=h2h&apiKey=" + apiKey;
        System.out.println(url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Request failed: " + response.code());
                return;
            }

            String jsonData = response.body().string();

            try (FileWriter writer = new FileWriter("odds.txt",true)) {
                writer.write(jsonData);
            }

            System.out.println("Data saved to odds.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readdata(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("odds.txt"));
            String line;
            while ((line = br.readLine()) != null){
                String[] splits = line.split("id");
                splits = Arrays.copyOfRange(splits,1,splits.length);
                for(String i: splits){
                    if(i.substring(i.indexOf("bookmakers")).length()>20){
                        try{
                            String id = i.substring(3,35);
                            String sport = i.substring(i.indexOf("sport_title")+14,i.indexOf("commence")-3);
                            String teamodds = i.substring(i.indexOf("outcomes")+10,i.length()-8);
                            String[] teams = teamodds.split("},\\{");
                            String team1 = teams[0].substring(10,teams[0].indexOf("price")-3);
                            double team1odds = Double.parseDouble(teams[0].substring(teams[0].indexOf("price")+7));
                            String team2 = teams[1].substring(8,teams[1].indexOf("price")-3);
                            double team2odds;
                            if (teams[1].substring(teams[1].indexOf("price")).indexOf("}") < 0){
                                 team2odds = Double.parseDouble(teams[1].substring(teams[1].indexOf("price")+7));
                            }
                            else{
                                 team2odds = Double.parseDouble(teams[1].substring(teams[1].indexOf("price")+7,teams[1].indexOf("}")));

                            }
                            Sportbet sb = new Sportbet(id,sport,team1,team2,team1odds,team2odds,"N/A");
                        /*public Sportbet(String id, String sport, String team1, String team2,
                     double team1price, double team2price, String status)*/
                            allbets.add(sb);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*for(Sportbet s:allbets){
            System.out.println(s);
        }*/
    }
    public ArrayList<Sportbet> getAllbets(){
        return this.allbets;
    }
}
