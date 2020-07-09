package adventure;

public class Clothing extends Item implements Wearable{

    private boolean wearing;

    public Clothing(String newName, String newID, String newDesc){
        super(newName, newID, newDesc);
        setWearing(false);
    }
    public final String wear() {
        setWearing(true);
        return ("You are now wearing "+getName());
    }

    public final boolean getWearing(){
        return wearing;
    }

    public final void setWearing(boolean bool){
        wearing=bool;
    }
}
