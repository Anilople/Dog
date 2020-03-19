package com.github.anilople.dog.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class MapUtil {

  /**
   * 根据key来进行合并. 注意在a和b中不能有相同的key
   *
   * @throws IllegalStateException 如果存在冲突的key
   */
  public static <K, V> Map<K, V> union(Map<K, V> a, Map<K, V> b) {
    // 检查key是否冲突
    Set<K> commonKeys = SetUtil.intersection(a.keySet(), b.keySet());
    if (commonKeys.size() > 0) {
      throw new IllegalStateException("key存在冲突:" + commonKeys);
    }

    Map<K, V> c = new HashMap<>();
    c.putAll(a);
    c.putAll(b);
    return Collections.unmodifiableMap(c);
  }

  /**
   * 根据key做差，不关心value的值
   */
  public static <K, V> Map<K, V> differenceByKey(Map<K, V> a, Map<K, V> b) {
    Map<K, V> c = new HashMap<>();
    for (Map.Entry<K, V> entry : a.entrySet()) {
      K key = entry.getKey();
      V value = entry.getValue();
      if (!b.containsKey(key)) {
        c.put(key, value);
      }
    }
    return Collections.unmodifiableMap(c);
  }

  /**
   * @param keyWrapper key改变器
   * @param <K1>       旧key的类型
   * @param <K2>       新key的类型
   * @return key改变，value不变的新{@link Map}
   */
  public static <K1, K2, V> Map<K2, V> transferKey(Map<K1, V> map, Function<K1, K2> keyWrapper) {
    Map<K2, V> newMap = new HashMap<>();
    for (Map.Entry<K1, V> entry : map.entrySet()) {
      K1 oldKey = entry.getKey();
      K2 newKey = keyWrapper.apply(oldKey);
      newMap.put(newKey, entry.getValue());
    }
    return Collections.unmodifiableMap(newMap);
  }

  /**
   * 不改变key，仅仅对value进行改变
   *
   * @param valueTransfer 作用在旧value上的函数
   * @param <V1>          旧value的类型
   * @param <V2>          新value的类型
   * @return 改变value后的map
   */
  public static <K, V1, V2> Map<K, V2> transferValue(Map<K, V1> map,
      Function<V1, V2> valueTransfer) {
    Map<K, V2> newMap = new HashMap<>();
    for (Map.Entry<K, V1> entry : map.entrySet()) {
      V1 oldValue = entry.getValue();
      V2 newValue = valueTransfer.apply(oldValue);
      newMap.put(entry.getKey(), newValue);
    }
    return Collections.unmodifiableMap(newMap);
  }

  /**
   * 根据key来过滤map中的键值对
   *
   * @param predicate 过滤函数
   */
  public static <K, V> Map<K, V> filterByKey(Map<K, V> map, Predicate<K> predicate) {
    Map<K, V> newMap = new HashMap<>();
    for (Map.Entry<K, V> entry : map.entrySet()) {
      K key = entry.getKey();
      if (predicate.test(key)) {
        newMap.put(key, entry.getValue());
      }
    }
    return Collections.unmodifiableMap(newMap);
  }
}
