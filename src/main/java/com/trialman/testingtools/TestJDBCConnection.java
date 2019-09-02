package com.trialman.testingtools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBCConnection {

	public static void main(String[] args) {

		String jdbcUrl = "jdbc:postgresql://localhost:5432/projekt?useSSL=false";
		String user = "admin";
		String password = "admin";

		try {
			System.out.println("Start connection to Database");
			Connection myConnection = DriverManager.getConnection(jdbcUrl, user, password);
			Statement statement = myConnection.createStatement();
			statement.execute("INSERT INTO animal VALUES (1, 'TESTOWY', 'TYGRYSEK')");
			System.out.println("End connection to Database");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
