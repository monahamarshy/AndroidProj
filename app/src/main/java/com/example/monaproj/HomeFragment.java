package com.example.monaproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    ImageView elegan, special, sport, slippers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_frafment, container, false);

        elegan = v.findViewById(R.id.ivElegan);
        special = v.findViewById(R.id.ivSpeacial);
        sport = v.findViewById(R.id.ivSport);
        slippers = v.findViewById(R.id.ivSlippers);

        elegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductInfo("Elegant");
            }
        });

        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductInfo("Special Choose");
            }
        });

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductInfo("Sport");
            }
        });

        slippers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductInfo("Slippers");
            }
        });

        return v;
    }

    private void navigateToProductInfo(String category) {
        Intent Ele = new Intent (HomeFragment.this, ElegantActivity.class);
        startActivity(Ele);

    }
}
