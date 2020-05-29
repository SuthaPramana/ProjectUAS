package com.aa183.putra;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelectedFilmActivity extends AppCompatActivity {

    TextView tvTitle, tvDesc, tvGenre, tvDirector, tvActor, tvCountry, tvDuration;
    ImageView imgFIlm;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_film);

        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvDesc = (TextView)findViewById(R.id.tv_desc);
        tvGenre = (TextView)findViewById(R.id.tv_genre);
        tvDirector = (TextView)findViewById(R.id.tv_director);
        tvActor = (TextView)findViewById(R.id.tv_actor);
        tvCountry = (TextView)findViewById(R.id.tv_country);
        tvDuration = (TextView)findViewById(R.id.tv_duration);
        imgFIlm = (ImageView)findViewById(R.id.img_film);
        db = new DatabaseHandler(this);

        Intent imgId = getIntent();
        final Integer id = Integer.parseInt(imgId.getExtras().getString("id"));
        imgFIlm.setImageBitmap(db.getImage(id));

        // menampilkan detail data film yang dipilih berdasarkan id
        Cursor res = db.getFilmData(id);
        if (res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            while (res.moveToNext()) {
                tvTitle.setText(res.getString(0));
                tvDesc.setText(res.getString(1));
                tvGenre.setText(res.getString(2));
                tvDirector.setText(res.getString(3));
                tvActor.setText(res.getString(4));
                tvCountry.setText(res.getString(5));
                tvDuration.setText(res.getString(6));
            }
        }
    }
}
