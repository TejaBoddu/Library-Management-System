package com.bookAssignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SystemLibrary {
	
	public static void main(String[] args) throws SQLException {
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/teja_database?user=root&password=12345";
			
			Connection conn = DriverManager.getConnection(url);
			
			boolean start = true;
			
			Scanner scan = new Scanner(System.in);
			
			while(start) {
				System.out.println("Library Management System Menu:");
                System.out.println("1. Add Book");
                System.out.println("2. Search Book by Book Id");
                System.out.println("3. Search Book by Book Title");
                System.out.println("4. Remove Book");
                System.out.println("5. Exit");
                System.out.println();
                
                int choice = scan.nextInt();
                
                
                switch (choice) {
				case 1: {
					addBook(conn, scan);
					
					break;
				}
				case 2: {
					searchBookById(conn, scan);
					break;
				}
				case 3:{
					searchBookByTitle(conn, scan);
					break;
				}
				case 4:{
					removeBook(conn, scan);
					break;
				}
				case 5:{
					start = false;
					System.out.println("Thank You. GoodBye..");
				}
				break;
				default: System.out.println("Invalid Choice. Please select a valid Choice..");
			
				}
			}
			conn.close();
			scan.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
	}
	
	private static void addBook(Connection conn, Scanner scan) throws SQLException {
		
		String insertQuery = "insert into teja_database.book values(?,?,?,?,?)";
		
		
		System.out.print("Enter Book Id: ");
		int bookId = scan.nextInt();
		scan.nextLine();
		
		
		System.out.print("Enter Book Title: ");
		String bookTitle = scan.nextLine();
		
		System.out.print("Enter Author Name: ");
		String authorName = scan.nextLine();
		
		System.out.print("Enter Price: ");
		double price = scan.nextDouble();
		
		System.out.print("Enter Pages: ");
		int pages = scan.nextInt();
		
		PreparedStatement pstmt = conn.prepareStatement(insertQuery);
		
		pstmt.setInt(1, bookId);
		pstmt.setString(2, bookTitle);
		pstmt.setString(3, authorName);
		pstmt.setDouble(4, price);
		pstmt.setInt(5, pages);
		
		int rowsAdded = pstmt.executeUpdate();
		
		if(rowsAdded > 0) {
			System.out.println("Book added Sucessfully..");
		}
		else {
			System.err.println("Failed to add the book!");
		}
	}
	
	private static void searchBookById(Connection conn, Scanner scan) throws SQLException {
		
		System.out.print("Enter Book Id: ");
		int bookId = scan.nextInt();
		
		
		String selectQuery = "select* from book where bookId = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(selectQuery);
		pstmt.setInt(1, bookId);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()){
			
			System.out.println("Book Id: " + rs.getInt("bookId"));
            System.out.println("Book Title: " + rs.getString("bookTittle"));
            System.out.println("Author Name: " + rs.getString("authorName"));
            System.out.println("Price: " + rs.getDouble("price"));
            System.out.println("Pages: " + rs.getInt("pages"));
            System.out.println("***********************************");
			
		}
		else {
			System.err.println("Book with Book Id :"+bookId+" not found!");
		}
	}
	
	private static void searchBookByTitle(Connection conn, Scanner scan) throws SQLException {
	    System.out.print("Enter Book Title: ");
	    scan.nextLine(); // Consume the newline character left from previous input
	    String bookTitle = scan.nextLine();
	    
	    String searchQuery = "select * from book where bookTittle = ?";
	    PreparedStatement pstmt = conn.prepareStatement(searchQuery);
	    pstmt.setString(1, bookTitle);
	    
	    ResultSet rs = pstmt.executeQuery();
	    if (rs.next()) {
	        System.out.println("***********************************");
	        System.out.println("Book Id: " + rs.getInt("bookId"));
	        System.out.println("Book Title: " + rs.getString("bookTittle"));
	        System.out.println("Author Name: " + rs.getString("authorName"));
	        System.out.println("Price: " + rs.getDouble("price"));
	        System.out.println("Pages: " + rs.getInt("pages"));
	        System.out.println("***********************************");
	    } else {
	        System.err.println("Book Not Found!");
	    }
	}

	
	private static void removeBook(Connection conn, Scanner scan) throws SQLException {
		
		System.out.print("Enter Book Id to remove: ");
		int bookId = scan.nextInt();
		
		
		String deleteQuery = "delete from book where bookId = ?";
		PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
		
		pstmt.setInt(1, bookId);
		
		int deletedQuery = pstmt.executeUpdate();
		
		if(deletedQuery > 0) {
			System.out.println("Book with book Id "+bookId+" removed successfully..");
		}
		else {
			System.err.println("Book with book Id "+bookId+" not found!");
		}
		
	}
}
