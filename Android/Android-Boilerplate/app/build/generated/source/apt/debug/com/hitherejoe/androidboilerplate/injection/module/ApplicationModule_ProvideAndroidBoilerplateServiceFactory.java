package com.hitherejoe.androidboilerplate.injection.module;

import com.hitherejoe.androidboilerplate.data.remote.AndroidBoilerplateService;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ApplicationModule_ProvideAndroidBoilerplateServiceFactory implements Factory<AndroidBoilerplateService> {
  private final ApplicationModule module;

  public ApplicationModule_ProvideAndroidBoilerplateServiceFactory(ApplicationModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public AndroidBoilerplateService get() {  
    AndroidBoilerplateService provided = module.provideAndroidBoilerplateService();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<AndroidBoilerplateService> create(ApplicationModule module) {  
    return new ApplicationModule_ProvideAndroidBoilerplateServiceFactory(module);
  }
}

