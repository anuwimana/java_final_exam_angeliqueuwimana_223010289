package com.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{
	private static final String URL="jdbc:mysql://localhost:3306/hospitalityMS_db";
	private static final String USER="root";
	private static final String PASSWORD="";
	
	private static Connection connection=null;
	public static Connection getConnection(){
		try{
			if (connection== null || connection.isClosed()){
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(URL,USER,PASSWORD);
				System.out.println("(yes)connected to hospitalityMS_db successfully!");
			}
			
		}catch (ClassNotFoundException | SQLException e){
			System.out.println("(no)Database connection failed:" + e.getMessage());
			
		}
		return connection;
	}
	
	
	
}