package com.example.listycitylab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddEditCityFragment.Listener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {
                "Edmonton", "Vancouver", "Moscow",
                "Sydney", "Berlin", "Vienna",
                "Tokyo", "Beijing", "Osaka", "New Delhi"
        };
        String[] provinces = {
                "AB", "BC", "RU",
                "AU", "DE", "AT",
                "JP", "CN", "JP", "IN"
        };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // 点击列表 -> 编辑
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City clicked = dataList.get(position);
            AddEditCityFragment.newInstance(clicked)
                    .show(getSupportFragmentManager(), "Edit City");
        });

        // FAB -> 添加
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v ->
                AddEditCityFragment.newInstance(null)
                        .show(getSupportFragmentManager(), "Add City")
        );
    }

    @Override
    public void onCityAdded(City city) {
        dataList.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCityEdited() {
        cityAdapter.notifyDataSetChanged();
    }
}
