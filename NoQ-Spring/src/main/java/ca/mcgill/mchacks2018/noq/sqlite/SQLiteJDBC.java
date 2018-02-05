package ca.mcgill.mchacks2018.noq.sqlite;

import org.json.*;

import java.sql.*;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.ArrayList;

import ca.mcgill.mchacks2018.noq.model.User;
import ca.mcgill.mchacks2018.noq.model.Location;

public class SQLiteJDBC {
    private static Connection c;
    private static Statement stmt;
    private static String dbPath;

    public SQLiteJDBC() {
        dbPath = new File(System.getProperty("user.dir")).getParent() + "/db/users_locations.db";
    }

    public SQLiteJDBC(String filename) {
    	dbPath = new File(System.getProperty("user.dir")).getParent() + filename;
    }

    // ==============================
    // CONNECTION API
    // ==============================

    // Connect to a database
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            // Create a connection to the database
            String url = String.format("jdbc:sqlite:%s", dbPath);
            c = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

            // Users DB Table
            String sqlUsers = "CREATE TABLE IF NOT EXISTS USERS "
                            + "(ID INT PRIMARY KEY NOT NULL,"
                            + " USERNAME  CHAR(25) NOT NULL,"
                            + " PASSWORD  CHAR(25) NOT NULL,"
                            + " AGE       INT      NOT NULL,"
                            + " POINTS    INT      NOT NULL,"
                            + " FAVS      TEXT     NOT NULL)";

            // Locations DB Table
            String sqlLocations = "CREATE TABLE IF NOT EXISTS LOCATIONS "
                                + "(ID TEXT PRIMARY KEY NOT NULL,"
                                + " NAME       CHAR(50) NOT NULL,"
                                + " STRTNUM    CHAR(50) NOT NULL,"
                                + " ADDRESS    CHAR(50) NOT NULL,"
                                + " QTIME      INT      NOT NULL,"
                                + " CHECKTIMES TEXT     NOT NULL)";

            stmt = c.createStatement();
            stmt.executeUpdate(sqlUsers);
            stmt.executeUpdate(sqlLocations);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Close connection to database
    public void closeConnection() {
        try {
            if (stmt != null)
                stmt.close();
            if (c != null)
                c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void deleteDB() {
        try {
            Files.deleteIfExists(new File(dbPath).toPath());
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    // ==============================
    // USER TABLE API
    // ==============================

    // Add new user to Users Table
    public void insertUser(int id, String username, String password, int age, int points, String favs) {
        // User Insert Info
        String insertUser = String.format("INSERT INTO USERS (ID, USERNAME, PASSWORD, AGE, POINTS, FAVS) "
                                        + "VALUES (%d, '%s', '%s', %d, %d, '%s');", id, username, password, age, points, favs);

        try {
            stmt.executeUpdate(insertUser);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Update a user's password in Users Table
    public void updateUserPassword(int id, String password) {
        try {
            String updatePass = String.format("UPDATE USERS SET PASSWORD = '%s' WHERE ID = %d;", password, id);
            stmt.executeUpdate(updatePass);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Update a user's points in Users Table
    public void updateUserPoints(int id, int points) {
        try {
            String updatePoints = String.format("UPDATE USERS SET POINTS = %d WHERE ID = %d;", points, id);
            stmt.executeUpdate(updatePoints);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Delete a user in Users Table
    public void deleteUser(int id) {
        try {
            String userDelete = String.format("DELETE FROM USERS WHERE ID = %d;", id);
            stmt.executeUpdate(userDelete);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Get all user data
    public ArrayList<User> getUserAll() {
        ArrayList<User> userList = new ArrayList<User>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");

            while (rs.next())
                userList.add(new User(rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getInt("AGE"),
                                      rs.getInt("POINTS"), new JSONObject(rs.getString("FAVS")), true));

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return userList;
    }

    // Get all user Id, Username, Password
    public HashMap<String, String[]> getUsers() {
        HashMap<String, String[]> userList = new HashMap<String, String[]>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT ID, USERNAME, PASSWORD FROM USERS;");

            while (rs.next())
                userList.put(rs.getString("USERNAME"), new String[] {String.valueOf(rs.getInt("ID")), rs.getString("PASSWORD")});

                rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return userList;
    }

    // Get a user's age using id and username
    public int getUserAge(int id, String username) {
        int age = -1;
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT USERNAME, AGE FROM USERS WHERE ID = %d;", id));

            while (rs.next()) {
                if (rs.getString("USERNAME").equals(username))
                    age = rs.getInt("AGE");
            }

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return age;
    }

    // Get a user's point using id and username
    public int getUserPoints(int id, String username) {
        int points = -1;
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT USERNAME, POINTS FROM USERS WHERE ID = %d;", id));

            while (rs.next()) {
                if (rs.getString("USERNAME").equals(username))
                    points = rs.getInt("POINTS");
            }

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return points;
    }

    // Get a user's favorite locations using id and username
    public JSONObject getUserFavs(int id, String username) {
        JSONObject favs = null;
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT USERNAME, FAVS FROM USERS WHERE ID = %d;", id));

            while (rs.next()) {
                if (rs.getString("USERNAME").equals(username))
                    favs = new JSONObject(rs.getString("FAVS"));
            }

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return favs;
    }


    // ==============================
    // LOCATION TABLE API
    // ==============================

    // Add new location to Locations Table
    public void insertLocation(String id, String name, String streetNum, String address, int qTime, String checkTimes) {
        // Locations Insert Info
        String insertLocation = String.format("INSERT INTO LOCATIONS (ID, NAME, STRTNUM, ADDRESS, QTIME, CHECKTIMES) "
                                            + "VALUES ('%s', '%s', '%s', '%s', '%d', '%s');", id, name, streetNum, address, qTime, checkTimes);

        try {
            stmt.executeUpdate(insertLocation);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Update a location's qTime in Locations Table
    public void updateLocationQTime(String id, int qTime) {
        try {
            String updateQTime = String.format("UPDATE LOCATIONS SET QTIME = %d WHERE ID = '%s';", qTime, id);
            stmt.executeUpdate(updateQTime);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Update a location's checkIn time in Locations Table
    public JSONObject updateLocationCheckIn(String id, String username, String checkIn) {
        JSONObject lCheckTimes = getLocationCheckTimes(id);
        try {
            JSONObject times;

            if (lCheckTimes.has(username))
                times = (JSONObject) lCheckTimes.get(username);
            else
                times = new JSONObject("{}");

            times.put("checkIn", checkIn);
            lCheckTimes.put(username, times);

            String updateCheckIn = String.format("UPDATE LOCATIONS SET CHECKTIMES = '%s' WHERE ID = '%s';", lCheckTimes.toString(), id);
            stmt.executeUpdate(updateCheckIn);
            return lCheckTimes;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    // Update a location's checkOut time in Locations Table
    public JSONObject updateLocationCheckOut(String id, String username, String checkOut) {
        JSONObject lCheckTimes = getLocationCheckTimes(id);
        try {
            JSONObject times;

            if (lCheckTimes.has(username))
                times = (JSONObject) lCheckTimes.get(username);
            else
                times = new JSONObject("{}");

            times.put("checkOut", checkOut);
            lCheckTimes.put(username, times);

            String updateCheckOut = String.format("UPDATE LOCATIONS SET CHECKTIMES = '%s' WHERE ID = '%s';", lCheckTimes.toString(), id);
            stmt.executeUpdate(updateCheckOut);
            return lCheckTimes;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    // Delete a location in Locations Table
    public void deleteLocation(String id) {
        try {
            String locationDelete = String.format("DELETE FROM LOCATIONS WHERE ID = '%s';", id);
            stmt.executeUpdate(locationDelete);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Get all location data
    public ArrayList<Location> getLocationAll() {
        ArrayList<Location> locationList = new ArrayList<Location>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM LOCATIONS");

            while (rs.next())
                locationList.add(new Location(rs.getString("ID"), rs.getString("NAME"), rs.getString("STRTNUM"), rs.getString("ADDRESS"),
                                 rs.getInt("QTIME"), new JSONObject(rs.getString("CHECKTIMES")), true));

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return locationList;
    }

    // Get a location's name and address using id
    public HashMap<String, String[]> getLocations(String id) {
        HashMap<String, String[]> locationList = new HashMap<String, String[]>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT NAME, STRTNUM, ADDRESS FROM LOCATIONS WHERE ID = '%s';", id));

            while (rs.next())
                locationList.put(id, new String[] {rs.getString("NAME"), rs.getString("STRTNUM"), rs.getString("ADDRESS")});

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return locationList;
    }

    // Get a location's qtime using id and name
    public int getLocationQTime(String id) {
        int qtime = -1;
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT QTIME FROM LOCATIONS WHERE ID = '%s';", id));

            while (rs.next())
                qtime = rs.getInt("QTIME");

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return qtime;
    }

    // Get a location's checkTime object using id and name
    public JSONObject getLocationCheckTimes(String id) {
        JSONObject checkTimes = null;
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT CHECKTIMES FROM LOCATIONS WHERE ID = '%s';", id));

            while (rs.next())
                checkTimes = new JSONObject(rs.getString("CHECKTIMES"));

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return checkTimes;
    }

    public void showUsers() {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");

            while (rs.next()) {
                System.out.println(rs.getInt("ID") + rs.getString("USERNAME") + rs.getString("PASSWORD")
                                 + rs.getInt("AGE") + rs.getInt("POINTS") + rs.getString("FAVS"));
            }

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void showLocations() {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM LOCATIONS");

            while (rs.next()) {
                System.out.println(rs.getString("ID") + rs.getString("NAME") + rs.getString("STRTNUM")
                                 + rs.getString("ADDRESS") + rs.getInt("QTIME") + rs.getString("CHECKTIMES"));
            }

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // ==============================
    // MAIN FUNCTION
    // ==============================

    /*
    // @param args the command line arguments
    public void main(String[] args) {
        c = null;
        stmt = null;

        connect();
        insertUser(2, "Abbas", "1q2w3e", 21, 100, "{\"AXIOS1EV2\": 3, \"ELEK56VUA\": 9, \"IDEK1053R\": 10}");
        insertLocation("AXAX", "Pizza Pizza", "1846", "Saint-Catherine Street", 600,
        "{" + "\"Martin\": {\"checkIn\": \"17:30:00\", \"checkOut\": \"17:45:00\"},"
                    + "\"Jennifer\": {\"checkIn\": \"09:11:11\", \"checkOut\": \"09:12:34\"},"
                    + "\"Motassaem\": {\"checkIn\": \"09:59:00\", \"checkOut\": \"19:00:00\"}"
                    + "}");

        showUsers();
        showLocations();

        deleteUser(2);
        deleteLocation("AXAX");

        showUsers();
        showLocations();

        updateUserPassword(1, "fgtboi");

        closeConnection();
    }
    */

    public String getDbPath() {
        return dbPath;
    }
}
