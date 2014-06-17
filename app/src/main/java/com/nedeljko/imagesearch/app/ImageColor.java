package com.nedeljko.imagesearch.app;

public enum ImageColor {
    NONE(0, null, R.string.any_color),
    BLACK(1, "black", R.string.black),
    BLUE(2, "blue", R.string.blue),
    BROWN(3, "brown", R.string.brown),
    GRAY(4, "gray", R.string.gray),
    GREEN(5, "green", R.string.green),
    ORANGE(6, "orange", R.string.orange),
    PINK(7, "pink", R.string.pink),
    PURPLE(8, "purple", R.string.purple),
    RED(9, "red", R.string.red),
    TEAL(10, "teal", R.string.teal),
    WHITE(11, "white", R.string.white),
    YELLOW(12, "yellow", R.string.yellow);

    private final int mIndex;
    private final String mName;
    private final int mDisplayNameId;

    private ImageColor(final int index, final String name, final int displayNameId) {
        mIndex = index;
        mName = name;
        mDisplayNameId = displayNameId;
    }

    public int getIndex() {
        return mIndex;
    }

    public String getName() {
        return mName;
    }

    public int getDisplayNameId() {
        return mDisplayNameId;
    }

    public static ImageColor toEnum(int index) {
        return ImageColor.values()[index];
    }

    @Override
    public String toString() {
        return MainActivity.getInstance().getResources().getString(mDisplayNameId);
    }
}
