package com.charge.controller.school;

import com.charge.util.JsonUtil;
import java.util.HashMap;
import java.util.Map;

public class LRUCache<K,V> {

  private Node<V> first;
  private Node<V> last;
  private int capacity = 5;//最大容量
  private int size;//当前容量
  private Map<K, Node<V>> caches = new HashMap<>(capacity);

  public V get(K k){
    Node<V> value = caches.get(k);
    if(value == null){
      return null;
    }
    if(last == value){
      return value.value;
    }
    if(first == value){
      Node<V> firstNext = first.next;
      Node<V> oldLast = last;
      oldLast.next = value;
      value.pre = oldLast;
      value.next = null;
      last = value;
      firstNext.pre = null;
      first = firstNext;
      return value.value;
    }
    Node<V> pre = value.pre;
    Node<V> next = value.next;
    pre.next = next;
    next.pre = pre;
    last.next = value;
    value.pre = last;
    value.next = null;
    last = value;
    return value.value;
  }

  public void put(K k, V v){
    if(size == 0){
      Node<V> node = new Node<>(v, null, null);
      caches.put(k, node);
      first = node;
      last = node;
      size++;
      return;
    }
    if(size < capacity){
      Node<V> oldLast = last;
      Node<V> node = new Node<>(v, oldLast, null);
      oldLast.next = node;
      last = node;
      caches.put(k, node);
      size++;
      return;
    }
    K removeKey = null;
    for(K key:caches.keySet()){
      if(caches.get(key) == first){
        removeKey = key;
        break;
      }
    }
    caches.remove(removeKey);
    first.next.pre = null;
    first = first.next;
    Node<V> node = new Node<>(v, last, null);
    last.next = node;
    last = node;
    caches.put(k, node);
  }

  public static void main(String[] args) {
    LRUCache<String, String> cache = new LRUCache<>();
    cache.put("1","a");
    cache.put("2","b");
    cache.put("3","c");
    cache.put("4","d");
    cache.put("5","e");
    cache.put("6","f");
    cache.put("7","g");
    cache.get("4");
    System.out.println(JsonUtil.toJson(cache));
  }

  class Node<V>{
    V value;
    Node<V> pre;
    Node<V> next;
    public Node(V v, Node<V> pre, Node<V> next){
      this.value = v;
      this.pre = pre;
      this.next = next;
    }
  }
}
