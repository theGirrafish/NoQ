package ca.mcgill.mchacks2018.noq.dto;

import org.json.JSONObject;

public class UserDto {

	private String username;
	private String password;
	private int age;
	private int points;
	private JSONObject favs;

	public UserDto() {
	}

	public UserDto(String username, String password, int age) {
		this(username, password, age, 0, emptyJSON());
	}

	public UserDto(String username, String password, int age, int points, JSONObject favs) {
		this.username = username;
		this.password = password;
		this.age = age;
		this.points = points;
		this.favs = favs;
	}

	public static JSONObject emptyJSON() {
		try {
			return new JSONObject("{}");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getAge() {
		return age;
	}

	public int getPoints() {
		return points;
	}

	public JSONObject getFavs() {
		return favs;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setFavs(JSONObject favs) {
		this.favs = favs;
	}
}
