package adventure;

public class Food extends Item implements Edible{
    public Food(String newName, String newID, String newDesc){
        super(newName, newID, newDesc);
    }
    public final String eat(){
        return ("You have eaten "+getName());
    }
}
