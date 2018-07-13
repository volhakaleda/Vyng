package com.drughi.vyng.dagger;

import com.drughi.vyng.MainActivity;
import com.drughi.vyng.mvp.play.PlayController;
import com.drughi.vyng.mvp.search.SearchController;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class})
public interface AppComponent {

  void inject(MainActivity mainActivity);

  void inject(SearchController searchController);

  void inject(PlayController playController);

}
