package adventure;

import java.util.List;

/**
*
*
* @author Conor Roberts
*
*/
public class Parser{

  public Parser(){

  }

  /**
  * Parses a given string into a valid command object
  * @param input Line of text to parse
  * @return Command object complete with parsed input
  */
  public Command parseUserCommand(String input) throws InvalidCommandException{
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
    return Command.listActions();
  }

}
