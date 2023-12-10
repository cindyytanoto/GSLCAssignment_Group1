package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Team;
import model.User;
import repository.TeamRepository;
import repository.UserRepository;
import utility.Connection;

public class Main {
	Connection conn = Connection.getInstance();

	private int idx;
	private Scanner sc = new Scanner(System.in);
	private UserRepository userRepository = new UserRepository();
	private TeamRepository teamRepository = new TeamRepository();

	public Main() throws IOException {
		mainMenu();
		sc.close();
	}

	public void writeTeam() {
		String teamName;
	    System.out.print("add name : ");
	    teamName = sc.nextLine();

	    if (isTeamNameUnique(teamName)) {
	        ArrayList<Team> teamList = conn.readTeam();
	        int idTeam = teamList.size() + 1; 

	        teamRepository.insert(new Team(idTeam, teamName), conn);
	        System.out.println("Team Created!");
	    } else {
	        System.out.println("Error: Team name already exists. Please choose a unique team name.");
	    }
	}

	public void writeUser() {
		String nim, name, teamName;

		System.out.print("add name: ");
		name = sc.nextLine();
		System.out.print("add nim: ");
		nim = sc.nextLine();

		System.out.print("add team name: ");
		teamName = sc.nextLine();
		int idTeam = getTeamIdByName(teamName);

		if (idTeam == -1) {
			System.out.println("Error: Team not found.");
			writeTeam();
		} else if (isTeamFull(idTeam)) {
			System.out.println("Error: Team is full.");
		} else {
			userRepository.insert(new User(name, nim, idTeam), conn);
			System.out.println("User Created! (ID: " + idx + ")");
		}
	}

	private int getTeamIdByName(String teamName) {
		ArrayList<Team> teamList = conn.readTeam();
		for (Team team : teamList) {
			if (teamName.equals(team.getTeamName())) {
				return team.getIdTeam();
			}
		}
		return -1;
	}

	private boolean isTeamFull(int idTeam) {
		int userCount = 0;
		ArrayList<User> userList = conn.readUser();

		for (User user : userList) {
			if (idTeam == user.getIdTeam()) {
				userCount++;
			}
		}

		return userCount >= 3;
	}
	
	private boolean isTeamNameUnique(String teamName) {
	    ArrayList<Team> teamList = conn.readTeam();

	    for (Team team : teamList) {
	        if (teamName.equalsIgnoreCase(team.getTeamName())) {
	            return false;
	        }
	    }

	    return true; 
	}

	public void insertData() {
		int input = 0;
		do {
			System.out.println("Which table to insert? 1. User , 2. Team, 0. Exit");
			try {
				String inputStr = sc.nextLine();
				input = Integer.parseInt(inputStr);

				switch (input) {
				case 1:
					writeUser();
					break;
				case 2:
					writeTeam();
					break;
				case 0:
					break;
				default:
					System.out.println("Invalid input. Please enter 1, 2, or 0.");
					break;
				}
			} catch (NumberFormatException e) {
				System.err.println("Invalid input. Please enter 1, 2, or 0.");
			}
		} while (input != 0);
	}

	public void showMenu() {
	    System.out.println("Which table to show? 1. User, 2. Team.");
	    int tableChoice = sc.nextInt();
	    sc.nextLine();

	    String columnName = null;
	    String operator = null;
	    String keyword = null;

	    System.out.println("Want to filter by condition? 1. Yes, 2. No.");
	    int filterChoice = sc.nextInt();
	    sc.nextLine();

	    if (filterChoice == 1) {
	        System.out.println("Enter filter condition (columnName operator keyword), separated by space:");
	        String filterCondition = sc.nextLine();
	        String[] conditionParts = filterCondition.split(" ");

	        if (conditionParts.length == 3) {
	            columnName = conditionParts[0].trim();
	            operator = conditionParts[1].trim();
	            keyword = conditionParts[2].trim();
	        } else {
	            System.out.println("Invalid condition format. Please enter a valid condition.");
	            return;
	        }
	    }

	    switch (tableChoice) {
	        case 1:
	            userRepository.show(columnName, operator, keyword, conn);
	            break;
	        case 2:
//	        	System.out.println("dah masok");
	            teamRepository.show(columnName, operator, keyword, conn);
	            break;
	        default:
	            System.out.println("Invalid table choice. Please enter 1 or 2.");
	            break;
	    }
	}

	public void mainMenu() {
		int input = 0;
		do {
			System.out.println("==================");
			System.out.println("1. Menu Utama");
			System.out.println("2. Insert Data");
			System.out.println("3. Show ");
			System.out.println("4. Exit");

			try {
				input = sc.nextInt();
				sc.nextLine();
				switch (input) {
				case 2:
					insertData();
					break;
				case 3:
					System.out.println("Teams:");
					showMenu();
					System.out.println("Users:");
					showMenu();
					break;
				case 4:
					System.out.println("Exiting program. Goodbye!");
					break;
				default:
					System.out.println("Invalid input. Please enter 2, 3, or 4.");
					break;
				}
			} catch (Exception e) {
				sc.nextLine();
			}
		} while (input != 4);
	}

	public static void main(String[] args) throws IOException {
		new Main();
	}
}
