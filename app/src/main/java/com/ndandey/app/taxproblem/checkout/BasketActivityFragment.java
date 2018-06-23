package com.ndandey.app.taxproblem.checkout;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ndandey.app.taxproblem.R;
import com.ndandey.app.taxproblem.listitem.model.ShoppingBasket;
import com.ndandey.app.taxproblem.listitem.model.ShoppingItem;
import com.ndandey.app.taxproblem.listitem.view.ListItemActivity;
import com.ndandey.app.taxproblem.listitem.viewmodel.ShoppingBasketViewModel;
import com.ndandey.app.taxproblem.listitem.viewmodel.ShoppingItemListViewModel;

import java.math.BigDecimal;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class BasketActivityFragment extends Fragment {
    
    ShoppingItemListViewModel shoppingItemListViewModel;
    ShoppingBasketViewModel shoppingBasketViewModel;
    public BasketActivityFragment() {
    }
    
    View view;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shoppingItemListViewModel = ViewModelProviders.of(this)
                .get(ShoppingItemListViewModel.class);
        view = inflater.inflate(R.layout.fragment_basket, container, false);
        shoppingBasketViewModel = ViewModelProviders.of(getActivity())
                .get(ShoppingBasketViewModel.class);
        shoppingBasketViewModel.getShoppingBasket()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ShoppingBasket>>() {
                    @Override
                    public void accept(List<ShoppingBasket> shoppingBasket) throws Exception {
                        showReceipt(view, shoppingBasket);
                    }
                });
        return view;
    }
    
    BigDecimal totalPrice;
    BigDecimal totalTax;
    
    private void showReceipt(final View view, List<ShoppingBasket> selectedItems) {
        final LinearLayout contentView = view.findViewById(R.id.content_view);
        final LayoutInflater inflater = getLayoutInflater();
        totalPrice = new BigDecimal("0");
        totalTax = new BigDecimal("0");
        for (final ShoppingBasket shoppingBasket : selectedItems) {
            shoppingItemListViewModel.getShoppingItem(shoppingBasket.getItemId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ShoppingItem>() {
                        @Override
                        public void accept(ShoppingItem shoppingItem) throws Exception {
                            View basketItemView = inflater.inflate(R.layout.receipt_item, null);
                            TextView itemQtyTextView = basketItemView.findViewById(R.id.itemQtyTextView);
                            final Integer itemQuantity = shoppingBasket.getItemQuantity();
                            itemQtyTextView.setText(String.valueOf(itemQuantity));
                            TextView itemNameTextView = basketItemView.findViewById(R.id.itemNameTextView);
                            itemNameTextView.setText(shoppingItem.getName());
                            TextView itemTotalPriceTextView = basketItemView.findViewById(R.id.itemTotalPriceTextView);
                            BigDecimal itemPrice = new BigDecimal(shoppingItem.getPrice());
                            itemPrice = itemPrice.multiply(BigDecimal.valueOf(itemQuantity));
                            BigDecimal itemTax = calculateTax(itemPrice, shoppingItem.isImported(), shoppingItem.isExempted());
                            BigDecimal itemPriceWithTax = itemPrice.add(itemTax);
                            itemTotalPriceTextView.setText(itemPriceWithTax.toPlainString());
                            contentView.addView(basketItemView);
                            totalPrice = totalPrice.add(itemPriceWithTax);
                            totalTax = totalTax.add(itemTax);
                            TextView totalPriceValueTextView = view.findViewById(R.id.totalPriceValueTextView);
                            totalPriceValueTextView.setText(totalPrice.toPlainString());
                            TextView totalSalesTaxValueTextView = view.findViewById(R.id.totalSalesTaxValueTextView);
                            totalSalesTaxValueTextView.setText(totalTax.toPlainString());
                            shoppingBasketViewModel.deleteItem(shoppingBasket);
                            shoppingItemListViewModel.deleteItem(shoppingItem);
                        }
                    });
        }
    }
    
    /**
     * This method computes the Tax based on whether an Item  Is/not Imported and is/not Exempted
     */
    public BigDecimal calculateTax(BigDecimal itemPrice, Boolean isImported, Boolean isExempted) {
        double totTaxPercent = 0.0;
        
        if (isImported && !isExempted) {
            totTaxPercent = 0.15;
        } else if (isImported && isExempted) {
            totTaxPercent = 0.05;
        } else if (!isImported && !isExempted) {
            totTaxPercent = 0.1;
        }
        BigDecimal tax = BigDecimal.valueOf(totTaxPercent)
                .multiply(itemPrice);
        
        return roundOff(tax);
    }
    
    /**
     * It gives the taxCalcuated value rounded up to the nearest 0.05
     *
     * @param value
     * @return taxCalculated
     */
    public BigDecimal roundOff(BigDecimal value) {
        float taxCalculated = ((float) (Math.ceil(value.floatValue() / 0.05) * 0.05));
        BigDecimal bigDecTax = new BigDecimal(taxCalculated);
        bigDecTax = bigDecTax.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecTax;
    }
}
