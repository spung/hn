package com.hn.shared;

import com.hn.network.HnApiClientModule;
import com.hn.screen.itemdetail.ItemDetailActivity;
import com.hn.screen.items.ItemsActivity;
import com.hn.screen.items.TopItemsProviderModule;

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
    void inject(ItemDetailActivity activity);
}
