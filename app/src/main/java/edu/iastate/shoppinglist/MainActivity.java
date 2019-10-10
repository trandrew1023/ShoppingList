package edu.iastate.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ShoppingListViewModel> shoppingLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ListAdapter(shoppingLists);
        recyclerView.setAdapter(adapter);
    }

    public void newList(View view) {
        ShoppingListViewModel newShoppingList = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ShoppingListViewModel.class);
        shoppingLists.add(newShoppingList);
        adapter = new ListAdapter(shoppingLists);
        recyclerView.setAdapter(adapter);
    }
}
