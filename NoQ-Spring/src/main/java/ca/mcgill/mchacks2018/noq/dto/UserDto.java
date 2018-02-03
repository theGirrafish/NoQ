package ca.mcgill.mchacks2018.noq.dto;

public class UserDto {

	private String username;
	private String password;
	private int age;
	private int points;

	public UserDto() {
	}

	public UserDto(String username, String password, int age) {
		this(username, password, age, 0);
	}

	public UserDto(String username, String password, int age, int points) {
		this.username = username;
		this.password = password;
		this.age = age;
		this.points = points;
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
}
