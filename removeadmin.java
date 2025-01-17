package com.trailerHunt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/removeadmin")
public class removeadmin extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sqlQuery = "delete from useractivity where email =?";
		Connection connection = null;
		
		String userMail = request.getParameter("#");//Upadte
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/trailerhunt","root","sagar@120");
			
			PreparedStatement psmt = connection.prepareStatement(sqlQuery);
			psmt.setString(1, userMail);
			
		} catch (ClassNotFoundException e) {
			System.out.println("Problem Occured While Loading the Driver Class..");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Problem Occured While Opening the Driver Class.");
			e.printStackTrace();
		}
		
	}

}
