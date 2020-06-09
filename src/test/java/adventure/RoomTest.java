package adventure;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.Before;


public class RoomTest{
    private Room testRoom;
    private Room testConnection;

@Before
public void setup(){
    testRoom = new Room();
    testConnection = new Room();
    testRoom.setConnectedRoomAsRoom("N",testConnection);
    testConnection.setConnectedRoomAsRoom("S",testRoom);
}

@Test
public void testConnectedRoomWithValidInput(){
    System.out.println("Testing getConnectedRoom with valid direction");
    assertSame(testRoom.getConnectedRoom("N"),testConnection);
}

@Test
public void testConnectedRoomWithInvalidDirection(){
    System.out.println("Testing getConnectedRoom with invalid direction");
    assertNotSame(testRoom.getConnectedRoom("E"),testConnection);
}

@Test
public void testConnectedRoomWithInvalidDirectionNumberTwo(){
    System.out.println("Testing getConnectedRoom with a really weird direction");
    assertNotSame(testRoom.getConnectedRoom("monkey"),testConnection);
}

@Test
public void testConnectedRoomWithConnectionOfConnection(){
    System.out.println("Testing getConnectedRoom with a chained connection");
    assertNotSame(testRoom.getConnectedRoom("N").getConnectedRoom("S"),testConnection);
}


}