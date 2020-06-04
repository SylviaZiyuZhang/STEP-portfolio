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

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns a hardcode list of travel destinations **/
@WebServlet("/city")
public final class CityServlet extends HttpServlet {

  private List<String> CityRecs;
  @Override
  public void init() {
    CityRecs = new ArrayList<>();
    CityRecs.add("Kyoto, Japan");
    CityRecs.add("Vladivostok, Russia");
    CityRecs.add("Sofia, Bulgaria");
  }
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertToJson(CityRecs);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
  private String convertToJson(List CityRecs) {
    Gson gson = new Gson();
    String json = gson.toJson(CityRecs);
    return json;
  }
}