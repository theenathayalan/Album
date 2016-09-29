package com.zoho.task.album.gallery;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zoho.task.album.BuildConfig;
import com.zoho.task.album.R;
import com.zoho.task.album.imageloader.ImageCache;
import com.zoho.task.album.imageloader.ImageFetcher;
import com.zoho.task.album.imageloader.ImageWorker;
import com.zoho.task.album.imageloader.Utils;


public class FullViewActivity extends FragmentActivity implements ImageWorker.OnImageLoadedListener {
    private static final String IMAGE_CACHE_DIR = "images";
    private ImageFetcher mImageFetcher;
    private ImageView imageViewPreview;
    private ProgressBar progressBar;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Utils.enableStrictMode();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_fullscreen_preview);
        initializeObject();
        loadImage();
    }

    /**
     * initializeObject method is used to create all objects.
     */
    private void initializeObject() {
        imageViewPreview = (ImageView) findViewById(R.id.image_preview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;

        final int longest = (height > width ? height : width) / 2;

        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f);
        mImageFetcher = new ImageFetcher(this, longest);
        mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);
        mImageFetcher.setImageFadeIn(false);
        bundle = this.getIntent().getExtras();
    }

    /**
     * loadImage method is used to load the image to imageview.
     */
    private void loadImage() {
        String imageUrl = bundle.getString("imageURL");
        mImageFetcher.loadImage(imageUrl, imageViewPreview, this);
    }

    @Override
    public void onImageLoaded(boolean success) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mImageFetcher.setExitTasksEarly(true);
        mImageFetcher.flushCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageFetcher.closeCache();
    }

    /**
     * Called by the ViewPager child fragments to load images via the one ImageFetcher
     */
    public ImageFetcher getImageFetcher() {
        return mImageFetcher;
    }
}
