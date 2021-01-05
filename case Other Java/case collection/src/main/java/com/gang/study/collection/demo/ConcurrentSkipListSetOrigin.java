package com.gang.study.collection.demo;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentSkipListSetOrigin<c> extends ConcurrentSkipListSet<c> {

    @Override
    public boolean removeAll(Collection<?> c) {
        // Override AbstractSet version to avoid unnecessary call to size()
        boolean modified = false;
        for (Object e : c)
            if (remove(e))
                modified = true;
        return modified;
    }
}
