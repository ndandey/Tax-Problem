package com.ndandey.app.taxproblem.listitem.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "shopping_item")
public class ShoppingItem {
    
    @PrimaryKey(autoGenerate = true) private int id;
    
    @ColumnInfo(name = "itm_name") private String name;
    
    @ColumnInfo(name = "itm_imported") private Boolean isImported;
    
    @ColumnInfo(name = "itm_exempted") private Boolean isExempted;
    
    @ColumnInfo(name = "itm_price") private String price;
    
    public int getItemQty() {
        return itemQty;
    }
    
    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }
    
    private int itemQty;
    
    @Ignore
    public ShoppingItem(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public ShoppingItem(String name, String price, Boolean isImported, Boolean isExempted) {
        this.name = name;
        this.price = price;
        this.isImported = isImported;
        this.isExempted = isExempted;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public Boolean isImported() {
        return isImported;
    }
    
    public Boolean isExempted() {
        return isExempted;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public void setIsImported(Boolean isImported) {
        this.isImported = isImported;
    }
    
    public void setIsExempted(Boolean isExempted) {
        this.isExempted = isExempted;
    }
}
