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
public final class HistoryViewModel_Factory implements Factory<HistoryViewModel> {
  private final Provider<ConversationDao> conversationDaoProvider;

  public HistoryViewModel_Factory(Provider<ConversationDao> conversationDaoProvider) {
    this.conversationDaoProvider = conversationDaoProvider;
  }

  @Override
  public HistoryViewModel get() {
    return newInstance(conversationDaoProvider.get());
  }

  public static HistoryViewModel_Factory create(Provider<ConversationDao> conversationDaoProvider) {
    return new HistoryViewModel_Factory(conversationDaoProvider);
  }

  public static HistoryViewModel newInstance(ConversationDao conversationDao) {
    return new HistoryViewModel(conversationDao);
  }
}
