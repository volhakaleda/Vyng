package com.drughi.vyng.data.model;

import com.google.gson.annotations.SerializedName;

public class Image {

    private Original original;

    @SerializedName("fixed_height")
    private FixedHeight fixedHeight;

    public Original getOriginal() {
        return original;
    }

    public FixedHeight getFixedHeight() {
        return fixedHeight;
    }
}
