package edu.iastate.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Main activity class for shopping lists. <br>
 * Tap on the shopping list name to open the item list. <br>
 * Tap and hold the shopping list name to rename the shopping list. <br>
 * Tap copy to create a copy of the shopping list. <br>
 * Tap remove to remove the shopping list. <br>
 * Tap the plus button to create a new shopping list. <br>
 * The number next to copy displays the number of items the shopping list has.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //Constants
    /**
     * Intent extra key for the shopping list's items.
     */
    private static final String ITEM_LIST_EXTRA = "itemList";
    /**
     * Intent extra key for the shopping list's position in the ArrayList.
     */
    private static final String POSITION = "position";
    /**
     * Name of file to save and load content from.
     */
    private static final String FILENAME = "filename";
    /**
     *  Used to identify the result from starting item list activity.
     */
    private static final int CONTACT_REQUEST = 1;

    /**
     * Recycler view for the shopping lists.
     */
    private RecyclerView recyclerView;
    /**
     * Adaptor to bind shopping lists to recycler view.
     */
    private RecyclerView.Adapter adapter;
    /**
     * Recycler view layout.
     */
    private RecyclerView.LayoutManager layoutManager;
    /**
     * Shopping list view model which contains all shopping lists.
     */
    private ShoppingListViewModel shoppingListViewModel;

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shoppingListViewModel = new ShoppingListViewModel();
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(savedInstanceState == null) {
            shoppingListViewModel.loadShoppingList(this, FILENAME);
            Log.d(TAG, "Loaded from file.");
        }

        adapter = new ListAdapter(shoppingListViewModel);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Adds a new shopping list.
     * @param view The current view.
     */
    public void newList(final View view) {
        //Text input prompt.
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        final EditText input = new EditText(view.getContext());
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newListName = input.getText().toString();
                ShoppingList newShoppingList = new ShoppingList(newListName);
                shoppingListViewModel.getShoppingLists().add(newShoppingList);
                adapter = new ListAdapter(shoppingListViewModel);
                recyclerView.setAdapter(adapter);
                shoppingListViewModel.saveShoppingList(view.getContext(), FILENAME);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * {@inheritDoc}
     * @param requestCode Request code.
     * @param resultCode Result code.
     * @param data Intent.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CONTACT_REQUEST) {
            if(resultCode == RESULT_OK) {
                ArrayList<String> itemList = data.getExtras().getStringArrayList(ITEM_LIST_EXTRA);
                int position = data.getExtras().getInt(POSITION);
                shoppingListViewModel.getShoppingLists().get(position).setItems(itemList);
                adapter = new ListAdapter(shoppingListViewModel);
                recyclerView.setAdapter(adapter);
                shoppingListViewModel.saveShoppingList(this, FILENAME);
            }
        }
    }
}
