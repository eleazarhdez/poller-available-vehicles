package com.eleazar.polling.pollerAvailableVehicles;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static utils.Utils.bufferedReaderToJson;

public class Poller {

    final static String MEEP_URL = "https://apidev.meep.me/tripplan/api/v1/routers/lisboa/resources?" +
            "lowerLeftLatLon=38.711046,-9.160096&upperRightLatLon=38.739429,-" +
            "9.137115&companyZoneIds=545,467,473";

    public static Set getIdSet() {
        ArrayList<String> idList = new ArrayList<String>();
        try {
            JSONArray response = meepApiRequest();

            for (int i = 0; i < response.length(); i++) {
                idList.add((String) response.getJSONObject(i).get("id"));
            }
            System.out.println(idList);
        } catch (IOException e) {
            System.out.println("Problem getting vehicles: " + e.getMessage());
        }
        return new HashSet<String>(idList);
    }

    public static JSONArray meepApiRequest() throws IOException {
        URL url = new URL(MEEP_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        if (con.getResponseCode() != 200) {
            throw new RuntimeException("Request failed: " + con.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
        JSONArray jsonArray = bufferedReaderToJson(br);
        con.disconnect();
        return jsonArray;
    }
}
