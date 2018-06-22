package com.ndandey.app.taxproblem.listitem.view;

import com.ndandey.app.taxproblem.R;
import com.ndandey.app.taxproblem.listitem.model.ShoppingItem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by prokarma on 6/21/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    
    private List<ShoppingItem> ShoppingItemList;
    private View.OnClickListener onClickListener;
    
    public RecyclerViewAdapter(List<ShoppingItem> ShoppingItemList, View.OnClickListener onClickListener) {
        this.ShoppingItemList = ShoppingItemList;
        this.onClickListener = onClickListener;
    }
    
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false));
    }
    
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        ShoppingItem shoppingItem = ShoppingItemList.get(position);
        holder.priceTextView.setText(String.valueOf(shoppingItem.getPrice()));
        holder.nameTextView.setText(shoppingItem.getName());
        holder.plusImageView.setTag(shoppingItem);
        holder.minusImageView.setTag(shoppingItem);
        holder.minusImageView.setOnClickListener(onClickListener);
        holder.plusImageView.setOnClickListener(onClickListener);
    }
    
    @Override
    public int getItemCount() {
        return ShoppingItemList.size();
    }
    
    public void addItems(List<ShoppingItem> ShoppingItemList) {
        this.ShoppingItemList = ShoppingItemList;
        notifyDataSetChanged();
    }
    
    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView priceTextView;
        private TextView nameTextView;
        private ImageView plusImageView;
        private ImageView minusImageView;
        private TextView qtyTextView;
        
        RecyclerViewHolder(View view) {
            super(view);
            priceTextView = view.findViewById(R.id.priceTextView);
            nameTextView = view.findViewById(R.id.nameTextView);
            plusImageView = view.findViewById(R.id.cart_plus_img);
            minusImageView = view.findViewById(R.id.cart_minus_img);
            qtyTextView = view.findViewById(R.id.itemQtyTextView);
        }
    }
}
