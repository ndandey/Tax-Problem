package com.ndandey.app.taxproblem.listitem.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ndandey on 6/20/2018.
 */
@Entity(tableName = "shopping_basket")
public class ShoppingBasket {
    
    @PrimaryKey @ColumnInfo(name = "sbt_item_id") private Integer itemId;
    
    @ColumnInfo(name = "sbt_item_qty") private Integer itemQuantity;
    
    public ShoppingBasket(Integer itemId, Integer itemQuantity) {
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
    }
    
    public Integer getItemQuantity() {
        return itemQuantity;
    }
    
    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    
    public Integer getItemId() {
        return itemId;
    }
    
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
