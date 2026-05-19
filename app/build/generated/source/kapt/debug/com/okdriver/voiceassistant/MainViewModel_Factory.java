package com.okdriver.voiceassistant;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<AppDatabase> databaseProvider;

  public MainViewModel_Factory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(databaseProvider.get());
  }

  public static MainViewModel_Factory create(Provider<AppDatabase> databaseProvider) {
    return new MainViewModel_Factory(databaseProvider);
  }

  public static MainViewModel newInstance(AppDatabase database) {
    return new MainViewModel(database);
  }
}
