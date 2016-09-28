package com.zoho.task.album.gallery;

import android.content.Context;

import com.zoho.task.album.gallery.model.GalleryData;
import com.zoho.task.album.rest.ApiClient;
import com.zoho.task.album.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryInteractorImpl implements GalleryInteractor {

    @Override
    public void galleryAPI(Context context, final OnLoginFinishedListener listener) {
        ApiInterface apiService =
                ApiClient.getClient(context).create(ApiInterface.class);
        Call<List<GalleryData>> call = apiService.getPhotosAPI();
        call.enqueue(new Callback<List<GalleryData>>() {
            @Override
            public void onResponse(Call<List<GalleryData>> call, Response<List<GalleryData>> response) {
                List<GalleryData> galleryDataList = response.body();
                listener.onSuccess(galleryDataList);
            }

            @Override
            public void onFailure(Call<List<GalleryData>> call, Throwable t) {
                // Log error here since request failed
                listener.onException();
            }
        });
    }

}
