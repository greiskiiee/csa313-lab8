package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class PersonTest {

    private Person person;
    private Meeting meeting;
    private Room room;

    @Before
    public void setUp() throws TimeConflictException {
        person = new Person("Alice");
        room = new Room("R101");
        meeting = new Meeting(6, 15, 10, 12, new ArrayList<>(), room, "Team Meeting");
        person.addMeeting(meeting);
    }

    @Test
    public void testAddMeeting() throws TimeConflictException {
        Meeting newMeeting = new Meeting(6, 16, 9, 11, new ArrayList<>(), room, "Project Meeting");
        person.addMeeting(newMeeting);
        assertTrue(person.isBusy(6, 16, 9, 11));
    }

    @Test(expected = TimeConflictException.class)
    public void testAddOverlappingMeeting() throws TimeConflictException {
        // Overlaps with existing meeting (6/15, 10-12)
        Meeting overlapping = new Meeting(6, 15, 11, 13, new ArrayList<>(), room, "Overlap Meeting");
        person.addMeeting(overlapping);
    }

    @Test
    public void testRemoveMeeting() throws TimeConflictException {
        person.removeMeeting(6, 15, 0);
        assertFalse(person.isBusy(6, 15, 10, 12));
    }

    @Test
    public void testIsBusy() throws TimeConflictException {
        assertTrue(person.isBusy(6, 15, 10, 12));
        assertFalse(person.isBusy(6, 15, 13, 15));
    }

    @Test
    public void testGetMeeting() {
        Meeting m = person.getMeeting(6, 15, 0);
        assertEquals("Team Meeting", m.getDescription());
        assertEquals(10, m.getStartTime());
        assertEquals(12, m.getEndTime());
    }

    @Test
    public void testPrintAgenda() {
        String agendaMonth = person.printAgenda(6);
        String agendaDay = person.printAgenda(6, 15);
        assertNotNull(agendaMonth);
        assertNotNull(agendaDay);
        assertTrue(agendaMonth.contains("Team Meeting") || agendaDay.contains("Team Meeting"));
    }
}
