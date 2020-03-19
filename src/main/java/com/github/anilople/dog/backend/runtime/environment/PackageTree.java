package com.github.anilople.dog.backend.runtime.environment;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class PackageTree<DATA> {

  private final Map<String, DATA> map = new ConcurrentHashMap<>();

  public boolean exists(String packageName) {
    return map.containsKey(packageName);
  }

  public void ensureExists(String packageName) {
    if (!exists(packageName)) {
      throw new IllegalStateException(packageName + "不存在");
    }
  }

  public void ensureNotExists(String packageName) {
    if (exists(packageName)) {
      throw new IllegalStateException(packageName + "已经存在");
    }
  }

  public void add(String packageName, DATA data) {
    ensureNotExists(packageName);
    map.put(packageName, data);
  }

  public DATA get(String packageName) {
    ensureExists(packageName);
    return map.get(packageName);
  }

  public void forEach(BiConsumer<String, ? super DATA> action) {
    map.forEach(action);
  }

  public static class PackageNode<DATA> {

    private final PackageNode<DATA> parent;

    private final Set<PackageNode<DATA>> children;

    private final DATA data;

    public PackageNode(
        PackageNode<DATA> parent,
        Set<PackageNode<DATA>> children, DATA data) {
      this.parent = parent;
      this.children = children;
      this.data = data;
    }

    public PackageNode<DATA> getParent() {
      return parent;
    }

    public Set<PackageNode<DATA>> getChildren() {
      return children;
    }

    public DATA getData() {
      return data;
    }
  }
}

