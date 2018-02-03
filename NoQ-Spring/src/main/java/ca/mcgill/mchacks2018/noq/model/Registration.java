/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.mchacks2018.noq.model;

// line 20 "../../../../../EventRegistration.ump"
// line 44 "../../../../../EventRegistration.ump"
public class Registration
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Autounique Attributes
  private int id;

  //Registration Associations
  private User user;
  private Event event;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Registration(User aUser, Event aEvent)
  {
    id = nextId++;
    if (!setUser(aUser))
    {
      throw new RuntimeException("Unable to create Registration due to aUser");
    }
    if (!setEvent(aEvent))
    {
      throw new RuntimeException("Unable to create Registration due to aEvent");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getId()
  {
    return id;
  }

  public User getUser()
  {
    return user;
  }

  public Event getEvent()
  {
    return event;
  }

  public boolean setUser(User aNewUser)
  {
    boolean wasSet = false;
    if (aNewUser != null)
    {
      user = aNewUser;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setEvent(Event aNewEvent)
  {
    boolean wasSet = false;
    if (aNewEvent != null)
    {
      event = aNewEvent;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    user = null;
    event = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "event = "+(getEvent()!=null?Integer.toHexString(System.identityHashCode(getEvent())):"null");
  }
}