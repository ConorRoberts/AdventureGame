package adventure;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Game{

    /* this is the class that runs the game.
    You may need some member variables */

    public static void main(String args[]) throws Exception {

        /* You will need to instantiate an object of type
        game as we're going to avoid using static methods
        for this assignment */

        Game theGame = new Game();

        // 1. Print a welcome message to the user
        System.out.println("Hello! Welcome to Adventure.");

        // 2. Ask the user if they want to load a json file.
        /*System.out.print("Do you want to load a json file? (Yes/No) ");
        Scanner sc = new Scanner(System.in);*/

        //Load JSON

        /* 3. Parse the file the user specified to create the
        adventure, or load your default adventure*/
        JSONObject adventure=theGame.loadAdventureJson("adventure_files/example_adventure.json");

        Adventure myAdventure = theGame.generateAdventure(adventure);

        // 4. Print the beginning of the adventure

        // 5. Begin game loop here
        /*Scanner sc = new Scanner(System.in);

        while (1==2){
          System.out.println("You are in <room>. Where would you like to go? (N/E/S/W) ");
          String userInput = sc.nextLine();

          String[] splitInput = userInput.split(" ");

          if (splitInput[0].equals("go")){
            adventure.currentRoom().getConnectedRoom(splitInput[1]);
          }
        }*/

        // 6. Get the user input. You'll need a Scanner

        /* 7+. Use a game instance method to parse the user
        input to learn what the user wishes to do*/

        //use a game instance method to execute the users wishes*/

        /* if the user doesn't wish to quit,
        repeat the steps above*/
    }

    /* you must have these instance methods and may need more*/

    public JSONObject loadAdventureJson(String filename){
        try{
            Object file=new JSONParser().parse(new FileReader(filename));

            JSONObject fileJson=(JSONObject) file;
            JSONObject adventure = (JSONObject) fileJson.get("adventure");

            return adventure;

        }catch (FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }catch(ParseException e){
            System.out.println(e);
        }

        return null;

    }

    public Adventure generateAdventure(JSONObject obj) {
        Adventure adventure = new Adventure();
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

        //Handles room connections
        for (Object currentRoom : rooms){
          JSONObject r = (JSONObject) currentRoom;

        }

        //Prints rooms and their respective items. For testing
        for (Room r : adventure.listAllRooms()){
          System.out.println(r.getName() + " : " + r.getLongDescription());
          for (Item i : r.listItems()){
            System.out.println(i.getName());
          }
        }

        return adventure;
    }

}
