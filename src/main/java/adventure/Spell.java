package adventure;

public class Spell extends Item implements Readable{
    public Spell(String newName, String newID, String newDesc){
        super(newName, newID, newDesc);
    }
    public final String read(){
        return ("You have read "+getName());
    }
}
