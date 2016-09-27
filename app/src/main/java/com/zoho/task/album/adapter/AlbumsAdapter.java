package com.zoho.task.album.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoho.task.album.R;
import com.zoho.task.album.model.AlbumData;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private List<AlbumData> albumDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;//, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }


    public AlbumsAdapter(List<AlbumData> albumDataList) {
        this.albumDataList = albumDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AlbumData movie = albumDataList.get(position);
        holder.title.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return albumDataList.size();
    }
}
