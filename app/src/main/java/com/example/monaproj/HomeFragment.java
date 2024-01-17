package com.example.monaproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ImageView elegant, special, sport, slippers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_frafment, container, false);

        elegant = v.findViewById(R.id.ivElegan);
        special = v.findViewById(R.id.ivSpeacial);
        sport = v.findViewById(R.id.ivSport);
        slippers = v.findViewById(R.id.ivSlippers);
        elegant.setOnClickListener(this);
        special.setOnClickListener(this);
        sport.setOnClickListener(this);
        slippers.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ivElegan){
            Intent i = new Intent(getContext(),SelcetedCategoryView.class);
            i.putExtra("Category", "Elligant");
            startActivity(i);
        }
        if(view.getId() == R.id.ivSpeacial){
            Intent i = new Intent(getContext(),SelcetedCategoryView.class);
            i.putExtra("Category", "Special Choose");
            startActivity(i);
        }
        if(view.getId() == R.id.ivSlippers){
            Intent i = new Intent(getContext(),SelcetedCategoryView.class);
            i.putExtra("Category", "Slippers");
            startActivity(i);
        }
        if(view.getId() == R.id.ivSport){
            Intent i = new Intent(getContext(),SelcetedCategoryView.class);
            i.putExtra("Category", "Sport");
            startActivity(i);
        }
    }
}