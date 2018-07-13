package com.drughi.vyng.util;

import android.os.Bundle;

public class ArgBuilder {
    private final Bundle bundle;

    public ArgBuilder(Bundle bundle) {
        this.bundle = bundle;
    }

    public ArgBuilder putString(String key, String value) {
        bundle.putString(key, value);
        return this;
    }

    public Bundle build() {
        return bundle;
    }
}