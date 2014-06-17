package com.nedeljko.imagesearch.app;

public enum ImageSize {
    NONE(1, null, R.string.any_size),
    SMALL(1, "small", R.string.small),
    MEDIUM(2, "medium", R.string.medium),
    LARGE(3, "large", R.string.large),
    XLARGE(4, "xlarge", R.string.xlarge),
    XXLARGE(5, "xxlarge", R.string.xxlarge),
    HUGE(6, "huge", R.string.huge);

    private final int mIndex;
    private final String mName;
    private final int mDisplayNameId;

    private ImageSize(final int index, final String name, final int displayNameId) {
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

    public static ImageSize toEnum(int index) {
        return ImageSize.values()[index];
    }

    @Override
    public String toString() {
        return MainActivity.getInstance().getResources().getString(mDisplayNameId);
    }
}
