package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

// I got the idea from the chapter 5 & 6 from the textbook

public class ItemDataSource {
    private SQLiteDatabase database;
    private ItemDBHelper dbHelper;

    public ItemDataSource(Context context) {
        dbHelper = new ItemDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertItem(Item i) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("name", i.getName());
            initialValues.put("price", i.getPrice());
            initialValues.put("description", i.getDescription());
            int purchased;
            if(i.isPurchased()) {
                purchased = 1;
            } else {
                purchased = 0;
            }
            initialValues.put("purchased", purchased);
            initialValues.put("category", i.getCategory());
            didSucceed = database.insert("item", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateItem(Item i) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) i.getItemID();
            ContentValues updatedValues = new ContentValues();

            updatedValues.put("name", i.getName());
            updatedValues.put("price", i.getPrice());
            updatedValues.put("description", i.getDescription());
            int purchased;
            if(i.isPurchased()) {
                purchased = 1;
            } else {
                purchased = 0;
            }
            updatedValues.put("purchased", purchased);
            updatedValues.put("category", i.getCategory());

            didSucceed = database.update("item", updatedValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public int getLastItemId() {
        int lastId;
        try {
            String query = "Select MAX(_id) from item";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    public ArrayList<Item> getItems(String sortField, String sortOrder) {
        ArrayList<Item> items = new ArrayList<Item>();
        try {
            String query = "SELECT  * FROM item ORDER BY " + sortField + " " + sortOrder;
//            String query = "SELECT  * FROM item";
            Cursor cursor = database.rawQuery(query, null);

            Item newItem;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newItem = new Item();
                newItem.setItemID(cursor.getInt(0));
                newItem.setName(cursor.getString(1));
                newItem.setPrice(cursor.getInt(2));
                newItem.setDescription(cursor.getString(3));
                if(cursor.getInt(4)==1) {
                    newItem.setPurchased(true);
                } else if(cursor.getInt((4))==0) {
                    newItem.setPurchased(false);
                }
                newItem.setCategory(cursor.getString(5));
                items.add(newItem);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            items = new ArrayList<Item>();
        }
        return items;
    }

    public Item getSpecificItem(int itemId) {
        Item item = new Item();
        String query = "SELECT  * FROM item WHERE _id =" +itemId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            item = new Item();
            item.setItemID(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setPrice(cursor.getInt(2));
            item.setDescription(cursor.getString(3));
            if(cursor.getInt(4)==1) {
                item.setPurchased(true);
            } else if(cursor.getInt((4))==0) {
                item.setPurchased(false);
            }
            item.setCategory(cursor.getString(5));

            cursor.close();
        }
        return item;
    }

    public boolean deleteItem(int itemId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("item", "_id=" + itemId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }

}
