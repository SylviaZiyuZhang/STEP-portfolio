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

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
    ['I play the bass guitar.', 'My favorite video game is One Shot', 'I run a karate club at school', 'Я говорю по русский.'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/** Maintains the floating navigation bar **/
function FloatingNavbar() {
  var navbar = document.getElementById("navbar");
  var sticky = navbar.offsetTop;
  if (window.pageYOffset >= sticky) {
    navbar.classList.add("sticky");
  }
  else {
    navbar.classList.remove("sticky");
  }
}



/** Fetches comments from DataServlet and display */
function getComments() {
  const numDisplayBox = document.getElementById("num-comments");
  const numdisplay = numDisplayBox.value;

  const request = '/load-comment?numdisplay=' + numdisplay;
  fetch(request).then(handleFetchErrors).
    then(response => response.json()).then((commentList) => {
      const historyElem = document.getElementById('history');
      historyElem.innerHTML = '';
      var i;
      for (i = 0; i < commentList.length; i++) {
        historyElem.appendChild(createListElement(commentList[i].userEmail + ":\n" + commentList[i].commentContent));
      }
    });
}

/** Posts comment onto datastore, then reload existing comments **/
function postComment() {
  var commentText = document.getElementById("comment-content");
  const comment = commentText.value;
  commentText.value = "Leave your comment here";
  fetch('/comment', {
    method: "POST",
    body: new URLSearchParams({ comment })
  }).then(handleFetchErrors).then(getComments());
}

/** Deletes all comments in datastore, then clear the comment section in the page **/
function deleteAllComments() {
  fetch('/delete-data', { method: "POST" }).then(handleFetchErrors).then(getComments());
}

/** Fetches comments from CityServlet and display **/
function getCityRec() {
  fetch('/city').then(handleFetchErrors).then(response => response.json())
    .then((cities) => {
      const cityListElement = document.getElementById('city-container');
      cityListElement.innerHTML = '';
      var i;
      for (i = 0; i < cities.length; i++) {
        cityListElement.appendChild(
          createListElement(cities[i]));
      }
    });
}

function getLoginInfo() {
  console.log("Getting login info");
  fetch('/login').then(handleFetchErrors).then(response => response.json())
    .then(loginInfo => {
      const loginElement = document.getElementById('login-box');
      loginElement.innerHTML = loginInfo.toggleLoginURL;
      const formElement = document.getElementById('comment-form');
      const deleteElement = document.getElementById('delete-button');
      if (loginInfo.isLoggedIn) {
        formElement.style.display = "block";
      }
      else {
        formElement.style.display = "none";
      }
      if (loginInfo.isAdmin) {
        deleteElement.style.display = "block";
      }
      else {
        deleteElement.style.display = "none";
      }
    })
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

function handleFetchErrors(response) {
  if (!response.ok) throw Error(response.statusText);
  return response;
}

function indexOnload() {
  getComments();
  getLoginInfo();
}