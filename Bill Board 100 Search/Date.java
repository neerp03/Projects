import java.time.DayOfWeek;
import java.time.LocalDate;

public class Date implements Comparable<Date> {

	private int month;
	private int day;
	private int year;

	// default constructor
	public Date() {
	}

	public Date(String date) {
		String[] splitDate = date.split("/");
		this.month = Integer.valueOf(splitDate[0]);
		this.day = Integer.valueOf(splitDate[1]);
		this.year = Integer.valueOf(splitDate[2]);
	}

	
	public Date(int month, int day, int year) {
		this.month = month;
		this.day = day;
		this.year = year;
	}

	
	public Date getWeek() {
		Date week;
		LocalDate date = LocalDate.of(year, month, day);
		while (date.getDayOfWeek() != DayOfWeek.SATURDAY) {
			date = date.plusDays(1);
		}
		int month = date.getMonthValue();
		int day = date.getDayOfMonth();
		int year = date.getYear();
		week = new Date(month, day, year);
		return week;
	}


	public boolean equals(Object o) {
		if (o == null || !(o instanceof Date))
			return false;
		else {
			Date d = (Date) o;
			return (month == d.month && day == d.day && year == d.year);
		}
	}

	
	public int compareTo(Date other) {
		if (this.equals(other))
			return 0;
		else if ((year < other.year) || (year == other.year && month < other.month)
				|| (year == other.year && month == other.month && day < other.day))
			return -1;
		else
			return 1;
	}

	public String toString() {
		return month + "/" + day + "/" + year;
	}

}