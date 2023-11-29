package com.example.monaproj.Admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.monaproj.Classes.Product;
import com.example.monaproj.DataBase.DBHelper;
import com.example.monaproj.R;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_BUYPRICE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_CATEGORY;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_FOOTSHAPE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_IMAGE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_SALEPRICE;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_STOCK;
import static com.example.monaproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_TYPE;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static int RESULT_LOAD_IMAGE = 1;
    EditText etType,etFootshape,etstock,etsaleprice,etbuyprice;
    ImageButton imageButton;
    Button btadd,btupdate,btdelete;
    Product p;
    byte[] image;
    boolean SelectedNewImage;
    int selectedId;
    Uri selectedImageUri;
    DBHelper dbHelper;
    Spinner spCat;
    ArrayAdapter ad;
    String selectedcategory = "Sport";
    String[] categorys = {"Choose Category ...","Sport","Elligant","Slippers","Special Choose"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        etType = findViewById(R.id.etType);
        etFootshape = findViewById(R.id.etFootshape);
        etstock = findViewById(R.id.etStock);
        etsaleprice = findViewById(R.id.etSalePrice);
        etbuyprice = findViewById(R.id.etBuyPrice);
        imageButton = findViewById(R.id.imageButton);
        btadd = findViewById(R.id.addButton);
        btadd.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        dbHelper.OpenWriteAble();
        btupdate = findViewById(R.id.btUpdate);
        btupdate.setOnClickListener(this);
        btdelete = findViewById(R.id.btDelete);
        btdelete.setOnClickListener(this);
        spCat = findViewById(R.id.spCategory);
        imageButton.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item,categorys);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCat.setAdapter(ad);
        spCat.setOnItemSelectedListener(this);
        Intent i = getIntent();
        if(i.getStringExtra("Selected_Id")==null){
            btdelete.setVisibility(View.GONE);
            btupdate.setVisibility(View.GONE);
        }
        else {
            btadd.setVisibility(View.GONE);
            selectedId = Integer.parseInt(i.getStringExtra("Selected_Id"));
            setProduct();
        }

    }private void setProduct() {

        dbHelper.OpenReadAble();
        p=new Product();
        Cursor c = p.SelectById(dbHelper.getDb(),selectedId);
        if(c!=null){
            c.moveToFirst();
            etType.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_TYPE)));
            etFootshape.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_FOOTSHAPE)));
            etbuyprice.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_BUYPRICE )));
            etsaleprice.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_SALEPRICE)));
            etstock.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_STOCK)));
            selectedcategory = c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_CATEGORY));
            spCat.setSelection(ad.getPosition((selectedcategory)));
            image = c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE));
            Bitmap bm = BitmapFactory.decodeByteArray(image, 0 ,image.length);
            imageButton.setImageBitmap(bm);
        }
        dbHelper.Close();

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.addButton) {
            byte[] data = imageViewToByte();
            p = new Product(etType.getText().toString(), etFootshape.getText().toString(),
                    Integer.parseInt(etstock.getText().toString()),
                    Double.parseDouble(etsaleprice.getText().toString()),
                    Double.parseDouble(etbuyprice.getText().toString()), data,selectedcategory);
            dbHelper.OpenWriteAble();
            if (p.Add(dbHelper.getDb()) > -1) {
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                dbHelper.Close();
                Intent i = new Intent(this, ShowProduct.class);
                startActivity(i);

            }
            if (view.getId() == R.id.btUpdate) {
                p.setId(selectedId);
                p.setType(etType.getText().toString());
                p.setFootShape(etFootshape.getText().toString());
                p.setBuyprice(Double.parseDouble(etbuyprice.getText().toString()));
                p.setSaleprice(Double.parseDouble(etsaleprice.getText().toString()));
                p.setStock(Integer.parseInt(etstock.getText().toString()));
                p.setCategory(selectedcategory);
                if (SelectedNewImage)
                    p.setImageByte(imageViewToByte());
                else
                    p.setImageByte(image);
                dbHelper.OpenWriteAble();
                p.Update(dbHelper.getDb(), selectedId);
                dbHelper.Close();
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, ShowProduct.class);
                startActivity(i);
            }
            if (view.getId() == R.id.btDelete) {
                dbHelper.OpenWriteAble();
                p.Delete(dbHelper.getDb(), selectedId);
                dbHelper.Close();
                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, ShowProduct.class);
                startActivity(i);


            }
        }
        if(view.getId()==R.id.imageButton){
            Intent gallery = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, RESULT_LOAD_IMAGE);
        }
    }
    public byte[] imageViewToByte() {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() ,selectedImageUri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1){
            selectedImageUri = data.getData();
            imageButton.setImageURI(selectedImageUri);
            SelectedNewImage = true;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedcategory = categorys[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

