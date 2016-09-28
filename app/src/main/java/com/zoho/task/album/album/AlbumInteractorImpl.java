package com.zoho.task.album.album;

import android.content.Context;

import com.zoho.task.album.album.model.AlbumData;
import com.zoho.task.album.rest.ApiClient;
import com.zoho.task.album.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumInteractorImpl implements AlbumInteractor {

    @Override
    public void albumAPI(Context context,final OnAlbumAPIFinishedListener listener) {

        ApiInterface apiService =
                ApiClient.getClient(context).create(ApiInterface.class);
        Call<List<AlbumData>> call = apiService.getAlbumsAPI();

        call.enqueue(new Callback<List<AlbumData>>() {
            @Override
            public void onResponse(Call<List<AlbumData>> call, Response<List<AlbumData>> response) {
                List albumDataList = response.body();
                listener.onSuccess(albumDataList);
            }

            @Override
            public void onFailure(Call<List<AlbumData>> call, Throwable t) {
                // Log error here since request failed
                listener.onException();
            }
        });
    }
}
