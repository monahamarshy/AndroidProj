package com.example.monaproj.Classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import static com.example.monaproj.DataBase.TablesString.CartTable.COLUMN_PRODUCT_ID;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_BUYPRICE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_CATEGORY;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_FOOTCONCAVITY;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_FOOTSHAPE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_IMAGE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_SALEPRICE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_SIZE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_STOCK;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_TYPE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_WIDTH;
import static com.example.monaproj.DataBase.TablesString.ProductTable.TABLE_PRODUCT;

public class Product implements SqlInterface {

    //region Attribute
    protected int id;
    protected String type;
    protected String footShape;
    protected int stock;
    protected double saleprice;
    protected double buyprice;
    protected byte[] imageByte;
    protected String category;
    //endregion

    //region Constructors
    public Product(Product p) {
        id = p.getId();
        type= p.getType();
        footShape = p.getFootShape();
        stock = p.getStock();
        saleprice = p.getSaleprice();
        buyprice = p.getBuyprice();
        imageByte = p.getImageByte();
        category = p.getCategory();
    }
    public Product(String footShape, String type, int stock, double saleprice, double buyprice, byte[] image,String category) {
        this.saleprice = saleprice;
        this.buyprice = buyprice;
        this.type = type;
        this.footShape = footShape;
        this.stock = stock;
        this.imageByte = image;
        this.category = category;
    }
    public Product(int id,String footShape, String type, int stock, double saleprice, double buyprice, byte[] image,String category) {
        this.id = id;
        this.saleprice = saleprice;
        this.buyprice = buyprice;
        this.type = type;
        this.footShape = footShape;
        this.stock = stock;
        this.imageByte = image;
        this.category = category;
    }
    public Product() {
    }
    //endregion

    //region Add,Delete,Update,Select Sql
    @Override
    public long Add(SQLiteDatabase db) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_TYPE, type);
        values.put(COLUMN_PRODUCT_FOOTSHAPE, footShape);
        values.put(COLUMN_PRODUCT_BUYPRICE, buyprice);
        values.put(COLUMN_PRODUCT_SALEPRICE, saleprice);
        values.put(COLUMN_PRODUCT_STOCK, stock);
        values.put(COLUMN_PRODUCT_IMAGE, imageByte);
        values.put(COLUMN_PRODUCT_CATEGORY, category);


// Insert the new row, returning the primary key value of the new row
        return db.insert(TABLE_PRODUCT, null, values);

    }

    @Override
    public int Delete(SQLiteDatabase db, int id) {
        String selection = BaseColumns._ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {id + ""};
// Issue SQL statement.
        return db.delete(TABLE_PRODUCT, selection, selectionArgs);

    }

    @Override
    public int Update(SQLiteDatabase db, int id) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_TYPE, type);
        values.put(COLUMN_PRODUCT_BUYPRICE, buyprice);
        values.put(COLUMN_PRODUCT_SALEPRICE, saleprice);
        values.put(COLUMN_PRODUCT_STOCK, stock);
        values.put(COLUMN_PRODUCT_IMAGE, imageByte);
        values.put(COLUMN_PRODUCT_FOOTSHAPE, footShape);
        values.put(COLUMN_PRODUCT_CATEGORY, category);

// Which row to update, based on the title
        String selection = BaseColumns._ID + " LIKE ?";
        String[] selectionArgs = {id + ""};

        return db.update(
                TABLE_PRODUCT,
                values,
                selection,
                selectionArgs);

    }

    @Override
    public Cursor Select(SQLiteDatabase db) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_PRODUCT_TYPE,
                COLUMN_PRODUCT_IMAGE,
                COLUMN_PRODUCT_STOCK,
                COLUMN_PRODUCT_SALEPRICE,
                COLUMN_PRODUCT_BUYPRICE,
                COLUMN_PRODUCT_FOOTSHAPE,
                COLUMN_PRODUCT_CATEGORY
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
    public Cursor SelectById(SQLiteDatabase db, int id) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_PRODUCT_TYPE,
                COLUMN_PRODUCT_IMAGE,
                COLUMN_PRODUCT_STOCK,
                COLUMN_PRODUCT_SALEPRICE,
                COLUMN_PRODUCT_BUYPRICE,
                COLUMN_PRODUCT_FOOTSHAPE,
                COLUMN_PRODUCT_CATEGORY
        };
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {id+""};

        Cursor c = db.query(
                TABLE_PRODUCT,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null  );
        return c;
    }
    public Cursor SelectByCategory(SQLiteDatabase db, String category) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_PRODUCT_TYPE,
                COLUMN_PRODUCT_IMAGE,
                COLUMN_PRODUCT_STOCK,
                COLUMN_PRODUCT_SALEPRICE,
                COLUMN_PRODUCT_BUYPRICE,
                COLUMN_PRODUCT_FOOTSHAPE,
                COLUMN_PRODUCT_CATEGORY
        };
        String selection = COLUMN_PRODUCT_CATEGORY + " = ?";
        String[] selectionArgs = {category};

        Cursor c = db.query(
                TABLE_PRODUCT,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null  );
        return c;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFootShape() {
        return footShape;
    }

    public void setFootShape(String footShape) {
        this.footShape = footShape;
    }

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}
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

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    @Override
    public String toString() {
        return type ;
    }

//endregion
}
    //region Setter and Getter
