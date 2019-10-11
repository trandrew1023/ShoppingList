package edu.iastate.shoppinglist;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ShoppingListViewModel extends ViewModel {
    private static final String TAG = "ShoppingListViewModel";

    private ArrayList<ShoppingList> shoppingLists;

    public ShoppingListViewModel() {
        shoppingLists = new ArrayList<>();
    }

    public ArrayList<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public void saveShoppingList(Context context, String filename) {
        FileOutputStream fos;
        ObjectOutputStream oos;

        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(shoppingLists);
            oos.close();
            fos.close();
        } catch (Exception e) {
            Log.d(TAG, "Exception on saving", e);
        }
    }

    public void loadShoppingList(Context context, String filename) {
        FileInputStream fis;
        ObjectInputStream ois;

        try {
            fis = new FileInputStream(context.getFileStreamPath(filename));
            ois = new ObjectInputStream(fis);
            shoppingLists = (ArrayList<ShoppingList>) ois.readObject();
            ois.close();
            fis.close();
        } catch(Exception e) {
            Log.d(TAG, "Exception on loading", e);
        }
    }
}
