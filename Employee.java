import java.util.ArrayList;
import java.util.Collections;

public class Employee implements Comparable<Employee>
{
    private String name;
    private int annualLeaves;
    private ArrayList<Leave> allLeaves;

    // getters
    public String getName() {return name;}
    public int getAL() {return annualLeaves;}
    public ArrayList<Leave> getAllLeaves(){return this.allLeaves;}

    // constructor
    public Employee(String aName, int aL){
        this.name = aName;
        this.annualLeaves = aL;
        this.allLeaves = new ArrayList<Leave>();
    }

    // search for employee by his name
    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch){
        for (Employee e : list){
            if(e.getName().equals(nameToSearch))
                return e;
        }
        return null;
    }

    // print all employees
    public static void list(ArrayList<Employee> list){
        for (Employee e : list){
            System.out.printf("%s (Entitled Annual Leaves: %d days)\n", e.getName(), e.getAL());
        }
    }

    // calculate number of annual leaves
    public int calcAnnualLeave(){
        int res = getAL();
        for (Leave l : this.allLeaves){
            res -= Day.daysBetween(l.getStartDay(), l.getEndDay()) + 1;
        }
        return res;
    }

    // print only leaves
    public String listOnlyLeaves(){
        String sD = SystemDate.getString();
        Day d = new Day(sD);
        StringBuilder res = new StringBuilder();
        for (Leave leave : allLeaves){
            if (d.compareTo(leave.getEndDay()) != 1){
                res.append(", " + leave.toString());
            }
        }

        return res.toString().equals("") ? "--" : res.toString().substring(2);
    }
    // print all leaves of employee
    public void listLeaves(){
        System.out.printf("%s: ", getName());
        System.out.println(listOnlyLeaves());
    }

    // add leave to list
    public void addLeave(Leave l){
        allLeaves.add(l);
        Collections.sort(allLeaves);
    }

    // remove leave from the list
    public void removeLeave(Leave l){
        allLeaves.remove(l);
    }

    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.getName());
    }
}
