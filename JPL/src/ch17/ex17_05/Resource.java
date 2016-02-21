/* Copyright (C) 2016 Ken Miura */
package ch17.ex17_05;

import java.lang.ref.WeakReference;

/**
 * @author Ken Miura
 *
 */
public interface Resource {

	void use (Object key, Object... args);
	void release ();
	
	static class ResourceImpl implements Resource {

		WeakReference<Object> keyRef;
		boolean needRelease = false;
		
		ResourceImpl (Object key) {
			keyRef = new WeakReference<Object>(key);
			
			// 外部リソース設定
			
			needRelease = true;
		}
		
		@Override
		public void use(Object key, Object... args) {
			Object tempKey = keyRef.get();
			if (key != tempKey) {
				throw new IllegalArgumentException("wrong key");
			}
			
			// リソースの使用
		}

		@Override
		public synchronized void release() {
			if (needRelease) {
				needRelease = false;
				// リソースの開放
			}
		}
		
	}
}
