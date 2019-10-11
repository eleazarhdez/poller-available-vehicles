package utils;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;

public class Utils {
    public static JSONArray bufferedReaderToJson (BufferedReader br) throws IOException {
        String output;
        StringBuilder sb = new StringBuilder();

        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return new JSONArray(sb.toString());
    }
}
