public class Day implements Cloneable, Comparable<Day>
{
    private int year;
	private int month;
	private int day;
    private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

    private Day(int y, int m, int d){ // private constructor
        this.year = y;
        this.month = m;
        this.day = d;
    }

    public Day(String sDay) {set(sDay);} // public constructor, simply call set(sDay)

    public void set(String sDay) //Set year,month,day based on a string like 01-Jan-2022
    {		
        String[] sDayParts = sDay.split("-");
        this.day = Integer.parseInt(sDayParts[0]);
        this.year = Integer.parseInt(sDayParts[2]);
        this.month = MonthNames.indexOf(sDayParts[1])/3 +1;
    }

	// getters for fields
	public int getYear(){return year;}
	public int getMonth(){return month;}
	public int getDay(){return day;}
	
	// check if a given year is a leap year
	public static boolean isLeapYear(int y)
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	public static boolean valid(int y, int m, int d)
	{
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

	// calculate day difference between two day objects
	public static int daysBetween(Day d1, Day d2) {
        int[] daysInMonth1 = {31, isLeapYear(d1.getYear()) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] daysInMonth2 = {31, isLeapYear(d2.getYear()) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int dayDiff = 0;

        // calculate difference in years
        for (int i = d1.getYear(); i < d2.getYear(); i++) {
            Day temp = new Day(i+"-Jan-01");
            dayDiff += isLeapYear(temp.getYear()) ? 366 : 365;
        }

        // calculate difference in months for the final year
        for (int i = 1; i < d2.month; i++) {
            dayDiff += daysInMonth2[i-1];
        }

        dayDiff += d2.getDay();

        // subtract days passed in the first year until first date
        for (int i = 1; i < d1.month; i++) {
            dayDiff -= daysInMonth1[i-1];
        }

        dayDiff -= d1.getDay();

        return dayDiff;
    }

    // check if the end of the month
    public boolean isEndOfAMonth() 
    {
        if (valid(year,month,day+1)) //A smart method: check whether (year month [day+1]) is still a valid date
            return false;
        else
            return true;
    }

    // return next day
    public Day next() {
        if (isEndOfAMonth())
            if (month==12)
                return new Day(year+1,1,1); //Since the current day is the end of the year, the next day should be Jan 01
            else
                return new Day(year, month+1, 1); // <== your task: first day of next month
        else
            return new Day(year, month, day+1); // <== your task: next day of current month
    }

    // calculate end day by day difference
    public static Day calcEndDay(Day d, int duration) {
        Day endDay = d.clone();
        for (int i = 0; i < duration; i++){
            endDay = endDay.next();
        }
        return endDay;
    }

    // return minimum of two days
    public static Day minDay(Day d1, Day d2){
        if (d1.compareTo(d2) == -1) return d1.clone();
        else return d2.clone();
    }

    // return maximum of two days
    public static Day maxDay(Day d1, Day d2){
        if (d1.compareTo(d2) == 1) return d1.clone();
        else return d2.clone();
    }

    @Override
    public String toString() {		
        return day+"-"+ MonthNames.substring((month-1)*3, month*3) + "-"+ year; // (month-1)*3,(month)*3
    }

    @Override
    public Day clone() {
        Day copy = null;
        try {
            copy = (Day) super.clone();
        } catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return copy;
    }
    @Override
    public int compareTo(Day another) {
        int d1 = this.year*10000 + this.month*100 + this.day;
        int d2 = another.getYear()*10000 + another.getMonth()*100 + another.getDay();
        if (d1 == d2) return 0;
        else if (d1 > d2) return 1;
        else return -1;
    }
}

