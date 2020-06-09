package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletREquest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      String userEmail = userService.getCurrentUser().getEmail();
      String redirUrlAfterLogout = "/";
      String logoutUrl = userService.createLogoutURL(redirUrlAfterLogout);

      response.getWriter().println("<a href=\"" + logoutUrl + "\">Logout from" + userEmail + "</a>");
    }
    else {
      String redirUrlAfterLogin = "/";
      String loginUrl = userService.createLogoutURL(redirUrlAfterLogin);
      response.getWriter().println("<a href=\"" + loginUrl + "\"> Log In</a>");
    }
  }
}
