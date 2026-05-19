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
public final class VoiceAssistantEventBus_Factory implements Factory<VoiceAssistantEventBus> {
  @Override
  public VoiceAssistantEventBus get() {
    return newInstance();
  }

  public static VoiceAssistantEventBus_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static VoiceAssistantEventBus newInstance() {
    return new VoiceAssistantEventBus();
  }

  private static final class InstanceHolder {
    private static final VoiceAssistantEventBus_Factory INSTANCE = new VoiceAssistantEventBus_Factory();
  }
}
