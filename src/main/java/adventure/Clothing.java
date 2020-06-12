package adventure;

public class Clothing extends Item{

    public Clothing(String newName, String newID, String newDesc){
        super(newName, newID, newDesc);
    }
    public String wear() {
        return ("You are now wearing "+getName());
    }
}
