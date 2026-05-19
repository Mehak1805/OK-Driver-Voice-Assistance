package com.okdriver.voiceassistant;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class WakeWordDetector_Factory implements Factory<WakeWordDetector> {
  private final Provider<Context> contextProvider;

  public WakeWordDetector_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WakeWordDetector get() {
    return newInstance(contextProvider.get());
  }

  public static WakeWordDetector_Factory create(Provider<Context> contextProvider) {
    return new WakeWordDetector_Factory(contextProvider);
  }

  public static WakeWordDetector newInstance(Context context) {
    return new WakeWordDetector(context);
  }
}
