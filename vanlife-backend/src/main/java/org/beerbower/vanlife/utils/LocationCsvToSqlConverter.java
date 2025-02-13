package org.beerbower.vanlife.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocationCsvToSqlConverter {
    public static void main(String[] args) {
        String csvFile = "src/main/resources/NTAD_Truck_Stop_Parking_9110160632932345500.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = parseCsvLine(line);
                if (fields.length < 11 || containsNull(fields)) continue; // Ensure valid data and no null values

                String name = fields[2];
                String highwayRoute = fields[3];
                String milePost = fields[4];
                String municipality = fields[5];
                String county = fields[6];
                String state = fields[7];
                String latitude = fields[8];
                String longitude = fields[9];
                String numberOfSpots = fields[10];

                String description = String.format(
                        "Rest stop in %s, %s county, %s on RT %s at mile %s. This rest stop has %s parking spots.",
                        municipality, county, state, highwayRoute, milePost, numberOfSpots);

                String sql = String.format(
                        "INSERT INTO location (latitude, longitude, user_id, name, description) VALUES (%s, %s, 1, '%s', '%s');",
                        latitude, longitude, name.replace("'", "''"), description.replace("'", "''"));

                System.out.println(sql);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] parseCsvLine(String line) {
        Pattern pattern = Pattern.compile("(?:^|,)(\"(?:[^\"]+)*\"|[^,]*)");
        Matcher matcher = pattern.matcher(line);

        String[] values = new String[13]; // Adjust size as needed
        int index = 0;

        while (matcher.find() && index < values.length) {
            values[index++] = matcher.group(1).replaceAll("^\"|\"$", "");
        }

        return values;
    }

    private static boolean containsNull(String[] fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
