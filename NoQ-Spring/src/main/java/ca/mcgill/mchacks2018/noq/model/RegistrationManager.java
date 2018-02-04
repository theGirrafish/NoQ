/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.mchacks2018.noq.model;

import java.util.*;
import ca.mcgill.mchacks2018.noq.sqlite.SQLiteJDBC;

// line 23 "../../../../../NoQ.ump"
// line 39 "../../../../../NoQ.ump"
public class RegistrationManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RegistrationManager Associations
  private List<User> users;
  private List<Location> locations;
  private SQLiteJDBC sql = new SQLiteJDBC();;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RegistrationManager()
  {
    users = new ArrayList<User>();
    locations = new ArrayList<Location>();
    sql.connect();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }

  public Location getLocation(int index)
  {
    Location aLocation = locations.get(index);
    return aLocation;
  }

  public List<Location> getLocations()
  {
    List<Location> newLocations = Collections.unmodifiableList(locations);
    return newLocations;
  }

  public int numberOfLocations()
  {
    int number = locations.size();
    return number;
  }

  public boolean hasLocations()
  {
    boolean has = locations.size() > 0;
    return has;
  }

  public int indexOfLocation(Location aLocation)
  {
    int index = locations.indexOf(aLocation);
    return index;
  }

  public static int minimumNumberOfUsers()
  {
    return 0;
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    users.add(aUser);
    sql.insertUser(aUser.getId(), aUser.getUsername(), aUser.getPassword(), aUser.getAge(), aUser.getPoints(), aUser.getFavs().toString());
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    if (users.contains(aUser))
    {
      users.remove(aUser);
      sql.deleteUser(aUser.getId());
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);

      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    else
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfLocations()
  {
    return 0;
  }

  public boolean addLocation(Location aLocation)
  {
    boolean wasAdded = false;
    if (locations.contains(aLocation)) { return false; }
    locations.add(aLocation);
    sql.insertLocation(aLocation.getId(), aLocation.getName(), aLocation.getStrtNum(),
                       aLocation.getAddress(), aLocation.getQTime(), aLocation.getCheckTimes().toString());
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLocation(Location aLocation)
  {
    boolean wasRemoved = false;
    if (locations.contains(aLocation))
    {
      locations.remove(aLocation);
      sql.deleteLocation(aLocation.getId());
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addLocationAt(Location aLocation, int index)
  {
    boolean wasAdded = false;
    if(addLocation(aLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocations()) { index = numberOfLocations() - 1; }
      locations.remove(aLocation);
      locations.add(index, aLocation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLocationAt(Location aLocation, int index)
  {
    boolean wasAdded = false;
    if(locations.contains(aLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocations()) { index = numberOfLocations() - 1; }
      locations.remove(aLocation);
      locations.add(index, aLocation);
      wasAdded = true;
    }
    else
    {
      wasAdded = addLocationAt(aLocation, index);
    }
    return wasAdded;
  }

  public List<User> getRMUsers() {
    return users;
  }

  public List<Location> getRMLocations() {
    return locations;
  }

  public void setUsers(ArrayList<User> users) {
    this.users = users;
  }

  public void setLocations(ArrayList<Location> locations) {
    this.locations = locations;
  }

  public void delete()
  {
    users.clear();
    locations.clear();
  }

}