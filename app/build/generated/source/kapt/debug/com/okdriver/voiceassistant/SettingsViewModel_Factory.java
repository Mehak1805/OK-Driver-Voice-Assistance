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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<SecurePreferencesProvider> securePreferencesProvider;

  private final Provider<ConversationDao> conversationDaoProvider;

  public SettingsViewModel_Factory(Provider<SecurePreferencesProvider> securePreferencesProvider,
      Provider<ConversationDao> conversationDaoProvider) {
    this.securePreferencesProvider = securePreferencesProvider;
    this.conversationDaoProvider = conversationDaoProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(securePreferencesProvider.get(), conversationDaoProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<SecurePreferencesProvider> securePreferencesProvider,
      Provider<ConversationDao> conversationDaoProvider) {
    return new SettingsViewModel_Factory(securePreferencesProvider, conversationDaoProvider);
  }

  public static SettingsViewModel newInstance(SecurePreferencesProvider securePreferences,
      ConversationDao conversationDao) {
    return new SettingsViewModel(securePreferences, conversationDao);
  }
}
