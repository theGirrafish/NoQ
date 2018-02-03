package ca.mcgill.mchacks2018.noq.dto;

public class RegistrationDto {

	private UserDto user;
	private EventDto event;

	public RegistrationDto() {
	}

	public RegistrationDto(UserDto user, EventDto event) {
		this.user = user;
		this.event = event;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public EventDto getEvent() {
		return event;
	}

	public void setEvent(EventDto event) {
		this.event = event;
	}
}
