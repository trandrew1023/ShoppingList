package edu.iastate.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter that binds the shopping list items to the recycler view.
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {
    private static final String TAG = "ItemListAdapter";

    /**
     * Items in the shopping list.
     */
    private ArrayList<String> items;

    /**
     * Constructor for {@link ItemListAdapter}.
     * @param itemList
     */
    public ItemListAdapter(ArrayList<String> itemList) {
        this.items = itemList;
    }

    /**
     * {@inheritDoc}
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    /**
     * {@inheritDoc}
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //Item name.
        holder.itemName.setText(items.get(position));
        //Edit item name.
        holder.itemName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final EditText input = new EditText(view.getContext());
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newItemName = input.getText().toString();
                        holder.itemName.setText(newItemName);
                        items.set(position, newItemName);
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
        //Item remove button.
        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            }
        });
    }

    /**
     * {@inheritDoc}
     * @return Number of items.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * View holder class for the item list recycler view.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        Button removeItem;

        /**
         * Constructor for {@link MyViewHolder}
         * @param itemView
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            removeItem = itemView.findViewById(R.id.remove_item);
        }
    }
}