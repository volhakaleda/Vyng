package com.drughi.vyng.dagger;

import android.app.Application;
import android.content.Context;

import com.drughi.vyng.data.VyngService;
import com.drughi.vyng.data.model.MyObjectBox;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

  private Application application;

  public AppModule(Application application) {
    this.application = application;
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return application;
  }

  @Provides
  @Singleton
  public Retrofit provideRetrofit() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.giphy.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build();

    return retrofit;
  }

  @Provides
  @Singleton
  public VyngService provideService(Retrofit retrofit) {
    return retrofit.create(VyngService.class);
  }

  @Provides
  @Singleton
  public ExoPlayer providesPlayer(Context context) {
    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    TrackSelection.Factory videoTrackSelectionFactory =
            new AdaptiveTrackSelection.Factory(bandwidthMeter);
    TrackSelector trackSelector =
            new DefaultTrackSelector(videoTrackSelectionFactory);

    return ExoPlayerFactory.newSimpleInstance(context, trackSelector);
  }

  @Provides
  @Singleton
  public BoxStore provideBoxStore(Context context) {
    return MyObjectBox.builder().androidContext(context).build();
  }

}
