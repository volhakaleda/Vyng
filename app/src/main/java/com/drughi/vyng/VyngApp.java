package com.drughi.vyng;

import android.app.Application;

import com.drughi.vyng.dagger.AppComponent;
import com.drughi.vyng.dagger.AppModule;
import com.drughi.vyng.dagger.DaggerAppComponent;

public class VyngApp extends Application {

  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }
}
