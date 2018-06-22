package com.ndandey.app.taxproblem.addItem;

import com.ndandey.app.taxproblem.db.TaxProblemDatabase;
import com.ndandey.app.taxproblem.listitem.model.ShoppingItem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

/**
 * Created by ndandey on 6/21/2018.
 */

public class AddItemViewModel extends AndroidViewModel {
    private TaxProblemDatabase taxProblemDatabase;
    
    public AddItemViewModel(Application application) {
        super(application);
        
        taxProblemDatabase = TaxProblemDatabase.getInstance(this.getApplication());
        
    }
    
    public void addShoppingItem(final ShoppingItem borrowModel) {
        new addAsyncTask(taxProblemDatabase).execute(borrowModel);
    }
    
    private static class addAsyncTask extends AsyncTask<ShoppingItem, Void, Void> {
        
        private TaxProblemDatabase db;
        
        addAsyncTask(TaxProblemDatabase taxProblemDatabase) {
            db = taxProblemDatabase;
        }
        
        @Override
        protected Void doInBackground(final ShoppingItem... params) {
            db.getShoppingItems()
                    .addShoppingItem(params[0]);
            return null;
        }
        
    }
}
