package ch07.ex07_02;

public class BaseNum {

	byte b = 0;
	short s = 0;
	char c = 0;
	int i = 0;
	long l = 0L;
	
	float f = 0.0f;
	double d = 0.0;
	
	
	public static void main(String[] args) {
		
		BaseNum baseNum = new BaseNum();
		
		// byteにbyte型以外のリテラルを代入しようとしたとき
		baseNum.b = 0; // byteの範囲に収まるint
		//baseNum.b = 128; // byteの範囲に収まらないint compile error
		//baseNum.b = -129; // byteの範囲に収まらないint compile error
		//baseNum.b = 0L; // long compile error
		//baseNum.b = 0.0f // float compile error
		//baseNum.b = 0.0 // double compile error
		
		// shortにshort型以外のリテラルを代入しようとしたとき
		baseNum.s = 0; // byteの範囲に収まるint
		//baseNum.s = 32768; // shortの範囲に収まらないint compile error
		//baseNum.s = -32769; // shortの範囲に収まらないint compile error
		//baseNum.s = 0L; // long compile error
		//baseNum.s = 0.0f // float compile error
		//baseNum.s = 0.0 // double compile error
		
		// intにint型以外のリテラルを代入しようとしたとき
		//baseNum.i = 0L; // long compile error
		//baseNum.i = 0.0f; // float compile error
		//baseNum.i = 0.0; // double compile error
		
		// longにlong型以外のリテラルを代入しようとしたとき
		baseNum.l = 0; // int
		//baseNum.l = 0.0f; // float compile error
		//baseNum.l = 0.0; // double compile error
		
		//floatにfloat型以外のリテラルを代入しようとしたとき
		System.out.println("float型の出力の確認");
		baseNum.f = 0; // int
		System.out.println(baseNum.f);
		baseNum.f = 2147483647; // int型最大値
		System.out.println(baseNum.f);
		baseNum.f = -2147483648; // int型の最小値
		System.out.println(baseNum.f);
		baseNum.f = 0L; // long
		System.out.println(baseNum.f);
		baseNum.f = 9223372036854775807L; // long型最大値
		System.out.println(baseNum.f);
		baseNum.f = -9223372036854775808L; // long型最小値
		System.out.println(baseNum.f);
		//baseNum.f = 0.0; //double compile error 
		
		//doubleにdouble型以外のリテラルを代入しようとしたとき
		System.out.println("double型の出力の確認");
		baseNum.d = 0; // int
		System.out.println(baseNum.d);
		baseNum.d = 2147483647; // int型最大値
		System.out.println(baseNum.d);
		baseNum.d = -2147483648; // int型の最小値
		System.out.println(baseNum.d);
		baseNum.d = 0L; // long
		System.out.println(baseNum.d);
		baseNum.d = 9223372036854775807L; // long型最大値
		System.out.println(baseNum.d);
		baseNum.d = -9223372036854775808L; // long型最小値
		System.out.println(baseNum.d);
		baseNum.d = 0.0f; //float
		System.out.println(baseNum.d);
		baseNum.d = 3.4028235E38f; //float型最大値
		System.out.println(baseNum.d);
		baseNum.d = 1.4E-45f; //float型最小値
		System.out.println(baseNum.d);
	}

}
