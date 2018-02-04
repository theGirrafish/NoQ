/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.mchacks2018.noq.model;

import org.json.JSONObject;

// line 3 "../../../../../NoQ.ump"
// line 29 "../../../../../NoQ.ump"
public class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String username;
  private String password;
  private int age;
  private int points;
  private JSONObject favs;

  //Autounique Attributes
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, String aPassword, int aAge, int aPoints, JSONObject aFavs)
  {
    username = aUsername;
    password = aPassword;
    age = aAge;
    points = aPoints;
    favs = aFavs;
    id = nextId++;
  }

  public User(String aUsername, String aPassword, int aAge, int aPoints, JSONObject aFavs, boolean init) {
    username = aUsername;
    password = aPassword;
    age = aAge;
    points = aPoints;
    favs = aFavs;
    id = nextId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setAge(int aAge)
  {
    boolean wasSet = false;
    age = aAge;
    wasSet = true;
    return wasSet;
  }

  public boolean setPoints(int aPoints)
  {
    boolean wasSet = false;
    points = aPoints;
    wasSet = true;
    return wasSet;
  }

  public boolean setFavs(JSONObject aFavs)
  {
    boolean wasSet = false;
    favs = aFavs;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public int getAge()
  {
    return age;
  }

  public int getPoints()
  {
    return points;
  }

  public JSONObject getFavs()
  {
    return favs;
  }

  public int getId()
  {
    return id;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "age" + ":" + getAge()+ "," +
            "points" + ":" + getPoints()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "favs" + "=" + (getFavs() != null ? !getFavs().equals(this)  ? getFavs().toString().replaceAll("  ","    ") : "this" : "null");
  }
}