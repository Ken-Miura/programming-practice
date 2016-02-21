/* Copyright (C) 2016 Ken Miura */
package ch17.ex17_04;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;

import ch17.ex17_04.Resource.ResourceImpl;;

/**
 * @author Ken Miura
 *
 */
public final class ResourceManager {

	final ReferenceQueue<Object> queue;
	final Map<Reference<?>, Resource> refs;
	final Thread reaper;
	boolean shutdown = false;
	
	public ResourceManager () {
		queue = new ReferenceQueue<Object>();
		refs = new HashMap<Reference<?>, Resource>();
		reaper = new ReaperThread();
		reaper.start();
		
		// リソースの初期化
	}
	
	public synchronized void shutdown () {
		if (!shutdown) {
			shutdown = true;
			reaper.interrupt();
		}
	}
	
	public synchronized Resource getResource (Object key) {
		if (shutdown) {
			throw new IllegalStateException();
		}
		Resource res = new ResourceImpl(key);
		Reference<?> ref = new PhantomReference<Object>(key, queue);
		refs.put(ref, res);
		return res;
	}
	
	class ReaperThread extends Thread {
		private boolean shouldShutdown = false;
		
		public void run () {
			while (!shouldShutdown && queue.poll() != null) {
				try {
					Reference<?> ref = queue.remove();
					Resource res = null;
					synchronized (ResourceManager.this) {
						res = refs.get(ref);
						refs.remove(ref);
					}
					res.release();
					ref.clear();
				} catch (InterruptedException e) {
					shouldShutdown = true;
				}
			}
		}
	}
}
