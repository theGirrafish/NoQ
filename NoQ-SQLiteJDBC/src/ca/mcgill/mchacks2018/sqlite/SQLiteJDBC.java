package ca.mcgill.mchacks2018.sqlite;

import org.json.*;

import java.sql.*;
import java.io.File;
import java.util.HashMap;

public class SQLiteJDBC {
    static private Connection c;
    static private Statement stmt;


    // ==============================
    // CONNECTION API
    // ==============================

    // Connect to a database
    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            // Create a connection to the database
            String dbDir = new File(System.getProperty("user.dir")).getParent();
            String urlUsers = String.format("jdbc:sqlite:%s/db/users.db", dbDir);

            c = DriverManager.getConnection(urlUsers);
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
                                + "(ID INT PRIMARY KEY      NOT NULL,"
                                + " NAME           CHAR(50) NOT NULL,"
                                + " STRTNUM        CHAR(50) NOT NULL,"
                                + " ADDRESS        CHAR(50) NOT NULL,"
                                + " QTIME          INT      NOT NULL,"
                                + " CHECKTIMES     TEXT     NOT NULL)";

            stmt = c.createStatement();
            stmt.executeUpdate(sqlUsers);
            stmt.executeUpdate(sqlLocations);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Close connection to database
    public static void closeConnection() {
        try {
            if (stmt != null)
                stmt.close();
            if (c != null)
                c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    // ==============================
    // USER TABLE API
    // ==============================

    // Add new user to Users Table
    public static void insertUser(int id, String username, String password, int age, int points, String favs) {
        // User Insert Info
        String insertUser = String.format("INSERT INTO USERS (ID, USERNAME, PASSWORD, AGE, POINTS, FAVS) "
                                        + "VALUES (%d, '%s', '%s', %d, %d, %s);", id, username, password, age, points, favs);

        try {
            stmt.executeUpdate(insertUser);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage() + "hi");
        }
    }

    // Update a user's password in Users Table
    public static void updateUserPassword(int id, String password) {
        try {
            String updatePass = String.format("UPDATE USERS SET PASSWORD = '%s' WHERE ID = %d;", password, id);
            stmt.executeUpdate(updatePass);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Update a user's points in Users Table
    public static void updateUserPoints(int id, int points) {
        try {
            String updatePoints = String.format("UPDATE USERS SET POINTS = %d WHERE ID = %d;", points, id);
            stmt.executeUpdate(updatePoints);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Delete a user in Users Table
    public static void deleteUser(int id) {
        try {
            String userDelete = String.format("DELETE FROM USERS WHERE ID = %d;", id);
            stmt.executeUpdate(userDelete);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Get all user Id, Username, Password
    public static HashMap<String, String[]> getUsers() {
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
    public static int getUserAge(int id, String username) {
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
    public static int getUserPoints(int id, String username) {
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
    public static JSONObject getUserFavs(int id, String username) {
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
    public static void insertLocation(int id, String name, String streetNum, String address, int qTime, String checkTimes) {
        // Locations Insert Info
        String insertLocation = String.format("INSERT INTO LOCATIONS (ID, NAME, STRTNUM, ADDRESS, QTIME, CHECKTIMES) "
                                            + "VALUES (%d, '%s', %s, '%s', '%d', '%s');", id, name, streetNum, address, qTime, checkTimes);

        try {
            stmt.executeUpdate(insertLocation);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Delete a location in Locations Table
    public static void deleteLocation(int id) {
        try {
            String locationDelete = String.format("DELETE FROM LOCATIONS WHERE ID = %d;", id);
            stmt.executeUpdate(locationDelete);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Get a location's name and address using id
    public static HashMap<Integer, String[]> getLocations(int id) {
        HashMap<Integer, String[]> locationList = new HashMap<Integer, String[]>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT NAME, STRTNUM, ADDRESS FROM LOCATIONS WHERE ID = %d;", id));

            while (rs.next())
                locationList.put(id, new String[] {rs.getString("NAME"), rs.getString("STRTNUM"), rs.getString("ADDRESS")});

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return locationList;
    }

    // Get a location's qtime using id and name
    public static int getLocationQTime(int id, String name) {
        int qtime = -1;
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT NAME, QTIME FROM LOCATIONS WHERE ID = %d;", id));

            while (rs.next()) {
                if (rs.getString("NAME").equals(name))
                    qtime = rs.getInt("QTIME");
            }

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return qtime;
    }

    // Get a location's checkTime object using id and name
    public static JSONObject getLocationCheckTimes(int id, String name) {
        JSONObject checkTimes = null;
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT NAME, CHECKTIMES FROM LOCATIONS WHERE ID = %d;", id));

            while (rs.next()) {
                if (rs.getString("NAME").equals(name))
                    checkTimes = new JSONObject(rs.getString("CHECKTIMES"));
            }

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return checkTimes;
    }


    // ==============================
    // MAIN FUNCTION
    // ==============================

    // @param args the command line arguments
    public static void main(String[] args) {
        c = null;
        stmt = null;

        connect();
        insertUser(2, "Abbas", "1q2w3e", 21, 100, "{}");
        insertLocation(3, "Pizza Pizza", "1846", "Saint-Catherine Street", 600, "{}");

        deleteUser(2);
        deleteLocation(2);

        updateUserPassword(1, "fgtboi");

        closeConnection();
    }
}
