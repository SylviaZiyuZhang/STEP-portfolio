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

package com.google.sps;

import java.util.Comparator;

/**
 * Class representing a span of time, enforcing properties (e.g. start comes before end) and
 * providing methods to make ranges easier to work with (e.g. {@code overlaps}).
 */
public final class TimePoint {
  public final int time;
  public final boolean start;

  public TimePoint(int time, boolean start) {
  this.time = time;
  this.start = start;
  }

  /** Order by first ascending order of the time then priority of ending timepoints **/
  public static final Comparator<TimePoint> ORDER_BY_TIME = new Comparator<TimePoint>() {
    @Override
    public int compare(TimePoint a, TimePoint b) {
      if (Long.compare(a.time, b.time) > 0)
        return 1;
      else if (Long.compare(a.time, b.time) == 0 && b.start == false)
        return 1;
      else
        return -1;
        
    }
  };
}
