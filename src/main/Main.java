package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Respository.TeamRespository;
import Respository.UserRespository;
import utility.Connection;

public class Main {
    private int idx;
    private Scanner sc = new Scanner(System.in);
    private String filePathTeams = "src/teams.csv";
    private String filePathUser = "src/user.csv";
	UserRespository userRespository = new UserRespository("src/user.csv");
    TeamRespository teamRespository = new TeamRespository("src/teams.csv");
    	

	public Main() throws IOException {
		Connection conn = Connection.getInstance(filePathTeams);
		BufferedReader reader = conn.openFile();
		// conn.closeFile(reader);

        idx = getTeamCount(filePathTeams);
        mainMenu();
        sc.close();
    }

    private int getTeamCount(String filePath) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error counting teams: " + e.getMessage());
        }
        return count;
    }

    public void writeTeam() {
        String nama;
        try (FileWriter writer = new FileWriter(filePathTeams, true)) {
            System.out.print("add name : ");
            nama = sc.nextLine();
            writer.write(idx + "," + nama + "\n");
            System.out.println("Team Created! (ID: " + idx + ")");
            idx++;
        } catch (IOException e) {
            System.err.println("Error writing team data: " + e.getMessage());
        }
    }

    public void writeUser() {
        String nim, nama, idTeam;

        try (FileWriter writer = new FileWriter(filePathUser, true)) {
            System.out.print("add name: ");
            nama = sc.nextLine();
            System.out.print("add nim: ");
            nim = sc.nextLine();

            
            System.out.print("add team name: ");
            String teamName = sc.nextLine();
            idTeam = getTeamIdByName(teamName);

            if (idTeam == null) {
                System.out.println("Error: Team not found.");
                writeTeam();
            } else if (isTeamFull(idTeam)) {
                System.out.println("Error: Team is full.");
            } else {
                writer.write(nim + "," + nama + "," + idTeam + "\n");
                System.out.println("User Created! (ID: " + idx + ")");
            }
        } catch (IOException e) {
            System.err.println("Error writing user data: " + e.getMessage());
        }
    }

    private String getTeamIdByName(String teamName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathTeams))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] teamData = line.split(",");
                if (teamData.length >= 2 && teamName.equals(teamData[1])) {
                    return teamData[0];
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting team ID by name: " + e.getMessage());
        }
        return null;
    }

    private boolean isTeamFull(String idTeam) {
        int userCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathUser))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 3 && idTeam.equals(userData[2])) {
                    userCount++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking team size: " + e.getMessage());
        }
        return userCount >= 3;
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

    System.out.println("Want to filter by condition? 1. Yes, 2. No.");
    int filterChoice = sc.nextInt();
    sc.nextLine();  

    String columnName = null;
    String operator = null;
    String keyword = null;

    if (filterChoice == 1) {
        System.out.println("Add condition, separate by semicolon.");
        String condition = sc.nextLine();

        // Split the condition by semicolon
        String[] conditionParts = condition.split(";");
        if (conditionParts.length == 3) {
            columnName = conditionParts[0].trim();
            operator = conditionParts[1].trim();
            keyword = conditionParts[2].trim();
        } else {
            System.out.println("Invalid condition format. Please enter a valid condition.");
            return;
        }
    }

    // Now you have columnName, operator, and keyword.
    // You can use these in your repository method call.
    // For example:
    // userRepository.find(columnName, new String[]{operator, keyword}, false, null, conn);

    switch (tableChoice) {
        case 1:
            // Show users
            System.out.println("Users:");
            userRespository.show(columnName, operator, keyword);
            break;
        case 2:
            // Show teams
            System.out.println("Teams:");
            teamRespository.show(columnName, operator, keyword);
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
