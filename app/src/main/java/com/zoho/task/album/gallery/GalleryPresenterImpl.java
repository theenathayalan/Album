/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.zoho.task.album.gallery;

import android.content.Context;

import com.zoho.task.album.gallery.model.GalleryData;

import java.util.List;

public class GalleryPresenterImpl implements GalleryPresenter, GalleryInteractor.OnGalleryAPIFinishedListener {

    private GalleryView galleryView;
    private GalleryInteractor galleryInteractor;

    public GalleryPresenterImpl(GalleryView galleryView) {
        this.galleryView = galleryView;
        this.galleryInteractor = new GalleryInteractorImpl();
    }

    @Override
    public void callGalleryAPI(Context context) {
        if (galleryView != null) {
            galleryView.showProgress();
        }
        galleryInteractor.galleryAPI(context,this);
    }

    @Override
    public void onDestroy() {
        galleryView = null;
    }


    @Override
    public void onSuccess(List<GalleryData> galleryDataList) {
        if (galleryView != null) {
            galleryView.onSuccess(galleryDataList);
            galleryView.hideProgress();
        }
    }

    @Override
    public void onException() {
        if (galleryView != null) {
            galleryView.onException();
            galleryView.hideProgress();
        }
    }
}
