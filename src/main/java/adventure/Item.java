package adventure;

public class Item implements java.io.Serializable{

    private static final long serialVersionUID = -9064936473102319459L;

    private String description;
    private String name;
    private Room room;
    private String id;

    public Item(){
      
    }

    public Item(String itemName, String itemID, String itemDesc){
        this.setName(itemName);
        this.setID(itemID);
        this.setLongDescription(itemDesc);
    }

    /**
     * @return A string formatted version of the object
     */
    @Override
    public final String toString(){
      return (name.toUpperCase()+": "+description);
    }

    /**
     * @return Name of item
     */
    public final String getName(){
        return this.name;
    }

    /**
     * 
     * @param newName
     */
    public final void setName(String newName){
        this.name=newName;
    }

    /**
     * 
     * @return Item long description
     */
    public final String getLongDescription(){
        return this.description;
    }

    /**
     * 
     * @param newDescription
     */
    public final void setLongDescription(String newDescription){
      this.description=newDescription;
    }

    /**
     * 
     * @return Room containing the item
     */
    public final Room getContainingRoom(){
        return this.room;
    }

    /**
     * 
     * @param newRoom
     */
    public final void setContainingRoom(Room newRoom){
        this.room=newRoom;
    }

    /**
     * 
     * @param newID
     */
    public final void setID(String newID){
        this.id=newID;
    }

    /**
     * 
     * @return Item ID
     */
    public final String getID(){
        return this.id;
    }

}
