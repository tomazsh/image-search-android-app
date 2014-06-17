package com.nedeljko.imagesearch.app;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageSearch {
    private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images";
    private ImageSearchListener mListener;
    private static AsyncHttpClient sClient;
    private String mSearchQuery;
    private ImageSearchOptions mSearchOptions;
    private int mPageStart;
    private int mPageIndex;
    private boolean mLoading;

    public interface ImageSearchListener {
        void onImageSearchSuccess(ArrayList<Image> newImages);
        void onImageSearchFailure();
    }

    public ImageSearch(ImageSearchOptions searchOptions) {
        if (sClient == null) {
            sClient = new AsyncHttpClient();
        }

        mSearchOptions = searchOptions;
        resetSearch();
    }

    public void setListener(ImageSearchListener listener) {
        this.mListener = listener;
    }

    public void setSearchQuery(String searchQuery) {
        mSearchQuery = searchQuery;
        resetSearch();
    }

    public void resetSearch() {
        mPageStart = 0;
        mPageIndex = 0;
        mLoading = false;
    }

    public void fetchImages() {
        if (mLoading) {
            return;
        }

        mLoading = true;
        RequestParams params = mSearchOptions.generateRequestParams();
        params.put("q", mSearchQuery);
        params.put("v", "1.0");
        params.put("rsz", "8");
        params.put("start", Integer.toString(mPageStart));

        sClient.get(BASE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONObject responseData = response.getJSONObject("responseData");
                    JSONObject cursor = responseData.getJSONObject("cursor");

                    mPageIndex++;
                    JSONArray pages = cursor.getJSONArray("pages");
                    JSONObject page = pages.getJSONObject(mPageIndex);
                    mPageStart = page.getInt("start");

                    ArrayList<Image> newImages = new ArrayList<Image>();
                    JSONArray results = responseData.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        newImages.add(new Image(result));
                    }

                    mListener.onImageSearchSuccess(newImages);
                    mLoading = false;
                } catch (JSONException e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, JSONObject response) {
                System.out.println("Failed to fetch images: " + response);
                mListener.onImageSearchFailure();
                mLoading = false;
            }
        });
    }
}
