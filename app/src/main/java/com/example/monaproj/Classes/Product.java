package com.example.monaproj.Classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.lang.reflect.Type;

import static com.example.monaproj.DataBase.TablesString.CartTable.COLUMN_PRODUCT_ID;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_BUYPRICE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_FOOTCONCAVITY;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_FOOTSHAPE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_IMAGE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_PRICE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_SALEPRICE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_SIZE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_STOCK;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_TYPE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_WIDTH;
import static com.example.monaproj.DataBase.TablesString.ProductTable.TABLE_PRODUCT;

public class Product implements SqlInterface{

    //region Attribute
    protected int id;
    protected String type;
    protected String footShape ;
    protected int price;
    protected int size;
    protected int width;
    protected int footConcavity;
    protected int stock;
    protected double saleprice;
    protected double buyprice;
    protected byte[] imageByte;
    //endregion

    //region Constructors
    public Product( String footShape,String type ,int stock,double saleprice,double buyprice,byte[] image ){
        this.saleprice=saleprice;
        this.buyprice=buyprice;
        this.type=type;
        this.footShape=footShape;
        this.stock=stock;
        this.imageByte = image;
        this.id = id;
        this.footConcavity=footConcavity;
        this.width=width;
        this.price=price;
        this.size=size;
    }
    //endregion

    //region Add,Delete,Update,Select Sql
    @Override
    public long Add(SQLiteDatabase db) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_TYPE,type );
        values.put(COLUMN_PRODUCT_FOOTSHAPE,footShape);
        values.put(COLUMN_PRODUCT_BUYPRICE, buyprice);
        values.put(COLUMN_PRODUCT_SALEPRICE, saleprice);
        values.put(COLUMN_PRODUCT_STOCK, stock);
        values.put(COLUMN_PRODUCT_IMAGE, imageByte);
        values.put(COLUMN_PRODUCT_ID, id);
        values.put(COLUMN_PRODUCT_FOOTCONCAVITY,footConcavity);
        values.put(COLUMN_PRODUCT_WIDTH,width);
        values.put(COLUMN_PRODUCT_SIZE,size);
        values.put(COLUMN_PRODUCT_PRICE,price);


// Insert the new row, returning the primary key value of the new row
        return db.insert(TABLE_PRODUCT, null, values);

    }

    @Override
    public int Delete(SQLiteDatabase db, int id) {
        String selection = BaseColumns._ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {id+""};
// Issue SQL statement.
        return db.delete(TABLE_PRODUCT, selection, selectionArgs);

    }

    @Override
    public int Update(SQLiteDatabase db, int id) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_TYPE, type);
        values.put(COLUMN_PRODUCT_FOOTCONCAVITY, footConcavity);
        values.put(COLUMN_PRODUCT_BUYPRICE, buyprice);
        values.put(COLUMN_PRODUCT_SALEPRICE, saleprice);
        values.put(COLUMN_PRODUCT_STOCK, stock);
        values.put(COLUMN_PRODUCT_IMAGE, imageByte.toString());
        values.put(COLUMN_PRODUCT_FOOTSHAPE, footShape);
        values.put(COLUMN_PRODUCT_SIZE,size );
        values.put(COLUMN_PRODUCT_ID,id );
        values.put(COLUMN_PRODUCT_PRICE,price);

// Which row to update, based on the title
        String selection = BaseColumns._ID + " LIKE ?";
        String[] selectionArgs = { id+"" };

        return  db.update(
                TABLE_PRODUCT,
                values,
                selection,
                selectionArgs);

    }

    @Override
    public Cursor Select(SQLiteDatabase db) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_DESCRIPTION,
                COLUMN_PRODUCT_IMAGE,
                COLUMN_PRODUCT_STOCK,
                COLUMN_PRODUCT_SALEPRICE,
                COLUMN_PRODUCT_BUYPRICE
        };
// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " DESC";
        Cursor c = db.query(TABLE_PRODUCT,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);
        return c;
    }

    //endregion

    //region Setter and Getter
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public String getProddisc() {
        return proddisc;
    }

    public void setProddisc(String proddisc) {
        this.proddisc = proddisc;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public double getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(double buyprice) {
        this.buyprice = buyprice;
    }
    //endregion

}
