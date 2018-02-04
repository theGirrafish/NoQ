/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.mchacks2018.noq.model;

import java.util.*;
import org.json.JSONObject;

// line 13 "../../../../../NoQ.ump"
// line 34 "../../../../../NoQ.ump"
public class Location
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Location> locationsById = new HashMap<String, Location>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private String id;
  private String name;
  private String strtNum;
  private String address;
  private int qTime;
  private JSONObject checkTimes;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(String aId, String aName, String aStrtNum, String aAddress, int aQTime, JSONObject aCheckTimes)
  {
    name = aName;
    strtNum = aStrtNum;
    address = aAddress;
    qTime = aQTime;
    checkTimes = aCheckTimes;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id");
    }
  }

  public Location(String aId, String aName, String aStrtNum, String aAddress, int aQTime, JSONObject aCheckTimes, boolean init) {
    id = aId;
    name = aName;
    strtNum = aStrtNum;
    address = aAddress;
    qTime = aQTime;
    checkTimes = aCheckTimes;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    String anOldId = getId();
    if (hasWithId(aId)) {
      return wasSet;
    }
    id = aId;
    wasSet = true;
    if (anOldId != null) {
      locationsById.remove(anOldId);
    }
    locationsById.put(aId, this);
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setStrtNum(String aStrtNum)
  {
    boolean wasSet = false;
    strtNum = aStrtNum;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setQTime(int aQTime)
  {
    boolean wasSet = false;
    qTime = aQTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setCheckTimes(JSONObject aCheckTimes)
  {
    boolean wasSet = false;
    checkTimes = aCheckTimes;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public static Location getWithId(String aId)
  {
    return locationsById.get(aId);
  }

  public static boolean hasWithId(String aId)
  {
    return getWithId(aId) != null;
  }

  public String getName()
  {
    return name;
  }

  public String getStrtNum()
  {
    return strtNum;
  }

  public String getAddress()
  {
    return address;
  }

  public int getQTime()
  {
    return qTime;
  }

  public JSONObject getCheckTimes()
  {
    return checkTimes;
  }

  public void delete()
  {
    locationsById.remove(getId());
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "strtNum" + ":" + getStrtNum()+ "," +
            "address" + ":" + getAddress()+ "," +
            "qTime" + ":" + getQTime()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "checkTimes" + "=" + (getCheckTimes() != null ? !getCheckTimes().equals(this)  ? getCheckTimes().toString().replaceAll("  ","    ") : "this" : "null");
  }
}