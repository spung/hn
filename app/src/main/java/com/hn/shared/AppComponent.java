package com.hn.shared;

import com.hn.network.HnApiClientModule;
import com.hn.items.ItemsActivity;
import com.hn.items.TopItemsProviderModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by stevenpungdumri on 6/28/17.
 */

@Singleton
@Component(modules = {
    HnApiClientModule.class,
    TopItemsProviderModule.class
})
public interface AppComponent {
    void inject(ItemsActivity activity);
}
