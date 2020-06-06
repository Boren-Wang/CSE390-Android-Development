package com.example.shoppinglist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class EditDialog extends DialogFragment {
    private Item item;
    private Context parentContext;

    public interface SaveListener {
        void didFinishEditDialog(Item item);
    }

    public EditDialog(Context context) {
        parentContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_dialog, container);

        getDialog().setTitle("Edit a Item");

        final EditText name = (EditText) view.findViewById(R.id.editTextName);
        final EditText price = (EditText) view.findViewById(R.id.editTextPrice);
        final EditText description = (EditText) view.findViewById(R.id.editTextDescription);
        final CheckBox purchased = (CheckBox) view.findViewById(R.id.checkBoxPurchased);
        final Spinner category = (Spinner) view.findViewById(R.id.spinnerCategory);
        final Button save = (Button) view.findViewById(R.id.buttonSave);
        final Button cancel = (Button) view.findViewById(R.id.buttonCancel);
        Bundle args = getArguments();
        item = new Item();
        if(args!=null) { // if the item is being edited
            Toast.makeText(parentContext, "The item is being edited!", Toast.LENGTH_SHORT).show();
            item.setItemID(args.getInt("id"));
            item.setName(args.getString("name"));
            item.setPrice(args.getInt("price"));
            item.setDescription(args.getString("description"));
            item.setPurchased(args.getBoolean("purchased"));
            item.setCategory(args.getString("category")); // what if these data contain null???
        }

        // populate the date
        name.setText(item.getName());
        price.setText(Integer.toString(item.getPrice()));
        description.setText(item.getDescription());
        purchased.setChecked(item.isPurchased());
        int pos=0;
        String selection = item.getCategory();
        if(selection=="Food") {
            pos = 0;
        } else if(selection=="Book") {
            pos = 1;
        } else if(selection=="Electronic") {
            pos = 2;
        }
        category.setSelection(pos);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setName(name.getText().toString());
                item.setPrice(Integer.parseInt(price.getText().toString()));
                item.setDescription(description.getText().toString());
                item.setPurchased(purchased.isChecked());
                item.setCategory(category.getSelectedItem().toString());
                save();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    private void save() {
        boolean wasSuccessful;
//        hideKeyboard();
        ItemDataSource ds = new ItemDataSource(parentContext);
        try {
            ds.open();

            if (item.getItemID() == -1) {
                wasSuccessful = ds.insertItem(item);
                if(wasSuccessful) {
                    Toast.makeText(parentContext, "Insert successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(parentContext, "Insertion failed!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                wasSuccessful= ds.updateItem(item);
                if(wasSuccessful) {
                    Toast.makeText(parentContext, "Update successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(parentContext, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }
            ds.close();
            SaveListener activity = (SaveListener) getActivity();
            activity.didFinishEditDialog(item);
            getDialog().dismiss();
        }
        catch (Exception e) {

        }
    }
}
