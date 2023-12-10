package model;

public class Team extends Model {
	private int idTeam;
	private String teamName;

	public Team(int idTeam, String teamName) {
		this.idTeam = idTeam;
		this.teamName = teamName;
	}

	public boolean matchesCondition(String columnName, String operator, String conditionValue) {
		switch (columnName) {
		case "idTeam":
			return evaluateCondition(Integer.toString(this.idTeam), operator, conditionValue);
		case "teamName":
			return evaluateCondition(this.teamName, operator, conditionValue);
		default:
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("Team Name : %s, Team ID : %d", teamName, idTeam);
	}

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

}
