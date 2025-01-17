package com.trailerHunt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class Login extends HttpServlet{
	
	//Getting the Details of the user 
	LocalDate ld = LocalDate.now();
	String date = ld.toString();
	
	ZonedDateTime indiaTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
    
    // Format to display only time
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String time = indiaTime.format(timeFormatter);
    
	Connection connection = null;
	PreparedStatement psmt = null;
	String sqlQuery = "select * from users where umail = ? and password =?";
	
	String uaQuery = "insert into useractivity (name , email , date ) values (?,?,?)";
	public void init(ServletConfig config) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/trailerhunt?useSSL=false&allowPublicKeyRetrieval=true",
					"root",
					"sagar@120"
					);

		} catch (Exception e) {
			System.out.println("Problem Occured While Opening the Driver Class .");
			e.printStackTrace();
		}
				
	}
	
	public void service(HttpServletRequest request , HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		PrintWriter pr = response.getWriter();
		try {
			psmt = connection.prepareStatement(sqlQuery);
			psmt.setString(1,mail);
			psmt.setString(2, password);
			
			ResultSet rs = psmt.executeQuery();
			PreparedStatement pst = connection.prepareStatement(uaQuery);
			pst.setString(1,name );
			pst.setString(2, mail);
			pst.setString(3, date);
//			pst.setString(4, time);
			if(rs.next()) {
				pst.executeUpdate();
				RequestDispatcher rd = request.getRequestDispatcher("main.html");
				rd.forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				pr.print("<script>" +
        	            "window.onload = function() {" +
        	            "  document.getElementById('error-details').innerHTML = 'Login Credentials not found';" +
        	            "};" +
        	            "</script>");
				rd.include(request, response);
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
}
