package ca.mcgill.mchacks2018.noq.dto;

public class LocationDto {

    private String id;
    private String name;
    private String strtNum;
    private String address;
    private int qTime;
    private String checkTimes;

    public LocationDto() {
    }

    public LocationDto(String id, String name, String strtNum, String address) {
        this(id, name, strtNum, address, -1, "{}");
    }

    public LocationDto(String id, String name, String strtNum, String address, int qTime, String checkTimes) {
        this.id = id;
        this.name = name;
        this.strtNum = strtNum;
        this.address = address;
        this.qTime = qTime;
        this.checkTimes = checkTimes;
    }

    public String getId() {
        return id;
	}

    public String getName() {
        return name;
    }

	public String getStrtNum() {
		return strtNum;
	}

	public String getAddress() {
		return address;
	}

	public int getQTime() {
		return qTime;
	}

	public String getCheckTimes() {
		return checkTimes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStrtNum(String strtNum) {
		this.strtNum = strtNum;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setQTime(int qTime) {
		this.qTime = qTime;
	}

	public void setCheckTimes(String checkTimes) {
		this.checkTimes = checkTimes;
	}
}
