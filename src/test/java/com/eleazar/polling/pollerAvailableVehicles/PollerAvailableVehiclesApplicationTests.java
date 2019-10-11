package com.eleazar.polling.pollerAvailableVehicles;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.eleazar.polling.pollerAvailableVehicles.Poller.getIdSet;
import static com.eleazar.polling.pollerAvailableVehicles.Poller.meepApiRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PollerAvailableVehiclesApplicationTests {

	@Test
	public void checkApiResponse() {
		JSONArray response = null;
		try {
			response = meepApiRequest();
			assert (response.getJSONObject(0).get("id") != null);
		} catch (IOException | JSONException e) {
			System.out.println("Something was wrong: " + e.getMessage());
		}
	}

	@Test
	public void checkIdListIsNotEmpty() {
		Set ids = getIdSet();
		assert (ids.size() > 0);
	}

}
