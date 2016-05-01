/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_06;

import java.util.Random;

/**
 * @author Ken Miura
 *
 */
public final class NextGaussianTest {

	private static final Random r = new Random();
	private static final int NUMBER_OF_TRIALS = 1000000;
	private static final double[] RESULT = new double[NUMBER_OF_TRIALS];
	private static double average = 0.0;
	private static double variance = 0.0;
	private static double stdDeviation = 0.0;
	private static final int FROM = -16;
	private static final int TO = 15;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test();
	}

	private static void test () {
		for (int i=0; i<NUMBER_OF_TRIALS; i++) {
			RESULT[i] = r.nextGaussian();
		}
		calculate();
		displayGraph();
	}
	
	private static void calculate() {
		double sumForAverage = 0.0;
		for (double num: RESULT) {
			sumForAverage += num;
		}
		NextGaussianTest.average = sumForAverage / NUMBER_OF_TRIALS;
		System.out.printf("average: %.16f%n", NextGaussianTest.average);
		
		double sumForVariance = 0.0;
		for (double num: RESULT) {
			double delta = num - NextGaussianTest.average;
			sumForVariance += Math.pow(delta, 2);
		}
		NextGaussianTest.variance = sumForVariance / NUMBER_OF_TRIALS;
		System.out.printf("variance: %.16f%n", NextGaussianTest.variance);
		
		NextGaussianTest.stdDeviation = Math.sqrt(NextGaussianTest.variance);
		System.out.printf("standard deviation: %.16f%n", NextGaussianTest.stdDeviation);
	}
	
	private static void displayGraph() {
		System.out.println("↓x →y");
		for (int i=FROM; i<TO; i++) {
			double y = getY(i);
			System.out.printf("%3d: ", i);
			y *= 100; // yの平均値が低すぎてグラフが見づらいので100掛け
			for (int j=0; j<y; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}

	private static double getY (double x) {
		return Math.pow(Math.exp(-(((x - average) * (x - average)) / ((2 * variance)))), 1 / (stdDeviation * Math.sqrt(2 * Math.PI))); 
	}
}
