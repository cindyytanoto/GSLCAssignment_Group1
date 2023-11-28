package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Main {
	int idx = 1;
	Scanner sc = new Scanner(System.in);
	String filePathTeams = "C:\\Users\\user\\OneDrive - Bina Nusantara\\CODING JAVA\\GSLCAssignment_Group1-master\\src\\teams.csv";
	String filePathUser = "C:\\Users\\user\\OneDrive - Bina Nusantara\\CODING JAVA\\GSLCAssignment_Group1-master\\src\\user.csv";



	public void writeUSer(){
		String nim;
		String nama;
		String idTeam;

		 try (FileWriter writer = new FileWriter(filePathUser, true)) {
			System.out.print("Masukin NIM : ");
			nim = sc.nextLine();
			System.out.print("Masukin Nama : ");
			nama = sc.nextLine();
			System.out.print("ID Team : ");
			idTeam = sc.nextLine();
			writer.write(nim + "," +nama +"," + idTeam\n);

            System.out.println("User Created!");

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void writeTeam(){
		String nama;
		String idTeam;

		 try (FileWriter writer = new FileWriter(filePathTeams, true)) {
			System.out.print("Masukin Nama : ");
			nama = sc.nextLine();
			writer.write(idx+","+nama+"\n");
            System.out.println("Team Created!");

        } catch (IOException e) {
            e.printStackTrace();
        }
		idx++;
			
	}
	public void insertData(){
		int input = 0;
		String team;
		do{
			System.out.println("Which table to insert? 1. User , 2. Team.");
			try {
				input = sc.nextInt(); sc.nextLine();
			} catch (Exception e) {
				input = 0; sc.nextLine();
			}
			
			if(input == 1){
				writeUSer();
			}else if(input == 2){
				writeTeam();
			}
		}while(input != 0);
		
	}

	public void show(){
      try (Scanner scanner = new Scanner(new File(filePathTeams))) {
            // Setel pemisah (delimiter) sesuai dengan format CSV Anda
            scanner.useDelimiter(",");

            // Baca baris per baris
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Proses data sesuai kebutuhan Anda
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
	public void mainMenu(){
		int input = 0;
		do{
			System.out.println("==================");
			System.out.println("1. Menu Utama");
			System.out.println("2. Insert Data");
			System.out.println("3. Show ");
			System.out.println("4. Exit");
			try {
				input = sc.nextInt(); sc.nextLine();
			} catch (Exception e) {
				input = 1;
			}
			
			if(input == 2){
				insertData();
			}
			else if(input == 3){
				show();
			}
		}while(input != 4);
		
	}


	public Main() {
		mainMenu();
	}
	public static void main(String[] args) {
		new Main(); 

	}

}
