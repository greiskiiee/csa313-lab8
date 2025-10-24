package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class RoomTest {

    private Room room;
    private Meeting meeting;

    @Before
    public void setUp() throws TimeConflictException {
        room = new Room("R101");
        meeting = new Meeting(5, 20, 10, 12, new ArrayList<>(), room, "Team Meeting");
        room.addMeeting(meeting);
    }

    // Test adding a meeting
    @Test
    public void testAddMeeting() throws TimeConflictException {
        Meeting newMeeting = new Meeting(5, 21, 9, 11, new ArrayList<>(), room, "Project Meeting");
        room.addMeeting(newMeeting);
        assertTrue(room.isBusy(5, 21, 9, 11));
    }

    // Test adding an overlapping meeting
    @Test(expected = TimeConflictException.class)
    public void testAddOverlappingMeeting() throws TimeConflictException {
        // Overlaps with existing meeting (5/20, 10-12)
        Meeting overlapping = new Meeting(5, 20, 11, 13, new ArrayList<>(), room, "Overlap Meeting");
        room.addMeeting(overlapping);
    }

    // Test removing a meeting
    @Test
    public void testRemoveMeeting() throws TimeConflictException {
        room.removeMeeting(5, 20, 0); // remove the first meeting
        assertFalse(room.isBusy(5, 20, 10, 12));
    }

    // Test checking if room is busy
    @Test
    public void testIsBusy() throws TimeConflictException {
        assertTrue(room.isBusy(5, 20, 10, 12));
        assertFalse(room.isBusy(5, 20, 13, 15));
    }

    // Test getting room's meeting
    @Test
    public void testGetMeeting() {
        Meeting m = room.getMeeting(5, 20, 0);
        assertEquals("Team Meeting", m.getDescription());
        assertEquals(10, m.getStartTime());
        assertEquals(12, m.getEndTime());
    }

    // Test printing agenda
    @Test
    public void testPrintAgenda() {
        String agendaMonth = room.printAgenda(5);
        String agendaDay = room.printAgenda(5, 20);
        assertNotNull(agendaMonth);
        assertNotNull(agendaDay);
        assertTrue(agendaMonth.contains("Team Meeting") || agendaDay.contains("Team Meeting"));
    }
}
