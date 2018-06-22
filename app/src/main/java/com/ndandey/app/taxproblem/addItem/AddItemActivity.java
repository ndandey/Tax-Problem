package com.ndandey.app.taxproblem.addItem;

import com.ndandey.app.taxproblem.R;
import com.ndandey.app.taxproblem.listitem.model.ShoppingItem;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {
    private EditText itemEditText;
    private EditText itemPriceEditText;
    private CheckBox isImportedCheckBox;
    private CheckBox isExcemptedCheckBox;
    
    private AddItemViewModel addItemViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);
        }
        itemEditText = findViewById(R.id.itemName);
        itemPriceEditText = findViewById(R.id.itemPrice);
        isImportedCheckBox = findViewById(R.id.isImported);
        isExcemptedCheckBox = findViewById(R.id.isExempted);
        addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            if (TextUtils.isEmpty(itemEditText.getText().toString()) || TextUtils.isEmpty(itemPriceEditText.getText().toString())) {
                Toast.makeText(AddItemActivity.this,"Missing fields", Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
            else {
                addItemViewModel.addShoppingItem(new ShoppingItem(
                        itemEditText.getText().toString(),
                        itemPriceEditText.getText().toString(),
                        isImportedCheckBox.isChecked(),
                        isExcemptedCheckBox.isChecked()
                ));
                finish();
                return true;
            }
        }
        else if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
