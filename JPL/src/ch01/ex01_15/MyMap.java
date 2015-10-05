package ch01.ex01_15;

import java.util.Map;
import java.util.HashMap;

public class MyMap implements ExtendedLookup {

	private final Map<String, Object> map = new HashMap<>();
	private final Object lock = new Object();

	@Override
	public Object find(String name) {
		synchronized (lock) {
			Object obj = null;
			if (map.containsKey(name)) {
				obj = map.get(name);
			}
			return obj;
		}
	}

	@Override
	public Object add(String name, Object value) {
		return map.put(name, value);
	}

	@Override
	public boolean remove(String name, Object value) {
		return map.remove(name, value);
	}

}
