package com.example.sunny.moviemagazine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sunny on 18-05-2018.
 */

public class RecyclerViewAdapterRiv extends RecyclerView.Adapter<RecyclerViewAdapterRiv.MyViewHolder> {
    Context rcontext;
    ArrayList<Review> rivdata;

    public RecyclerViewAdapterRiv(Context listener, ArrayList<Review> isReview) {
        this.rcontext = listener;
        this.rivdata = isReview;
    }

    @Override
    public RecyclerViewAdapterRiv.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(rcontext);
        view = mInflater.inflate(R.layout.review_frame, parent, false);
        return new RecyclerViewAdapterRiv.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterRiv.MyViewHolder holder, int position) {
        Review rr = rivdata.get(position);
        holder.rvitva.setText(rcontext.getString(R.string.arrow_rvapd) + rr.getRauthor());
        holder.rvitvr.setText(rr.getRcontent());
    }

    @Override
    public int getItemCount() {
        return rivdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rvitva, rvitvr;

        public MyViewHolder(View itemView) {
            super(itemView);
            rvitva = itemView.findViewById(R.id.id_Author);
            rvitvr = itemView.findViewById(R.id.id_Review);
        }
    }
}
