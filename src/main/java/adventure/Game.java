package adventure;

import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Game implements java.io.Serializable{

  private static final long serialVersionUID = -9064936473102319459L;
  private Parser parser = new Parser();
  private Adventure adventure;
  private Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        Game game = new Game();
        String userInput=null;
        game.startup(args);
        do{
          System.out.println("Room: "+game.adventure.getPlayer().getCurrentRoom().getShortDescription());
          game.printRoomItems(game.adventure.getPlayer().getCurrentRoom());
          userInput = game.sc.nextLine();
          try{
            Command command = game.parseCommand(userInput);
            game.executeCommand(command);
          }catch (InvalidCommandException e){
            System.out.println("Usage Error - Type 'help' for a list of commands.");
          }
        }while(!userInput.equals("quit"));

        System.out.println("Would you like to save? (Y/N)");
        userInput=game.sc.nextLine();
        game.handleSave(userInput,game);
        game.sc.close();
    }

    private void handleSave(String input, Game game){
      if (input.equals("Y")){
        System.out.println("Enter a file name: ");
        input=game.sc.nextLine();
        game.save(input);
      }
    }

    private void startup(String[] args){
        Game.printWelcome();
        InputStream inputStream;
        if (args.length==2){
          if (args[0].equals("-l")){ //Load game save
            restore(args[1]);
          }else if(args[0].equals("-a")){ //Load adventure json
            try{
              inputStream = Game.class.getClassLoader().getResourceAsStream(args[1]);
              this.adventure=this.generateAdventure(this.loadAdventureJson(inputStream));
            }catch(Exception e){
              System.out.println(e);
            }
          }
        }
    }

    private Command parseCommand(String cmd) throws InvalidCommandException{
      return parser.parseUserInput(cmd);
    }

    private static void printWelcome(){
      System.out.println("************************************");
      System.out.println("    Hello! Welcome to Adventure.");
      System.out.println("************************************");
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
      }else{
        throw new InvalidCommandException();
      }
    }

    private void commandInventory(Command cmd){
      Player player = adventure.getPlayer();
      if (player.getInventory().isEmpty()){
        System.out.println("--Inventory is empty--");
      }else{
        System.out.println("--Inventory--");
        for (Item i : player.getInventory()){
          System.out.println("\t"+i.getName());
        }
      }
    }

    private void printRoomItems(Room room){
      if (!room.listItems().isEmpty()){
        System.out.println("--Items contained here--");
        for (Item i : room.listItems()){
          System.out.println("\t"+i.getName());
        }
      }
    }

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

    private void commandDrop(Command cmd) throws InvalidCommandException{
      Player player = adventure.getPlayer();
      Item toDrop = player.findItem(cmd.getNoun());

      if (toDrop!=null){
        adventure.getPlayer().drop(toDrop);
      }else{
        throw new InvalidCommandException();
      }
    }

    private void commandLook(Command cmd) throws InvalidCommandException{
      Room currentRoom = adventure.getPlayer().getCurrentRoom();
      if (cmd.hasSecondWord() && currentRoom.containsItem(cmd.getNoun())) {
        System.out.println(currentRoom.findItem(cmd.getNoun()).getLongDescription());
      }else if(!cmd.hasSecondWord()){
        System.out.println(adventure.getCurrentRoomDescription());
      }else{
        throw new InvalidCommandException();
      }
    }

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
      try{
        FileOutputStream fileStream = new FileOutputStream(fileName);
        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

        objectStream.writeObject(adventure);

        fileStream.close();
        objectStream.close();

      }catch(IOException e){
        System.out.println(e);
      }
    }

    /**
     * 
     * @param fileName
     */
    public void restore(String fileName){
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName)); ){ 
        adventure = (Adventure) in.readObject(); 
      }catch(IOException e){ 
        System.out.println(e); 
      }catch(ClassNotFoundException e){ 
        System.out.println(e); 
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
        JSONObject newAdventure = (JSONObject) fileJson.get("adventure");

        return newAdventure;

    }catch (FileNotFoundException e){
        return null;
    }catch(IOException e){
        return null;
    }catch(ParseException e){
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
            JSONObject newAdventure = (JSONObject) fileJson.get("adventure");

            return newAdventure;

        }catch (FileNotFoundException e){
            return null;
        }catch(IOException e){
            return null;
        }catch(ParseException e){
            return null;
        }
    }

  private void parseItems(Adventure newAdventure, JSONArray objItems){
    for (Object i : objItems){
      JSONObject current = (JSONObject) i;

      Item item = new Item();

      item.setID(current.get("id").toString());
      item.setName(current.get("name").toString());
      item.setLongDescription(current.get("desc").toString());

      newAdventure.addItem(item);
    }
  }

  private void parseLoot(Adventure newAdventure, Room room, JSONArray objLoot){
    if (objLoot!=null){
      for (Object i : objLoot){
        JSONObject item = (JSONObject) i;
        room.addItem(newAdventure.findItemID(item.get("id").toString()));
      }
    }
  }

  private void parseConnections(Room room, JSONArray objEntrances){
    for (Object conn : objEntrances){
      JSONObject c = (JSONObject) conn;
      room.setConnectedRoom(c.get("dir").toString(),c.get("id").toString());
    }
  }

  private void parseRooms(Adventure newAdventure, JSONArray objRooms){
    for (Object obj : objRooms){
      JSONObject r = (JSONObject) obj;

      Room room  = new Room();

      JSONArray connections = (JSONArray) r.get("entrance");

      parseConnections(room, connections);

      room.setID(r.get("id").toString());
      room.setName(r.get("name").toString());
      room.setShortDescription(r.get("short_description").toString());
      room.setLongDescription(r.get("long_description").toString());

      JSONArray loot = (JSONArray) r.get("loot");

      parseLoot(newAdventure, room, loot);

      newAdventure.addRoom(room);
    }
  }

  private void parseConnectionsAsRooms(Adventure newAdventure){
    for(Room r : newAdventure.listAllRooms()){
      for (Map.Entry<String,String> m : r.getConnectedRoomsList().entrySet()) {
        r.setConnectedRoomAsRoom(m.getKey(),newAdventure.findRoom(m.getValue()));
      }
    }
  }

  /**
  * @param obj a JSONObject pointing to the first level into an adventure.json
  * @return completed Adventure object
  */
    public final Adventure generateAdventure(JSONObject obj) {
        Adventure newAdventure = new Adventure();

        JSONArray rooms = (JSONArray) obj.get("room");
        JSONArray items = (JSONArray) obj.get("item");

        parseItems(newAdventure, items);
        parseRooms(newAdventure, rooms);

        parseConnectionsAsRooms(newAdventure);

        newAdventure.getPlayer().setCurrentRoom(newAdventure.listAllRooms().get(0));
        return newAdventure;
    }
}
