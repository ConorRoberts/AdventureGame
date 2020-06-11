package adventure;

import java.util.List;

/**
*
*
* @author Conor Roberts
*
*/
public class Parser{

  private Adventure adventure;

  public void setAdventure (Adventure adv){
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
  public Command parseUserCommand(String input) throws InvalidCommandException{ /*TODO Validate noun here*/
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
  public final List<String> allCommands(){
    return Command.getValidActions();
  }

  /**
   * Method used to validate the noun of a command
   * @param cmd
   * @return
   */
  public final boolean validNoun(Command cmd, Adventure adv) throws InvalidCommandException{
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
    switch(action){
      case "go":
        break;
      case "look":
        break;
      case "drop":
        break;
      case "take":
        break;
      case "read":
        break;
      case "toss":
        break;
      case "wear":
        break;
      case "eat":
        break;
    }
    return false;
  }

  public void validateGo(Command cmd, Adventure adventure) throws InvalidCommandException{
    if (!adventure.getCurrentRoom().hasConnection(cmd.getNoun())){
      throw new InvalidCommandException();
    }
  }

}
