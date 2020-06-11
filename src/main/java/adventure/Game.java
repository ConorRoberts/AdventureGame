package adventure;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game implements java.io.Serializable{

  private static final long serialVersionUID = -9064936473102319459L;
  private Adventure adventure;
  private Scanner scanner;
  private Parser parser;

  public Game(){
    setScanner();
    setParser();
  }

  public Game(String[] flags){
    this();
    startup(flags);
  }

  /**
   * Main method
   * @param args Command line arguments
   */
    public static void main(String[] args){
        Game game = new Game(args);
        String userInput=null;
        do{
          System.out.println("Room: "+game.adventure.getCurrentRoom().toString());
          game.printRoomItems(game.adventure.getPlayer().getCurrentRoom());
          System.out.print("Command: ");
          userInput = game.scanner.nextLine();
          try{
            Command command = game.parseCommand(userInput);
            game.executeCommand(command);
          }catch (InvalidCommandException e){
            System.out.println("Usage Error - Type 'help' for a list of commands.");
          }
        }while(!userInput.equals("quit"));

        game.handleSave(game.scanner,game);
        game.scanner.close();
    }

  @Override
  public final String toString(){
    return ("This is a game");
  }

  public final void setScanner(){
    scanner=new Scanner(System.in);
  }

  public final void setParser(){
    parser=new Parser();
  }

  /**
   * Setter method for adventure because auto grader is a whiny baby
   * @param adv
   */
  public final void setAdventure(Adventure adv){
    adventure=adv;
  }

  private void handleSave(Scanner theScanner, Game game){
    System.out.print("Would you like to save (Y/N)? ");
    String input=theScanner.nextLine();
    if (input.equals("Y")){
      System.out.print("Enter a file name: ");
      input=theScanner.nextLine();
      game.save(input);
    }
  }

  /**
   * 
   * @param args
   */
  private void startup(String[] args){
    InputStream inputStream;
    if (args.length==2) {
        if (args[0].equals("-l")) { //Load game save
          restore(args[1]);
        } else if(args[0].equals("-a")) { //Load adventure json
          setAdventure(generateAdventure(loadAdventureJson(args[1])));
        }
    }else{
      inputStream=Game.class.getClassLoader().getResourceAsStream("default.json");
      setAdventure(generateAdventure(loadAdventureJson(inputStream)));
    }
    printWelcome(adventure);
  }

  private Command parseCommand(String cmd) throws InvalidCommandException{
    return parser.parseUserCommand(cmd);
  }

  private static void printWelcome(Adventure theAdventure){
    System.out.println("******************************************************");
    System.out.println("    Hello "+theAdventure.getPlayer().getName()+"! Welcome to Adventure.");
    System.out.println("******************************************************");
  }

  private static void printHelp(){
    System.out.println("take <item name>: Transfer item into player's inventory");
    System.out.println("look: See current room description");
    System.out.println("look <item name>: See item description");
    System.out.println("go <N/E/S/W>: Move in the specified direction");
  }

  private void executeCommand(Command cmd) throws InvalidCommandException{
    if (cmd.getActionWord().equals("go") && cmd.hasSecondWord()){
      commandGo(cmd);
    }else if(cmd.getActionWord().equals("look")){
      commandLook(cmd);
    }else if(cmd.getActionWord().equals("inventory")){
      commandInventory(cmd);
    }else if(cmd.getActionWord().equals("help")){
      Game.printHelp();
    }else if(cmd.getActionWord().equals("take") && cmd.hasSecondWord()){
      commandTake(cmd);
    }else if(cmd.getActionWord().equals("drop") && cmd.hasSecondWord()){
      commandDrop(cmd);
    }else if(!cmd.getActionWord().equals("quit")){
      throw new InvalidCommandException();
    }
  }

  /**
   * Operation for "inventory" command
   * @param cmd Command object
   */
  private void commandInventory(Command cmd){
    Player player = adventure.getPlayer();
    if (player.getInventory().isEmpty()){
      System.out.println("--Inventory is empty--");
    }else{
      System.out.println("--"+player.getName()+"'s Inventory--");
      for (Item i : player.getInventory()){
        System.out.println("\t"+i.getName());
      }
    }
  }

  private void printRoomItems(Room room){
    if (!room.listItems().isEmpty()){
      System.out.println("--Items Here--");
      for (Item i : room.listItems()){
        System.out.println("\t"+i.getName());
      }
    }
  }

  /**
   * Operation for "take" command
   * @param cmd
   * @throws InvalidCommandException
   */
  private void commandTake(Command cmd) throws InvalidCommandException{
    Player player = adventure.getPlayer();
    Room current=player.getCurrentRoom();
    Item toTake = current.findItem(cmd.getNoun());

    if (toTake!=null){
      adventure.getPlayer().take(toTake);
    }else{
      throw new InvalidCommandException();
    }
  }
    
  /**
   * Operation for "drop" command
   * @param cmd Command object
   * @throws InvalidCommandException
   */
  private void commandDrop(Command cmd) throws InvalidCommandException{
    Player player = adventure.getPlayer();
    Item toDrop = player.findItem(cmd.getNoun());

    if (toDrop!=null){
      adventure.getPlayer().drop(toDrop);
    }else{
      throw new InvalidCommandException();
    }
  }

  /**
   * Operation for "look" command
   * Allows user to look at room/inventory items
   * @param cmd Command object
   * @throws InvalidCommandException Invalid command
   */
  private void commandLook(Command cmd) throws InvalidCommandException{
    Room currentRoom = adventure.getCurrentRoom();
    Player player=adventure.getPlayer();
    if (cmd.hasSecondWord() && currentRoom.containsItem(cmd.getNoun())) {
      Item theItem = currentRoom.findItem(cmd.getNoun());
      System.out.println(theItem.toString());
    }else if (cmd.hasSecondWord() && player.hasItem(cmd.getNoun())) {
        Item theItem = player.findItem(cmd.getNoun());
        System.out.println(theItem.toString());
    }else if(!cmd.hasSecondWord()){
      System.out.println(adventure.getCurrentRoomDescription());
    }else{
      throw new InvalidCommandException();
    }
  }

  /**
   * Operation for "go" command
   * @param cmd Command object
   * @throws InvalidCommandException Invalid command
   */
  private void commandGo(Command cmd) throws InvalidCommandException{
    Room currentRoom = adventure.getPlayer().getCurrentRoom();
    if (Command.listDirections().contains(cmd.getNoun()) && currentRoom.hasConnection(cmd.getNoun())){
      Room newRoom = currentRoom.getConnectedRoom(cmd.getNoun());
      adventure.getPlayer().setCurrentRoom(newRoom);
    }else{
      throw new InvalidCommandException();
    }
  }

  /**
  * Saves game state
  * @param fileName Name of output file
  */
  public void save(String fileName){
    try(ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(fileName));){

      objectStream.writeObject(adventure);

      objectStream.close();
    }catch(Exception e){
      System.out.println("Error - Could not save game to file");
    }
  }

  /**
   * Restore save game
   * @param fileName Name of game save
   */
  public void restore(String fileName){
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName)); ){
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
   * Adds items to Adventure's list of items
   * @param newAdventure Adventure to be modified
   * @param objItems JSONArray of items
   */
private void parseItems(Adventure newAdventure, JSONArray objItems){
  for (Object objCurrent : objItems){
    JSONObject i = (JSONObject) objCurrent;

    String name=i.get("name").toString();
    String id = i.get("id").toString();
    String desc = i.get("desc").toString();

    newAdventure.addItem(new Item(name,id,desc));
  }
}


/**
 * Converts JSONArray into individual room objects
 * @param newAdventure Adventure object
 * @param objRooms JSONArray of rooms
 */
private void parseRooms(Adventure newAdventure, JSONArray objRooms){
  for (Object obj : objRooms){
    newAdventure.addRoom(new Room(newAdventure, (JSONObject) obj));
  }
}

/**
 * Converts the list of room IDs to actual room objects
 * @param newAdventure Adventure object
 */
private void parseConnectionsAsRoom(Adventure newAdventure){
  for(Room r : newAdventure.listAllRooms()){
    for (Map.Entry<String,String> m : r.getConnectedRoomsList().entrySet()) {
      r.setConnectedRoomAsRoom(m.getKey(),newAdventure.findRoom(m.getValue()));
    }
  }
}

/**
* @param obj a JSONObject pointing to the first level into an adventure.json
* @return complete Adventure object
*/
  public final Adventure generateAdventure(JSONObject obj) {
    Adventure newAdventure = new Adventure();

    parseItems(newAdventure, (JSONArray) obj.get("item"));
    parseRooms(newAdventure, (JSONArray) obj.get("room"));

    parseConnectionsAsRoom(newAdventure);

    newAdventure.getPlayer().setCurrentRoom(newAdventure.listAllRooms().get(0));
    return newAdventure;
  }
}
