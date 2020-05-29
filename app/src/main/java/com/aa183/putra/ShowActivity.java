package com.aa183.putra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        db = new DatabaseHandler(this);
        // mendapatkan data film dan menampilkan pada listview
        final ListView lvFilm = (ListView)findViewById(R.id.lv_film);
        ArrayList<Film> filmList = db.getList();
        ListFilmAdapter listFilmAdapter = new ListFilmAdapter(getApplicationContext(), R.layout.activity_itemfilm, filmList);
        lvFilm.setAdapter(listFilmAdapter);

        // menampilkan detail film dari listview yang di tap
        lvFilm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayoutParent = (LinearLayout) view;
                TextView tvId = (TextView) linearLayoutParent.getChildAt(1);
                String idFilm = tvId.getText().toString();

                Intent intent = new Intent(getApplicationContext(), SelectedFilmActivity.class);
                intent.putExtra("id", idFilm);
                startActivity(intent);
            }
        });
    }
}
