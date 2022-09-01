import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;
    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();//creates an arrayList called values

        for (HashMap<String, String> row : allJobs) {//For the hash map look at each key value pair(row) in allJobs
            String aValue = row.get(field);//avalue is the name of each string in the keyvalue pair

            if (!values.contains(aValue)) {//if the values have the string
                values.add(aValue);//add them to aValue
            }
        }

        // Bonus mission: sort the results
        Collections.sort(values);

        return values;


    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        // Bonus mission; normal version returns allJobs
//        System.out.println(allJobs);//NEW
        return new ArrayList<>(allJobs);

    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();//creates an array list called jobs to put data in

        for (HashMap<String, String> row : allJobs) {//for each hash map or key value (AKA ROW) in allJObs

            String aValue = row.get(column).toLowerCase();//aValue is the data in the columns in lowercase

            if (aValue.contains(value.toLowerCase()) && !jobs.contains(value.toLowerCase())) {
                jobs.add(row);
            }
        }

        return jobs;
    }

    /**
     * Search all columns for the given term
     *
     * @return List of all jobs with at least one field containing the value
     */
    public static ArrayList<HashMap<String, String>> findByValue(String searchTerm) {//sets up findByValue method whch takes in an array list of hash maps with strings
        loadData();       // load data, if not already loaded DO NOT TOUCH

        ArrayList<HashMap<String, String>> searchResults = new ArrayList<>();//creates an array list with hash maps called searchResults to hold the hash maps with the search terms

        for (HashMap<String, String> hashMaps : allJobs) {//for each loop that cycles through the array list (of hashmaps) from allJobs
            for (Map.Entry<String, String> keyValue : hashMaps.entrySet()) {// for each keyvalue pair
                String word = keyValue.getValue().toLowerCase();
//                System.out.println(word.toLowerCase());//this printed everything in lowercase.
                if (word.toLowerCase().contains(searchTerm.toLowerCase())) {// if the value in keyvalue is the search term
                    searchResults.add(hashMaps);// add it to the searchresults
                }
            }
        }
            return searchResults;
    }

    /**
     * Read in data from a CSV file and store it in a list     for (Map.Entry<String, String> techJobsList : map.entrySet()) {
     *                 System.out.println(techJobsList.getKey() + ": " + techJobsList.getValue());
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

    public static ArrayList<HashMap<String, String>> somejobs() {
        return null;
    }//I don't remember what this line is

}
