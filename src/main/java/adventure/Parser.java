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
    String action;
    String noun;
    if (split.length==1){
      action = split[0];
      return new Command(action);
    }else if(split.length==2){
      action = split[0];
      noun = split[1];
      return new Command(action,noun);
    }else{
      throw new InvalidCommandException();
    }
  }

  /**
  *  @return A list containing all valid action words
  */
  public final String allCommands(){
    return Command.getValidActions().toString();
  }

  /**
   * Method used to validate the noun of a command
   * @param cmd Command object with validated action
   */
  public final void validNoun(Command cmd) throws InvalidCommandException{
    /*Guaranteed to have valid action
    * Actions that need nouns validated
    * go
    * look
    * eat
    * read
    * wear
    * toss
    * drop
    * take
    * */
    String action = cmd.getActionWord();
    String noun = cmd.getNoun();
    if (action.equals("go")){
      validateConnection(cmd.getNoun(),adventure);
    }else if(action.equals("take") && cmd.hasSecondWord()) {
      validateRoomItem(cmd.getNoun(), adventure);
    }else if(action.equals("look") && cmd.hasSecondWord()){
      validateLook(cmd.getNoun(),adventure);
    }else if (action.equals("eat") || action.equals("wear") || action.equals("read") || action.equals("toss")){
      validateInventoryItem(cmd.getNoun(), adventure);
    }else{
      throw new InvalidCommandException();
    }
  }

  public final void validateLook(String item, Adventure adv) throws InvalidCommandException{
    Player player=adventure.getPlayer();
    if (!adv.getCurrentRoom().containsItem(item) && !player.hasItem(item)){
      throw new InvalidCommandException();
    }
  }

  /**
   * Checks if there is a connected room in given direction
   * @param dir Direction letter
   * @param adv Adventure object
   * @throws InvalidCommandException Invalid command
   */
  public void validateConnection(String dir, Adventure adv) throws InvalidCommandException{
    if (!adv.getCurrentRoom().hasConnection(dir)){
      throw new InvalidCommandException();
    }
  }

  /**
   * Checks if item is within inventory
   * @param item Item name
   * @param adv Adventure obj
   * @throws InvalidCommandException Invalid command
   */
  public void validateInventoryItem(String item, Adventure adv) throws InvalidCommandException{
    if(!adv.getPlayer().hasItem(item)){
      throw new InvalidCommandException();
    }
  }

  /**
   * Checks if item name is within room
   * @param item Item name
   * @param adv Adventure object
   * @throws InvalidCommandException Invalid command
   */
  public void validateRoomItem(String item, Adventure adv) throws InvalidCommandException{
    if(!adv.getCurrentRoom().containsItem(item)){
      throw new InvalidCommandException();
    }
  }

}
