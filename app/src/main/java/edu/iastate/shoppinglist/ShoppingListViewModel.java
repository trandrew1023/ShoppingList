package edu.iastate.shoppinglist;

import androidx.lifecycle.ViewModel;

public class ShoppingListViewModel extends ViewModel {
    public ShoppingList shoppingList;

    public ShoppingListViewModel() {
        shoppingList = new ShoppingList("New Shopping List");
    }

}
