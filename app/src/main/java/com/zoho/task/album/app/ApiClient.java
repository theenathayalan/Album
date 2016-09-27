package com.zoho.task.album.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static Retrofit retrofit = null;
    private Context context;

    /**
     * getClient method is used to get the retrofit object.
     *
     * @param context
     * @return retrofit
     */
    public static Retrofit getClient(final Context context) {
        //setup cache
        OkHttpClient okHttpClient = createCachedClient(context);
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * createCachedClient method is used to store the cache to httpclient.
     *
     * @param context
     * @return okHttpClient
     */
    private static OkHttpClient createCachedClient(final Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "cache_file");
        Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                String cacheHeaderValue = isNetworkAvailable(context)
                        ? "public, max-age=2419200"
                        : "public, only-if-cached, max-stale=2419200";
                Request request = originalRequest.newBuilder().build();
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheHeaderValue)
                        .build();
            }
        }).cache(cache).build();
        return okHttpClient;
    }

    /**
     * isNetworkAvailable method is used to find mobile have network or not.
     *
     * @param context
     * @return boolean
     */
    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
