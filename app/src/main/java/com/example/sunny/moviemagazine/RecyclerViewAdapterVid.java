package com.example.sunny.moviemagazine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sunny on 16-05-2018.
 */

public class RecyclerViewAdapterVid extends RecyclerView.Adapter<RecyclerViewAdapterVid.MyViewHolder> {

    Context vidcontext;
    ArrayList<Video> viddata;

    public RecyclerViewAdapterVid(Context listener, ArrayList<Video> isViedo) {
        this.vidcontext = listener;
        this.viddata = isViedo;
    }


    @Override
    public RecyclerViewAdapterVid.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(vidcontext);
        view = mInflater.inflate(R.layout.video_frame, parent, false);
        return new RecyclerViewAdapterVid.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterVid.MyViewHolder holder, int position) {
        final Video vv = viddata.get(position);
        holder.vitv.setText(vv.getVname());
        holder.vitv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + vv.getVkey());
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                vidcontext.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return viddata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView vitv;

        public MyViewHolder(View itemView) {
            super(itemView);
            vitv = itemView.findViewById(R.id.vid_txt);
        }
    }
}
