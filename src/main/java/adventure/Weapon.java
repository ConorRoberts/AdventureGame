package adventure;

public class Weapon extends Item implements Tossable{

    public Weapon(String newName, String newID, String newDesc){
        super(newName, newID, newDesc);
    }

    public final String toss(){
        return ("You have thrown "+getName());
    }
}
