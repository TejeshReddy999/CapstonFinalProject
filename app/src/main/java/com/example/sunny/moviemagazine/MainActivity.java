package com.example.sunny.moviemagazine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, LoaderManager.LoaderCallbacks<Cursor> {

    final static int Fav_ID = 34;
    private static final String api = BuildConfig.api_key;
    public ArrayList<Movie> ismovie;
    public ArrayList<TvShows> istvshow;
    Cursor cursor;
    boolean retrystate = false;
    private ArrayList<Movie> moviecursor = new ArrayList<>();
    private LinearLayout lsnak;
    private RecyclerView myrv;
    private ProgressDialog pd;
    private RecyclerViewAdapter myrevad;
    private TvShowsAdapter tvadapter;
    private String geted_data, data = "popular", pointer = "key", message, action, tvshows_data;
    private RequestQueue requestQueue;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lsnak = findViewById(R.id.id_LinerSank);
        pd = new ProgressDialog(MainActivity.this);
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                }
            }
        };
        geted_data = "http://api.themoviedb.org/3/movie/popular?api_key=" + api;
        tvshows_data = "https://api.themoviedb.org/3/discover/tv?api_key=" + api;
        retrystate = retry();
        if (retrystate == false) {
            if (savedInstanceState != null) {
                if (savedInstanceState.getString(pointer).equals("popular")) {
                    data = savedInstanceState.getString(pointer);
                    pjson(geted_data);
                    setTitle(R.string.popular_movies);
                } else if (savedInstanceState.getString(pointer).equals("top")) {
                    istvshow = new ArrayList<TvShows>();
                    data = savedInstanceState.getString(pointer);
                    setTitle(R.string.tv_shows);
                    tjson(tvshows_data);
                } else if (savedInstanceState.getString(pointer).equals("favourites")) {
                    setTitle(R.string.favourite);
                    data = savedInstanceState.getString(pointer);
                    getSupportLoaderManager().initLoader(Fav_ID, null, this);
                } else {
                    pjson(geted_data);
                }
            } else {
                pjson(geted_data);
            }
        }
    }

    private void tjson(String tvshows_data) {
        pd.setMessage(getString(R.string.lodin));
        pd.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tvshows_data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject results = jsonArray.getJSONObject(i);
                        String imagurl = results.getString("poster_path");
                        String imagurl2 = results.getString("backdrop_path");
                        String title_path = results.getString("name");
                        String rate = results.getString("vote_average");
                        String relese_date = results.getString("first_air_date");
                        String descrip = results.getString("overview");
                        String id = results.getString("id");
                        istvshow.add(new TvShows(imagurl, imagurl2, title_path, rate, relese_date, descrip, id));
                    }
                    tvadapter = new TvShowsAdapter(MainActivity.this, istvshow);
                    myrv.setAdapter(tvadapter);
                    pd.dismiss();
                } catch (JSONException e) {

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(pointer, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (data == "favourites") {
            getSupportLoaderManager().restartLoader(Fav_ID, null, this);
        }
    }

    private boolean retry() {
        if (!Connection(MainActivity.this)) {
            displaydialog(MainActivity.this);
            return true;
        } else {

            myrv = findViewById(R.id.id_recyclerviwe);
            myrv.setHasFixedSize(true);
            ismovie = new ArrayList<Movie>();
            requestQueue = Volley.newRequestQueue(this);
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager gm = new GridLayoutManager(this, 2);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                myrv.setLayoutManager(gm);
            } else {
                GridLayoutManager gm = new GridLayoutManager(this, 1);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                myrv.setLayoutManager(gm);
            }
            message = getString(R.string.swipe_left_scroll);
            action = "";
            messg(message, action);
            return false;
        }
    }

    private void displaydialog(MainActivity mainActivity) {
        message = getString(R.string.no_net);
        action = getString(R.string.retry);
        messg(message, action);
    }

    public boolean Connection(MainActivity mainActivity) {
        ConnectivityManager ce = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = ce.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            android.net.NetworkInfo wcont = ce.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mcont = ce.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return (mcont != null && ni.isConnectedOrConnecting()) || (wcont != null && ni.isConnectedOrConnecting());
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.id_popular) {
            setTitle(R.string.popular_movies);
            data = "popular";
            geted_data = "http://api.themoviedb.org/3/movie/popular?api_key=" + api;
            ismovie = new ArrayList<Movie>();
            requestQueue = Volley.newRequestQueue(this);
            retry();
            if (retry() == false)
                pjson(geted_data);
            else
                messg(message, action);
        }
        if (item.getItemId() == R.id.id_top) {
            setTitle(R.string.tv_shows);
            data = "top";
            istvshow = new ArrayList<TvShows>();
            requestQueue = Volley.newRequestQueue(this);
            retry();
            if (retry() == false)
                tjson(tvshows_data);
            else
                messg(message, action);
        }
        if (item.getItemId() == R.id.id_farvoritmenu) {
            data = "favourites";
            getSupportLoaderManager().restartLoader(Fav_ID, null, this);
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Movie> getFavoriteMoviesDataFromCursor(Cursor cursor) {
        ArrayList<Movie> results = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(cursor);
                results.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return results;
    }

    private void pjson(String geted_data) {
        pd.setMessage(getString(R.string.loding));
        pd.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, geted_data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject results = jsonArray.getJSONObject(i);
                        String imagurl = results.getString("poster_path");
                        String imagurl2 = results.getString("backdrop_path");
                        String title_path = results.getString("original_title");
                        String rate = results.getString("vote_average");
                        String relese_date = results.getString("release_date");
                        String descrip = results.getString("overview");
                        String id = results.getString("id");
                        ismovie.add(new Movie(imagurl, imagurl2, title_path, rate, relese_date, descrip, id));
                    }
                    myrevad = new RecyclerViewAdapter(MainActivity.this, ismovie);
                    myrv.setAdapter(myrevad);
                    pd.dismiss();
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

    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void messg(final String message, final String action) {

        Snackbar snackbar = Snackbar.make(lsnak, message, Snackbar.LENGTH_LONG).setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry();
                if (retry() == false) {
                    pjson(geted_data);
                } else {
                    messg(message, action);
                }
            }
        });
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.GREEN);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void onResponse(JSONObject response) {

    }

    public void fab(View view) {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            @Override
            public Cursor loadInBackground() {

                cursor = getContentResolver()
                        .query(MovieAttributes.dataAttributes.CONTENT_URI,
                                null,
                                null,
                                null,
                                null);
                moviecursor = getFavoriteMoviesDataFromCursor(cursor);
                return cursor;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor dat) {
        if (!moviecursor.isEmpty()) {
            myrevad = new RecyclerViewAdapter(MainActivity.this, moviecursor);
            myrv.setAdapter(myrevad);
            setTitle(R.string.favourite);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.nofav);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (!Connection(MainActivity.this)) {
                        displaydialog(MainActivity.this);
                    } else {
                        pjson(geted_data);
                        setTitle(R.string.app_name);
                    }
                }
            });
            builder.show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}