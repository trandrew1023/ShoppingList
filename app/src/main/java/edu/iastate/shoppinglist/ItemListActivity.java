package edu.iastate.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 *
 */
public class ItemListActivity extends AppCompatActivity {
    private static final String TAG = "ItemListActivity";

    private int position;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> itemList = new ArrayList<>();

    private static final String ITEM_LIST_EXTRA = "itemList";
    private static final String POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        //Get the items for the selected shopping list.
        itemList = getIntent().getExtras().getStringArrayList(ITEM_LIST_EXTRA);
        //Get the position of the selected shopping list to return.
        position = getIntent().getExtras().getInt(POSITION);

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

    public void onClear(View view) {
        itemList = new ArrayList<>();
        adapter = new ItemListAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

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