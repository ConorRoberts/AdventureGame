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
        JSONObject adventure=theGame.loadAdventureJson("example_adventure.json");

        Adventure myAdventure = theGame.generateAdventure(adventure);

        /*for (Object r : rooms){
            JSONObject current = (JSONObject) r;
            String temp=(String) current.get("name");
            System.out.println(temp);
        }*/

        /* TESTING
        Parse json
        Store rooms in arraylist using methods
        Print all rooms and some given properties
        */


        /* 3. Parse the file the user specified to create the
        adventure, or load your default adventure*/

        // 4. Print the beginning of the adventure

        // 5. Begin game loop here

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

        JSONArray jsonRoom = (JSONArray) obj.get("room");
        JSONArray jsonItem = (JSONArray) obj.get("item");

        return adventure;
    }

}
