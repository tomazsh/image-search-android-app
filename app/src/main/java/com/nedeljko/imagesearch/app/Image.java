package com.nedeljko.imagesearch.app;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    private int mTbWidth;
    private int mTbHeight;
    private String mTbUrl;
    private int mWidth;
    private int mHeight;
    private String mUrl;

    public Image(JSONObject jsonObject) {
        try {
            mTbWidth = jsonObject.getInt("tbWidth");
            mTbHeight = jsonObject.getInt("tbHeight");
            mTbUrl = jsonObject.getString("tbUrl");
            mWidth = jsonObject.getInt("width");
            mHeight = jsonObject.getInt("height");
            mUrl = jsonObject.getString("url");
        } catch (JSONException e) {
            System.out.println(e);
        }
    }

    public int getTbWidth() {
        return mTbWidth;
    }

    public void setTbWidth(int tbWidth) {
        this.mTbWidth = tbWidth;
    }

    public int getTbHeight() {
        return mTbHeight;
    }

    public void setTbHeight(int tbHeight) {
        this.mTbHeight = tbHeight;
    }

    public String getTbUrl() {
        return mTbUrl;
    }

    public void setTbUrl(String tbUrl) {
        this.mTbUrl = tbUrl;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
