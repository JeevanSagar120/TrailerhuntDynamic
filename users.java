package com.trailerHunt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class users extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter pr = response.getWriter();
        pr.print("<h1 align=center> Users Data</h1>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/trailerhunt", "root", "sagar@120");

            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                deleteUser(request, connection);
            }

            PreparedStatement psmt = connection.prepareStatement("SELECT * FROM USERS");
            ResultSet rs = psmt.executeQuery();

            pr.print("<center style=\"margin-top:100px;\">" +
                    "<table border=1px solid black cellspacing=0px cellpadding=10px>" +
                    "<tr>" +
                    "<td> <center> <strong> ID</strong> </center> </td>" +
                    "<td> <center> <strong> Name </strong> </center> </td>" +
                    "<td> <center> <strong> Email </strong> </center> </td>" +
                    "<td> <center> <strong> Password </strong>  </center> </td>" +
                    "<td> <center> <strong>Registered Date and Time</strong> </center></td>" +
                    "<td> <center> <strong>Actions</strong> </center></td>" +
                    "</tr>"
            );

            while (rs.next()) {
                int usrId = rs.getInt(1);
                pr.print("<tr>" +
                        "<td>" + usrId + "</td>" +
                        "<td>" + rs.getString(2) + "</td>" +
                        "<td>" + rs.getString(3) + "</td>" +
                        "<td>" + "********" + "</td>" +
                        "<td><center>" + rs.getString(6) + "</center></td>" +
                        "<td>" +
                        "<button style=\"cursor:pointer; margin-left:10px; margin-right:20px;	 background-color: rgb(151, 251, 64);	height:30px;	width:100px;  border-radius: 30px;  border:none;\">" +
                        "<a href=\"/editUser?id=" + usrId + "\" style=\"text-decoration: none; color: black;\">Edit</a>" +
                        "</button>" +
                        "   " +
                        "<button style=\"cursor:pointer; margin-right:10px;  background-color: rgb(251, 120, 64); height:30px; width:100px ; border-radius: 30px;  border:none;  \">" +
                        "<a href=\"/users?action=delete&id=" + usrId + "\" style=\"text-decoration: none; color: black;\">Delete</a>" +
                        "</button>" +
                        "</td>" +
                        "</tr>"
                );
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Problem Occurred While Loading the Driver Files");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(HttpServletRequest request, Connection connection) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        PreparedStatement deleteStmt = connection.prepareStatement("DELETE FROM USERS WHERE id = ?");
        deleteStmt.setInt(1, id);
        deleteStmt.executeUpdate();
    }
}
