package ca.mcgill.mchacks2018.noq.persistence;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.mchacks2018.noq.model.Event;
import ca.mcgill.mchacks2018.noq.model.Registration;
import ca.mcgill.mchacks2018.noq.model.RegistrationManager;
import ca.mcgill.mchacks2018.noq.model.User;
import ca.mcgill.mchacks2018.noq.persistence.PersistenceXStream;

public class TestPersistence {

	private RegistrationManager rm;

	@Before
	public void setUp() throws Exception {
	    rm = new RegistrationManager();

	    // Create participants
	    User user1 = new User("Martin", "Garrix", 25, 69);
	    User user2 = new User("Jennifer", "Lawrence", 45, 1000);

	    // Create event
	    Calendar c = Calendar.getInstance();
	    c.set(2015,Calendar.SEPTEMBER,15,8,30,0);
	    Date eventDate = new Date(c.getTimeInMillis());
	    Time startTime = new Time(c.getTimeInMillis());
	    c.set(2015,Calendar.SEPTEMBER,15,10,0,0);
	    Time endTime = new Time(c.getTimeInMillis());
	    Event ev = new Event("Concert", eventDate, startTime, endTime);

	    // Register users to event
	    Registration re1 = new Registration(user1, ev);
	    Registration re2 = new Registration(user2, ev);

	    // Manage registrations
	    rm.addRegistration(re1);
	    rm.addRegistration(re2);
	    rm.addEvent(ev);
	    rm.addUser(user1);
	    rm.addUser(user2);
	}

	@After
	public void tearDown() throws Exception {
		rm.delete();
	}

	@Test
	public void test() {
		// Initialize model file
		File dataFile = new File("output"+File.separator+"data.xml");
		//if (dataFile.exists())
	    PersistenceXStream.initializeModelManager(dataFile.toString());

	    // Save model that is loaded during test setup
	    if (!PersistenceXStream.saveToXMLwithXStream(rm))
	        fail("Could not save file.");

	    // Clear the model in memory
	    rm.delete();
	    assertEquals(0, rm.getUsers().size());
	    assertEquals(0, rm.getEvents().size());
	    assertEquals(0, rm.getRegistrations().size());

	    // Load model
	    rm = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
	    if (rm == null)
	        fail("Could not load file.");

	    // Check participants
	    assertEquals(2, rm.getUsers().size());
	    assertEquals("Martin", rm.getUser(0).getUsername());
	    assertEquals("Jennifer", rm.getUser(1).getUsername());

	    // Check event
	    assertEquals(1, rm.getEvents().size());
	    assertEquals("Concert", rm.getEvent(0).getName());
	    Calendar c = Calendar.getInstance();
	    c.set(2015,Calendar.SEPTEMBER,15,8,30,0);
	    Date eventDate = new Date(c.getTimeInMillis());
	    Time startTime = new Time(c.getTimeInMillis());
	    c.set(2015,Calendar.SEPTEMBER,15,10,0,0);
	    Time endTime = new Time(c.getTimeInMillis());
	    assertEquals(eventDate.toString(), rm.getEvent(0).getEventDate().toString());
	    assertEquals(startTime.toString(), rm.getEvent(0).getStartTime().toString());
	    assertEquals(endTime.toString(), rm.getEvent(0).getEndTime().toString());

	    // Check registrations
	    assertEquals(2, rm.getRegistrations().size());
	    assertEquals(rm.getEvent(0), rm.getRegistration(0).getEvent());
	    assertEquals(rm.getUser(0), rm.getRegistration(0).getUser());
	    assertEquals(rm.getEvent(0), rm.getRegistration(1).getEvent());
	    assertEquals(rm.getUser(1), rm.getRegistration(1).getUser());
	}

}
