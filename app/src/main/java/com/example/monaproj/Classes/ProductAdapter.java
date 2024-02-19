package com.example.monaproj.Classes;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.monaproj.DataBase.DBHelper;
import com.example.monaproj.ProductInfo;
import com.example.monaproj.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> productList;
    int pid;
    String uid;
    View view;
    Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {

        view = LayoutInflater.from(context).inflate(R.layout.oneview, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        // here we will find the position and start setting the output on our views
        String typeOfProduct = productList.get(position).getType();
        String footshape = productList.get(position).getFootShape();
        double price = productList.get(position).getSaleprice();
        byte[] images = productList.get(position).getImageByte();
        Bitmap bm = BitmapFactory.decodeByteArray(images, 0 ,images.length);

        holder.tvTypeOfProduct.setText(typeOfProduct);
        holder.tvSize.setText(footshape);
        holder.tvPrice.setText(price+"");
        holder.imageOfProduct.setImageBitmap(bm);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // in order to make our views responsive we can implement onclicklistener on our recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // here we will find the views on which we will inflate our data

        TextView tvTypeOfProduct, tvPrice,tvSize;
        ImageView imageOfProduct, addToCartBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTypeOfProduct = itemView.findViewById(R.id.eachShoeName);
            tvSize = itemView.findViewById(R.id.eachShoeSiza);
            tvPrice = itemView.findViewById(R.id.eachShoePriceTv);
            imageOfProduct = itemView.findViewById(R.id.eachShoeIv);
            addToCartBtn = itemView.findViewById(R.id.eachShoeAddToCartBtn);
            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pid = productList.get(getLayoutPosition()).getId();
                    uid = FirebaseAuth.getInstance().getUid();
                    Cart c = new Cart(uid,pid,1);
                    DBHelper dbHelper = new DBHelper(context);
                    dbHelper.OpenWriteAble();
                    if(c.Add(dbHelper.getDb())>-1)
                        Toast.makeText(context, "Added To Cart Successfully", Toast.LENGTH_SHORT).show();
                    dbHelper.Close();
                }
            });
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ProductInfo.class);
            intent.putExtra("id", productList.get(getLayoutPosition()).getId() + "");
            v.getContext().startActivity(intent);
        }

    }
}

