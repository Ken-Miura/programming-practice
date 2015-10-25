package ch10.ex10_03;

import static org.junit.Assert.*;

import org.junit.Test;

public class DayOfWeekTest {

	@Test
	public void if文でメソッドで正しく働くべき日が返るかの確認() {
		if (!DayOfWeek.isWorkDayIf(DayOfWeek.MONDAY)) {
			fail();
		}
		if (!DayOfWeek.isWorkDayIf(DayOfWeek.TUESDAY)) {
			fail();
		}
		if (!DayOfWeek.isWorkDayIf(DayOfWeek.WEDNESDAY)) {
			fail();
		}
		if (!DayOfWeek.isWorkDayIf(DayOfWeek.THURSDAY)) {
			fail();
		}
		if (!DayOfWeek.isWorkDayIf(DayOfWeek.FRIDAY)) {
			fail();
		}
		if (DayOfWeek.isWorkDayIf(DayOfWeek.SATURDAY)) {
			fail();
		}
		if (DayOfWeek.isWorkDayIf(DayOfWeek.SUNDAY)) {
			fail();
		}
	}
	
	@Test
	public void switch文でメソッドで正しく働くべき日が返るかの確認() {
		if (!DayOfWeek.isWorkDaySwitch(DayOfWeek.MONDAY)) {
			fail();
		}
		if (!DayOfWeek.isWorkDaySwitch(DayOfWeek.TUESDAY)) {
			fail();
		}
		if (!DayOfWeek.isWorkDaySwitch(DayOfWeek.WEDNESDAY)) {
			fail();
		}
		if (!DayOfWeek.isWorkDaySwitch(DayOfWeek.THURSDAY)) {
			fail();
		}
		if (!DayOfWeek.isWorkDaySwitch(DayOfWeek.FRIDAY)) {
			fail();
		}
		if (DayOfWeek.isWorkDaySwitch(DayOfWeek.SATURDAY)) {
			fail();
		}
		if (DayOfWeek.isWorkDaySwitch(DayOfWeek.SUNDAY)) {
			fail();
		}		
	}

}
