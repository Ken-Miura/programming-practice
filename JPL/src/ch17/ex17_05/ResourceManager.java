/* Copyright (C) 2016 Ken Miura */
package ch17.ex17_05;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;

import ch17.ex17_05.Resource.ResourceImpl;

/**
 * @author Ken Miura
 *
 */
public final class ResourceManager {

	final ReferenceQueue<Object> queue;
	final Map<Reference<?>, Resource> refs;
	boolean shutdown = false;
	
	public ResourceManager () {
		queue = new ReferenceQueue<Object>();
		refs = new HashMap<Reference<?>, Resource>();
		
		// リソースの初期化
	}
	
	public synchronized void shutdown () {
		if (!shutdown) {
			shutdown = true;
			releaseWhileQueueIsNotEmpty();
		}
	}
	
	/**
	 * リソース獲得前にリリースできるものがあれば開放する
	 **/
	public synchronized Resource getResource (Object key) {
		if (shutdown) {
			throw new IllegalStateException();
		}
		
		releaseWhileQueueIsNotEmpty();
		
		Resource res = new ResourceImpl(key);
		Reference<?> ref = new PhantomReference<Object>(key, queue);
		refs.put(ref, res);		
		return res;
	}
	
	private void releaseWhileQueueIsNotEmpty () {
		while (queue.poll() != null) {
			Reference<?> polledRef = queue.poll();
			Resource polledRes = null;
			polledRes = refs.get(polledRef);
			refs.remove(polledRef);
			polledRes.release();
			polledRef.clear();				
		}
	}
}
