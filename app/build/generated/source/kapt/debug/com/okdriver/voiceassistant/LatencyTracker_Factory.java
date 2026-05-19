package com.okdriver.voiceassistant;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class LatencyTracker_Factory implements Factory<LatencyTracker> {
  @Override
  public LatencyTracker get() {
    return newInstance();
  }

  public static LatencyTracker_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static LatencyTracker newInstance() {
    return new LatencyTracker();
  }

  private static final class InstanceHolder {
    private static final LatencyTracker_Factory INSTANCE = new LatencyTracker_Factory();
  }
}
