package com.drughi.vyng.data.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class GifMutable {

    @Id
    private long id;
    private String url;
    private String mp4;

    public GifMutable(String url, String mp4) {
        this.url = url;
        this.mp4 = mp4;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }
}
