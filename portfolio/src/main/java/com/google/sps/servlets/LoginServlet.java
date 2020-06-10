package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.LoginInfo;
import com.google.sps.data.Constants;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private LoginInfo loginInfo = new LoginInfo();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType(Constants.jsonContentType);
    
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      String userEmail = userService.getCurrentUser().getEmail();
      String redirUrlAfterLogout = "/";
      String logoutUrl = userService.createLogoutURL(redirUrlAfterLogout);
      loginInfo.toggleLoginURL = "<a href=\"" + logoutUrl + "\">Logout from " +
        userEmail + "</a>";
      loginInfo.isLoggedIn = true;
      loginInfo.isAdmin = userService.isUserAdmin();
      response.getWriter().println(new Gson().toJson(loginInfo));
    }
    else {
      String redirUrlAfterLogin = "/";
      String loginUrl = userService.createLoginURL(redirUrlAfterLogin);
      loginInfo.toggleLoginURL = "<a href=\"" + loginUrl + "\"> Log In</a>";
      loginInfo.isLoggedIn = false;
      loginInfo.isAdmin = false;
      response.getWriter().println(new Gson().toJson(loginInfo));
    }
  }
}
