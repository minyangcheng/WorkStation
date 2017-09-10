package com.hitherejoe.androidboilerplate.injection.component;

import android.app.LauncherActivity;
import com.hitherejoe.androidboilerplate.data.DataManager;
import com.hitherejoe.androidboilerplate.injection.module.ActivityModule;
import com.hitherejoe.androidboilerplate.ui.activity.CharacterActivity;
import com.hitherejoe.androidboilerplate.ui.activity.DetailActivity;
import com.hitherejoe.androidboilerplate.ui.activity.MainActivity;
import com.hitherejoe.androidboilerplate.ui.activity.MainActivity_MembersInjector;
import com.hitherejoe.androidboilerplate.ui.adapter.CharacterAdapter;
import com.hitherejoe.androidboilerplate.ui.adapter.CharacterAdapter_Factory;
import com.hitherejoe.androidboilerplate.ui.fragment.DetailFragment;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerActivityComponent implements ActivityComponent {
  private Provider<DataManager> dataManagerProvider;
  private Provider<CharacterAdapter> characterAdapterProvider;
  private MembersInjector<MainActivity> mainActivityMembersInjector;

  private DaggerActivityComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.dataManagerProvider = new Factory<DataManager>() {
      private final ApplicationComponent applicationComponent = builder.applicationComponent;
      @Override public DataManager get() {
        DataManager provided = applicationComponent.dataManager();
        if (provided == null) {
          throw new NullPointerException("Cannot return null from a non-@Nullable component method");
        }
        return provided;
      }
    };
    this.characterAdapterProvider = CharacterAdapter_Factory.create((MembersInjector) MembersInjectors.noOp());
    this.mainActivityMembersInjector = MainActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), dataManagerProvider, characterAdapterProvider);
  }

  @Override
  public void inject(LauncherActivity launcherActivity) {  
    MembersInjectors.noOp().injectMembers(launcherActivity);
  }

  @Override
  public void inject(MainActivity mainActivity) {  
    mainActivityMembersInjector.injectMembers(mainActivity);
  }

  @Override
  public void inject(CharacterActivity characterActivity) {  
    MembersInjectors.noOp().injectMembers(characterActivity);
  }

  @Override
  public void inject(DetailActivity detailActivity) {  
    MembersInjectors.noOp().injectMembers(detailActivity);
  }

  @Override
  public void inject(DetailFragment detailFragment) {  
    MembersInjectors.noOp().injectMembers(detailFragment);
  }

  public static final class Builder {
    private ActivityModule activityModule;
    private ApplicationComponent applicationComponent;
  
    private Builder() {  
    }
  
    public ActivityComponent build() {  
      if (activityModule == null) {
        throw new IllegalStateException("activityModule must be set");
      }
      if (applicationComponent == null) {
        throw new IllegalStateException("applicationComponent must be set");
      }
      return new DaggerActivityComponent(this);
    }
  
    public Builder activityModule(ActivityModule activityModule) {  
      if (activityModule == null) {
        throw new NullPointerException("activityModule");
      }
      this.activityModule = activityModule;
      return this;
    }
  
    public Builder applicationComponent(ApplicationComponent applicationComponent) {  
      if (applicationComponent == null) {
        throw new NullPointerException("applicationComponent");
      }
      this.applicationComponent = applicationComponent;
      return this;
    }
  }
}

