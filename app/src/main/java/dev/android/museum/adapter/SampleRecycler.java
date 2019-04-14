package dev.android.museum.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class SampleRecycler extends RecyclerView.Adapter<SampleRecycler.SampleHolder> {
    @Override
    public SampleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SampleHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SampleHolder extends RecyclerView.ViewHolder {
        public SampleHolder(View itemView) {
            super(itemView);
        }
    }
}