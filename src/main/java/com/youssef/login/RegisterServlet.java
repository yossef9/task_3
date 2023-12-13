package com.youssef.login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Retrieve database username and password from servlet context
            ServletContext context = getServletContext();
            String dbUsername = context.getInitParameter("dbUsername");
            String dbPassword = context.getInitParameter("dbPassword");

            // Load the JDBC driver for PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Replace "jdbc:mysql://your-database-url" with your actual database URL
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scmdb", dbUsername, dbPassword);

            // Insert new user into the database
            if (registerUser(email, password, connection)) {
                // If registration is successful, forward to login page
                RequestDispatcher rd = request.getRequestDispatcher("/login.html");
                rd.forward(request, response);
            } else {
                // If registration fails, forward to registration page
                RequestDispatcher rd = request.getRequestDispatcher("/register.html");
                rd.forward(request, response);
            }

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean registerUser(String email, String password, Connection connection) throws SQLException {
        // Perform database query to insert new user
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if registration is successful, false otherwise
        }
    }
}

