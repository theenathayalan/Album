package com.zoho.task.album.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zoho.task.album.R;
import com.zoho.task.album.adapter.AlbumsAdapter;
import com.zoho.task.album.app.ApiClient;
import com.zoho.task.album.app.ApiInterface;
import com.zoho.task.album.helper.DividerItemDecoration;
import com.zoho.task.album.helper.RecyclerTouchListener;
import com.zoho.task.album.model.AlbumData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity {
    private List<AlbumData> albumDataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AlbumsAdapter mAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initializeObject();
        setRecyclerClickListener();
        callAlbumsAPI();
    }

    /**
     * initializeObject method is used to create all objects.
     */
    private void initializeObject() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setAdapter();
    }

    /**
     * setRecyclerClickListener method is used for set click listener for recyclerView.
     */
    private void setRecyclerClickListener() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(AlbumActivity.this, GalleryActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    /**
     * callAlbumsAPI method is used for call the albums api.
     */
    private void callAlbumsAPI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading json...");
        progressDialog.show();
        ApiInterface apiService =
                ApiClient.getClient(AlbumActivity.this).create(ApiInterface.class);
        Call<List<AlbumData>> call = apiService.getAlbumsAPI();

        call.enqueue(new Callback<List<AlbumData>>() {
            @Override
            public void onResponse(Call<List<AlbumData>> call, Response<List<AlbumData>> response) {
                albumDataList = response.body();
                setAdapter();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<AlbumData>> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                progressDialog.dismiss();
            }
        });
    }

    /**
     * setAdapter method is used for set adapter to recycler view.
     */
    private void setAdapter() {
        mAdapter = new AlbumsAdapter(albumDataList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(AlbumActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

}
