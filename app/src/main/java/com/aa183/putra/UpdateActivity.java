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

public class UpdateActivity extends AppCompatActivity {

    EditText edtTitle, edtDesc, edtGenre, edtDirector, edtActor, edtCountry, edtDuration;
    Button btnSelect, btnUpdate, btnCancel;
    ImageView imgFilm;
    DatabaseHandler db;
    private static final int GALLERY_REQUEST_CODE = 100;
    Uri imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtTitle = (EditText)findViewById(R.id.edt_title);
        edtDesc = (EditText)findViewById(R.id.edt_desc);
        edtGenre = (EditText)findViewById(R.id.edt_genre);
        edtDirector = (EditText)findViewById(R.id.edt_director);
        edtActor = (EditText)findViewById(R.id.edt_actor);
        edtCountry = (EditText)findViewById(R.id.edt_country);
        edtDuration = (EditText)findViewById(R.id.edt_duration);
        btnSelect = (Button)findViewById(R.id.btn_select);
        btnUpdate = (Button)findViewById(R.id.btn_update);
        btnCancel = (Button)findViewById(R.id.btn_cancel);
        imgFilm = (ImageView)findViewById(R.id.img_film);
        db = new DatabaseHandler(this);

        Intent imgId = getIntent();
        final Integer id = Integer.parseInt(imgId.getExtras().getString("id"));
        imgFilm.setImageBitmap(db.getImage(id));

        // menampilkan detail data film yang dipilih berdasarkan id kemudian ditampilkan pada form
        Cursor res = db.getFilmData(id);
        if (res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        } else {
            while (res.moveToNext()) {
                edtTitle.setText(res.getString(0));
                edtDesc.setText(res.getString(1));
                edtGenre.setText(res.getString(2));
                edtDirector.setText(res.getString(3));
                edtActor.setText(res.getString(4));
                edtCountry.setText(res.getString(5));
                edtDuration.setText(res.getString(6));
            }
        }

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

        // tombol update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
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
                        if (db.updateDataImage(id, title, desc, genre, director, actor, country, duration, loc)) {
                            Toast.makeText(getApplicationContext(), "Berhasil diperbaharui", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
                            intent.putExtra("id", id.toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Gagal diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (db.updateDataText(id, title, desc, genre, director, actor, country, duration)) {
                            Toast.makeText(getApplicationContext(), "Berhasil diperbaharui", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
                            intent.putExtra("id", id.toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Gagal diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Semua data wajib diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // tombol cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectedManageActivity.class);
                intent.putExtra("id", id.toString());
                startActivity(intent);
                finish();
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
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
}
