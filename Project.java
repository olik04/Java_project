import java.util.ArrayList;

public class Project implements Comparable<Project>{
    private String projectName;
    private Day startDay;
    private Day endDay;
    private Team team;

    // getters
    public String getPName(){return this.projectName;}
    public Day getSDay(){return this.startDay;}
    public Day getEDay(){return this.endDay;}
    public Team getTeam(){return this.team;}

    // constuctor
    public Project(String aName, String sDay, int aDuration){
        this.projectName = aName;
        this.startDay = new Day(sDay);
        this.endDay = Day.calcEndDay(startDay, aDuration);
        this.team = null;
    }

    // print all projects
    public static void list(ArrayList<Project> list){
        System.out.printf("%-8s %-12s %-12s %s\n", "Project", "Start Day", "End Day", "Team");
        for (Project p : list){
            System.out.printf("%-8s %-12s %-12s %s\n", p.getPName(), p.getSDay().toString(), p.getEDay().toString(), (p.getTeam()==null ? "--" : p.getTeam().toString()));
        }
    }

    // search for team by name
    public static Project searchProject(ArrayList<Project> list, String nameToSearch){
        for (Project p : list){
            if(p.getPName().equals(nameToSearch))
                return p;
        }
        return null;
    }

    // assign project to a team
    public void assignTeam(Team t){this.team = t;}

    public String toString(){
        return this.startDay + " to " + this.endDay;
    }

    @Override
    public int compareTo(Project another) {
        return this.projectName.compareTo(another.getPName());
    }
}
