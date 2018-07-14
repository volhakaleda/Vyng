package com.drughi.vyng.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Mutable Model that is stored in local data source and passed to views.
 */
@Entity
public class GifMutable implements Parcelable{

    @Id
    private long id;
    private String url;
    private String mp4;
    private long upVotes;
    private long downVotes;
    private String searchText;

    public GifMutable(String url, String mp4, String searchText) {
        this.url = url;
        this.mp4 = mp4;
        this.searchText = searchText;
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

    public long getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(long upVotes) {
        this.upVotes = upVotes;
    }

    public long getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(long downVotes) {
        this.downVotes = downVotes;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.url);
        dest.writeString(this.mp4);
        dest.writeLong(this.upVotes);
        dest.writeLong(this.downVotes);
        dest.writeString(this.searchText);
    }

    protected GifMutable(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
        this.mp4 = in.readString();
        this.upVotes = in.readLong();
        this.downVotes = in.readLong();
        this.searchText = in.readString();
    }

    public static final Creator<GifMutable> CREATOR = new Creator<GifMutable>() {
        @Override
        public GifMutable createFromParcel(Parcel source) {
            return new GifMutable(source);
        }

        @Override
        public GifMutable[] newArray(int size) {
            return new GifMutable[size];
        }
    };
}
