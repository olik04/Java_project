public class Leave implements Comparable<Leave>
{
    private Day startDay;
    private Day endDay;

    // getters
    public Day getStartDay(){return this.startDay;}
    public Day getEndDay(){return this.endDay;}

    // constructor
    public Leave(Day d1, Day d2){
        this.startDay = d1;
        this.endDay = d2;
    }

    public String toString(){
        return this.getStartDay() + " to " + this.getEndDay();
    }

    @Override
    public int compareTo(Leave l) {
        return this.startDay.compareTo(l.getStartDay());
    }
}
