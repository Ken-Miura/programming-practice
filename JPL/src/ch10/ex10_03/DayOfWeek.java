package ch10.ex10_03;

public enum DayOfWeek {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, ;

	public static boolean isWorkDayIf(DayOfWeek dayOfWeek) {
		if (dayOfWeek == MONDAY 
				|| dayOfWeek == TUESDAY
				|| dayOfWeek == WEDNESDAY 
				|| dayOfWeek == THURSDAY
				|| dayOfWeek == FRIDAY) {
			return true;
		} else if (dayOfWeek == SATURDAY 
				|| dayOfWeek == SUNDAY) {
			return false;
		} else {
			throw new IllegalArgumentException("dayOfWeek is incorrect value");
		}
	}

	public static boolean isWorkDaySwitch(DayOfWeek dayOfWeek) {
		switch (dayOfWeek) {
		case MONDAY:
		case TUESDAY:
		case WEDNESDAY:
		case THURSDAY:
		case FRIDAY:
			return true;
		case SATURDAY:
		case SUNDAY:
			return false;
		default:
			throw new IllegalArgumentException("dayOfWeek is incorrect value");
		}
	}
}
