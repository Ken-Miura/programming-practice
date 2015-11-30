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

	private final Object lock = new Object();
	private boolean hasStarted = false;
	
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
    }

    /**
     * Starts threads.
     *
     * @throws IllegalStateException if threads has been already started.
     */
    public void start() {
       	synchronized (lock) {
       		if (hasStarted) {
       			throw new IllegalStateException("start has been already invoked.");
       		}
       		
       		// Do something
       		
       		//　あとでfinallyに入れる。
       		hasStarted = true;
       	}
    }   

    /**
     * Stop all threads and wait for their terminations.
     *
     * @throws IllegalStateException if threads has not been started.
     */
    public void stop() {
    	//synchronized (lock) {
    		if (!hasStarted) {
   				throw new IllegalStateException("start has not been invoked yet.");
   			}
    	//}
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
    }
}
