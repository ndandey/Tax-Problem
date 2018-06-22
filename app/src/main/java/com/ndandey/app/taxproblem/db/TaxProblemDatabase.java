package com.ndandey.app.taxproblem.db;

import com.ndandey.app.taxproblem.dao.ShoppingBasketDao;
import com.ndandey.app.taxproblem.dao.ShoppingItemDao;
import com.ndandey.app.taxproblem.listitem.model.ShoppingBasket;
import com.ndandey.app.taxproblem.listitem.model.ShoppingItem;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ShoppingItem.class, ShoppingBasket.class}, version = 1, exportSchema = false)
public abstract class TaxProblemDatabase extends RoomDatabase {
    public static final String TAX_PROBLEM_DB = "tax_problem.db";
    public static TaxProblemDatabase INSTANCE;
    

    public abstract ShoppingItemDao getShoppingItems();
    
    public abstract ShoppingBasketDao getShoppingBasket();

    public static TaxProblemDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaxProblemDatabase.class, TAX_PROBLEM_DB).build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
