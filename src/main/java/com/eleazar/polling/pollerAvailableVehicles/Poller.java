package com.eleazar.polling.pollerAvailableVehicles;

import com.eleazar.polling.models.Coordinates;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.eleazar.polling.utils.Utils.bufferedReaderToJson;
import static com.eleazar.polling.utils.Utils.buildUrl;

public class Poller {

    final static int INITIAL_DELAY= 30;
    final static int DELAY = 30;
    static Set<String> firstRequestResults = new HashSet<>();


    public static void pollerMeep (Coordinates lowerLeftLatLon, Coordinates upperRightLatLon, List<Integer> companyIds) {
        firstRequestResults = getIdSet(lowerLeftLatLon, upperRightLatLon, companyIds);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            getAvailableAndNotAvailableVehicles(lowerLeftLatLon, upperRightLatLon, companyIds);
        };
        executor.scheduleWithFixedDelay(task, INITIAL_DELAY, DELAY, TimeUnit.SECONDS);

    }

    public static void getAvailableAndNotAvailableVehicles(Coordinates lowerLeftLatLon, Coordinates upperRightLatLon,
                                                           List<Integer> companyIds) {
        Set<String> secondRequestResults = getIdSet(lowerLeftLatLon, upperRightLatLon, companyIds);

        Set<String> resultsOnlyOnFirstRequest = new HashSet<String>(firstRequestResults);
        Set<String> resultsOnlyOnSecondRequest = new HashSet<String>(secondRequestResults);

        Set<String> intersection = new HashSet<String>(firstRequestResults);
        intersection.retainAll(secondRequestResults);

        resultsOnlyOnFirstRequest.removeAll(intersection);
        resultsOnlyOnSecondRequest.removeAll(intersection);

        System.out.println("No longer available: " + resultsOnlyOnFirstRequest.toString());
        System.out.println("New available: " + resultsOnlyOnSecondRequest.toString());

        firstRequestResults = secondRequestResults;
    }

    public static Set getIdSet(Coordinates lowerLeftLatLon, Coordinates upperRightLatLon, List<Integer> companyIds) {
        ArrayList<String> idList = new ArrayList<String>();
        try {
            JSONArray response = meepApiRequest(lowerLeftLatLon, upperRightLatLon, companyIds);

            for (int i = 0; i < response.length(); i++) {
                idList.add((String) response.getJSONObject(i).get("id"));
            }
            System.out.println(idList);
        } catch (IOException e) {
            System.out.println("Problem getting vehicles: " + e.getMessage());
        }
        return new HashSet<String>(idList);
    }


    public static JSONArray meepApiRequest(Coordinates lowerLeftLatLon, Coordinates upperRightLatLon, List<Integer> companyIds) throws IOException {
        URL url = new URL(buildUrl(lowerLeftLatLon, upperRightLatLon, companyIds));
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
