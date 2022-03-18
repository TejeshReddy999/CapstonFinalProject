package com.example.sunny.moviemagazine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.MyTvHolder> {
    private static final String api = BuildConfig.api_key;
    Context tvcontext;

    public TvShowsAdapter(Response.Listener<JSONObject> tvcontext, ArrayList<TvShows> tvdata) {
        this.tvcontext = (Context) tvcontext;
        this.tvdata = tvdata;
    }

    ArrayList<TvShows> tvdata;
    @Override
    public TvShowsAdapter.MyTvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(tvcontext);
        view = mInflater.inflate(R.layout.card_view_frame, parent, false);
        return new TvShowsAdapter.MyTvHolder(view);
    }

    @Override
    public void onBindViewHolder(TvShowsAdapter.MyTvHolder holder, int position) {
        final TvShows tv = tvdata.get(position);
        final String imageurl = (tv.getTumbnail());
        final String imageurl2 = (tv.getBackDrop());
        final String title = (tv.getTitle());
        final String rating = (tv.getRating());
        final String releaseing = (tv.getRelease());
        final String decriptipon = (tv.getDescription());
        final String uid = (tv.getId());
        String s="http://image.tmdb.org/t/p/w300" + imageurl + "?api_key="+ api ;
        Picasso.with(tvcontext).load(s).fit().centerInside().placeholder(R.mipmap.ic_launcher).into(holder.imgbookThumb);
        Log.i("tv Image url",s);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tvcontext, MovieInfo.class);
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
        return tvdata.size();
    }

    public class MyTvHolder extends RecyclerView.ViewHolder {
        private ImageView imgbookThumb;
        private CardView cardView;
        private RecyclerView myrv;
        public MyTvHolder(View itemView) {
            super(itemView);
            imgbookThumb = itemView.findViewById(R.id.id_MovieImage);
            cardView = itemView.findViewById(R.id.id_cardview);
            myrv = itemView.findViewById(R.id.id_recyclerviwe);
        }
    }
}
