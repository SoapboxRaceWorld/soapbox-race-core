package com.soapboxrace.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.soapboxrace.jaxb.http.PerformancePartTrans;

public class ListDiffer
{
    public static class ProxyItem<T>
    {
        private final T item;
        private final List<T> list;

        public ProxyItem(T item, List<T> list)
        {
            Preconditions.checkNotNull(item, "item");
            Preconditions.checkNotNull(list, "list");
            
            this.list = list;
            this.item = item;
        }

        public T getItem()
        {
            return item;
        }

        public List<T> getList()
        {
            return list;
        }
    }

    public static void main(String[] args)
    {
        PerformancePartTrans part1 = new PerformancePartTrans();
        PerformancePartTrans part1_clone = new PerformancePartTrans();
        part1.setPerformancePartAttribHash(1333333337);
        part1_clone.setPerformancePartAttribHash(1333333337);

        PerformancePartTrans part2 = new PerformancePartTrans();
        part2.setPerformancePartAttribHash(1000);

        List<PerformancePartTrans> list1 = new ArrayList<>();
        list1.add(part1);
        list1.add(part2);

        PerformancePartTrans newPart = new PerformancePartTrans();
        newPart.setPerformancePartAttribHash(1337);
        
        List<PerformancePartTrans> list2 = new ArrayList<>();
        list2.add(part1_clone);
        list2.add(newPart);

        ListDifferences<ProxyItem<PerformancePartTrans>> listDifferences = getDifferencesByKey(list1, list2, PerformancePartTrans::getPerformancePartAttribHash);
        System.out.println();
        System.out.println();
        System.out.println();
        
        System.out.println("Part Differences");
        System.out.println("    Original Parts: " + list1);
        System.out.println("    New Parts: " + list2);
        System.out.println();
        System.out.println("    Added: " + listDifferences.added.stream().map(ProxyItem::getItem).collect(Collectors.toList()));
        System.out.println("    Kept: " + listDifferences.kept.stream().map(ProxyItem::getItem).collect(Collectors.toList()));
        System.out.println("    Removed: " + listDifferences.removed.stream().map(ProxyItem::getItem).collect(Collectors.toList()));
        
//        System.out.println(String.format("    Added: %s", valueDifferences.added.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
//        System.out.println(String.format("    Kept: %s", valueDifferences.kept.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
//        System.out.println(String.format("    Removed: %s", valueDifferences.removed.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
//        System.out.println();
//        System.out.println("Key differences");
//        System.out.println(String.format("    Added: %s", keyDifferences.added.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
//        System.out.println(String.format("    Kept: %s", keyDifferences.kept.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
//        System.out.println(String.format("    Removed: %s", keyDifferences.removed.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
    }

    public static class ListDifferences<T>
    {
        private final Collection<T> kept;
        private final Collection<T> added;
        private final Collection<T> removed;

        ListDifferences(Collection<T> kept, Collection<T> added, Collection<T> removed)
        {
            this.kept = kept;
            this.added = added;
            this.removed = removed;
        }

        public Collection<T> getAdded()
        {
            return added;
        }

        public Collection<T> getKept()
        {
            return kept;
        }

        public Collection<T> getRemoved()
        {
            return removed;
        }

        @Override
        public String toString()
        {
            return String.format("ListDifferences{added=%s/kept=%s/removed=%s}", this.getAdded(), this.getKept(), this.getRemoved());
        }
    }

    public static <T, K> ListDifferences<ProxyItem<T>> getDifferencesByKey(Collection<T> first, Collection<T> second, Function<T, K> keyFunction)
    {
        List<K> firstKeys = first.stream().map(keyFunction).collect(Collectors.toList());
        List<K> secondKeys = second.stream().map(keyFunction).collect(Collectors.toList());
        ListDifferences<ProxyItem<K>> keyDifferences = getDifferences(firstKeys, secondKeys);
        ListDifferences<ProxyItem<T>> valueDifferences = getDifferences(first, second);

//        System.out.println("Original differences");
//        System.out.println(String.format("    Added: %s", valueDifferences.added.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
//        System.out.println(String.format("    Kept: %s", valueDifferences.kept.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
//        System.out.println(String.format("    Removed: %s", valueDifferences.removed.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
//        System.out.println();
//        System.out.println("Key differences");
//        System.out.println(String.format("    Added: %s", keyDifferences.added.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
//        System.out.println(String.format("    Kept: %s", keyDifferences.kept.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
//        System.out.println(String.format("    Removed: %s", keyDifferences.removed.stream().map(ProxyItem::getItem).collect(Collectors.toList())));
        
        List<ProxyItem<T>> added = new ArrayList<>();
        List<ProxyItem<T>> kept = new ArrayList<>();
        List<ProxyItem<T>> removed = new ArrayList<>();

        for (ProxyItem<K> addedKey : keyDifferences.added)
        {
            T item;
            
            for (T iFirst : second)
            {
//                System.out.println(String.format("Trying added key %s with item %s", addedKey.item, iFirst));
                
                K itemKey = keyFunction.apply(iFirst);
//                System.out.println(String.format("ItemKey = %s", itemKey));
                
                if (itemKey.equals(addedKey.item))
                {
//                    System.out.println("Found item!");
                    item = iFirst;
                    added.add(new ProxyItem<>(item, Lists.newArrayList(second)));
                    break;
                }
            }
            
//            System.out.println(String.format("key %s -> %s", addedKey.item, item));
        }

        for (ProxyItem<K> keptKey : keyDifferences.kept)
        {
            T item;

            for (T iFirst : second)
            {
//                System.out.println(String.format("Trying kept key %s with item %s", keptKey.item, iFirst));

                K itemKey = keyFunction.apply(iFirst);
//                System.out.println(String.format("ItemKey = %s", itemKey));
//                System.out.println(String.format("ItemKey class = %s, keptKey class = %s", itemKey.getClass(), keptKey.item.getClass()));

                if (itemKey.equals(keptKey.item))
                {
//                    System.out.println("Found item!");
                    item = iFirst;
                    kept.add(new ProxyItem<>(item, Lists.newArrayList(second)));
                    break;
                }
            }

//            System.out.println(String.format("key %s -> %s", keptKey.item, item));
        }

        for (ProxyItem<K> removedKey : keyDifferences.removed)
        {
            T item = null;

            for (T iFirst : first)
            {
//                System.out.println(String.format("In list 1 - Trying removed key %s with item %s", removedKey.item, iFirst));

                K itemKey = keyFunction.apply(iFirst);
//                System.out.println(String.format("ItemKey = %s", itemKey));

                if (itemKey.equals(removedKey.item))
                {
//                    System.out.println("Found item!");
                    item = iFirst;
                    removed.add(new ProxyItem<>(item, Lists.newArrayList(first)));
                    break;
                }
            }
            
            if (item == null)
            {
                for (T iFirst : second)
                {
//                    System.out.println(String.format("In list 2 - Trying removed key %s with item %s", removedKey.item, iFirst));

                    K itemKey = keyFunction.apply(iFirst);
//                    System.out.println(String.format("ItemKey = %s", itemKey));

                    if (itemKey.equals(removedKey.item))
                    {
//                        System.out.println("Found item!");
                        item = iFirst;
                        removed.add(new ProxyItem<>(item, Lists.newArrayList(second)));
                        break;
                    }
                }
            }

//            System.out.println(String.format("key %s -> %s", removedKey.item, item));
        }

        return new ListDifferences<>(kept, added, removed);
//        return new ListDifferences<>(kept, added, removed);
    }

    public static <T> ListDifferences<ProxyItem<T>> getDifferences(Collection<T> first, Collection<T> second)
    {
        List<T> clonedFirst = clone(first);
        List<T> clonedSecond = clone(second);

        List<T> alreadyThere = clone(clonedSecond);
        alreadyThere.retainAll(clonedFirst);
        List<T> added = clone(clonedSecond);
        added.removeAll(alreadyThere);
        List<T> removed = clone(clonedFirst);
        removed.removeAll(alreadyThere);

        List<ProxyItem<T>> addedProxies = new ArrayList<>();
        List<ProxyItem<T>> keptProxies = new ArrayList<>();
        List<ProxyItem<T>> removedProxies = new ArrayList<>();
        
        for (T addedItem : added)
        {
            addedProxies.add(new ProxyItem<>(addedItem, clonedFirst.contains(addedItem) ? clonedFirst : clonedSecond));
        }

        for (T keptItem : alreadyThere)
        {
            keptProxies.add(new ProxyItem<>(keptItem, clonedFirst));
        }

        for (T removedItem : removed)
        {
            removedProxies.add(new ProxyItem<>(removedItem, clonedFirst.contains(removedItem) ? clonedFirst : clonedSecond));
        }
        
        return new ListDifferences<>(keptProxies, addedProxies, removedProxies);
//        return new ListDifferences<>(alreadyThere, added, removed);
    }

    private static <T> List<T> clone(Collection<T> collection)
    {
        ArrayList<T> list = new ArrayList<>();
        list.addAll(collection);

        return list;
    }
}
