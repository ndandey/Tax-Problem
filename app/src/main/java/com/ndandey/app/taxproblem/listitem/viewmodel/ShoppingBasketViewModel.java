package com.ndandey.app.taxproblem.listitem.viewmodel;

import com.ndandey.app.taxproblem.db.TaxProblemDatabase;
import com.ndandey.app.taxproblem.listitem.model.ShoppingBasket;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by ndandey on 6/21/2018.
 */

public class ShoppingBasketViewModel extends AndroidViewModel {
    private TaxProblemDatabase taxProblemDatabase;
    
    public ShoppingBasketViewModel(Application application) {
        super(application);
        taxProblemDatabase = TaxProblemDatabase.getInstance(this.getApplication());
    }
    
    
    public Maybe<List<ShoppingBasket>> getShoppingBasket() {
        return taxProblemDatabase.getShoppingBasket()
                .getShoppingBasketItems();
    }
    
    public void deleteItem(ShoppingBasket shoppingBasket) {
        new deleteAsyncTask(taxProblemDatabase).execute(shoppingBasket);
    }
    
    private static class deleteAsyncTask extends AsyncTask<ShoppingBasket, Void, Void> {
        
        private TaxProblemDatabase db;
        
        deleteAsyncTask(TaxProblemDatabase appDatabase) {
            db = appDatabase;
        }
        
        @Override
        protected Void doInBackground(final ShoppingBasket... params) {
            db.getShoppingBasket()
                    .deleteAll(params[0]);
            return null;
        }
        
    }
    
    public void addItemToBasket(final ShoppingBasket shoppingBasket) {
        new ShoppingBasketViewModel.addAsyncTask(taxProblemDatabase).execute(shoppingBasket);
    }
    
    private static class addAsyncTask extends AsyncTask<ShoppingBasket, Void, Void> {
        
        private TaxProblemDatabase db;
        
        addAsyncTask(TaxProblemDatabase taxProblemDatabase) {
            db = taxProblemDatabase;
        }
        
        @Override
        protected Void doInBackground(final ShoppingBasket... params) {
            db.getShoppingBasket()
                    .addItemToShoppingBasket(params[0]);
            return null;
        }
        
    }
}
