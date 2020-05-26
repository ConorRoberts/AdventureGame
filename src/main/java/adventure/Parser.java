package adventure;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Parser{

  public final JSONObject loadAdventureJson(String filename){
      try{
          Object file=new JSONParser().parse(new FileReader(filename));

          JSONObject fileJson=(JSONObject) file;
          JSONObject adventure = (JSONObject) fileJson.get("adventure");

          return adventure;

      }catch (FileNotFoundException e){
          return null;
      }catch(IOException e){
          return null;
      }catch(ParseException e){
          return null;
      }
  }

  public final Adventure generateAdventure(JSONObject obj) {
      Adventure adventure = new Adventure();

      //Starting points in JSON
      JSONArray rooms = (JSONArray) obj.get("room");
      JSONArray items = (JSONArray) obj.get("item");

      //Adds items to Adventure's list of items
      for (Object i : items){
        JSONObject current = (JSONObject) i;

        Item newItem = new Item();

        //Gives newItem properties of JSONObject
        newItem.setID(current.get("id").toString());
        newItem.setName(current.get("name").toString());
        newItem.setLongDescription(current.get("desc").toString());

        adventure.listAllItems().add(newItem);
      }

      //Adds rooms to Adventure's list of rooms
      for (Object r : rooms){
        JSONObject current = (JSONObject) r;

        //Initializes blank room to be written on to
        Room newRoom  = new Room();

        JSONArray roomConnections = (JSONArray) current.get("entrance");

        //Connects room IDs to be translated into rooms later
        for (Object connection : roomConnections){
          JSONObject c = (JSONObject) connection;
          newRoom.setConnectedID(c.get("id").toString(),c.get("dir").toString());
        }

        //Gives newRoom the properties of the JSONObject
        newRoom.setID(current.get("id").toString());
        newRoom.setName(current.get("name").toString());
        newRoom.setShortDescription(current.get("short_description").toString());
        newRoom.setLongDescription(current.get("long_description").toString());

        //Grabs the properties of loot from JSON
        JSONArray loot = (JSONArray) current.get("loot");

        //Checks for loot inside a room
        if (loot!=null){

          //Goes through each loot item in room
          for (Object j : loot){

            JSONObject objLoot = (JSONObject) j;

            //Goes through all items in adventure added earlier
            for (Item i : adventure.listAllItems()){

              //Checks for an identical ID within Adventure's item list
              if (i.getID().equals(objLoot.get("id").toString())){
                //Appends the loot item to the end of the room's item list
                newRoom.addItem(i);
              }
            }
          }
        }

        //Adds the room to the adventure
        adventure.listAllRooms().add(newRoom);
      }

      //Iterates through all rooms
      for (Room r : adventure.listAllRooms()){

        //Iterates through connections of each room
        for (int i=0; i<r.getConnectedID().size();i++){

          //Iterates through all rooms to find matching ID
          for (Room c : adventure.listAllRooms()){
            //Checks for identical ID
            if (c.getID().equals(r.getConnectedID().get(i))){
              //Set r's connection as c
              r.setConnectedRoom(c,r.getConnectedDir().get(i));
            }
          }
        }
      }
      return adventure;
  }

}
