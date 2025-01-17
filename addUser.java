package com.trailerHunt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/adduser")
public class addUser extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		//Connection RequireMents
	    Connection connection = null;
	    PreparedStatement psmt = null;
	    PrintWriter pr = response.getWriter();
	    
	    //Connection Urls
	    String Driver = "com.mysql.cj.jdbc.Driver"; // Corrected Driver class name
	    String url = "jdbc:mysql://localhost:3306/trailerhunt";
	    String userName = "root";
	    String passkey = "sagar@120";
	    
	    //Date and Time object
	    LocalDate ld = LocalDate.now();
		String date = ld.toString();

	    // Form Values
	    String name = request.getParameter("name");
	    String mail = request.getParameter("mail");
	    String password = request.getParameter("password");

	    String sqlQuery = "INSERT INTO users (name, umail, password , registereddate) VALUES (?, ?, ? ,? )";
	    String chkQuery = "SELECT * FROM users WHERE umail = ? AND password = ?";

	    try {
	        Class.forName(Driver);
	        connection = DriverManager.getConnection(url, userName, passkey);
	        
	        // Check if user already exists
	        psmt = connection.prepareStatement(chkQuery);
	        psmt.setString(1, mail);
	        psmt.setString(2, password);
	        ResultSet rs = psmt.executeQuery();

	        if (rs.next()) {
	            response.setContentType("text/html;charset=UTF-8");
	            pr.print("<script>" +
	                    "window.onload = function() {" +
	                    " document.getElementById('error-details').innerHTML = 'Email already Exists';" +
	                    "};" +
	                    "</script>");
	            RequestDispatcher rd = request.getRequestDispatcher("addUser.html");
	            rd.include(request, response);
	        } else {
	            // Add new user
	            psmt.close();
	            psmt = connection.prepareStatement(sqlQuery);
	            psmt.setString(1, name);
	            psmt.setString(2, mail);
	            psmt.setString(3, password);
	            psmt.setString(4, date);

	            int rowsAffected = psmt.executeUpdate();
	            if (rowsAffected > 0) {

	            	pr.print("<script>" +
		                    "window.onload = function() {" +
		                    " alert('User added Successfully ');" +
		                    "};" +
		                    "</script>");
	            	RequestDispatcher rd = request.getRequestDispatcher("adminDashboard.html");
	                rd.forward(request, response);
	            }
	        }
	    } catch (ClassNotFoundException e) {
	        System.out.println("Problem occurred while loading the Driver class.");
	        e.printStackTrace();
	    } catch (SQLException e) {
	        System.out.println("Problem occurred while accessing the database.");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (psmt != null) psmt.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

}
