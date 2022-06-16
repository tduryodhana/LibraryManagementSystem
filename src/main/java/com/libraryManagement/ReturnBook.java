package com.libraryManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReturnBook {
	
	Scanner sc = new Scanner(System.in);
	int bookid = 0;
	public void return_book() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","Teppala@123");
			System.out.println("Enter Book Id");
			bookid = sc.nextInt();
			System.out.println("Enter Student id");
			int stdid = sc.nextInt();
			
			PreparedStatement stmt = con.prepareStatement("select * from studentDetails where bookId=? AND Stdid=?");
			stmt.setInt(1, bookid);
			stmt.setInt(2, stdid);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				PreparedStatement stmt2=con.prepareStatement("delete from studentDetails where bookId=? and Stdid=?");
				stmt2.setInt(1, bookid);
				stmt2.setInt(2, stdid);
				stmt2.executeUpdate();
				System.out.println("--------Book returned Successfully-------");
				update_issued_books();
					
			} 
			else {
				System.out.println("invalid bookId & student Id");
			}
	
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	public void update_issued_books() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","Teppala@123");
			int quantity = 0;
		    int issued = 0;
		    PreparedStatement stmt3 = con.prepareStatement("select quantity,issued from bookStore where bookId=?");
		    stmt3.setInt(1, bookid);
		    ResultSet rs3 = stmt3.executeQuery();
		    
		    if(rs3.next()) {
		    	quantity = rs3.getInt("quantity")+1;
		    	issued = rs3.getInt("issued")-1;
			    PreparedStatement stmt4 = con.prepareStatement("update bookStore set quantity=?,issued=? where bookId=?");
			    stmt4.setInt(1, quantity);
			    stmt4.setInt(2, issued);
			    stmt4.setInt(3, bookid);
			    stmt4.executeUpdate();
		    	
		    }
			
		} catch(SQLException sl) {
			System.out.println(sl);
		}
	}
}
