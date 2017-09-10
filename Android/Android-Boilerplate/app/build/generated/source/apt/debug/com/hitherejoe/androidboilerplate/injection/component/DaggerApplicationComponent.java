package com.hitherejoe.androidboilerplate.injection.component;

import android.app.Application;
import android.content.Context;
import com.hitherejoe.androidboilerplate.AndroidBoilerplateApplication;
import com.hitherejoe.androidboilerplate.data.DataManager;
import com.hitherejoe.androidboilerplate.data.local.PreferencesHelper;
import com.hitherejoe.androidboilerplate.data.remote.AndroidBoilerplateService;
import com.hitherejoe.androidboilerplate.injection.module.ApplicationModule;
import com.hitherejoe.androidboilerplate.injection.module.ApplicationModule_DataManagerFactory;
import com.hitherejoe.androidboilerplate.injection.module.ApplicationModule_PreferencesHelperFactory;
import com.hitherejoe.androidboilerplate.injection.module.ApplicationModule_ProvideAndroidBoilerplateServiceFactory;
import com.hitherejoe.androidboilerplate.injection.module.ApplicationModule_ProvideApplicationFactory;
import com.hitherejoe.androidboilerplate.injection.module.ApplicationModule_ProvideContextFactory;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerApplicationComponent implements ApplicationComponent {
  private Provider<Context> provideContextProvider;
  private Provider<Application> provideApplicationProvider;
  private Provider<AndroidBoilerplateService> provideAndroidBoilerplateServiceProvider;
  private Provider<PreferencesHelper> preferencesHelperProvider;
  private Provider<DataManager> dataManagerProvider;

  private DaggerApplicationComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.provideContextProvider = ApplicationModule_ProvideContextFactory.create(builder.applicationModule);
    this.provideApplicationProvider = ApplicationModule_ProvideApplicationFactory.create(builder.applicationModule);
    this.provideAndroidBoilerplateServiceProvider = ScopedProvider.create(ApplicationModule_ProvideAndroidBoilerplateServiceFactory.create(builder.applicationModule));
    this.preferencesHelperProvider = ScopedProvider.create(ApplicationModule_PreferencesHelperFactory.create(builder.applicationModule, provideContextProvider));
    this.dataManagerProvider = ScopedProvider.create(ApplicationModule_DataManagerFactory.create(builder.applicationModule, provideAndroidBoilerplateServiceProvider, preferencesHelperProvider));
  }

  @Override
  public void inject(AndroidBoilerplateApplication androidBoilerplateApplication) {  
    MembersInjectors.noOp().injectMembers(androidBoilerplateApplication);
  }

  @Override
  public Context context() {  
    return provideContextProvider.get();
  }

  @Override
  public Application application() {  
    return provideApplicationProvider.get();
  }

  @Override
  public AndroidBoilerplateService androidBoilerplateService() {  
    return provideAndroidBoilerplateServiceProvider.get();
  }

  @Override
  public PreferencesHelper preferencesHelper() {  
    return preferencesHelperProvider.get();
  }

  @Override
  public DataManager dataManager() {  
    return dataManagerProvider.get();
  }

  public static final class Builder {
    private ApplicationModule applicationModule;
  
    private Builder() {  
    }
  
    public ApplicationComponent build() {  
      if (applicationModule == null) {
        throw new IllegalStateException("applicationModule must be set");
      }
      return new DaggerApplicationComponent(this);
    }
  
    public Builder applicationModule(ApplicationModule applicationModule) {  
      if (applicationModule == null) {
        throw new NullPointerException("applicationModule");
      }
      this.applicationModule = applicationModule;
      return this;
    }
  }
}

