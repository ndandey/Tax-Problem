package com.ndandey.app.taxproblem.listitem.view;

import com.ndandey.app.taxproblem.R;
import com.ndandey.app.taxproblem.addItem.AddItemActivity;
import com.ndandey.app.taxproblem.checkout.BasketActivity;
import com.ndandey.app.taxproblem.listitem.model.ShoppingBasket;
import com.ndandey.app.taxproblem.listitem.model.ShoppingItem;
import com.ndandey.app.taxproblem.listitem.viewmodel.ShoppingBasketViewModel;
import com.ndandey.app.taxproblem.listitem.viewmodel.ShoppingItemListViewModel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListItemActivity extends AppCompatActivity implements View.OnClickListener {
    private ShoppingItemListViewModel shoppingItemListViewModel;
    private ShoppingBasketViewModel shoppingBasketViewModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<ShoppingItem>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        recyclerView.setAdapter(recyclerViewAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        
        shoppingItemListViewModel = ViewModelProviders.of(this)
                .get(ShoppingItemListViewModel.class);
        shoppingBasketViewModel = ViewModelProviders.of(this)
                .get(ShoppingBasketViewModel.class);
        shoppingItemListViewModel.getShoppingItems()
                .observe(ListItemActivity.this, new Observer<List<ShoppingItem>>() {
                    @Override
                    public void onChanged(@Nullable List<ShoppingItem> itemAndPeople) {
                        recyclerViewAdapter.addItems(itemAndPeople);
                    }
                });
    }
    
    @Override
    public void onClick(View v) {
        ShoppingItem shoppingItem = (ShoppingItem) v.getTag();
        
        if (v.getId() == R.id.cart_plus_img) {
            updateShoppingBasket(v, shoppingItem, Boolean.TRUE);
        } else if (v.getId() == R.id.cart_minus_img) {
            updateShoppingBasket(v, shoppingItem, Boolean.FALSE);
        }
        recyclerViewAdapter.notifyDataSetChanged();
    }
    
    private void updateShoppingBasket(View v, ShoppingItem shoppingItem, Boolean isAddition) {
        TextView itemQtyTextView = ((View) v.getParent()).findViewById(R.id.itemQtyTextView);
        final Integer vPreviousQty = Integer.valueOf(itemQtyTextView.getText()
                .toString());
        if (!((vPreviousQty == 0 && v.getId() == R.id.cart_minus_img) || (vPreviousQty == 10 && v.getId() == R.id.cart_plus_img))) {
            final int currQty = vPreviousQty + (isAddition ? 1 : -1);
            itemQtyTextView.setText(String.valueOf(currQty));
            ShoppingBasket shoppingBasket = new ShoppingBasket(shoppingItem.getId(), currQty);
            shoppingBasketViewModel.addItemToBasket(shoppingBasket);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(this, AddItemActivity.class));
        } else if (item.getItemId() == R.id.checkout) {
            startActivity(new Intent(this, BasketActivity.class));
        }
        return false;
    }
}
