package com.nedeljko.imagesearch.app;

import android.content.SharedPreferences;
import com.loopj.android.http.RequestParams;

public class ImageSearchOptions {
    private static final String IMAGE_SIZE_KEY = "imageSize";
    private static final String IMAGE_COLOR_KEY = "imageColor";
    private static final String IMAGE_TYPE_KEY = "imageType";
    private static final String SITE_KEY = "site";

    private SharedPreferences mSharedPreferences;
    private ImageSize mImageSize;
    private ImageColor mImageColor;
    private ImageType mImageType;
    private String mSite;

    public ImageSearchOptions(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
        populateDefaults();
    }

    public ImageSize getImageSize() {
        return mImageSize;
    }

    public void setImageSize(ImageSize imageSize) {
        this.mImageSize = imageSize;
    }

    public ImageColor getImageColor() {
        return mImageColor;
    }

    public void setImageColor(ImageColor imageColor) {
        this.mImageColor = imageColor;
    }

    public ImageType getImageType() {
        return mImageType;
    }

    public void setImageType(ImageType imageType) {
        this.mImageType = imageType;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        this.mSite = site;
    }

    public void writeDefaults() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(IMAGE_SIZE_KEY, mImageSize.getIndex());
        editor.putInt(IMAGE_COLOR_KEY, mImageType.getIndex());
        editor.putInt(IMAGE_TYPE_KEY, mImageType.getIndex());
        editor.putString(SITE_KEY, mSite);
        editor.commit();
    }

    private void populateDefaults() {
        mImageSize = ImageSize.toEnum(mSharedPreferences.getInt(IMAGE_SIZE_KEY, 0));
        mImageType = ImageType.toEnum(mSharedPreferences.getInt(IMAGE_COLOR_KEY, 0));
        mImageColor = ImageColor.toEnum(mSharedPreferences.getInt(IMAGE_TYPE_KEY, 0));
        mSite = mSharedPreferences.getString(SITE_KEY, null);
    }

    public RequestParams generateRequestParams() {
        RequestParams params = new RequestParams();
        params.put("imgsz", mImageSize.getName());
        params.put("imgcolor", mImageColor.getName());
        params.put("imgtype", mImageType.getName());
        params.put("as_sitesearch", mSite);
        return params;
    }
}
