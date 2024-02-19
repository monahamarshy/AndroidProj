package com.example.monaproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.monaproj.Classes.Product;
import com.example.monaproj.Classes.ProductAdapter;
import com.example.monaproj.DataBase.DBHelper;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import static com.example.monaproj.DataBase.TablesString.ProductTable.*;

public class SelcetedCategoryView extends AppCompatActivity {

    List<Product> productList;
    RecyclerView recyclerView;
    ProductAdapter mAdapter;
    DBHelper dbHelper;
    String selctedCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selceted_category_view);
        Bundle extras = getIntent().getExtras();
        selctedCategory = extras.getString("Category");
        productList = new ArrayList<>();
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        dbHelper = new DBHelper(this);
        dbHelper = dbHelper.OpenReadAble();
        Product p = new Product(), p2;
        Cursor c;
        c = p.SelectByCategory(dbHelper.getDb(), selctedCategory);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            p2 = new Product(c.getInt(c.getColumnIndexOrThrow(BaseColumns._ID)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_FOOTSHAPE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_TYPE)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_PRODUCT_STOCK)),
                    c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_SALEPRICE)),
                    c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_BUYPRICE)),
                    c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_CATEGORY)));
            productList.add(p2);
            c.moveToNext();
        }
        dbHelper.Close();

        // adapter
        mAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(mAdapter);
    }

}