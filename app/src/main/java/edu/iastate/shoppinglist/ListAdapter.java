package edu.iastate.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private ArrayList<ShoppingListViewModel> shoppingLists;

    /**
     * {@inheritDoc}
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //Shopping list name.
        holder.listName.setText(shoppingLists.get(position).shoppingList.getListName());
        //Open shopping list.
        holder.listName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ItemListActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        //Edit shopping list name.
        holder.listName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final EditText input = new EditText(view.getContext());
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newListName = input.getText().toString();
                        holder.listName.setText(newListName);
                        shoppingLists.get(position).shoppingList.setListName(newListName);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return true;
            }
        });
        //Shopping list item count.
        holder.itemCount.setText(shoppingLists.get(position).shoppingList.getItemCount() + "");
        //Shopping list remove button.
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingLists.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, shoppingLists.size());
            }
        });
        //Shopping list copy button.
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingLists.add(shoppingLists.get(position));
                notifyItemInserted(position+1);
                notifyItemRangeChanged(position+1, shoppingLists.size());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView listName;
        TextView itemCount;
        Button copy;
        Button remove;

        public MyViewHolder(View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.list_name);
            itemCount = itemView.findViewById(R.id.item_count);
            copy = itemView.findViewById(R.id.copy);
            remove = itemView.findViewById(R.id.remove);
        }
    }

    public ListAdapter(ArrayList<ShoppingListViewModel> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }


}
