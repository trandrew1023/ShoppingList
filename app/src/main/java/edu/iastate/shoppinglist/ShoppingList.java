package edu.iastate.shoppinglist;

import java.util.ArrayList;

public class ShoppingList {
    private String listName;
    private ArrayList<String> items;

    public ShoppingList(String listName) {
        this.listName = listName;
        items = new ArrayList<>();
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public int getItemCount() {
        return items.size();
    }

    public void newItem(String item) {
        items.add(item);
    }

    public  void removeItem(String item) {
        items.remove(item);
    }
}
