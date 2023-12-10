package model;

public class User extends Model {
	private String userName;
	private String nim;
	private int idTeam;

	public User(String userName, String nim, int idTeam) {
		this.idTeam = idTeam;
		this.userName = userName;
		this.nim = nim;

	}

	public boolean matchesCondition(String conditionColumn, String operator, String conditionValue) {
		switch (conditionColumn) {
		case "userName":
			return evaluateCondition(this.userName, operator, conditionValue);
		case "nim":
			return evaluateCondition(this.nim, operator, conditionValue);
		case "idTeam":
			return evaluateCondition(Integer.toString(this.idTeam), operator, conditionValue);
		default:
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("Username : %s, NIM : %s, Team ID : %d", userName, nim, idTeam);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNim() {
		return nim;
	}

	public void setNim(String nim) {
		this.nim = nim;
	}

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

}
