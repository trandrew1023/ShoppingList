package edu.iastate.shoppinglist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Shopping list object.
 */
public class ShoppingList implements Serializable {
    /**
     * Shopping list name.
     */
    private String listName;
    /**
     * Shopping list items.
     */
    private ArrayList<String> items;

    /**
     * Constructor for {@link ShoppingList}
     * @param listName Shopping list name.
     */
    public ShoppingList(String listName) {
        this.listName = listName;
        items = new ArrayList<>();
    }

    /**
     * Gets the shopping list name.
     * @return Shopping list name.
     */
    public String getListName() {
        return listName;
    }

    /**
     * Sets the shopping list name.
     * @param listName New shopping list name.
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    /**
     * Gets the shopping list items.
     * @return List of items in the shopping list.
     */
    public ArrayList<String> getItems() {
        return items;
    }

    /**
     * Sets the shopping list items.
     * @param items New item list.
     */
    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    /**
     * Gets the number of items in the shopping list.
     * @return Number of items.
     */
    public int getItemCount() {
        return items.size();
    }
}
