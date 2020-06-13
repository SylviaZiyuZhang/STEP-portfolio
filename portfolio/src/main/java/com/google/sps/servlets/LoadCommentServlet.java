// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import com.google.sps.data.CommentInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for listing comments. */
@WebServlet("/load-comment")
public class LoadCommentServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<CommentInfo> commentList = new ArrayList<>();
    int numDisplay = getNumDisplay(request);
    for (Entity entity : results.asIterable(FetchOptions.Builder.withLimit(numDisplay))) {
      long id = entity.getKey().getId();
      CommentInfo commentInfo = new CommentInfo();
      commentInfo.commentContent = (String) entity.getProperty("content");
    	commentInfo.userEmail = (String) entity.getProperty("email"); 
      commentList.add(commentInfo);
    }
    
    response.setContentType("application/json;");
    response.getWriter().println(new Gson().toJson(commentList));
  }

  /** Safe wrapper for extracting the requested number of comments to display **/
  private int getNumDisplay(HttpServletRequest request) {
    String numString = request.getParameter("numdisplay");
    int num;
    try {
      num = Integer.parseInt(numString);
    } catch (NumberFormatException e) {
      System.err.println("Not an integer: " + numString);
      return -1;
    }
    if (num < 1) {
      System.err.println("Invalid number of display: " + numString);
      return -1;
    }
    return num;
  }
}
