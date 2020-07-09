package adventure;

public class SmallFood extends Food implements Tossable{
    public SmallFood(String newName, String newID, String newDesc){
        super(newName, newID, newDesc);
    }
    public final String toss(){
        return ("You have thrown "+getName());
    }
}
