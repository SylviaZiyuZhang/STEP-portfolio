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

google.charts.load('current', { 'packages': ['timeline'] });
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
  var container = document.getElementById('timeline-container');
  var chart = new google.visualization.Timeline(container);
  var dataTable = new google.visualization.DataTable();

  dataTable.addColumn({ type: 'string', id: 'Location' });
  dataTable.addColumn({ type: 'date', id: 'Start' });
  dataTable.addColumn({ type: 'date', id: 'End' });
  dataTable.addRows([
    ['Harbin', new Date(2000, 11, 1), new Date(2005, 12, 15)],
    ['Singapore', new Date(2005, 12, 15), new Date(2006, 9, 1)],
    ['Mt. Tai Rd. Elementary, Shenyang', new Date(2006, 9, 1), new Date(2012, 9, 1)],
    ['NEYC, Shenyang', new Date(2012, 9, 1), new Date(2018, 7, 1)],
    ['SDezhou', new Date(2018, 7, 1), new Date(2018, 8, 18)],
    ['CMU, Pittsburgh', new Date(2018, 8, 18), new Date(2019, 5, 15)],
    ['Bytedance, Shanghai', new Date(2019, 5, 15), new Date(2019, 8, 10)],
    ['DVFU, Vladivostok', new Date(2019, 8, 10), new Date(2019, 8, 26)],
    ['CMU, Pittsburgh', new Date(2019, 8, 26), new Date(2020, 5, 15)],
    ['Google, Mountainview', new Date(2020, 5, 15), new Date(2020, 6, 15)]
  ]);
  chart.draw(dataTable);
}