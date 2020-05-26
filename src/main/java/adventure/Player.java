package adventure;

import java.util.ArrayList;

public class Player{

  private ArrayList<Item> items= new ArrayList<Item>();

  public final ArrayList<Item> listItems(){
    return items;
  }

  public final void pickupItem(Item i){
    items.add(i);
  }

}
