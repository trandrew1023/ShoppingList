package edu.iastate.shoppinglist;

import android.app.Activity;
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

/**
 * Adapter that binds the shopping lists to the recycler view.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private static final String TAG = "ListAdapter";

    //Constants
    /**
     * Intent extra key for the shopping list's items.
     */
    private static final String ITEM_LIST_EXTRA = "itemList";
    /**
     * Intent extra key for the list name.
     */
    private static final String LIST_NAME_EXTRA = "listName";
    /**
     * Intent extra key for the shopping list's position in the ArrayList.
     */
    private static final String POSITION_EXTRA = "position";
    /**
     * Name of file to save and load content from.
     */
    private static final String FILENAME = "filename";
    /**
     * Used to identify the result from starting item list activity.
     */
    private static final int CONTACT_REQUEST = 1;

    /**
     * Shopping list view model which contains all shopping lists.
     */
    private ShoppingListViewModel shoppingListViewModel;

    /**
     * Constructor for {@link ListAdapter}
     * @param shoppingListViewModel The shopping list view model.
     */
    public ListAdapter(ShoppingListViewModel shoppingListViewModel) {
        this.shoppingListViewModel = shoppingListViewModel;
    }

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
        holder.listName.setText(shoppingListViewModel.getShoppingLists().get(position).getListName());
        //Open shopping list.
        holder.listName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ItemListActivity.class);
                intent.putExtra(ITEM_LIST_EXTRA, shoppingListViewModel.getShoppingLists().get(position).getItems());
                intent.putExtra(LIST_NAME_EXTRA, shoppingListViewModel.getShoppingLists().get(position).getListName());
                intent.putExtra(POSITION_EXTRA, position);
                ((Activity)view.getContext()).startActivityForResult(intent, CONTACT_REQUEST);
            }
        });
        //Edit shopping list name.
        holder.listName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final EditText input = new EditText(view.getContext());
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newListName = input.getText().toString();
                        holder.listName.setText(newListName);
                        shoppingListViewModel.getShoppingLists().get(position).setListName(newListName);
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
                return true;
            }
        });
        //Shopping list item count.
        holder.itemCount.setText(shoppingListViewModel.getShoppingLists().get(position).getItemCount() + "");
        //Shopping list remove button.
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingListViewModel.getShoppingLists().remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, shoppingListViewModel.getShoppingLists().size());
                shoppingListViewModel.saveShoppingList(view.getContext(), FILENAME);
            }
        });
        //Shopping list copy button.
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingListViewModel.getShoppingLists().add(shoppingListViewModel.getShoppingLists().get(position));
                notifyItemInserted(position+1);
                notifyItemRangeChanged(position+1, shoppingListViewModel.getShoppingLists().size());
                shoppingListViewModel.saveShoppingList(view.getContext(), FILENAME);
            }
        });
    }

    /**
     * {@inheritDoc}
     * @return The number of shopping lists.
     */
    @Override
    public int getItemCount() {
        return shoppingListViewModel.getShoppingLists().size();
    }

    /**
     * View holder class for the shopping lists recycler view.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView listName;
        TextView itemCount;
        Button copy;
        Button remove;

        /**
         * Constructor for {@link MyViewHolder}
         * @param itemView The current view.
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.list_name);
            itemCount = itemView.findViewById(R.id.item_count);
            copy = itemView.findViewById(R.id.copy);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
