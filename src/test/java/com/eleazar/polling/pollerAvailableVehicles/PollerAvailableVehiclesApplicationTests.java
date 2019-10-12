package com.eleazar.polling.pollerAvailableVehicles;

import com.eleazar.polling.models.Coordinates;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.eleazar.polling.pollerAvailableVehicles.Poller.getIdSet;
import static com.eleazar.polling.pollerAvailableVehicles.Poller.meepApiRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PollerAvailableVehiclesApplicationTests {
    Coordinates lowerLeftLatLon = new Coordinates(38.711046, -9.160096);
    Coordinates upperRightLatLon = new Coordinates(38.739429, -9.137115);
    List<Integer> companyIds = new ArrayList<Integer>(Arrays.asList(545,467,473)); 

    @Test
    public void checkApiResponse() {
        JSONArray response = null;
        try {
            response = meepApiRequest(lowerLeftLatLon, upperRightLatLon, companyIds);
            assert (response.getJSONObject(0).get("id") != null);
        } catch (IOException | JSONException e) {
            System.out.println("Something was wrong: " + e.getMessage());
        }
    }

    @Test
    public void checkIdListIsNotEmpty() {
        Set ids = getIdSet(lowerLeftLatLon, upperRightLatLon, companyIds);
        assert (ids.size() > 0);
    }

}
