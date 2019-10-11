package edu.iastate.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 *  Activity class for items in a shopping list. <br>
 *  Tap and hold the item name to rename the item. <br>
 *  Tap remove to remove the item from the list. <br>
 *  Tap save to save the shopping list.<br>
 *  Tap plus to add a new item. <br>
 *  Tap clear to remove all the items. <br>
 *  Back or save will save the items and go back to the main activity.
 */
public class ItemListActivity extends AppCompatActivity {
    private static final String TAG = "ItemListActivity";

    //Constants
    /**
     * Intent extra key for the shopping list's items.
     */
    private static final String ITEM_LIST_EXTRA = "itemList";
    /**
     * Name of file to save and load content from.
     */
    private static final String LIST_NAME_EXTRA = "listName";
    /**
     * Intent extra key for the shopping list's position in the ArrayList.
     */
    private static final String POSITION = "position";

    /**
     * The shopping list's position in the ArrayList.
     */
    private int position;
    /**
     * Recycler view for the item list.
     */
    private RecyclerView recyclerView;
    /**
     * Adaptor to bind the item list to recycler view.
     */
    private RecyclerView.Adapter adapter;
    /**
     * Recycler view layout.
     */
    private RecyclerView.LayoutManager layoutManager;
    /**
     * Items in the shopping list.
     */
    private ArrayList<String> itemList = new ArrayList<>();

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        //Set title to be the list name.
        setTitle(getIntent().getExtras().getString(LIST_NAME_EXTRA));
        //Get the items for the selected shopping list.
        itemList = getIntent().getExtras().getStringArrayList(ITEM_LIST_EXTRA);
        //Get the position of the selected shopping list to return.
        position = getIntent().getExtras().getInt(POSITION);
        //Get item list recycler view.
        recyclerView = findViewById(R.id.itemRecyclerView);
        //Set layout.
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //Set adaptor.
        adapter = new ItemListAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Prompts for a string input for item name and inserts a new item to the shopping list.
     * @param view The current view.
     */
    public void newItem(View view) {
        //Text input prompt.
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        final EditText input = new EditText(view.getContext());
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newListName = input.getText().toString();
                itemList.add(newListName);
                adapter = new ItemListAdapter(itemList);
                recyclerView.setAdapter(adapter);
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
     * Calls {@link #onBackPressed()} to save items and go back to shopping list.
     * @param view The current view.
     */
    public void onSave(View view) {
        onBackPressed();
    }

    /**
     * Clears the item list and updates the view.
     * @param view The current view.
     */
    public void onClear(View view) {
        itemList = new ArrayList<>();
        adapter = new ItemListAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ITEM_LIST_EXTRA, itemList);
        intent.putExtra("id", "Back");
        intent.putExtra(POSITION, position);
        setResult(RESULT_OK, intent);
        finish();
    }
}