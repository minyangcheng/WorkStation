package com.hitherejoe.androidboilerplate.injection.module;

import com.hitherejoe.androidboilerplate.data.DataManager;
import com.hitherejoe.androidboilerplate.data.local.PreferencesHelper;
import com.hitherejoe.androidboilerplate.data.remote.AndroidBoilerplateService;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ApplicationModule_DataManagerFactory implements Factory<DataManager> {
  private final ApplicationModule module;
  private final Provider<AndroidBoilerplateService> watchTowerServiceProvider;
  private final Provider<PreferencesHelper> preferencesHelperProvider;

  public ApplicationModule_DataManagerFactory(ApplicationModule module, Provider<AndroidBoilerplateService> watchTowerServiceProvider, Provider<PreferencesHelper> preferencesHelperProvider) {  
    assert module != null;
    this.module = module;
    assert watchTowerServiceProvider != null;
    this.watchTowerServiceProvider = watchTowerServiceProvider;
    assert preferencesHelperProvider != null;
    this.preferencesHelperProvider = preferencesHelperProvider;
  }

  @Override
  public DataManager get() {  
    DataManager provided = module.dataManager(watchTowerServiceProvider.get(), preferencesHelperProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<DataManager> create(ApplicationModule module, Provider<AndroidBoilerplateService> watchTowerServiceProvider, Provider<PreferencesHelper> preferencesHelperProvider) {  
    return new ApplicationModule_DataManagerFactory(module, watchTowerServiceProvider, preferencesHelperProvider);
  }
}

