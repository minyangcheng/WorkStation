package com.hitherejoe.androidboilerplate.ui.activity;

import com.hitherejoe.androidboilerplate.data.DataManager;
import com.hitherejoe.androidboilerplate.ui.adapter.CharacterAdapter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final MembersInjector<BaseActivity> supertypeInjector;
  private final Provider<DataManager> mDataManagerAndMDataManager_1Provider;
  private final Provider<CharacterAdapter> mCharacterAdapterProvider;

  public MainActivity_MembersInjector(MembersInjector<BaseActivity> supertypeInjector, Provider<DataManager> mDataManagerAndMDataManager_1Provider, Provider<CharacterAdapter> mCharacterAdapterProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert mDataManagerAndMDataManager_1Provider != null;
    this.mDataManagerAndMDataManager_1Provider = mDataManagerAndMDataManager_1Provider;
    assert mCharacterAdapterProvider != null;
    this.mCharacterAdapterProvider = mCharacterAdapterProvider;
  }

  @Override
  public void injectMembers(MainActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.mDataManager = mDataManagerAndMDataManager_1Provider.get();
    instance.mDataManager_1 = mDataManagerAndMDataManager_1Provider.get();
    instance.mCharacterAdapter = mCharacterAdapterProvider.get();
  }

  public static MembersInjector<MainActivity> create(MembersInjector<BaseActivity> supertypeInjector, Provider<DataManager> mDataManagerAndMDataManager_1Provider, Provider<CharacterAdapter> mCharacterAdapterProvider) {  
      return new MainActivity_MembersInjector(supertypeInjector, mDataManagerAndMDataManager_1Provider, mCharacterAdapterProvider);
  }
}

