package com.hitherejoe.androidboilerplate.injection.module;

import android.content.Context;
import com.hitherejoe.androidboilerplate.data.local.PreferencesHelper;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ApplicationModule_PreferencesHelperFactory implements Factory<PreferencesHelper> {
  private final ApplicationModule module;
  private final Provider<Context> contextProvider;

  public ApplicationModule_PreferencesHelperFactory(ApplicationModule module, Provider<Context> contextProvider) {  
    assert module != null;
    this.module = module;
    assert contextProvider != null;
    this.contextProvider = contextProvider;
  }

  @Override
  public PreferencesHelper get() {  
    PreferencesHelper provided = module.preferencesHelper(contextProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<PreferencesHelper> create(ApplicationModule module, Provider<Context> contextProvider) {  
    return new ApplicationModule_PreferencesHelperFactory(module, contextProvider);
  }
}

