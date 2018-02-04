package ca.mcgill.mchacks2018.noq.dto;

public class UserDto {

	private int id;
	private String username;
	private String password;
	private int age;
	private int points;
	private String favs;

	public UserDto() {
	}

	public UserDto(int id, String username, String password, int age) {
		this(id, username, password, age, 0, "{}");
	}

	public UserDto(int id, String username, String password, int age, int points, String favs) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.age = age;
		this.points = points;
		this.favs = favs;
	}

	public int getId() {
		return id;
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

	public String getFavs() {
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

	public void setFavs(String favs) {
		this.favs = favs;
	}
}
