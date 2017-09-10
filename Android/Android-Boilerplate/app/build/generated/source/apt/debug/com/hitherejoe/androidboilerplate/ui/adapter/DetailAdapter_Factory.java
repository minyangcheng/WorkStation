package com.hitherejoe.androidboilerplate.ui.adapter;

import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DetailAdapter_Factory implements Factory<DetailAdapter> {
  private final MembersInjector<DetailAdapter> membersInjector;

  public DetailAdapter_Factory(MembersInjector<DetailAdapter> membersInjector) {  
    assert membersInjector != null;
    this.membersInjector = membersInjector;
  }

  @Override
  public DetailAdapter get() {  
    DetailAdapter instance = new DetailAdapter();
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<DetailAdapter> create(MembersInjector<DetailAdapter> membersInjector) {  
    return new DetailAdapter_Factory(membersInjector);
  }
}

