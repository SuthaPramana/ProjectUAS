package com.aa183.putra;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListFilmAdapter extends ArrayAdapter<Film> {

    private Context mContext;
    int mResource;
    private static class ViewHolder {
        ImageView imgThumbfilm;
        TextView tvId, tvTitle;
    }
    public ListFilmAdapter(Context context, int resource, ArrayList<Film> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    // fungsi mendapatkan data untuk ditampilkan pada listview
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Integer id = getItem(position).getIdfilm();
        String title = getItem(position).getTitle();
        String desc = getItem(position).getDescr();
        String genre = getItem(position).getGenre();
        String director = getItem(position).getDirector();
        String actor = getItem(position).getActor();
        String country = getItem(position).getCountry();
        String duration = getItem(position).getDuration();
        byte[] img = getItem(position).getImg();

        Film film = new Film(id, title, desc, genre, director, actor, country, duration, img);
        View res;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.tvId = (TextView)convertView.findViewById(R.id.tv_id);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
            holder.imgThumbfilm = (ImageView)convertView.findViewById(R.id.img_thumbfilm);
            res = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
            res = convertView;
        }
        holder.tvId.setText(film.getIdfilm().toString());
        holder.tvTitle.setText(film.getTitle());
        holder.imgThumbfilm.setImageBitmap(BitmapFactory.decodeByteArray(film.getImg(), 0, film.getImg().length));
        return convertView;
    }
}
