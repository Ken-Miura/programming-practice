package ch09.ex09_01;

public class CalcInfinite {

	public static void main(String[] args) {

		System.out.println("+演算子");
		System.out.println(Double.POSITIVE_INFINITY + Double.POSITIVE_INFINITY);
		System.out.println(Double.POSITIVE_INFINITY + Double.NEGATIVE_INFINITY);
		System.out.println(Double.NEGATIVE_INFINITY + Double.POSITIVE_INFINITY);
		System.out.println(Double.NEGATIVE_INFINITY + Double.NEGATIVE_INFINITY);
		System.out.println();
		
		System.out.println("-演算子");
		System.out.println(Double.POSITIVE_INFINITY - Double.POSITIVE_INFINITY);
		System.out.println(Double.POSITIVE_INFINITY - Double.NEGATIVE_INFINITY);
		System.out.println(Double.NEGATIVE_INFINITY - Double.POSITIVE_INFINITY);
		System.out.println(Double.NEGATIVE_INFINITY - Double.NEGATIVE_INFINITY);
		System.out.println();
		
		System.out.println("*演算子");
		System.out.println(Double.POSITIVE_INFINITY * Double.POSITIVE_INFINITY);
		System.out.println(Double.POSITIVE_INFINITY * Double.NEGATIVE_INFINITY);
		System.out.println(Double.NEGATIVE_INFINITY * Double.POSITIVE_INFINITY);
		System.out.println(Double.NEGATIVE_INFINITY * Double.NEGATIVE_INFINITY);
		System.out.println();
		
		System.out.println("/演算子");
		System.out.println(Double.POSITIVE_INFINITY / Double.POSITIVE_INFINITY);
		System.out.println(Double.POSITIVE_INFINITY / Double.NEGATIVE_INFINITY);
		System.out.println(Double.NEGATIVE_INFINITY / Double.POSITIVE_INFINITY);
		System.out.println(Double.NEGATIVE_INFINITY / Double.NEGATIVE_INFINITY);
	}

}
