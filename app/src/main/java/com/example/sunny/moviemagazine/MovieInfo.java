package com.example.sunny.moviemagazine;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieInfo extends AppCompatActivity {
    private static final String api = BuildConfig.api_key;
    public ArrayList<Video> isViedo;
    public ArrayList<Review> isReview;
    ImageView imageView, favorit;
    RatingBar ratingBar;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    String veid, messege;
    DataBase dataBase = new DataBase(this);
    private TextView title, relese, rating, description;
    private CollapsingToolbarLayout ctbl;
    private CoordinatorLayout cl;
    private RecyclerView myrv, myrvreview;
    private RecyclerViewAdapterVid myrevadvid;
    private RecyclerViewAdapterRiv myrevadriv;
    private RequestQueue requestQueue;
    private boolean b = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        Intent i = getIntent();

        imageView = findViewById(R.id.id_Moviiiimg);
        ratingBar = findViewById(R.id.id_ratingbar);
        toolbar = findViewById(R.id.id_toolbar);
        title = findViewById(R.id.id_MOVIETITLE);
        relese = findViewById(R.id.id_MOVIEREALEASEDATE);
        rating = findViewById(R.id.id_MOVIERATING);
        description = findViewById(R.id.id_DESCRIPTION);
        ctbl = findViewById(R.id.id_collapsingtoolbar);
        cl = findViewById(R.id.id_coordlay);
        appBarLayout = findViewById(R.id.id_appbarLayout);
        favorit = findViewById(R.id.id_farvorit);

        String s1 = "http://image.tmdb.org/t/p/w500" + i.getStringExtra("imageinfo");
        String s = i.getStringExtra("titleinfo");
        veid = i.getStringExtra("uid");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(getApplicationContext())
                .load(s1).error(R.mipmap.ic_launcher_round)
                .fit().centerInside()
                .into(imageView);
        title.setText(i.getStringExtra("titleinfo"));
        relese.setText(i.getStringExtra("releaseinfo"));
        rating.setText(getString(R.string.rating_moviinfo) + i.getStringExtra("rateinfo"));
        ratingBar.setRating(Float.parseFloat(i.getStringExtra("rateinfo")));
        description.setText(i.getStringExtra("decinfo"));

        isViedo = new ArrayList<Video>();
        requestQueue = Volley.newRequestQueue(this);
        ctbl.setTitle(s);
        myrv = findViewById(R.id.id_RECYCLERVIEWVID);
        myrv.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        myrv.setLayoutManager(lm);
        vdata();

        isReview = new ArrayList<Review>();
        requestQueue = Volley.newRequestQueue(this);
        myrvreview = findViewById(R.id.id_RECYCLERVIEWREV);
        myrvreview.setHasFixedSize(true);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        myrvreview.setLayoutManager(lm2);
        rdata();

        int moid = Integer.parseInt(veid.toString());
        b = dataBase.isFavorite(moid);
        if (b) {
            favorit.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            favorit.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    private void vdata() {

        String uid = "http://api.themoviedb.org/3/movie/" + veid + "/videos?api_key=" + api;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject results = jsonArray.getJSONObject(i);
                        String vid = results.getString("id");
                        String vkey = results.getString("key");
                        String vname = results.getString("name");
                        String vsite = results.getString("site");
                        String vsize = results.getString("size");
                        String vtype = results.getString("type");
                        String viso_639_1 = results.getString("iso_639_1");
                        String viso_3166_l = results.getString("iso_3166_1");
                        isViedo.add(new Video(vid, vkey, vname, vsite, vsize, vtype, viso_639_1, viso_3166_l));
                    }
                    myrevadvid = new RecyclerViewAdapterVid(MovieInfo.this, isViedo);
                    myrv.setAdapter(myrevadvid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private void rdata() {

        String uid = "http://api.themoviedb.org/3/movie/" + veid + "/reviews?api_key=" + api;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject results = jsonArray.getJSONObject(i);
                        String rauthor = results.getString("author");
                        String rcontent = results.getString("content");
                        String rid = results.getString("id");
                        String rurl = results.getString("url");
                        isReview.add(new Review(rauthor, rcontent, rid, rurl));
                    }
                    myrevadriv = new RecyclerViewAdapterRiv(MovieInfo.this, isReview);
                    myrvreview.setAdapter(myrevadriv);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    public void clickFavroit(View view) {
        Intent k = getIntent();
        if (!b) {
            b = true;
            ContentValues moviecontentValues = new ContentValues();
            moviecontentValues.put(MovieAttributes.dataAttributes.COLUMN_MOVIEID, k.getStringExtra("uid"));
            moviecontentValues.put(MovieAttributes.dataAttributes.COLUMN_ORG_TITLE, k.getStringExtra("titleinfo"));
            moviecontentValues.put(MovieAttributes.dataAttributes.COLUMN_ORG_IMG, k.getStringExtra("imageinfo2"));
            moviecontentValues.put(MovieAttributes.dataAttributes.COLUMN_ORG_RAT, k.getStringExtra("rateinfo"));
            moviecontentValues.put(MovieAttributes.dataAttributes.COLUMN_ORG_REL, k.getStringExtra("releaseinfo"));
            Uri uri = getContentResolver().insert(Uri.parse(MovieAttributes.dataAttributes.CONTENT_URI + ""), moviecontentValues);
            if (uri != null) {
                favorit.setImageResource(R.drawable.ic_favorite_black_24dp);
                messege = getString(R.string.addinfavor_movieinfo);
                messg(messege);
            }
        } else {
            b = false;
            getContentResolver().delete(MovieAttributes.dataAttributes.CONTENT_URI, MovieAttributes.dataAttributes.COLUMN_MOVIEID + " =? ", new String[]{k.getStringExtra("uid")});
            favorit.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            messege = getString(R.string.removefav_movieinfo);
            messg(messege);
        }
    }

    public void messg(String messege) {
        Snackbar snackbar = Snackbar.make(cl, messege, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.GREEN);
        snackbar.show();

    }
}