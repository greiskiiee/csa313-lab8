package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class MeetingTest {

    private Meeting meeting;
    private Person alice;
    private Person bob;
    private Room room;

    @Before
    public void setUp() {
        room = new Room("R101");
        meeting = new Meeting(5, 20, 10, 12, new ArrayList<>(), room, "Team Meeting");
        alice = new Person("Alice");
        bob = new Person("Bob");
    }

    @Test
    public void testAddAttendee() {
        meeting.addAttendee(alice);
        assertTrue(meeting.getAttendees().contains(alice));
    }

    @Test
    public void testRemoveAttendee() {
        meeting.addAttendee(alice);
        meeting.addAttendee(bob);
        meeting.removeAttendee(alice);
        assertFalse(meeting.getAttendees().contains(alice));
        assertTrue(meeting.getAttendees().contains(bob));
    }

    @Test
    public void testConstructors() {
        // Test day-blocking constructor
        Meeting dayMeeting = new Meeting(6, 15, "Holiday");
        assertEquals(6, dayMeeting.getMonth());
        assertEquals(15, dayMeeting.getDay());
        assertEquals(0, dayMeeting.getStartTime());
        assertEquals(23, dayMeeting.getEndTime());
        assertEquals("Holiday", dayMeeting.getDescription());
    }

    @Test
    public void testGettersAndSetters() {
        meeting.setMonth(7);
        meeting.setDay(21);
        meeting.setStartTime(9);
        meeting.setEndTime(11);
        meeting.setDescription("Updated Meeting");
        Room newRoom = new Room("R102");
        meeting.setRoom(newRoom);

        assertEquals(7, meeting.getMonth());
        assertEquals(21, meeting.getDay());
        assertEquals(9, meeting.getStartTime());
        assertEquals(11, meeting.getEndTime());
        assertEquals("Updated Meeting", meeting.getDescription());
        assertEquals(newRoom, meeting.getRoom());
    }

    @Test
    public void testToString() {
        meeting.addAttendee(alice);
        meeting.addAttendee(bob);
        String result = meeting.toString();
        assertTrue(result.contains("5/20"));
        assertTrue(result.contains("10 - 12"));
        assertTrue(result.contains("Team Meeting"));
        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("Bob"));
        assertTrue(result.contains("R101"));
    }
}
