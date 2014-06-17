package com.nedeljko.imagesearch.app;

public enum ImageType {
    NONE(0, null, R.string.any_type),
    FACE(1, "face", R.string.face),
    PHOTO(2, "photo", R.string.photo),
    CLIPART(3, "clipart", R.string.clipart),
    LINEART(4, "lineart", R.string.lineart);

    private final int mIndex;
    private final String mName;
    private final int mDisplayNameId;

    private ImageType(final int index, final String name, final int displayNameId) {
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

    public static ImageType toEnum(int index) {
        return ImageType.values()[index];
    }

    @Override
    public String toString() {
        return MainActivity.getInstance().getResources().getString(mDisplayNameId);
    }
}