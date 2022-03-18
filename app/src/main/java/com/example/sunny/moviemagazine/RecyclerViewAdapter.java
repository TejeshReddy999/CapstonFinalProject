package com.example.sunny.moviemagazine;
/**
 * Created by sunny on 10-05-2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context moviecontext;
    ArrayList<Movie> moviedata;

    public RecyclerViewAdapter(Response.Listener<JSONObject> mcontext, ArrayList<Movie> mdata) {
        this.moviecontext = (Context) mcontext;
        this.moviedata = mdata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(moviecontext);
        view = mInflater.inflate(R.layout.card_view_frame, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Movie movi = moviedata.get(position);
        final String imageurl = (movi.getTumbnail());
        final String imageurl2 = (movi.getBackDrop());
        final String title = (movi.getTitle());
        final String rating = (movi.getRating());
        final String releaseing = (movi.getRelease());
        final String decriptipon = (movi.getDescription());
        final String uid = (movi.getId());
        Picasso.with(moviecontext).load("http://image.tmdb.org/t/p/w500" + imageurl + "?api_key=24978f2b10f3bb01cbf78c013b33ca26").fit().centerInside().placeholder(R.mipmap.ic_launcher).into(holder.imgbookThumb);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(moviecontext, MovieInfo.class);
                i.putExtra("imageinfo", imageurl2);
                i.putExtra("imageinfo2", imageurl);
                i.putExtra("titleinfo", title);
                i.putExtra("rateinfo", rating);
                i.putExtra("releaseinfo", releaseing);
                i.putExtra("decinfo", decriptipon);
                i.putExtra("uid", uid);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {

        if (moviedata == null)
            return 0;
        else
            return moviedata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgbookThumb;
        private CardView cardView;
        private RecyclerView myrv;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgbookThumb = itemView.findViewById(R.id.id_MovieImage);
            cardView = itemView.findViewById(R.id.id_cardview);
            myrv = itemView.findViewById(R.id.id_recyclerviwe);
        }
    }
}