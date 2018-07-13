package com.drughi.vyng.dagger;

import android.app.Application;
import android.content.Context;

import com.drughi.vyng.data.VyngService;

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

}
