/* Copyright (C) 2016 Ken Miura */
package interpret;

import java.util.Hashtable;
import java.util.Objects;

/**
 * @author Ken Miura
 *
 */
final class WrappedHashtable<K, V> extends Hashtable<K, V> {

	private static final Object nullObject = new Object(); 
	
	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -8373646536497435476L;
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized V put(K key, V value) {
		Objects.requireNonNull(key, "key must not be null.");
		if (value == null) {
			return super.put(key, (V)nullObject);
		}
		return super.put(key, value);
	}
	
	@Override
	public synchronized V get(Object key) {
		Objects.requireNonNull(key, "key must not be null.");
		if (super.get(key) == nullObject) {
			return null;
		}
		return super.get(key);
	}
}
