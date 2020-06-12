package adventure;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game implements java.io.Serializable{

  private static final long serialVersionUID = -9064936473102319459L;
  private Adventure adventure;
  private Scanner scanner;
  private Parser parser;

  public Game(){
    setScanner(new Scanner(System.in));
  }

  public Game(String[] flags){
    this();
    startup(flags);
    setParser(new Parser(adventure));
  }

/**
 * Main method
 * @param args Command line arguments
 */
  public static void main(String[] args){
      Game game = new Game(args);
      String userInput;
      do{
        System.out.println("Room: "+game.adventure.getCurrentRoom().toString());
        System.out.println(game.adventure.listRoomItems());
        //adventure.printRoomItems(game.adventure.getCurrentRoom());
        System.out.print("Command: ");
        userInput = game.getInput();
        game.handleCommand(game, userInput);
      }while(!userInput.equals("quit"));

      game.handleSave(game);
  }

  private String getInput(){
    return scanner.nextLine();
  }

  private void handleCommand(Game g, String str){
    try{
      Command command = g.parser.parseUserCommand(str);
      System.out.println(adventure.executeCommand(command));
    }catch (InvalidCommandException e){
      System.out.println("Usage Error - Type 'help' for a list of commands.");
    }
  }

  @Override
  public final String toString(){
    return ("This is a game");
  }

  public final void setScanner(Scanner s){
    scanner=s;
  }

  public final void setParser(Parser p){
    parser=p;
  }

  /**
   * Setter method for adventure because auto grader is a whiny baby
   * @param adv Adventure object
   */
  public final void setAdventure(Adventure adv){
    adventure=adv;
  }

  private void handleSave(Game game){
    System.out.print("Would you like to save (Y/N)? ");
    String input=getInput();
    if (input.equals("Y")){
      System.out.print("Enter a file name: ");
      input=getInput();
      game.save(input);
    }
    game.scanner.close();
  }

  /**
   * Starts the whole game
   * @param args Command line arguments
   */
  private void startup(String[] args){
    InputStream inputStream;
    if (args.length==2) {
        handleFlags(args);
    }else{
      inputStream=Game.class.getClassLoader().getResourceAsStream("default.json");
      setAdventure(generateAdventure(loadAdventureJson(inputStream)));
    }
    printWelcome(adventure);
  }

  private void handleFlags(String[] args){
    if (args[0].equals("-l")) { //Load game save
      restore(args[1]);
    } else if(args[0].equals("-a")) { //Load adventure json
      setAdventure(generateAdventure(loadAdventureJson(args[1])));
    }
  }

  private static void printWelcome(Adventure theAdventure){
    System.out.println("******************************************************");
    System.out.println("    Hello "+theAdventure.getPlayer().getName()+"! Welcome to Adventure.");
    System.out.println("******************************************************");
  }

  /**
  * Saves game state
  * @param fileName Name of output file
  */
  public void save(String fileName){
    try(ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(fileName))){

      objectStream.writeObject(adventure);

    }catch(Exception e){
      System.out.println("Error - Could not save game to file");
    }
  }

  /**
   * Restore save game
   * @param fileName Name of game save
   */
  public void restore(String fileName){
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))){
      setAdventure((Adventure) in.readObject());
      adventure.getPlayer().setGameSaveName(fileName);
    }catch(Exception e) {
      System.out.println("Error - Could not open game save.");
    }
  } 

  /**
  * Restores game state from file path
  * @param inputStream File to be loaded
  * @return JSONObject with details of the adventure
  */
  public final JSONObject loadAdventureJson(InputStream inputStream){
    try{
      Object file=new JSONParser().parse(new InputStreamReader(inputStream));

      JSONObject fileJson=(JSONObject) file;
      return (JSONObject) fileJson.get("adventure");
    }catch (Exception e){
      System.out.println("Error - Could not load adventure.json from stream");
      return null;
    }
  }

  /**
  * @param filename JSON file to be loaded and parsed
  * @return JSONObject with adventure data
  */
  public final JSONObject loadAdventureJson(String filename){
      try{
        Object file=new JSONParser().parse(new FileReader(filename));

        JSONObject fileJson=(JSONObject) file;
        return (JSONObject) fileJson.get("adventure");
      }catch (Exception e){
        System.out.println("Error - Could not load adventure.json from path");
        return null;
      }
  }

/**
* @param obj a JSONObject pointing to the first level into an adventure.json
* @return complete Adventure object
*/
  public final Adventure generateAdventure(JSONObject obj) {
    return (new Adventure(obj));
  }
}
