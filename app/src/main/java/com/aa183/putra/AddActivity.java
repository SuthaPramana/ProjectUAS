package com.aa183.putra;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText edtTitle, edtDesc, edtGenre, edtDirector, edtActor, edtCountry, edtDuration;
    Button btnSelect, btnSave;
    ImageView imgFilm;
    private static final int GALLERY_REQUEST_CODE = 100;
    Uri imageData;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtTitle = (EditText)findViewById(R.id.edt_title);
        edtDesc = (EditText)findViewById(R.id.edt_desc);
        edtGenre = (EditText)findViewById(R.id.edt_genre);
        edtDirector = (EditText)findViewById(R.id.edt_director);
        edtActor = (EditText)findViewById(R.id.edt_actor);
        edtCountry = (EditText)findViewById(R.id.edt_country);
        edtDuration = (EditText)findViewById(R.id.edt_duration);
        btnSelect = (Button)findViewById(R.id.btn_select);
        btnSave = (Button)findViewById(R.id.btn_save);
        imgFilm = (ImageView)findViewById(R.id.img_film);
        db = new DatabaseHandler(this);

        // tombol save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String desc = edtDesc.getText().toString();
                String genre = edtGenre.getText().toString();
                String director = edtDirector.getText().toString();
                String actor = edtActor.getText().toString();
                String country = edtCountry.getText().toString();
                String duration = edtDuration.getText().toString();

                // check form telah terisi semua
                if (!title.equals("") && !desc.equals("") && !genre.equals("") && !director.equals("") && !actor.equals("") && !country.equals("") && !duration.equals("")) {
                    // check gambar telah dipilih
                    if (imageData != null) {
                        String loc = getPath(imageData);
                        // check apakah proses simpan ke database berhasil
                        if (db.insertData(title, desc, genre, director, actor, country, duration, loc)) {
                            Toast.makeText(getApplicationContext(), "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Silahkan pilih gambar", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Semua data wajib diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // tombol pilih gambar
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"
                ));
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });
    }

    // mendapatkan data gambar
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageData = data.getData();
            imgFilm.setImageURI(imageData);
        }
    }

    // mendapatkan lokasi gambar
    public String getPath(Uri uri) {
        if (uri == null) return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null,null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
}
