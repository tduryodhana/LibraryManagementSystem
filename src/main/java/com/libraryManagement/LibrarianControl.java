package com.libraryManagement;

import java.sql.*;
import java.util.Scanner;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

public class LibrarianControl {
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
	
	String lid = null;
	public void Liblogin() throws SQLException {
		Connection con = getConnection();
		System.out.println(".....Welcome to Librarian Login Page.....");
		
		System.out.println("Enter Id: ");
		lid = sc.next();
		
		System.out.println("Enter Password: ");
		String password = sc.next();
		
		String q1 = "select * from librarian where lid=? AND password=?";
		try 
		{
			PreparedStatement stmt = con.prepareStatement(q1);
			stmt.setString(1, lid);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				
				System.out.println("************************************");
				System.out.println("*********** Login Success ************");
				LibDashboard();
			}else {
				System.out.println("********** Invalid Login Details ***********");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void libraryOptions() {
		System.out.println();
		System.out.println("press 1 to Add Books");
		System.out. println("press 2 to View Books");
		System.out.println("Press 3 to issue Books");
		System.out.println("Press 4 to view issued Books");
		System.out.println("Press 5 to return Books");
		System.out.println("Press 6 Back to Menu");
	}
	
	public void LibDashboard() throws SQLException {
		IssueBooks ib = new IssueBooks();
		ReturnBook rb = new ReturnBook();
		
		libraryOptions();
		try {
			int k = sc.nextInt();
			while (k>0 && k<=5) {
				switch(k) {
				case 1:addbooks();
				libraryOptions();
					k = sc.nextInt();
					break;
				case 2:viewBooks() ;
				libraryOptions();
					k = sc.nextInt();
					break;
				case 3:ib.issueBook();
				libraryOptions();
					k = sc.nextInt();
					break;
				case 4:ib.viewIssuedBooks();
				libraryOptions();
					k = sc.nextInt();
					break;
				case 5:rb.return_book();
				libraryOptions();
					k = sc.nextInt();
					break;
				}
			}
		}catch(SQLException n) {
			System.out.println("*___________Input mismatch____________*");
		}
	}
	
	
	public void addbooks() throws SQLException {
		try {
			System.out.println("ENTER BOOK NAME");
			String bookname = sc.next();
			System.out.println("ENTER AUTHOR NAME");
			String authorname = sc.next();
			System.out.println();
			System.out.println("ENTER DATE yyyy-mm-dd Format");
			String date = sc.next();
			System.out.println();
			System.out.println("ENTER QUANTITY");
			int quantity = sc.nextInt();
	
			Connection con = getConnection();
			String sql = "insert into bookstore(bName, bAuthor,added_date,quantity,lid) values(?,?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1,bookname);
			statement.setString(2,authorname);
			statement.setString(3,date);
			statement.setInt(4, quantity);
			statement.setInt(5,Integer.parseInt(lid));
	
			statement.executeUpdate();
			statement.getGeneratedKeys();
			System.out.println(".......Book Added Successfully......");
		}catch(MysqlDataTruncation m) {
			System.out.println("Book Not Added Successfully.......");
			System.out.println("Please Enter Valid Date Format ......");
		}
	}
	
	
	public void viewBooks() throws SQLException{
		Connection con = getConnection();
		String vv = "select * from bookStore where lid=?";
		
		PreparedStatement pst = con.prepareStatement(vv);
		pst.setInt(1,Integer.parseInt(lid));
		pst.executeQuery();
		ResultSet rst = pst.executeQuery();
		while(rst.next()) {
			System.out.println("id: "+rst.getString(1)+" Name: "+rst.getString(2)+" Author: "+rst.getString(3)+" Quantity: "+rst.getString(4)+" Date: "+rst.getDate(5)+" issued: "+rst.getString(7));
		}
	}
}





