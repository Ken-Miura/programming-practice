/* Copyright (C) 2016 Ken Miura */
package ch21.ex21_03;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ken Miura
 *
 */
public final class WeakValueMap<K, V> {

	private final Map<K, WeakReference<V>> map = new HashMap<>();
	
	public int size() {
		return map.size();
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public boolean containsKey (K key) {
		return map.containsKey(key);
	}
	
	public V get (K key) {
		WeakReference<V> value = map.get(key);
		return value.get();
	}
	
	public V put (K key, V value) {
		WeakReference<V> oldValue = map.put(key, new WeakReference<V>(value));
		return oldValue.get();
	}
	
	public V remove (K key) {
		WeakReference<V> value = map.remove(key);
		return value.get();
	}
	
	public void clear() {
		map.clear();
	}
}
