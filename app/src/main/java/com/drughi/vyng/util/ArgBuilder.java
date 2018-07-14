package com.drughi.vyng.util;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Utility class to populate bundle with data
 */
public class ArgBuilder {
    private final Bundle bundle;

    public ArgBuilder(Bundle bundle) {
        this.bundle = bundle;
    }

    public ArgBuilder putParcelable(String key, Parcelable value) {
        bundle.putParcelable(key, value);
        return this;
    }

    public Bundle build() {
        return bundle;
    }
}