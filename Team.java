import java.util.ArrayList;
import java.util.Collections;

public class Team implements Comparable<Team>{
    private String teamName;
    private Employee head;
    private Day dateSetup;
    private ArrayList<Employee> allMembers; 
    private ArrayList<Project> allProjects;

    // getters
    public String getTeamName(){return teamName;}
    public Employee getHead(){return head;}
    public ArrayList<Project> getAllProjects(){return allProjects;}

    // constructor
    public Team(String n, Employee hd) {
        this.teamName = n;
        this.head = hd;
        this.dateSetup = SystemDate.getInstance().clone();
        this.allMembers = new ArrayList<Employee>();
        this.allProjects = new ArrayList<Project>();
    }

    // search for team by its name
    public static Team searchTeam(ArrayList<Team> list, String nameToSearch){
        for (Team t : list){
            if(t.getTeamName().equals(nameToSearch))
                return t;
        }
        return null;
    }

    // print all teams
    public static void list(ArrayList<Team> list){
        System.out.printf("%-15s%-10s%-13s\n", "Team Name", "Leader", "Setup Date");
        for (Team t : list){
            System.out.printf("%-15s%-10s%-13s\n", t.teamName, t.head.getName(), t.dateSetup.toString());
        }
    }

    // print all team members
    public void listTeamMembers(){
        System.out.printf("%-10s%-10s%s\n", "Role", "Name", "Current / coming leaves");
        System.out.printf("%-10s%-10s%s\n", "Leader", this.head.getName(), this.head.listOnlyLeaves());
        for (Employee e : allMembers){
            System.out.printf("%-10s%-10s%s\n", "Member", e.getName(), e.listOnlyLeaves());
        }
    }

    // add member to a team
    public void addMember(Employee e){
        this.allMembers.add(e);
        Collections.sort(allMembers);
    }

    // remove member from team
    public void removeMember(Employee e){
        this.allMembers.remove(e);
    }

    // check if employee is in team
    public boolean checkIfInTeam(String empName){
        if (head.getName().equals(empName)) return true;
        for (Employee e : allMembers){
            if (e.getName().equals(empName)) return true;
        }
        return false;
    }

    // add project to all projects of team
    public void addProject(Project p){
        if (!allProjects.contains(p))
            allProjects.add(p);
    }

    // remove project from all projects list
    public void removeProject(Project p){
        allProjects.remove(p);
    }

    // calculate manpower
    public double calculateManPower(Day projStart, Day projEnd){
        double m = 0;
        double total_duration = (Day.daysBetween(projStart, projEnd)+1);
        double dayCnt;
        for (Employee e : allMembers){
            dayCnt = 0;
            for (Leave l : e.getAllLeaves()){
                if (l.getEndDay().compareTo(projStart) != -1 && l.getStartDay().compareTo(projEnd) != 1){ // if leave does not end before the project start date and does not start after the project end date
                    Day d1 = Day.maxDay(l.getStartDay(), projStart);
                    Day d2 = Day.minDay(l.getEndDay(), projEnd);
                    dayCnt += Day.daysBetween(d1, d2) + 1;
                }
            }
            m += (total_duration - dayCnt);
        } // calculate for each team member except leader
        dayCnt = 0;
        for (Leave l : head.getAllLeaves()){
            if (l.getEndDay().compareTo(projStart) != -1 && l.getStartDay().compareTo(projEnd) != 1){ // if leave does not end before the project start date and does not start after the project end date
                Day d1 = Day.maxDay(l.getStartDay(), projStart);
                Day d2 = Day.minDay(l.getEndDay(), projEnd);
                dayCnt += Day.daysBetween(d1, d2) + 1;
            }
        } // calculate for leader of the team
        m += (total_duration - dayCnt);
        m /= total_duration;
        return m;
    }

    // calculate existing projects
    public double calculateProjects(Day projStart, Day projEnd){
        double dayCnt = 0;
        for (Project proj : allProjects){
            if (proj.getEDay().compareTo(projStart) != -1 && proj.getSDay().compareTo(projEnd) != 1){ // if another project does not end before the project start date and does not start after the project end date
                Day d1 = Day.maxDay(proj.getSDay(), projStart);
                Day d2 = Day.minDay(proj.getEDay(), projEnd);
                dayCnt += Day.daysBetween(d1, d2) + 1;
            }
        }
        double p = dayCnt/(Day.daysBetween(projStart, projEnd)+1);
        return p;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Employee e : allMembers)
            sb.append(", " + e.getName());
        return String.format("%s (%s%s)", teamName, head.getName(), sb.toString());
    }

    @Override
    public int compareTo(Team another) {
        return this.teamName.compareTo(another.getTeamName());
    }
}
