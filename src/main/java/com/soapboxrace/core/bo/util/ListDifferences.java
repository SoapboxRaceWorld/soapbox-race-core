/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListDifferences<T> {
    private final Collection<T> kept;
    private final Collection<T> added;
    private final Collection<T> removed;

    private ListDifferences(Collection<T> kept, Collection<T> added, Collection<T> removed) {
        this.kept = kept;
        this.added = added;
        this.removed = removed;
    }

    public static <T> ListDifferences<T> getDifferences(Collection<T> first, Collection<T> second) {
        List<T> clonedFirst = clone(first);
        List<T> clonedSecond = clone(second);

        List<T> alreadyThere = clone(clonedSecond);
        alreadyThere.retainAll(clonedFirst);
        List<T> added = clone(clonedSecond);
        added.removeAll(alreadyThere);
        List<T> removed = clone(clonedFirst);
        removed.removeAll(alreadyThere);

        return new ListDifferences<>(alreadyThere, added, removed);
    }

    private static <T> List<T> clone(Collection<T> collection) {
        return new ArrayList<>(collection);
    }

    public Collection<T> getAdded() {
        return added;
    }

    public Collection<T> getKept() {
        return kept;
    }

    public Collection<T> getRemoved() {
        return removed;
    }

    public boolean hasDifferences() {
        return !added.isEmpty() || !removed.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("ListDifferences{added=%s/kept=%s/removed=%s}", this.getAdded(), this.getKept(),
                this.getRemoved());
    }
}