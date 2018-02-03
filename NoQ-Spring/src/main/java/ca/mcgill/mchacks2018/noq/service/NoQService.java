package ca.mcgill.mchacks2018.noq.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.mchacks2018.noq.model.Event;
import ca.mcgill.mchacks2018.noq.model.User;
import ca.mcgill.mchacks2018.noq.model.Registration;
import ca.mcgill.mchacks2018.noq.model.RegistrationManager;
import ca.mcgill.mchacks2018.noq.persistence.PersistenceXStream;

@Service
public class NoQService {
	private RegistrationManager rm;

	public NoQService(RegistrationManager rm) {
		this.rm = rm;
	}

	public List<User> findAllUsers() {
		return rm.getUsers();
	}

	public List<Event> findAllEvents() {
		return rm.getEvents();
	}

	public void resetDatabase() {
		rm.delete();
	}

	public User createUser(String username, String password, int age, int points) throws InvalidInputException {
		if (username == null || username.trim().length() == 0)
			throw new InvalidInputException("Username cannot be empty!");
		if (password == null || password.trim().length() == 0)
			throw new InvalidInputException("Password cannot be empty!");
		if (age < 0)
			throw new InvalidInputException("Age must be positive!");

		User u = new User(username, password, age, points);
		for (User user : rm.getUsers()) {
			if (user.getUsername().equals(u.getUsername())) {
				throw new InvalidInputException("Username is already taken!");
			}
		}

		rm.addUser(u);
		PersistenceXStream.saveToXMLwithXStream(rm);
		return u;
	}

	public List<Event> getEventsForUser(User u) {
		List<Event> userEvents = new ArrayList<Event>();

		for (Registration registration : rm.getRegistrations()) {
			if (registration.getUser().equals(u))
				userEvents.add(registration.getEvent());
		}

		return userEvents;
	}

	public Event createEvent(String name, Date date, Time startTime, Time endTime) throws InvalidInputException {
		if (name == null || date == null || startTime == null || endTime == null)
			throw new InvalidInputException("Event name cannot be empty! Event date cannot be empty! Event start time cannot be empty! Event end time cannot be empty!");
		if (name.trim().length() == 0)
			throw new InvalidInputException("Event name cannot be empty!");
		if (endTime.before(startTime))
			throw new InvalidInputException("Event end time cannot be before event start time!");

		Event e = new Event(name, date, startTime, endTime);
		for (Event event : rm.getEvents()) {
			if (event.getName().equals(e.getName()) && event.getEventDate().equals(e.getEventDate()) && event.getStartTime().equals(e.getStartTime()) && event.getEndTime().equals(e.getEndTime())) {
				throw new InvalidInputException("Cannot create identical events!");
			}
		}

		rm.addEvent(e);
		PersistenceXStream.saveToXMLwithXStream(rm);
		return e;
	}

	public Registration register(User user, Event event) throws InvalidInputException {
		if (user == null || event == null)
			throw new InvalidInputException("User needs to be selected for registration! Event needs to be selected for registration!");
		if (!rm.getEvents().contains(event) || !rm.getUsers().contains(user))
			throw new InvalidInputException("User does not exist! Event does not exist!");

		Registration registration = new Registration(user, event);
		for (Registration reg : rm.getRegistrations()) {
			if (reg.getEvent().getName().equals(registration.getEvent().getName()) && reg.getUser().getUsername().equals(registration.getUser().getUsername())) {
				throw new InvalidInputException("Cannot register multiple times for the same event!");
			}
		}

		rm.addRegistration(registration);
		PersistenceXStream.saveToXMLwithXStream(rm);
		return registration;
	}

	public User getUserByName(String username) throws InvalidInputException {
		for (User user : rm.getUsers()) {
			if (user.getUsername().equals(username))
				return user;
		}
		throw new InvalidInputException("User does not exist!");
	}

	public Event getEventByName(String name) throws InvalidInputException {
		for (Event event : rm.getEvents()) {
			if (event.getName().equals(name))
				return event;
		}
		throw new InvalidInputException("Event does not exist!");
	}
}
