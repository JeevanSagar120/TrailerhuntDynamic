package com.trailerHunt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editUser")
public class editUser extends HttpServlet{
	
	public void service(HttpServletRequest request , HttpServletResponse response) {
		Connection connection = null;
		PreparedStatement psmt = null;
		
		try {
			Class.forName("com.cj.mysql.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/trailerhunt","root","sagar@120");
			
		} catch (Exception e) {
			
		}
		
	}
	
}
