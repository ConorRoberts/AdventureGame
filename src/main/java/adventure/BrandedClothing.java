package adventure;

public class BrandedClothing extends Clothing implements Readable{
    public BrandedClothing(String newName, String newID, String newDesc){
        super(newName, newID, newDesc);
    }
    public final String read(){
        return ("You have read "+getName());
    }
}
