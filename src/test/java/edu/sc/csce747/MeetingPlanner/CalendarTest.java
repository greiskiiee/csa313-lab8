package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

public class CalendarTest {

    // --- addMeeting() and isBusy() ---
    @Test
    public void testAddMeeting_Valid() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting midsommar = new Meeting(6, 26, "Midsommar");
        midsommar.setStartTime(10);
        midsommar.setEndTime(12);
        calendar.addMeeting(midsommar);
        assertTrue(calendar.isBusy(6, 26, 10, 12));
    }

    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_Overlap_ThrowsException() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting m1 = new Meeting(9, 5, "Morning");
        m1.setStartTime(10);
        m1.setEndTime(12);
        Meeting m2 = new Meeting(9, 5, "Overlap");
        m2.setStartTime(11);
        m2.setEndTime(13);
        calendar.addMeeting(m1);
        calendar.addMeeting(m2); // should throw
    }

    @Test
    public void testAddMeeting_DoesNotOverlap() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting m1 = new Meeting(9, 5, "Morning");
        m1.setStartTime(8);
        m1.setEndTime(9);
        Meeting m2 = new Meeting(9, 5, "Later");
        m2.setStartTime(10);
        m2.setEndTime(11);
        calendar.addMeeting(m1);
        calendar.addMeeting(m2); // should succeed
        assertTrue(calendar.isBusy(9, 5, 8, 9));
        assertTrue(calendar.isBusy(9, 5, 10, 11));
    }

    // --- checkTimes() exceptions ---
    @Test(expected = TimeConflictException.class)
    public void testCheckTimes_InvalidDay_ThrowsException() throws TimeConflictException {
        Calendar.checkTimes(1, 32, 10, 11);
    }

    @Test(expected = TimeConflictException.class)
    public void testCheckTimes_InvalidMonth_ThrowsException() throws TimeConflictException {
        Calendar.checkTimes(13, 10, 10, 11);
    }

    @Test(expected = TimeConflictException.class)
    public void testCheckTimes_InvalidStartHour_ThrowsException() throws TimeConflictException {
        Calendar.checkTimes(1, 10, -1, 11);
    }

    @Test(expected = TimeConflictException.class)
    public void testCheckTimes_InvalidEndHour_ThrowsException() throws TimeConflictException {
        Calendar.checkTimes(1, 10, 10, 24);
    }

    @Test(expected = TimeConflictException.class)
    public void testCheckTimes_StartAfterEnd_ThrowsException() throws TimeConflictException {
        Calendar.checkTimes(1, 10, 12, 10);
    }

    @Test(expected = TimeConflictException.class)
    public void testCheckTimes_MonthEquals12Valid() throws TimeConflictException {
        // Should NOT throw
        Calendar.checkTimes(12, 1, 0, 1);
    }

    @Test(expected = TimeConflictException.class)
    public void testCheckTimes_MonthGreaterThan12Throws() throws TimeConflictException {
        Calendar.checkTimes(13, 1, 0, 1);
    }

    @Test(expected = TimeConflictException.class)
    public void testCheckTimes_DayZeroThrows() throws TimeConflictException {
        Calendar.checkTimes(1, 0, 9, 10);
    }

    // --- Remaining uncovered branches ---
    @Test
    public void testAddMeeting_NonOverlappingBeforeAndAfter() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting m1 = new Meeting(8, 10, "Morning");
        m1.setStartTime(9);
        m1.setEndTime(10);
        calendar.addMeeting(m1);

        // Meeting before
        Meeting before = new Meeting(8, 10, "Early");
        before.setStartTime(7);
        before.setEndTime(8);
        calendar.addMeeting(before);

        // Meeting after
        Meeting after = new Meeting(8, 10, "Later");
        after.setStartTime(11);
        after.setEndTime(12);
        calendar.addMeeting(after);

        assertTrue(calendar.isBusy(8, 10, 7, 12));
    }

    @Test
    public void testIsBusy_PartialOverlapAtStart() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting m = new Meeting(9, 15, "Afternoon");
        m.setStartTime(14);
        m.setEndTime(16);
        calendar.addMeeting(m);
        // Query ends in middle of meeting
        assertTrue(calendar.isBusy(9, 15, 13, 15));
    }

    @Test(expected = TimeConflictException.class)
    public void testCheckTimes_StartEqualsEndThrows() throws TimeConflictException {
        Calendar.checkTimes(5, 5, 10, 10);
    }

    @Test(expected = TimeConflictException.class)
    public void testCheckTimes_Month0Throws() throws TimeConflictException {
        Calendar.checkTimes(0, 10, 9, 10);
    }

    @Test
    public void testIsBusy_MeetingOutsideRange() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting m = new Meeting(10, 15, "Afternoon");
        m.setStartTime(14);
        m.setEndTime(16);
        calendar.addMeeting(m);
        // Query a non-overlapping range (morning)
        assertFalse(calendar.isBusy(10, 15, 8, 10));
    }

    // --- clearSchedule() ---
    @Test
    public void testClearSchedule_RemovesAllMeetings() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting m = new Meeting(5, 15, "Meeting");
        m.setStartTime(9);
        m.setEndTime(10);
        calendar.addMeeting(m);
        assertTrue(calendar.isBusy(5, 15, 9, 10));
        calendar.clearSchedule(5, 15);
        assertFalse(calendar.isBusy(5, 15, 9, 10));
    }

    // --- getMeeting() and removeMeeting() ---
    @Test
    public void testGetAndRemoveMeeting() throws TimeConflictException {
        Calendar calendar = new Calendar();
        Meeting m = new Meeting(7, 10, "Project discussion");
        m.setStartTime(14);
        m.setEndTime(15);
        calendar.addMeeting(m);
        Meeting fetched = calendar.getMeeting(7, 10, 0);
        assertEquals("Project discussion", fetched.getDescription());
        calendar.removeMeeting(7, 10, 0);
        assertFalse(calendar.isBusy(7, 10, 14, 15));
    }

    // --- constructor edge cases (invalid days) ---
    @Test
    public void testConstructor_InvalidDaysExist() {
        Calendar calendar = new Calendar();
        Meeting invalidDay = calendar.getMeeting(2, 29, 0);
        assertEquals("Day does not exist", invalidDay.getDescription());
    }

    // --- isBusy() false case ---
    @Test
    public void testIsBusy_ReturnsFalseIfNoMeeting() throws TimeConflictException {
        Calendar calendar = new Calendar();
        assertFalse(calendar.isBusy(4, 10, 8, 9));
    }

    @Test
    public void testCheckTimes_MonthEquals12_DoesNotThrow() throws TimeConflictException {
        // Should NOT throw
        Calendar.checkTimes(12, 15, 10, 11);
    }

    @Test
    public void testAddMeeting_DayDoesNotExistSkipped() throws TimeConflictException {
        Calendar calendar = new Calendar();
        // April 31 is prefilled with "Day does not exist"
        Meeting validMeeting = new Meeting(4, 31, "Test Meeting");
        validMeeting.setStartTime(9);
        validMeeting.setEndTime(10);
        calendar.addMeeting(validMeeting); // should NOT conflict
        assertTrue(calendar.isBusy(4, 31, 9, 10));
    }

}
