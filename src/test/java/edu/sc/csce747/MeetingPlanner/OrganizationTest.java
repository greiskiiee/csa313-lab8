package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class OrganizationTest {

    private Organization org;

    @Before
    public void setUp() {
        org = new Organization();
    }

    // verifies the employees list is properly initialized.
    @Test
    public void testEmployeesInitialization() {
        ArrayList<Person> employees = org.getEmployees();
        assertNotNull(employees);
        assertEquals(5, employees.size());
        assertEquals("Greg Gay", employees.get(0).getName());
        assertEquals("Csilla Farkas", employees.get(4).getName());
    }

    // verifies the rooms list is properly initialized.
    @Test
    public void testRoomsInitialization() {
        ArrayList<Room> rooms = org.getRooms();
        assertNotNull(rooms);
        assertEquals(5, rooms.size());
        assertEquals("2A01", rooms.get(0).getID());
        assertEquals("2A05", rooms.get(4).getID());
    }

    // Test retrieving a room by ID
    @Test
    public void testGetRoomByID_ExistingRoom() throws Exception {
        Room room = org.getRoom("2A03");
        assertNotNull(room);
        assertEquals("2A03", room.getID());
    }

    // Test retrieving an employee by name
    @Test
    public void testGetEmployeeByName_ExistingEmployee() throws Exception {
        Person employee = org.getEmployee("John Rose");
        assertNotNull(employee);
        assertEquals("John Rose", employee.getName());
    }

    // Test retrieving a non-existing room by ID
    @Test(expected = Exception.class)
    public void testGetRoomByID_NonExistingRoom() throws Exception {
        org.getRoom("NonExistent");
    }

    // Test retrieving a non-existing employee by name
    @Test(expected = Exception.class)
    public void testGetEmployeeByName_NonExistingEmployee() throws Exception {
        org.getEmployee("NonExistent");
    }
}
