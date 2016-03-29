/* Copyright (C) 2016 Ken Miura */
package ch21.ex21_03;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Ken Miura
 *
 */
public final class WeakValueMap<K, V> implements Map<K, V> {

	private final Map<K, WeakReference<V>> map;
	
	public WeakValueMap () {
		map = new HashMap<>();
	}

	public WeakValueMap (int initialCapacity, float loadFactor) {
		map = new HashMap<>(initialCapacity, loadFactor);
	}
	
	public WeakValueMap (int initialCapacity) {
		map = new HashMap<>(initialCapacity);
	}
	
	public WeakValueMap(Map<? extends K, ? extends V> otherMap) {
		Set<? extends java.util.Map.Entry<? extends K, ? extends V>> entries = otherMap.entrySet();
		Map<K, WeakReference<V>> m = new HashMap<>();
		for (final java.util.Map.Entry<? extends K, ? extends V> entry: entries) {
			m.put(entry.getKey(), new WeakReference<V>(entry.getValue()));
		}
		map = new HashMap<>(m);
	}
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		WeakReference<V> value = map.get(key);
		return value.get();
	}

	@Override
	public V put(K key, V value) {
		WeakReference<V> oldValue = map.put(key, new WeakReference<V>(value));
		return oldValue.get();
	}

	@Override
	public V remove(Object key) {
		WeakReference<V> oldValue = map.remove(key);
		return oldValue.get();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> otherMap) {
		Set<? extends java.util.Map.Entry<? extends K, ? extends V>> entries = otherMap.entrySet();
		Map<K, WeakReference<V>> m = new HashMap<>();
		for (final java.util.Map.Entry<? extends K, ? extends V> entry: entries) {
			m.put(entry.getKey(), new WeakReference<V>(entry.getValue()));
		}
		map.putAll(m);
	}	
	
	@Override
	public Collection<V> values() {
		Set<java.util.Map.Entry<K, WeakReference<V>>> entries = map.entrySet();
		Map<K, V> m = new HashMap<>();
		for (final java.util.Map.Entry<K, WeakReference<V>> entry :entries) {
			m.put(entry.getKey(), entry.getValue().get());
		}
		return m.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<java.util.Map.Entry<K, WeakReference<V>>> entries = map.entrySet();
		Map<K, V> m = new HashMap<>();
		for (final java.util.Map.Entry<K, WeakReference<V>> entry :entries) {
			m.put(entry.getKey(), entry.getValue().get());
		}
		return m.entrySet();
	}
	
}