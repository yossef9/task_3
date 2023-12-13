package com.youssef.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class homeServlet
 */
@WebServlet("/HomeServlet")
public class homeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // You can add code here to handle the home page display
        // For example, retrieve user data from the database and display a welcome message
        response.getWriter().println("Welcome to the Home Page!");
    }
}

