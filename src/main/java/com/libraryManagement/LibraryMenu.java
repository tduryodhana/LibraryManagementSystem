package com.libraryManagement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryMenu {
	
	static void options() {
		System.out.println("******** MAIN MENU ********");
		System.out.println("Press 1 to Admin Login");
		System.out.println("Press 2 to Libraian Login");
		System.out.println("Enter your choice.....");
	}
	
	public static void main(String[] args) throws SQLException {
		Scanner sc = new Scanner(System.in);
		AdminControl lc = new AdminControl();
		LibrarianControl lv = new LibrarianControl();
		
		try{
			String s = "y";
			while (s.equals("y")) {
				options();
				int n= sc.nextInt();
				switch(n) {
				case 1:lc.AdminLogin();
					break;
				case 2:lv.Liblogin();
					break;
				default: System.out.println("//________wrong choice________//");
				}
				System.out.println("Press y To Menu");
				System.out.println("Press n to EXIT");
				s = sc.next();
			}
		}catch (InputMismatchException i) {
				System.out.println(".....input mismatch.....");
			}
	}

}
