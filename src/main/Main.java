package main;import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private int idx;
    private Scanner sc = new Scanner(System.in);
    private String filePathTeams = "C:\\Kuliah Sem 3\\OOP\\TUgas Akhir Dan GSLC\\GSLCAssignment_Group1-master\\src\\teams.csv";
    private String filePathUser = "C:\\Kuliah Sem 3\\OOP\\TUgas Akhir Dan GSLC\\GSLCAssignment_Group1-master\\src\\user.csv";

    public Main() {
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
            nim = sc.nextLine();
            System.out.print("add nim: ");
            nama = sc.nextLine();

            // Automatically detect the team ID when adding a user
            System.out.print("add team name: ");
            String teamName = sc.nextLine();
            idTeam = getTeamIdByName(teamName);

            if (idTeam == null) {
                System.out.println("Error: Team not found. Please create the team first.");
            } else if (isTeamFull(idTeam)) {
                System.out.println("Error: Team is full. Cannot add more users to this team.");
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

    public void show(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading data: " + e.getMessage());
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
                        show(filePathTeams);
                        System.out.println("Users:");
                        show(filePathUser);
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

    public static void main(String[] args) {
        new Main();
    }
}
