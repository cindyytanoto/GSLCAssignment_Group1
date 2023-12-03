package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.Team;
import model.User;

public class Connection {
	private ArrayList<User> userList = new ArrayList<>();
	private ArrayList<Team> teamList = new ArrayList<>();

	private static Connection instance;

	private Connection() {

	}

	public static Connection getInstance() {
		if (instance == null) {
			instance = new Connection();
		}
		return instance;
	}

	public ArrayList<User> readUser() {
		
		userList.clear();
		String line = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/user.csv"));
			reader.readLine(); // untuk read header
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String nim = parts[0];
				String userName = parts[1];
				int idTeam = Integer.parseInt(parts[2]);
				userList.add(new User(userName, nim, idTeam));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userList;

	}

	public void writeUser(String nim, String userName, int idTeam) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/user.csv", true));
			writer.write(nim + "," + userName + "," + idTeam);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Team> readTeam() {
		teamList.clear();
		String line = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/teams.csv"));
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				int idTeam = Integer.parseInt(parts[0]);
				String teamName = parts[1];
				teamList.add(new Team(idTeam, teamName));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return teamList;

	}
	
	public void writeTeam(int idTeam, String teamName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/teams.csv", true));
			writer.write(idTeam + "," + teamName + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	public ArrayList<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(ArrayList<Team> teamList) {
		this.teamList = teamList;
	}

	

}
