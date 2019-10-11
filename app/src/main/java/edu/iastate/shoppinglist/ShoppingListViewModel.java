package edu.iastate.shoppinglist;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Shopping list view model. Used for orientation and saving.
 */
public class ShoppingListViewModel extends ViewModel {
    private static final String TAG = "ShoppingListViewModel";
    /**
     * List of {@link ShoppingList} objects.
     */
    private ArrayList<ShoppingList> shoppingLists;

    /**
     * Constructor for {@link ShoppingListViewModel}
     */
    public ShoppingListViewModel() {
        shoppingLists = new ArrayList<>();
    }

    /**
     * Gets the list of {@link ShoppingList}
     * @return
     */
    public ArrayList<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    /**
     * Saves the list of shopping lists to a file.
     * @param context Current state/context.
     * @param filename Name of file to save content.
     */
    public void saveShoppingList(Context context, String filename) {
        FileOutputStream fos;
        ObjectOutputStream oos;

        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(shoppingLists);
            oos.close();
            fos.close();
            Log.d(TAG, "Content saved");
        } catch (Exception e) {
            Log.d(TAG, "Exception on saving", e);
        }
    }

    /**
     * Loads the list of shopping lists from a file.
     * @param context Current state/conext.
     * @param filename Name of file to load content from.
     */
    public void loadShoppingList(Context context, String filename) {
        FileInputStream fis;
        ObjectInputStream ois;

        try {
            fis = new FileInputStream(context.getFileStreamPath(filename));
            ois = new ObjectInputStream(fis);
            shoppingLists = (ArrayList<ShoppingList>) ois.readObject();
            ois.close();
            fis.close();
            Log.d(TAG, "Content loaded");
        } catch(Exception e) {
            Log.d(TAG, "Exception on loading", e);
        }
    }
}
