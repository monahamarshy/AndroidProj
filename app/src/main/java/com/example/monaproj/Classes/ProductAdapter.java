package com.example.monaproj.Classes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.monaproj.R;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> productList;
    Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.oneview, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        // here we will find the position and start setting the output on our views

        String nameofProduct = productList.get(position).getType();
        String desc = productList.get(position).getFootShape();
        double SalePriceProduct = productList.get(position).getSaleprice();
        byte[] images = productList.get(position).getImageByte();
        Bitmap bm = BitmapFactory.decodeByteArray(images, 0 ,images.length);
        holder.tvprice.setText(SalePriceProduct+"");
        holder.tvNameOfProduct.setText(nameofProduct);
        holder.tvDescriptionOfProduct.setText(desc);
        holder.imageOfProduct.setImageBitmap(bm);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // in order to make our views responsive we can implement onclicklistener on our recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // here we will find the views on which we will inflate our data

        TextView tvNameOfProduct, tvDescriptionOfProduct,tvprice;
        ImageView imageOfProduct, addtocart;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameOfProduct = itemView.findViewById(R.id.eachShoeName);
            tvDescriptionOfProduct = itemView.findViewById(R.id.eachShoeSiza);
            imageOfProduct = itemView.findViewById(R.id.eachShoeIv);
            tvprice= itemView.findViewById(R.id.eachShoePriceTv);
            addtocart = itemView.findViewById(R.id.eachShoeAddToCartBtn);
            addtocart.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.eachShoeAddToCartBtn){
            }
            else{
            Intent intent = new Intent(v.getContext(),ProductInfo.class);
            intent.putExtra("id",productList.get(getLayoutPosition()).getId()+"");
            v.getContext().startActivity(intent);
        }
    }
}}