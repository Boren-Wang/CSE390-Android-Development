package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity implements EditDialog.SaveListener {
    RecyclerView shoppingList;
    ItemAdapter itemAdapter;
    ArrayList<Item> items;

    private View.OnClickListener onEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
//            int itemId = items.get(position).getItemID();
            Item item = items.get(position);
            Bundle args = new Bundle();
            args.putInt("id", item.getItemID());
            args.putString("name", item.getName());
            args.putInt("price", item.getPrice());
            args.putString("description", item.getDescription());
            args.putBoolean("purchased", item.isPurchased());
            args.putString("category", item.getCategory());
            FragmentManager fm = getSupportFragmentManager();
            EditDialog editDialog = new EditDialog(MainActivity.this);
            editDialog.setArguments(args);
            editDialog.show(fm, "EditDialog"); // how to pass data into edit dialog
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAddButton();
        initSettingButton();

    }

    @Override
    public void onResume() {
        super.onResume();

        String sortBy = getSharedPreferences("ShoppingListPreferences", Context.MODE_PRIVATE).getString("sortfield", "name");
        String sortOrder = getSharedPreferences("ShoppingListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

        ItemDataSource ds = new ItemDataSource(this);
        try {
            ds.open();
            items = ds.getItems(sortBy, sortOrder); // get items from the database
            ds.close();
            shoppingList = findViewById(R.id.shoppingList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            shoppingList.setLayoutManager(layoutManager);
            itemAdapter = new ItemAdapter(items, this);
            itemAdapter.setOnEditClickListener(onEditClickListener);
            shoppingList.setAdapter(itemAdapter);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

    }

    private void initAddButton() {
        ImageView add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                EditDialog editDialog = new EditDialog(MainActivity.this);
                editDialog.show(fm, "EditDialog");
            }
        });
    }

    private void initSettingButton() {
        ImageView setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void didFinishEditDialog(Item item, Boolean isNewItem) {
//        Toast.makeText(this, "Did finish EditDialog", Toast.LENGTH_LONG).show();
        if(isNewItem) {
            itemAdapter.addItemToData(item);
        } else {
            itemAdapter.editItemInData(item);
        }
        itemAdapter.notifyDataSetChanged();
    }
}
