package com.hn.shared;

import com.hn.data.HNItemType;
import com.hn.data.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stevenpungdumri on 7/6/17.
 */

public class MockItem {
    public static List<Item> createMockItems(List<Long> ids) {
        List<Item> items = new ArrayList<>();
        for(Long id : ids) {
            items.add(createMockItem(id));
        }

        return items;
    }

    public static List<Item> createMockItems(int count) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add(createMockItem(i));
        }

        return items;
    }

    public static Item createMockItem(long id) {
        return new Item(
            id,
            false,
            HNItemType.values()[0],
            "test",
            0,
            "text",
            false,
            1,
            1,
            new ArrayList<Long>(),
            "test",
            1,
            "test",
            new long[] {10},
            1,
            new ArrayList<Item>(),
            2
        );
    }
}
