package com.aa183.putra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    // membuat database dengan nama Films.db
    public DatabaseHandler(Context context) {
        super(context, "UASFilm.db", null, 1);
    }

    // membuat tabel dengan nama film dan terdapat 9 kolom data
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table film(id integer primary key autoincrement, title text not null, descr text not null, genre text not null, director text not null, actor text not null, country text not null, duration text not null, img blob not null)");
    }

    // upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists film");
        onCreate(db);
    }

    // fungsi insert/create data
    public Boolean insertData(String title, String descr, String genre, String director, String actor, String country, String duration, String imgLoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // convert data gambar menjadi byte
            FileInputStream fs = new FileInputStream(imgLoc);
            byte[] imgByte = new byte[fs.available()];
            fs.read(imgByte);
            // set nilai data yang akan disimpan
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", title);
            contentValues.put("descr", descr);
            contentValues.put("genre", genre);
            contentValues.put("director", director);
            contentValues.put("actor", actor);
            contentValues.put("country", country);
            contentValues.put("duration", duration);
            contentValues.put("img", imgByte);
            // insert data ke dalam database table film
            db.insert("film", null, contentValues);
            fs.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // fungsi update data dengan gambar
    public Boolean updateDataImage(Integer id, String title, String descr, String genre, String director, String actor, String country, String duration, String imgLoc) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // convert data gambar menjadi byte
            FileInputStream fs = new FileInputStream(imgLoc);
            byte[] imgByte = new byte[fs.available()];
            fs.read(imgByte);
            // set nilai data yang akan disimpan
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", title);
            contentValues.put("descr", descr);
            contentValues.put("genre", genre);
            contentValues.put("director", director);
            contentValues.put("actor", actor);
            contentValues.put("country", country);
            contentValues.put("duration", duration);
            contentValues.put("img", imgByte);
            // update data ke dalam database table film sesuai dengan id yang diberikan
            db.update("film", contentValues, "id=?", new String[]{String.valueOf(id)});
            fs.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // fungsi update data tanpa gambar
    public Boolean updateDataText(Integer id, String title, String descr, String genre, String director, String actor, String country, String duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        // set nilai data yang akan disimpan
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("descr", descr);
        contentValues.put("genre", genre);
        contentValues.put("director", director);
        contentValues.put("actor", actor);
        contentValues.put("country", country);
        contentValues.put("duration", duration);
        // update data ke dalam database table film sesuai dengan id yang diberikan
        Integer isUpdate = db.update("film", contentValues, "id=?", new String[]{String.valueOf(id)});
        if (isUpdate > 0) {
            return true;
        } else {
            return false;
        }
    }
    // fungsi delete data
    public Boolean deleteData(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // hapus data di database sesuai dengan id yang diberikan
        Integer isDelete = db.delete("film", "id=?", new String[]{String.valueOf(id)});
        if (isDelete > 0) {
            return true;
        } else {
            return false;
        }
    }

    // fungsi read data gambar dari database
    public Bitmap getImage(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        // mengambil data gambar dari database sesuai dengan id yang diberikan
        Cursor cursor = db.rawQuery("select * from film where id=?", new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            byte[] img = cursor.getBlob(8);
            bt = BitmapFactory.decodeByteArray(img, 0, img.length);
        }
        return bt;
    }

    // fungsi read data text dari database
    public Cursor getFilmData(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // mengambil data title, desc, genre, director, actor, country dan duration dari database sesuai dengan id yang diberikan
        Cursor res = db.rawQuery("select title, descr, genre, director, actor, country, duration from film where id=?", new String[]{String.valueOf(id)});
        return res;
    }

    // fungsi membuat arraylist dari semua data yang diambil pada database
    public ArrayList<Film> getList() {
        Film film = null;
        ArrayList<Film> filmList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from film", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            film = new Film(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getBlob(8));
            filmList.add(film);
            cursor.moveToNext();
        }
        return filmList;
    }
}
