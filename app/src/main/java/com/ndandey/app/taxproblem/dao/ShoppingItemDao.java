package com.ndandey.app.taxproblem.dao;


import com.ndandey.app.taxproblem.listitem.model.ShoppingItem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ShoppingItemDao {
    
    @Query("SELECT * FROM shopping_item")
    LiveData<List<ShoppingItem>> getAllShoppingItems();
    
    @Query("select * from shopping_item where id = :id")
    Maybe<ShoppingItem> getItembyId(Integer id);
    
    @Insert(onConflict = REPLACE)
    void addShoppingItem(ShoppingItem shoppingItem);
    
    @Delete
    void deleteShoppingItem(ShoppingItem shoppingItem);

    
    
}


