package com.ndandey.app.taxproblem.listitem.viewmodel;

import com.ndandey.app.taxproblem.db.TaxProblemDatabase;
import com.ndandey.app.taxproblem.listitem.model.ShoppingItem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by ndandey on 6/21/2018.
 */

public class ShoppingItemListViewModel extends AndroidViewModel {
    private final LiveData<List<ShoppingItem>> shoppingItems;
    
    private TaxProblemDatabase taxProblemDatabase;
    
    public ShoppingItemListViewModel(Application application) {
        super(application);
        
        taxProblemDatabase = TaxProblemDatabase.getInstance(this.getApplication());
        
        shoppingItems = taxProblemDatabase.getShoppingItems()
                .getAllShoppingItems();
    }
    
    
    public LiveData<List<ShoppingItem>> getShoppingItems() {
        return shoppingItems;
    }
    
    public Maybe<ShoppingItem> getShoppingItem(Integer itemId) {
        return taxProblemDatabase.getShoppingItems()
                .getItembyId(itemId);
    }
    
    public void deleteItem(ShoppingItem shoppingItem) {
        new deleteAsyncTask(taxProblemDatabase).execute(shoppingItem);
    }
    
    private static class deleteAsyncTask extends AsyncTask<ShoppingItem, Void, Void> {
        
        private TaxProblemDatabase db;
        
        deleteAsyncTask(TaxProblemDatabase appDatabase) {
            db = appDatabase;
        }
        
        @Override
        protected Void doInBackground(final ShoppingItem... params) {
            db.getShoppingItems()
                    .deleteShoppingItem(params[0]);
            return null;
        }
        
    }
}
