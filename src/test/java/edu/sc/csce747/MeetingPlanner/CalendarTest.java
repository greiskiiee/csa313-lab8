package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

public class CalendarTest {

    // valid meeting addition
    @Test
    public void testAddMeeting_Valid() {
        Calendar calendar = new Calendar();
        try {
            Meeting midsommar = new Meeting(6, 26, "Midsommar");
            calendar.addMeeting(midsommar);
            assertTrue(calendar.isBusy(6, 26, 0, 23));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    // invalid meeting addition - day out of range
    @Test(expected = TimeConflictException.class)
    public void testInvalidDay_ThrowsException() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting invalid = new Meeting(2, 35, "Invalid day");
        calendar.addMeeting(invalid);
    }

    // invalid meeting addition - month out of range
    @Test(expected = TimeConflictException.class)
    public void testInvalidMonth_ThrowsException() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting invalid = new Meeting(13, 1, "Invalid month");
        calendar.addMeeting(invalid);
    }

    // invalid meeting addition - start time greater than end time
    @Test(expected = TimeConflictException.class)
    public void testInvalidTimeRange_ThrowsException() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting invalid = new Meeting(5, 20, "Reversed time");
        invalid.setStartTime(15);
        invalid.setEndTime(10);
        calendar.addMeeting(invalid);
    }

    // valid meeting removal
    @Test
    public void testRemoveMeeting_Success() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting meeting = new Meeting(7, 10, "Project discussion");
        calendar.addMeeting(meeting);
        calendar.removeMeeting(7, 10, 0);
        assertFalse(calendar.isBusy(7, 10, 0, 23));
    }

    // overlapping meetings
    @Test(expected = TimeConflictException.class)
    public void testOverlappingMeetings_ThrowsException() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting m1 = new Meeting(9, 5, "Morning");
        m1.setStartTime(10);
        m1.setEndTime(12);
        Meeting m2 = new Meeting(9, 5, "Overlap");
        m2.setStartTime(11);
        m2.setEndTime(13);
        calendar.addMeeting(m1);
        calendar.addMeeting(m2);
    }
}
