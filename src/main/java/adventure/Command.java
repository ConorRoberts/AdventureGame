package adventure;

import java.util.ArrayList;
import java.util.List;

public class Command {
    public static final List<String> DIRECTIONS = new ArrayList<>(){{
        add("up");
        add("down");
        add("N");
        add("E");
        add("S");
        add("W");
    }};
    public static final List<String> ACTIONS = new ArrayList<>(){{
        add("go");
        add("look");
        add("quit");
        add("take");
        add("inventory");
        add("help");
        add("eat");
        add("wear");
        add("toss");
        add("read");
    }};
    private String action;
    private String noun;

    @Override
    public final String toString(){
        return ("Action: "+getActionWord()+", Noun: "+getNoun());
    }

  /**
     * Create a command object with default values.
     * both instance variables are set to null
     *
     */
    public Command() throws InvalidCommandException {
        this(null, null);
    }


  /**
     * Create a command object given only an action.  this.noun is set to null
     *
     * @param command The first word of the command.
     *
     */
    public Command(String command) throws InvalidCommandException{
        //TODO validate the action word here and throw an exception if it isn't
        // a single-word action
        this(command, null);
    }

    /**
     * Create a command object given both an action and a noun
     *
     * @param command The first word of the command.
     * @param what      The second word of the command.
     */
    public Command(String command, String what) throws InvalidCommandException{
        //TODO validate the command here and ensure that the noun provided
        // is a legitimate second word for the command
        // throw an exception if not
        if (ACTIONS.contains(command)){
          setAction(command);
          setNoun(what);
        }else{
          throw new InvalidCommandException();
        }
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     *
     * @return The command word.
     */
    public String getActionWord() {
        return this.action;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getNoun() {
        return this.noun;
    }


    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {
        return (noun != null);
    }

    /**
     *
     * @param word
     */
    public void setAction(String word){
        action=word;
    }

    /**
     *
     * @param word
     */
    public void setNoun(String word){
        noun=word;
    }
}
