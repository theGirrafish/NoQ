package ca.mcgill.mchacks2018.noq.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.mchacks2018.noq.dto.UserDto;
import ca.mcgill.mchacks2018.noq.dto.LocationDto;
import ca.mcgill.mchacks2018.noq.model.User;
import ca.mcgill.mchacks2018.noq.model.Location;
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
	private LocationDto convertToDto(Location l) {
		return modelMapper.map(l, LocationDto.class);
	}

	private UserDto convertToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	public JSONObject emptyJSON() {
		try {
			return new JSONObject("{}");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}

	@GetMapping(value = {"/users", "/users/"})
	public List<UserDto> findAllUsers() {
		List<UserDto> users = new ArrayList<UserDto>();
		for (User user : service.findAllUsers())
			users.add(convertToDto(user));
		return users;
	}

	@GetMapping(value = {"/locations", "/locations/"})
	public List<LocationDto> findAllLocations() {
		List<LocationDto> locations = new ArrayList<LocationDto>();
		for (Location location : service.findAllLocations())
			locations.add(convertToDto(location));
		return locations;
	}

	@GetMapping(value = {"/users/{username}", "/users/{username}/"})
	public UserDto getUserByName(@PathVariable("username") String username) throws InvalidInputException {
		User user = service.getUserByName(username);
		return convertToDto(user);
	}

	@GetMapping(value = {"/locations/{id}", "/locations/{id}/"})
	public LocationDto getLocationById(@PathVariable("id") String id) throws InvalidInputException {
		Location location = service.getLocationById(id);
		return convertToDto(location);
	}

	@PostMapping(value = {"/users/{username}", "/users/{username}/"})
	public UserDto createUser(@PathVariable("username") String username,
							  @RequestParam String password,
							  @RequestParam int age,
							  @RequestParam(value="points", defaultValue="0") int points
							  ) throws InvalidInputException {
		User user = service.createUser(username, password, age, points, emptyJSON());
		return convertToDto(user);
	}

	@PostMapping(value = {"/locations/{id}", "/locations/{id}/"})
	public LocationDto createLocation(@PathVariable ("id") String id,
								   	  @RequestParam String name,
								   	  @RequestParam String strtNum,
									  @RequestParam String address,
									  @RequestParam(value="qTime", defaultValue="-1") int qTime
								   	  ) throws InvalidInputException {
		Location location = service.createLocation(id, name, strtNum, address, qTime, emptyJSON());
		return convertToDto(location);
	}

	@DeleteMapping(value = {"/reset", "/reset/"})
	public void resetDatabase() {
		service.resetDatabase();
	}
}
