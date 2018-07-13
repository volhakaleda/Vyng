package com.drughi.vyng.dagger;

import com.drughi.vyng.MainActivity;
import com.drughi.vyng.mvp.SearchController;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class})
public interface AppComponent {

  void inject(MainActivity mainActivity);

  void inject(SearchController homeController);
}
