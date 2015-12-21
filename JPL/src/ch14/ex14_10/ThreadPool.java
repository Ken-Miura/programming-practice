package ch14.ex14_10;

import java.util.Objects;

/*
 * Copyright (C) 2012, 2013 RICOH Co., Ltd. All rights reserved.
 */

/**
 * Simple Thread Pool class.
 *
 * This class can be used to dispatch an Runnable object to
 * be exectued by a thread.
 *
 * [Instruction]
 *  Implement one constructor and three methods.
 *  Don't forget to write a Test program to test this class. 
 *  Pay attention to @throws tags in the javadoc.
 *  If needed, you can put "synchronized" keyword to methods.
 *  All classes for implementation must be private inside this class.
 *  Don't use java.util.concurrent package.
 */
public class ThreadPool {

	private static class ThreadInPool extends Thread {
		private final TaskQueue taskQueue;
		
		public ThreadInPool (TaskQueue taskQueue) {
			assert taskQueue != null;
			this.taskQueue = taskQueue;
		}
		
		@Override
		public void run () {
			while (!taskQueue.closed() || taskQueue.numOfTasks() > 0) {
				Runnable task = null;
				try {
					task = taskQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				if (task != null) {
					task.run();	
				}
			}
		}
	}
	
	private static class TaskQueue {
		private final Runnable[] tasks;
		private int head = 0;
		private int tail = 0;
		private int numOfTasks = 0;
		private volatile boolean closed = false;
		
		public TaskQueue (int queueSize) {
			assert queueSize >= 1;
			tasks = new Runnable[queueSize];
		}
		
		public synchronized boolean put (Runnable task) throws InterruptedException {
			assert task != null;
			while (numOfTasks >= tasks.length && !closed) {
				wait();
			}
			if (closed) {
				return false;
			}
			tasks[tail] = task;
			tail = (tail+1) % tasks.length;
			numOfTasks++;
			notifyAll();
			return true;
		}
		
		public synchronized Runnable take () throws InterruptedException {
			while (numOfTasks <= 0 && !closed) {
				wait();
			}
			if (numOfTasks <= 0) {
				return null;
			}
			Runnable task = tasks[head];
			head = (head+1) % tasks.length;
			numOfTasks--;
			notifyAll();
			assert task != null;
			return task;
		}
		
		public synchronized int numOfTasks () {
			return numOfTasks;
		}
		
		public synchronized void close () {
			closed = true;
			notifyAll();
		}
		
		public boolean closed (){
			return closed;
		}
	}
	
	private boolean hasStarted = false;
	private final TaskQueue taskQueue;
	private final Thread[] threads;
	
    /**
     * Constructs ThreadPool.
     *
     * @param queueSize the max size of queue
     * @param numberOfThreads the number of threads in this pool.
     *
     * @throws IllegalArgumentException if either queueSize or numberOfThreads
     *         is less than 1
     */
    public ThreadPool(int queueSize, int numberOfThreads) {
    	if (queueSize < 1) {
    		throw new IllegalArgumentException("queueSize must be 1 or more.");
    	}
    	if (numberOfThreads < 1) {
    		throw new IllegalArgumentException("numberOfThreads must be 1 or more.");
    	}
    	taskQueue = new TaskQueue(queueSize);
    	threads = new ThreadInPool[numberOfThreads];
    	for (int i=0; i<numberOfThreads; i++) {
    		threads[i] = new ThreadInPool(taskQueue);
    		threads[i].setName("pooled thread No." + i);
    	}
    }

    /**
     * Starts threads.
     *
     * @throws IllegalStateException if threads has been already started.
     */
    public synchronized void start() {
       		if (hasStarted) {
       			throw new IllegalStateException("start has been already invoked.");
       		}
           	for (int i=0; i<threads.length; i++) {
           		threads[i].start();
           	}
       		hasStarted = true;
    }   

    /**
     * Stop all threads and wait for their terminations.
     *
     * @throws IllegalStateException if threads has not been started.
     */
    public synchronized void stop() {
    	if (!hasStarted) {
   			throw new IllegalStateException("start has not been invoked yet.");
   		}
    	taskQueue.close();
       	for (final Thread t: threads) {
       		try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
       	}
    }

    /**
     * Executes the specified Runnable object, using a thread in the pool.
     * run() method will be invoked in the thread. If the queue is full, then
     * this method invocation will be blocked until the queue is not full.
     * 
     * @param runnable Runnable object whose run() method will be invoked.
     *
     * @throws NullPointerException if runnable is null.
     * @throws IllegalStateException if this pool has not been started yet.
     */
    public synchronized void dispatch(Runnable runnable) {
       	Objects.requireNonNull(runnable, "runnable must not be null.");
       	if (!hasStarted) {
       		throw new IllegalStateException("start has not been invoked yet.");
       	}
       	try {
			boolean succeedDispatching = taskQueue.put(runnable);
			if (!succeedDispatching) {
				System.err.println("failed in dispatching task.");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
    }
}
