package com.libraryManagement;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

public class IssueBooks {
	
	Scanner sc = new Scanner(System.in);

	LibrarianControl lo = new LibrarianControl();
	int quantity = 0;
    int issued = 0;
    int Std_issued = 0;
    
    String bookid = null;
	public void issueBook()throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","Teppala@123");
		System.out.println("________Books in Library_______");
		  // book view
		System.out.println("Select Book ");
		System.out.println("Enter Book id: ");
		
		try 
		{
			int bookid = sc.nextInt();
			/*int bookid[] = new int[3];
			for(int i=0;i<bookid.length;i++) {
				bookid[i] = sc.nextInt(); 
			} */
	 		System.out.println("Enter Student Id");
			int stdId = sc.nextInt();
			PreparedStatement stmt = con.prepareStatement("select * from studentDetails where Stdid=?");
			stmt.setInt(1, stdId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				System.out.println("Student Id "+stdId+" already exists");
			}else {
				System.out.println("Enter Student Name");
				String sName = sc.next();
				
				System.out.println("Enter Student Contact ");
				long sContact = sc.nextLong();
				
				System.out.println("Enter issued_date:");
				String sDate = sc.next();
				
					String s1 = "insert into studentDetails(Stdid,stdName,StdContact,issu_date,bookId,issue_books) values(?,?,?,?,?,?)";
				
					PreparedStatement pst = con.prepareStatement(s1);
					pst.setInt(1, stdId);
					pst.setString(2, sName);
					pst.setLong(3, sContact);
					pst.setString(4, sDate);
					pst.setInt(5, bookid);
					pst.setInt(6, Std_issued+1);
					pst.executeUpdate();
					System.out.println("Book Issued Successfully");
					
					String s2 = "select quantity,issued from bookStore where bookId=?";
					PreparedStatement ps3 = con.prepareStatement(s2);
					ps3.setInt(1, bookid);
					ResultSet rs1=ps3.executeQuery();
					
					if(rs1.next()){
						
						quantity=rs1.getInt("quantity")-1;
						issued=rs1.getInt("issued")+1;
						
						PreparedStatement ps4 = con.prepareStatement("update bookStore set quantity=?,issued=? where bookId=?");
						ps4.setInt(1, quantity);
						ps4.setInt(2, issued);
						ps4.setInt(3, bookid);
						ps4.executeUpdate();
					}
			}
		}
		catch(SQLIntegrityConstraintViolationException k) {
			System.out.println("Please Enter Valid BookId.....****");
		}
		catch(MysqlDataTruncation m) {
			System.out.println("Please Enter Valid Date Format ......");
		}catch (InputMismatchException i) {
			System.out.println("input mismatch.....");
		}
	}
	
	public void viewIssuedBooks() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","Teppala@123");
		Statement smt = con.createStatement();
		ResultSet rs= smt.executeQuery("select * from studentdetails");
		if(rs.next()) {
			while(rs.next()) {
				System.out.println("id: "+rs.getString(1)+" Name: "+rs.getString(2)+" Contact: "+rs.getString(3)+" Date: "+rs.getString(4)+" BookId:"+rs.getString(5));
			}
		}else {
			System.out.println("_________No Books Issued_______");
		}
	}
}
