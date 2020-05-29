package com.aa183.putra;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelectedManageActivity extends AppCompatActivity {

    TextView tvTitle, tvDesc, tvGenre, tvDirector, tvActor, tvCountry, tvDuration;
    ImageView imgFIlm;
    Button btnUpdate, btnDelete, btnCancel;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_manage);

        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvDesc = (TextView)findViewById(R.id.tv_desc);
        tvGenre = (TextView)findViewById(R.id.tv_genre);
        tvDirector = (TextView)findViewById(R.id.tv_director);
        tvActor = (TextView)findViewById(R.id.tv_actor);
        tvCountry = (TextView)findViewById(R.id.tv_country);
        tvDuration = (TextView)findViewById(R.id.tv_duration);
        imgFIlm = (ImageView)findViewById(R.id.img_film);
        btnUpdate = (Button)findViewById(R.id.btn_update);
        btnDelete = (Button)findViewById(R.id.btn_delete);
        btnCancel = (Button)findViewById(R.id.btn_cancel);
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

        // tombol menuju tampilan update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                intent.putExtra("id", id.toString());
                startActivity(intent);
                finish();
            }
        });

        // tombol untuk delete data
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.deleteData(id)) {
                    Toast.makeText(getApplicationContext(), "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // tombol cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
