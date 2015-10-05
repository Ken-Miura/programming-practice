package ch01.ex01_10;

import org.junit.Assert;
import org.junit.Test;

public class ElementTest {

	@Test
	public void デフォルト値の確認() {
		Element e = new Element();
		
		int expectedInt = 0;
		Assert.assertEquals(expectedInt, e.getNumber(), 0);
		
		boolean expectedBoolean = true;
		Assert.assertEquals(expectedBoolean, e.isEven());
	}

	@Test
	public void setNumberAndEvenの引数が正しくセットされているかの確認() {
		Element e1 = new Element();
		int expectedInt = 3;		
		boolean expectedBoolean = false;
		e1.setNumber(expectedInt);
		
		Assert.assertEquals(expectedInt, e1.getNumber(), 0);
		Assert.assertEquals(expectedBoolean, e1.isEven());
		
		Element e2 = new Element();
		expectedInt = 2;		
		expectedBoolean = true;		
		e2.setNumber(expectedInt);
		
		Assert.assertEquals(expectedInt, e2.getNumber(), 0);
		Assert.assertEquals(expectedBoolean, e2.isEven());
	}	
}
