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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    ArrayList<TimePoint> timePoints = constructTimePoints(events, request);
    ArrayList<TimeRange> meetingTimes = new ArrayList<TimeRange>();
    ArrayList<TimeRange> meetingTimesOptional = new ArrayList<TimeRange>();
    int lastValidStartTime = 0;
    int lastValidStartTimeOptional = 0;
    int intervalCount = 0;
    int intervalCountOptional = 0;
    for (TimePoint timePoint : timePoints) {
      /* Theoretically the sweepline operations can be functions to avoid code repetition,
       * but Java doesn't natively support passing integers by reference, constructing another
       * class for the loop structure hurts more than repeating code */
      int time = timePoint.time;
      // Maintain availabilities for mandatory attendees
      if (timePoint.mandatory) {
        if (intervalCount == 0 && timePoint.start
          && timePoint.time - lastValidStartTime >= request.getDuration()) {
          boolean endOfDay = (timePoint.time == TimeRange.END_OF_DAY);
          meetingTimes.add(TimeRange.fromStartEnd(lastValidStartTime, timePoint.time, endOfDay));
        }
        if (timePoint.start)
          intervalCount ++;
        else
          intervalCount --;
        if (!timePoint.start && intervalCount == 0) {
          lastValidStartTime = timePoint.time;
        }
      }
      // Maintain availabilities for optional attendees
      if (intervalCountOptional == 0 && timePoint.start
          && timePoint.time - lastValidStartTimeOptional >= request.getDuration()) {
        boolean endOfDay = (timePoint.time == TimeRange.END_OF_DAY);
        meetingTimesOptional.add(TimeRange.fromStartEnd(lastValidStartTimeOptional, timePoint.time, endOfDay));
      }
      if (timePoint.start)
        intervalCountOptional ++;
      else
        intervalCountOptional --;
      if (!timePoint.start && intervalCountOptional == 0) {
        lastValidStartTimeOptional = timePoint.time;
      }
    }
    return meetingTimesOptional.isEmpty() ? meetingTimes : meetingTimesOptional;
  }
  
  private ArrayList<TimePoint> constructTimePoints(Collection<Event> events, MeetingRequest request) {
    ArrayList<TimePoint> timePoints = new ArrayList<TimePoint>();
    for (Event event : events) {
      boolean counted = false;
      boolean optionalCounted = false;
      Set<String> attendees = event.getAttendees();
      for (String attendee : attendees) {
        if (request.getOptionalAttendees().contains(attendee))
        optionalCounted = true;
        if (request.getAttendees().contains(attendee)) {
          counted = true;
          break;
        }
      }
      if (counted) {
        timePoints.add(new TimePoint(event.getWhen().start(), true, true));
        timePoints.add(new TimePoint(event.getWhen().end(), false, true));
      }
      if ((!counted) && optionalCounted) {
        timePoints.add(new TimePoint(event.getWhen().start(), true, false));
        timePoints.add(new TimePoint(event.getWhen().end(), false, false));
      }
    }
    Collections.sort(timePoints, TimePoint.ORDER_BY_TIME);
    timePoints.add(new TimePoint(TimeRange.END_OF_DAY, true, true));
    return timePoints;
  }

}
