package com.okdriver.voiceassistant;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class BootCompletedReceiver_MembersInjector implements MembersInjector<BootCompletedReceiver> {
  private final Provider<ServiceIntentProvider> serviceIntentProvider;

  public BootCompletedReceiver_MembersInjector(
      Provider<ServiceIntentProvider> serviceIntentProvider) {
    this.serviceIntentProvider = serviceIntentProvider;
  }

  public static MembersInjector<BootCompletedReceiver> create(
      Provider<ServiceIntentProvider> serviceIntentProvider) {
    return new BootCompletedReceiver_MembersInjector(serviceIntentProvider);
  }

  @Override
  public void injectMembers(BootCompletedReceiver instance) {
    injectServiceIntentProvider(instance, serviceIntentProvider.get());
  }

  @InjectedFieldSignature("com.okdriver.voiceassistant.BootCompletedReceiver.serviceIntentProvider")
  public static void injectServiceIntentProvider(BootCompletedReceiver instance,
      ServiceIntentProvider serviceIntentProvider) {
    instance.serviceIntentProvider = serviceIntentProvider;
  }
}
