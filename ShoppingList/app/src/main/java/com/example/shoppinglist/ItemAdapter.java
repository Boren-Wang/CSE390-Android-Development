package com.example.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter {
    private ArrayList<Item> data;
    private Context parentContext;
    private View.OnClickListener onEditClickListener;

    public void addItemToData(Item item) {
        data.add(item);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;
        public TextView description;
        public CheckBox purchased;
        public ImageView category;
        public Button edit;
        public Button hide;
        public Button delete;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            price = itemView.findViewById(R.id.tvPrice);
            description = itemView.findViewById(R.id.tvDescription);
            purchased = itemView.findViewById(R.id.checkBoxPurchased);
            purchased.setEnabled(false); // disable the purchased checkbox
            category = itemView.findViewById(R.id.ivCategory);
            edit = itemView.findViewById(R.id.buttonEdit);
            hide = itemView.findViewById(R.id.buttonHide);
            hide.setOnClickListener(new View.OnClickListener() { // toggle visibility for description text view
                @Override
                public void onClick(View v) {
                    if(description.getVisibility()==View.VISIBLE) {
                        description.setVisibility(View.INVISIBLE);
                    } else {
                        description.setVisibility(View.VISIBLE);
                    }
                }
            });
            delete = itemView.findViewById(R.id.buttonDelete);
            itemView.setTag(this);
        }

        public TextView getNameTV() {
            return name;
        }
        public TextView getPriceTV() {
            return price;
        }
        public TextView getDescriptionTV() {
            return description;
        }
        public CheckBox getPurchasedCB() {
            return purchased;
        }
        public ImageView getCategoryIV() {
            return category;
        }
        public Button getEditButton() {
            return edit;
        }
        public Button getDeleteButton() {
            return delete;
        }
    }

    public ItemAdapter(ArrayList<Item> arrayList, Context context) {
        data = arrayList;
        parentContext = context;
    }

    public void setOnEditClickListener(View.OnClickListener listener) {
        onEditClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder ivh = (ItemViewHolder) holder;
        ivh.getNameTV().setText(data.get(position).getName());
        ivh.getPriceTV().setText(Integer.toString(data.get(position).getPrice()));
        ivh.getDescriptionTV().setText(data.get(position).getDescription());
        ivh.getPurchasedCB().setChecked(data.get(position).isPurchased());
        if(data.get(position).getCategory().equals("Food")) {
            ivh.getCategoryIV().setImageResource(R.drawable.baseline_fastfood_black_18dp);
        } else if(data.get(position).getCategory().equals("Book")) {
            ivh.getCategoryIV().setImageResource(R.drawable.baseline_menu_book_black_18dp);
        } else if(data.get(position).getCategory().equals("Electronic")) {
            ivh.getCategoryIV().setImageResource(R.drawable.baseline_laptop_chromebook_black_18dp);
        } else {
//            Toast.makeText(parentContext, data.get(position).getCategory(), Toast.LENGTH_SHORT).show();
        }
        ivh.getEditButton().setOnClickListener(onEditClickListener);
        ivh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void deleteItem(int position) {
        Item i = data.get(position);
        ItemDataSource ds = new ItemDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteItem(i.getItemID());
            ds.close();
            if (didDelete) {
                data.remove(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e) {

        }
    }
}
