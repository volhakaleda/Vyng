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

  /**
   * Provides context singleton.
   *
   * @return context
   */
  @Provides
  @Singleton
  public Context provideContext() {
    return application;
  }

  /**
   * Provides Retrofit singleton setup for Gson and RxJava.
   *
   * @return Retrofit instance
   */
  @Provides
  @Singleton
  public Retrofit provideRetrofit() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.giphy.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build();

    return retrofit;
  }

  /**
   * Provides Retrofit service singleton.
   *
   * @param retrofit - retrofit instance
   * @return Retrofit service
   */
  @Provides
  @Singleton
  public VyngService provideService(Retrofit retrofit) {
    return retrofit.create(VyngService.class);
  }

  /**
   * Provides ExoPlayer instance.
   *
   * @param context
   * @return simple instance of ExoPlayer
   */
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

  /**
   * Provides BoxStore singleton.
   *
   * @param context
   * @return BoxStore instance
   */
  @Provides
  @Singleton
  public BoxStore provideBoxStore(Context context) {
    return MyObjectBox.builder().androidContext(context).build();
  }

}
