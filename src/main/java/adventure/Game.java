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
        Scanner sc = new Scanner(System.in);
        JSONObject jsonAdventure=null;
        String userInput=null;

        Parser adventureParser = new Parser();

        //Welcome message
        System.out.println("Hello! Welcome to Adventure.");

        //Getting user input for loading JSON
        do{
          System.out.println("Do you want to load a json file? (Yes/No):");
          userInput=sc.nextLine();
        }while(!userInput.equals("Yes") && !userInput.equals("No"));

        //Loading JSON
        if (userInput.equals("No")){ //Loading default JSON
          jsonAdventure=adventureParser.loadAdventureJson("adventure_files/default.json");
        }else if(userInput.equals("Yes")){ //Loading custom JSON
          System.out.println("\n-- To use your own file, place it in the adventure_files folder and input the name of the file.json --");
          do{
            System.out.println("Enter your filename:");
            String fileName=sc.nextLine();
            jsonAdventure=adventureParser.loadAdventureJson("adventure_files/"+fileName);
          }while(jsonAdventure==null);
        }

        Adventure adventure = adventureParser.generateAdventure(jsonAdventure);

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
}
