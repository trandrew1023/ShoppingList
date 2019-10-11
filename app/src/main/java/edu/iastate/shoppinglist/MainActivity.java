package edu.iastate.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String ITEM_LIST_EXTRA = "itemList";
    private static final String POSITION = "position";
    private static final String FILENAME = "filename";
    private static final int CONTACT_REQUEST = 1;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ShoppingListViewModel shoppingListViewModel;

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

    public void newList(final View view) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CONTACT_REQUEST) {
            if(resultCode == RESULT_OK) {
                ArrayList<String> itemList = data.getExtras().getStringArrayList(ITEM_LIST_EXTRA);
                int position = data.getExtras().getInt(POSITION);
                shoppingListViewModel.getShoppingLists().get(position).setItems(itemList);
                shoppingListViewModel.saveShoppingList(this, FILENAME);
            }
        }
    }
}
