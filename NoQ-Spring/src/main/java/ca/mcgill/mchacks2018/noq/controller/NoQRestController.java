package ca.mcgill.mchacks2018.noq.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.mchacks2018.noq.dto.EventDto;
import ca.mcgill.mchacks2018.noq.dto.UserDto;
import ca.mcgill.mchacks2018.noq.dto.RegistrationDto;
import ca.mcgill.mchacks2018.noq.model.Event;
import ca.mcgill.mchacks2018.noq.model.User;
import ca.mcgill.mchacks2018.noq.model.Registration;
import ca.mcgill.mchacks2018.noq.service.NoQService;
import ca.mcgill.mchacks2018.noq.service.InvalidInputException;

@RestController
public class NoQRestController {

	@Autowired
	private NoQService service;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping("/")
	public String index() {
		return "No Q application root. Web-based frontend is a TODO. Use the REST API to manage users and locations.\n";
	}

	// Conversion methods (not part of the API)
	private EventDto convertToDto(Event e) {
		return modelMapper.map(e, EventDto.class);
	}

	private UserDto convertToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	private User convertToDomainObject(UserDto uDto) {
		// Mapping DTO to the domain object without using the mapper
		List<User> allUsers = service.findAllUsers();
		for (User user : allUsers) {
			if (user.getUsername().equals(uDto.getUsername()))
				return user;
		}
		return null;
	}

	private List<EventDto> createEventDtosForUser(User user) {
		List<Event> eventsForUser = service.getEventsForUser(user);
		List<EventDto> events = new ArrayList<EventDto>();
		for (Event event : eventsForUser)
			events.add(convertToDto(event));
		return events;
	}

	private RegistrationDto convertToDto(Registration r, User user, Event event) {
		// Manual conversion instead
		EventDto eDto = convertToDto(event);
		UserDto uDto = convertToDto(user);
		return new RegistrationDto(uDto, eDto);
	}

	@GetMapping(value = {"/users", "/users/"})
	public List<UserDto> findAllUsers() {
		List<UserDto> users = new ArrayList<UserDto>();
		for (User user : service.findAllUsers())
			users.add(convertToDto(user));
		return users;
	}

	@GetMapping(value = {"/events", "/events/"})
	public List<EventDto> findAllEvents() {
		List<EventDto> events = new ArrayList<EventDto>();
		for (Event event : service.findAllEvents())
			events.add(convertToDto(event));
		return events;
	}

	@GetMapping(value = {"/users/{username}", "/users/{username}/"})
	public UserDto getUserByName(@PathVariable("username") String username) throws InvalidInputException {
		User user = service.getUserByName(username);
		return convertToDto(user);
	}

	@GetMapping(value = {"/events/{name}", "/events/{name}/"})
	public EventDto getEventByName(@PathVariable("name") String name) throws InvalidInputException {
		Event event = service.getEventByName(name);
		return convertToDto(event);
	}

	@GetMapping(value = {"/registrations/user/{username}", "/registrations/user/{username}/"})
	public List<EventDto> getEventsOfParticipant(@PathVariable("username") UserDto uDto) {
		User user = convertToDomainObject(uDto);
		return createEventDtosForUser(user);
	}

	@PostMapping(value = {"/users/{username}", "/users/{username}/"})
	public UserDto createUser(@PathVariable("username") String username,
							  @RequestParam String password,
							  @RequestParam int age,
							  @RequestParam(value="points", required=false, defaultValue="0") int points
							  ) throws InvalidInputException {
		User user = service.createUser(username, password, age, points);
		return convertToDto(user);
	}

	@PostMapping(value = {"/events/{name}", "/events/{name}/"})
	public EventDto createEvent(@PathVariable ("name") String name,
								@RequestParam Date date,
								@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME, pattern="HH:mm") LocalTime startTime,
								@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME, pattern="HH:mm") LocalTime endTime
								) throws InvalidInputException {

		@SuppressWarnings("deprecation")
		Time startTimeSql = new Time(startTime.getHour(),startTime.getMinute(), 0);
		@SuppressWarnings("deprecation")
		Time endTimeSql = new Time(endTime.getHour(),endTime.getMinute(), 0);
		Event event = service.createEvent(name, date, startTimeSql, endTimeSql);
		return convertToDto(event);
	}

	@PostMapping(value = {"/register", "/register/"})
	public RegistrationDto registerParticipantForEvent(@RequestParam (name = "user") UserDto uDto,
													   @RequestParam (name = "event") EventDto eDto
													   ) throws InvalidInputException {
		// In this example application, we assumed that participants and events are identified by their names
		User user = service.getUserByName(uDto.getUsername());
		Event event = service.getEventByName(eDto.getName());
		Registration reg = service.register(user, event);
		return convertToDto(reg, user, event);
	}

	@DeleteMapping(value = {"/reset", "/reset/"})
	public void resetDatabase() {
		service.resetDatabase();
	}
}
