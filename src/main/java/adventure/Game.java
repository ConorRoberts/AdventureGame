package adventure;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Game{
    public static void main(String[] args){
        Game theGame = new Game();
        Scanner sc = new Scanner(System.in);
        JSONObject jsonAdventure=null;
        String userInput=null;

        //Welcome message
        System.out.println("Hello! Welcome to Adventure.");

        //Getting user input for loading JSON
        do{
          System.out.println("Do you want to load a json file? (Yes/No):");
          userInput=sc.nextLine();
        }while(!userInput.equals("Yes") && !userInput.equals("No"));

        //Loading JSON
        if (userInput.equals("No")){ //Loading default JSON
          jsonAdventure=theGame.loadAdventureJson("adventure_files/default.json");
        }else if(userInput.equals("Yes")){ //Loading custom JSON
          System.out.println("\n-- To use your own file, place it in the adventure_files folder and input the name of the file.json --");
          do{
            System.out.println("Enter your filename:");
            String fileName=sc.nextLine();
            jsonAdventure=theGame.loadAdventureJson("adventure_files/"+fileName);
          }while(jsonAdventure==null);
        }

        Adventure adventure = theGame.generateAdventure(jsonAdventure);

        //Sets current room as first in room list
        adventure.setCurrentRoom(adventure.listAllRooms().get(0));

        do{
          System.out.println("\n-----------------------------------");
          System.out.println("\nYou are currently in: "+adventure.getCurrentRoomDescription());

          if (adventure.getCurrentRoom().listItems().size()>0){
            System.out.println("\nItems contained here:");
            for (Item i : adventure.getCurrentRoom().listItems()){
              System.out.println("\t"+i.getName());
            }
          }
          System.out.println("\nWhat would you like to do?");
          userInput = sc.nextLine();

          String[] command = userInput.split(" ");

          if (command[0].equals("help")){
            System.out.println("\nCommands:");
            System.out.println("\tgo <N/E/S/W> - Traverses in the given direction");
            System.out.println("\tlook (item name)- Gives description of current room or item in room (with item parameter)");
            System.out.println("\tquit - Ends the adventure :(");
          }else if (command[0].equals("go") && command.length==2){
            if ((command[1].equals("N") || command[1].equals("E") || command[1].equals("S") || command[1].equals("W")) && adventure.getCurrentRoom().getConnectedRoom(command[1])!=null){
              adventure.setCurrentRoom(adventure.getCurrentRoom().getConnectedRoom(command[1]));
            }else{
              System.out.println("\nInvalid move.");
            }
          }else if (command[0].equals("look") && command.length==1){ //Look command for room description
            System.out.println("\nLong Description: "+adventure.getCurrentRoom().getLongDescription());
          }else if (command[0].equals("look") && command.length==2){ //Look command for items
            Item lookItem = adventure.getCurrentRoom().findItem(command[1]);
            if (lookItem!=null){
              System.out.println("\n"+lookItem.getLongDescription());
            }else{
              System.out.println("Invalid item name.");
            }
          }else{
            System.out.println("\nInvalid usage. Try 'help' for help.");
          }
        }while(!userInput.equals("quit"));
    }

    public JSONObject loadAdventureJson(String filename){
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

    public Adventure generateAdventure(JSONObject obj) {
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
