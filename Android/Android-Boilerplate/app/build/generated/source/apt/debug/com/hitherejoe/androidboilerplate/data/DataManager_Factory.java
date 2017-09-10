package com.hitherejoe.androidboilerplate.data;

import com.hitherejoe.androidboilerplate.data.local.PreferencesHelper;
import com.hitherejoe.androidboilerplate.data.remote.AndroidBoilerplateService;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DataManager_Factory implements Factory<DataManager> {
  private final Provider<AndroidBoilerplateService> watchTowerServiceProvider;
  private final Provider<PreferencesHelper> preferencesHelperProvider;

  public DataManager_Factory(Provider<AndroidBoilerplateService> watchTowerServiceProvider, Provider<PreferencesHelper> preferencesHelperProvider) {  
    assert watchTowerServiceProvider != null;
    this.watchTowerServiceProvider = watchTowerServiceProvider;
    assert preferencesHelperProvider != null;
    this.preferencesHelperProvider = preferencesHelperProvider;
  }

  @Override
  public DataManager get() {  
    return new DataManager(watchTowerServiceProvider.get(), preferencesHelperProvider.get());
  }

  public static Factory<DataManager> create(Provider<AndroidBoilerplateService> watchTowerServiceProvider, Provider<PreferencesHelper> preferencesHelperProvider) {  
    return new DataManager_Factory(watchTowerServiceProvider, preferencesHelperProvider);
  }
}

