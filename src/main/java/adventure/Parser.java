package adventure;

/**
* @author Conor Roberts
*/
public class Parser{

  private Adventure adventure;

  public final void setAdventure(Adventure adv){
    adventure=adv;
  }

  public Parser(){

  }

  public Parser(Adventure adv){
    this();
    setAdventure(adv);
  }

  @Override
  public final String toString(){
    return ("This is a parser (and a useless method)");
  }

  /**
  * Parses a given string into a valid command object
  * @param input Line of text to parse
  * @return Command object complete with parsed input
  */
  public final Command parseUserCommand(String input) throws InvalidCommandException{ /*TODO Validate noun here*/
    String[] split = input.split(" ",2);
    if (split.length==1){
      validateSingleWordCommand(split[0]);
      return new Command(split[0]);
    }else if(split.length==2){
      validateNoun(split[0],split[1]);
      return new Command(split[0],split[1]);
    }else{
      throw new InvalidCommandException();
    }
  }

  private void validateSingleWordCommand(String action) throws InvalidCommandException{
    if (!action.equals("look") && !action.equals("inventory") && !action.equals("help")){
      throw new InvalidCommandException();
    }
  }

  /**
  *  @return A list containing all valid action words
  */
  public static final String allCommands(){
    return Command.ACTIONS.toString();
  }

  /**
   * Method used to validate the noun of a command
   * @param action Action word
   * @param noun Noun word
   */
  private void validateNoun(String action, String noun) throws InvalidCommandException{
    if (action.equals("go")){
      validateGo(noun);
    }else if(action.equals("take")) {
      validateRoomItem(noun);
    }else if(action.equals("look")){
      validateLook(noun);
    }else if (action.equals("eat") || action.equals("wear") || action.equals("read") || action.equals("toss")){
      validateUse(action, noun);
    }else{
      throw new InvalidCommandException();
    }
  }

  public final void validateLook(String item) throws InvalidCommandException{
    Player player=adventure.getPlayer();
    Room room = adventure.getCurrentRoom();
    if (!room.containsItem(item) && !player.hasItem(item)){
      throw new InvalidCommandException();
    }
  }

  /**
   * Checks if there is a connected room in given direction
   * @param dir Direction letter
   * @throws InvalidCommandException Invalid command
   */
  public void validateGo(String dir) throws InvalidCommandException{
    if (!adventure.getCurrentRoom().hasConnection(dir)){
      throw new InvalidCommandException();
    }
  }

  /**
   * Checks if item is within inventory
   * @param item Item name
   * @param action Action word
   * @throws InvalidCommandException Invalid command
   */
  public void validateUse(String action, String item) throws InvalidCommandException{
    boolean has = adventure.getPlayer().hasItem(item);
    if(!has){
      throw new InvalidCommandException();
    }
    Item i = adventure.getPlayer().findItem(item);
    validateEat(action, i); //no space for large if statement (:
    validateWear(action, i);
    validateToss(action, i);
    validateRead(action, i);
  }

  private void validateToss(String action, Item item) throws InvalidCommandException{
    if (action.equals("toss") && !(item instanceof Weapon) && !(item instanceof SmallFood)){
      throw new InvalidCommandException();
    }
  }

  private void validateRead(String action, Item item) throws InvalidCommandException{
    if (action.equals("read") && !(item instanceof Spell) && !(item instanceof BrandedClothing)){
      throw new InvalidCommandException();
    }
  }

  private void validateWear(String action, Item item) throws InvalidCommandException{
    if (action.equals("wear") && !(item instanceof Clothing)){
      throw new InvalidCommandException();
    }
  }

  private void validateEat(String action, Item item) throws InvalidCommandException{
    if (action.equals("eat") && !(item instanceof Food) && !(item instanceof SmallFood)){
      throw new InvalidCommandException();
    }
  }

  /**
   * Checks if item name is within room
   * @param item Item name
   * @throws InvalidCommandException Invalid command
   */
  public void validateRoomItem(String item) throws InvalidCommandException{
    if(!adventure.getCurrentRoom().containsItem(item)){
      throw new InvalidCommandException();
    }
  }

}
