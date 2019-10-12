package com.eleazar.polling.utils;

import com.eleazar.polling.models.Coordinates;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Utils {

    public static JSONArray bufferedReaderToJson (BufferedReader br) throws IOException {
        String output;
        StringBuilder sb = new StringBuilder();

        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return new JSONArray(sb.toString());
    }

    public static String buildUrl (Coordinates lowerLeftLatLon, Coordinates upperRightLatLon, List<Integer> companyIds) {
        return "https://apidev.meep.me/tripplan/api/v1/routers/lisboa/resources?" +
                "lowerLeftLatLon=" + lowerLeftLatLon.getLatitude() + "," + lowerLeftLatLon.getLongitude() + "&upperRightLatLon=" +
                upperRightLatLon.getLatitude() + "," + upperRightLatLon.getLongitude() + "&companyZoneIds=" + StringUtils.join(companyIds, ",");
    }

    public static HashMap buildArgs (String[] args) throws WrongNumberArgsException {
        List<String> arguments = new ArrayList<String>(Arrays.asList(args));
        HashMap builtArguments = new HashMap();
        if (arguments.size() < 4) {
            throw new WrongNumberArgsException("The arguments number its not correct");
        } else if (arguments.size() < 5)
            throw new WrongNumberArgsException("You have to specify one company id at least");
        else {
            Coordinates lowerLeftLatLon = new Coordinates(Double.parseDouble(arguments.get(0)), Double.parseDouble(arguments.get(1)));
            Coordinates upperRightLatLon = new Coordinates(Double.parseDouble(arguments.get(2)), Double.parseDouble(arguments.get(3)));

            ArrayList<Integer> companyIds = new ArrayList<Integer>();
            for (int i = 4; i < arguments.size(); i++) {
                companyIds.add(Integer.parseInt(arguments.get(i)));
            }

            builtArguments.put("lowerLeftLatLon", lowerLeftLatLon);
            builtArguments.put("upperRightLatLon", upperRightLatLon);
            builtArguments.put("companyIds", companyIds);
        }
        return builtArguments;
    }
}
