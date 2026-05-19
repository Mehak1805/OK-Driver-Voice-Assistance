package com.okdriver.voiceassistant;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class LLMRepository_Factory implements Factory<LLMRepository> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<SecurePreferencesProvider> securePreferencesProvider;

  public LLMRepository_Factory(Provider<OkHttpClient> okHttpClientProvider,
      Provider<SecurePreferencesProvider> securePreferencesProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.securePreferencesProvider = securePreferencesProvider;
  }

  @Override
  public LLMRepository get() {
    return newInstance(okHttpClientProvider.get(), securePreferencesProvider.get());
  }

  public static LLMRepository_Factory create(Provider<OkHttpClient> okHttpClientProvider,
      Provider<SecurePreferencesProvider> securePreferencesProvider) {
    return new LLMRepository_Factory(okHttpClientProvider, securePreferencesProvider);
  }

  public static LLMRepository newInstance(OkHttpClient okHttpClient,
      SecurePreferencesProvider securePreferences) {
    return new LLMRepository(okHttpClient, securePreferences);
  }
}
