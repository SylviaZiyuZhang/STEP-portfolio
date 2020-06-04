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



/** Fetches comments from DataServlet and display **/
function getComments() {
  const numDisplayBox = document.getElementById("num-comments");
  const numdisplay = numDisplayBox.value;
  console.log(numdisplay);
  const request = '/load-comment?numdisplay=' + numdisplay;
  fetch(request).then(response => response.json()).then((commenthistory) => {
    const historyEl = document.getElementById('history');
    historyEl.innerHTML = '';
    var i;
    for (i = 0; i < commenthistory.length; i++) {
      historyEl.appendChild(createListElement(commenthistory[i]));
    }
  });
}

/** Posts comment onto datastore, then reload existing comments **/
function postComment() {
  var commentText = document.getElementById("comment-content");
  const comment = commentText.value;
  commentText.value = "Leave your comment here";
  fetch('/comment', { method: "POST", body: new URLSearchParams({ comment }) }).then(response => getComments());
}

/** Deletes all comments in datastore, then clear the comment section in the page **/
function deleteAllComments() {
  console.log("Deleting all comments");
  fetch('/delete-data', { method: "POST" }).then(response => getComments());
}

/** Fetches comments from CityServlet and display **/
function getCityRec() {
  fetch('/city').then(response => response.json()).then((cities) => {
    const cityListElement = document.getElementById('city-container');
    cityListElement.innerHTML = '';
    var i;
    for (i = 0; i < cities.length; i++) {
      cityListElement.appendChild(
        createListElement(cities[i]));
    }
  });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}