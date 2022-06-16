package com.libraryManagement;
import java.util.regex.*;
import java.sql.*;
import java.util.Scanner;

public class AdminControl {
	Scanner sc = new Scanner(System.in);
	public Connection getConnection() throws SQLException{
		Connection con = null;
		try {
			String url = "jdbc:mysql://localhost:3306/library";
			String user = "root";
			String password = "Teppala@123";
			con = DriverManager.getConnection(url,user,password);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}
	
	public void AdminLogin() throws SQLException {
		System.out.println("-------Enter Admin Details------");
		System.out.println("Enter AdminName: ");
		String user = sc.next();
		System.out.println("Enter Password: ");
		String password = sc.next();
		
		if(user.equals("Admin") && password.equals("Admin@123")) {
			System.out.println();
			System.out.println("****** Admin Login Successfully ******");
			adDashboard();
			
		}
		else {
			System.out.println("________Invalid Admin Details_________");
		} 
		
	}
	
	public void option() {   
		// creating options
		System.out.println();
		System.out.println("press 1 to Add Libraian");
		System.out.println("press 2 to View Libraian");
		System.out.println("press 3 to Delete Libraian");
		System.out.println("press 4 to MainMenu");
		System.out.println("Enter your response");
	}
	
	public void adDashboard() throws SQLException {
		option(); // calling options
		int n = sc.nextInt();

		while (n>0 && n<=3) {
			switch(n) {
			case 1:addLibrarian();
				option(); // calling options
				n = sc.nextInt();
				break;
			case 2: ViewLibrarian();
				option(); // calling options
				n = sc.nextInt();
				break;
			case 3: DeleteLibrarian();		
				option(); // calling options
				n = sc.nextInt();
				break;
			}
		}
	}

	
	public void addLibrarian() throws SQLException {
		Connection con = getConnection();
		System.out.println("****** Enter Librarian Details *******");
		
		System.out.println("Enter Name");
		String lName = sc.next();
		
		System.out.println("Set Password");
		String lPassword = sc.next();
		
		System.out.println("Enter Mobile Number");
		long lNumber = sc.nextLong();
		
		String q0 = "select * from librarian where name=?";
		String q1 = "insert into librarian(name,password,mobile) values(?,?,?)";
		
		PreparedStatement pst = con.prepareStatement(q0);
		pst.setString(1, lName);
		ResultSet rst = pst.executeQuery();
		if(rst.next()) {
			System.out.println("---------Librarian Name already exists--------");
		}else {
			PreparedStatement pst2 = con.prepareStatement(q1,Statement.RETURN_GENERATED_KEYS);
			pst2.setString(1, lName);
			pst2.setString(2, lPassword);
			pst2.setLong(3, lNumber);
			pst2.executeUpdate();
			pst2.getGeneratedKeys();
			
			System.out.println("Librarian Added Successfully");
			String q3 = "select lid from librarian where name=?";
			PreparedStatement pst3 = con.prepareStatement(q3);
			pst3.setString(1, lName);
			ResultSet rst2 = pst3.executeQuery();
			if(rst2.next()) {
				
				System.out.println("This is your ID = "+rst2.getString(1));
			}
		}
		con.close();	
	}
	
	public void ViewLibrarian() throws SQLException {
		Connection con = getConnection();
		Statement smt = con.createStatement();
		ResultSet rs=smt.executeQuery("select * from librarian");
		while(rs.next()) {
			System.out.println("id: "+rs.getString(1)+" Name: "+rs.getString(2)+" Password: "+rs.getString(3)+" mobile: "+rs.getString(4));
	
		}
	}
	
	public void DeleteLibrarian() throws SQLException{
		Connection con = getConnection();
		System.out.println("Enter Librarian ID: ");
		int id = sc.nextInt();
		String q0 = "select * from librarian where lid=?";
		String q1 = "delete from librarian where lid=?";
		
		PreparedStatement pst = con.prepareStatement(q0);
		pst.setInt(1, id);
		ResultSet rst = pst.executeQuery();
		if(rst.next()) {
			PreparedStatement pst2 = con.prepareStatement(q1);
			pst2.setInt(1, id);
			pst2.executeUpdate();
			System.out.println("Librarian Deleted Successfully");
		}else {
			System.out.println("///......Please check Librarian ID "+"......\\");
		}
		
	}
}









