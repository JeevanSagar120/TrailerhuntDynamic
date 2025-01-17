package com.trailerHunt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admins")
public class admins  extends HttpServlet{
	public void service(HttpServletRequest request , HttpServletResponse response) {
		String sqQuery ="SELECT * FROM admins";
		Connection connection = null;
		try {
			PrintWriter pr = response.getWriter();
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/trailerhunt",
					"root",
					"sagar@120"
					);
			pr.print(
					"<h1 align = center>Admins</h1>"
					);
			PreparedStatement psmt = connection.prepareStatement("SELECT * FROM admins");
			pr.print("<center style=\"margin-top:130px;\">"
					+ "<table border=1px solid black cellspacing=0px cellpadding=10px>"+
					"<tr>"+
					"<td> id </td>"+
					"<td> Name </td>"+
					"<td> Email </td>"+
					"<td> Password </td>"+
					"<td> Registered Date</td>"+
					"</tr>"
					);
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				pr.print(
						"<tr>"+
				"<td>"+rs.getString(1)+"</td>"+
				"<td>"+rs.getString(2)+"</td>"+	
				"<td>"+rs.getString(3)+"</td>"+
				"<td>********</td>"+
				"<td>"+rs.getString(5)+"</td>"+
				 "</tr>"
						);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Problem Occured While Opening the Driver Files.");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
