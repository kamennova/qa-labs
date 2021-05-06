package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestJunit {
    Statement stmt;
    Connection con;
    Integer linesMatched = 0;
    Integer linesMismatched = 0;
    ArrayList<String[]> errors = new ArrayList<>();

    private void checkWithCSV(ResultSet rs, ArrayList<ArrayList<String>> csvExport) {
        try {
            ArrayList<String> head = csvExport.get(0);

            for (int i = 1; i < csvExport.size(); i++) {
                ArrayList<String> dataRow = csvExport.get(i);
                rs.next();
                for (int a = 0; a < dataRow.size(); a++) {
                    String item1 = dataRow.get(a);
                    String item2 = rs.getString(a + 1);

                    if (!item1.equals(item2)) {
                        String[] arr = {head.get(a), item1, item2};
                        this.errors.add(arr);
                        this.linesMismatched++;
                    } else {
                        this.linesMatched++;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void writeError(String[] errorRow) {
        try (FileWriter fw = new FileWriter("resources/errors.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(String.join(",", errorRow));
        } catch (IOException e) {
            System.out.print(e);
        }
    }


    @Before
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");

            this.con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5433/postgres", "postgres", "postgres");

            this.stmt = this.con.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testTitles() {
        try {
            ResultSet rs = stmt.executeQuery(
                    "select \"Title\", count(*) from employees group by \"Title\" order by count desc");

            ArrayList<ArrayList<String>> csvExport = this.loadTitlesFromCSV();
            this.checkWithCSV(rs, csvExport);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private BufferedReader getEmployeesExport() throws FileNotFoundException {
        return new BufferedReader(new FileReader("resources/employees.csv"));
    }

    private ArrayList<ArrayList<String>> loadTitlesFromCSV() {
        ArrayList<ArrayList<String>> titlesData = new ArrayList<>();

        try {
            BufferedReader CSVFile1 = this.getEmployeesExport();

            ArrayList<String> headRow = new ArrayList<String>(Arrays.asList(CSVFile1.readLine().split(",")));
            titlesData.add(headRow);

            String dataRow = CSVFile1.readLine();
            Map<String, Integer> data = new HashMap<>();

            while (dataRow != null) {
                String[] dataArray = dataRow.split(",");
                String title = dataArray[1];
                data.put(title, data.getOrDefault(title, 0) + 1);
                dataRow = CSVFile1.readLine();
            }

            CSVFile1.close();

            for (Map.Entry<String, Integer> row : data.entrySet()) {
                titlesData.add(new ArrayList<>(Arrays.asList(row.getKey(), row.getValue().toString())));
            }
        } catch (Exception e) {
            System.out.print(e);
        }

        return titlesData;
    }

    private ArrayList<ArrayList<String>> loadIdsAndLastName() {
        ArrayList<ArrayList<String>> titlesData = new ArrayList<>();

        try {
            BufferedReader CSVFile1 = this.getEmployeesExport();
            String dataRow = CSVFile1.readLine();

            while (dataRow != null) {
                String[] dataArray = dataRow.split(",");
                String lastName = dataArray[2];
                String id = dataArray[0];
                titlesData.add(new ArrayList<>(Arrays.asList(id, lastName)));
                dataRow = CSVFile1.readLine();
            }

            CSVFile1.close();
        } catch (Exception e) {
            System.out.print(e);
        }

        return titlesData;
    }


    @Test
    public void testIds() {
        try {
            ResultSet rs = stmt.executeQuery("select \"EmployeeID\", \"LastName\" from employees order by \"EmployeeID\" asc");
            ArrayList<ArrayList<String>> csvExport = this.loadIdsAndLastName();
            this.checkWithCSV(rs, csvExport);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void addLogFile() {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String logName = "resources/log" + Long.toString(timestamp.getTime()) + ".csv";
            File logFile = new File(logName);
            logFile.createNewFile();

            FileWriter fw = new FileWriter(logName, true);
            fw.write(String.format("matched: %d\n", this.linesMatched));
            fw.write(String.format("mismatched: %d\n", this.linesMismatched));

            for (String[] error : this.errors) {
                fw.write(String.join("--", error));
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @After
    public void endConnection() throws SQLException {
        this.con.close();

        for (String[] error : this.errors) {
            this.writeError(error);
        }

        this.addLogFile();
    }
}
