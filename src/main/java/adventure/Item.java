package adventure;

public class Item implements java.io.Serializable{

    private static final long serialVersionUID = -9064936473102319459L;

    private String description;
    private String name;
    private Room room;
    private String id;

    public Item(){
      
    }

    /**
     * @return A string formatted version of the object
     */
    @Override
    public final String toString(){
      return (name + "("+id+"): "+description);
    }

    public final String getName(){
        return this.name;
    }

    public final void setName(String newName){
        this.name=newName;
    }

    public final String getLongDescription(){
        return this.description;
    }

    public final void setLongDescription(String newDescription){
      this.description=newDescription;
    }

    public final Room getContainingRoom(){
        return this.room;
    }

    public final void setContainingRoom(Room newRoom){
        this.room=newRoom;
    }

    public final void setID(String newID){
        this.id=newID;
    }

    public final String getID(){
        return this.id;
    }

}
