package com.hitherejoe.androidboilerplate.ui.adapter;

import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class CharacterAdapter_Factory implements Factory<CharacterAdapter> {
  private final MembersInjector<CharacterAdapter> membersInjector;

  public CharacterAdapter_Factory(MembersInjector<CharacterAdapter> membersInjector) {  
    assert membersInjector != null;
    this.membersInjector = membersInjector;
  }

  @Override
  public CharacterAdapter get() {  
    CharacterAdapter instance = new CharacterAdapter();
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<CharacterAdapter> create(MembersInjector<CharacterAdapter> membersInjector) {  
    return new CharacterAdapter_Factory(membersInjector);
  }
}

