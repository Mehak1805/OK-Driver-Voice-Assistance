package com.okdriver.voiceassistant;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class ServiceIntentProvider_Factory implements Factory<ServiceIntentProvider> {
  @Override
  public ServiceIntentProvider get() {
    return newInstance();
  }

  public static ServiceIntentProvider_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ServiceIntentProvider newInstance() {
    return new ServiceIntentProvider();
  }

  private static final class InstanceHolder {
    private static final ServiceIntentProvider_Factory INSTANCE = new ServiceIntentProvider_Factory();
  }
}
