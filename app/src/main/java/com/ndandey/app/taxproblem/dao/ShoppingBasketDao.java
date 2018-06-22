package com.ndandey.app.taxproblem.dao;

import com.ndandey.app.taxproblem.listitem.model.ShoppingBasket;
import com.ndandey.app.taxproblem.listitem.model.ShoppingItem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ndandey on 6/21/2018.
 */
@Dao
public interface ShoppingBasketDao {

    @Query("SELECT * FROM shopping_basket")
    Maybe<List<ShoppingBasket>> getShoppingBasketItems();
    
    @Insert(onConflict = REPLACE)
    void addItemToShoppingBasket(ShoppingBasket shoppingBasket);
    
    @Delete
    void deleteAll(ShoppingBasket... shoppingBaskets);
}
