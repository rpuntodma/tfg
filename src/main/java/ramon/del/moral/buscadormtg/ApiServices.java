package ramon.del.moral.buscadormtg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ApiServices {
    private static final String urlBase = "https://api.scryfall.com/cards/search?q=";
    public static String search(String endpoint) throws URISyntaxException, IOException {
        URL obj = new URI(urlBase + endpoint).toURL();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        if (con != null) {
            int responseCode = con.getResponseCode();
            System.out.println("'GET' request is sent to URL : " + urlBase + endpoint + "\nResponse Code: " + responseCode);
            if (responseCode != 200) {
                System.out.println("'GET' Request failed Http Status Code: " + responseCode);
            } else {
                System.out.println("'GET' Request succeed Http Status Code: " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
        }
        return "ERROR";
    }
}
