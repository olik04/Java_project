import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private ArrayList<Project> allProjects;
    private static Company instance = new Company();

    // constructor
    private Company(){
        allEmployees = new ArrayList<Employee>();
        allTeams = new ArrayList<Team>();
        allProjects = new ArrayList<Project>();
    }

    public static Company getInstance(){return instance;}

    // EMPLOYEE
    public void listEmployees(){
        Employee.list(allEmployees);
    }

    public Employee createEmployee(String aName, int aL){
        Employee e = new Employee(aName, aL);
        allEmployees.add(e);
        Collections.sort(allEmployees);
        return e;
    }

    public void removeEmployee(Employee e){
        for (Employee employee : allEmployees){
            if (employee.compareTo(e) == 0){
                allEmployees.remove(employee);
                break;
            }
        }
    }

    public Employee findEmployee(String employeeName){
        return Employee.searchEmployee(allEmployees, employeeName);
    }

    // TEAM
    public void listTeams(){
        Team.list(allTeams);
    }

    public Team createTeam(String aName, String aHead)
    {
        Employee e = findEmployee(aHead);
        Team t = new Team(aName, e);
        allTeams.add(t);
        Collections.sort(allTeams);
        return t;
    }

    public void removeTeam(Team t){
        for (Team team : allTeams){
            if (team.compareTo(t) == 0){
                allTeams.remove(team);
                break;
            }
        }
    }

    public Team findTeam(String teamName){
        return Team.searchTeam(allTeams, teamName);
    }

    public Team findTeamByTeamLead(String teamLeadName){
        for (Team team : allTeams){
            if (teamLeadName.equals(team.getHead().getName()))
                return team;
        }
        return null;
    }

    public void listTeamMembers(Team t){
        t.listTeamMembers();
    }

    public void addMember(Team t, Employee e){
        t.addMember(e);
    }

    public void removeMember(Team t, Employee e){
        t.removeMember(e);
    }

    public Team checkIfHasTeam(String empName){
        for (Team t : allTeams){
            if(t.checkIfInTeam(empName)) return t;
        }
        return null;
    }

    public void suggestTeam(Project proj){
        System.out.printf("During the period of project %s (%s):\n", proj.getPName(), proj.toString());
        System.out.println("  Average manpower (m) and count of existing projects (p) of each team:");
        for (Team t : allTeams){
            double m = t.calculateManPower(proj.getSDay(), proj.getEDay());
            double p = t.calculateProjects(proj.getSDay(), proj.getEDay());
            System.out.printf("    %s: m=%.2f workers, p=%.2f projects\n", t.getTeamName(), m, p);
        }
        System.out.printf("  Projected loading factor when a team takes this project %s:\n", proj.getPName());
        double minProjLF = Double.MAX_VALUE;
        int indexOfMin = -1;
        int currIndex = 0;
        for (Team t : allTeams){
            double m = t.calculateManPower(proj.getSDay(), proj.getEDay());
            double p = t.calculateProjects(proj.getSDay(), proj.getEDay());
            System.out.printf("    %s: (p+1)/m = %.2f\n", t.getTeamName(), (p+1)/m);
            if (minProjLF > (p+1)/m) {
                minProjLF = (p+1)/m;
                indexOfMin = currIndex;
            }
            currIndex++;
        }
        System.out.printf("Conclusion: %s should be assigned to %s for best balancing of loading\n", proj.getPName(), allTeams.get(indexOfMin).getTeamName());
    }

    // PROJECT
    public void listProjects(){
        Project.list(allProjects);
    }

    public Project createProject(String aName, String sDay, int aDuration){
        Project p = new Project(aName, sDay, aDuration - 1); // -1 since start day is also counted
        allProjects.add(p);
        Collections.sort(allProjects);
        return p;
    }

    public void removeProject(Project p){
        for (Project project : allProjects){
            if (project.compareTo(p) == 0){
                allProjects.remove(project);
                break;
            }
        }
    }

    public Project findProject(String projectName){
        return Project.searchProject(allProjects, projectName);
    }

    public void assignProjectToTeam(Project p, Team t){
        p.assignTeam(t);
        if (t != null){
            t.addProject(p);
            Collections.sort(allProjects);
        }
    }

    // LEAVE
    public void listLeaves(Employee e){
        e.listLeaves();
    }

    public void listLeaves(){
        for (Employee e : allEmployees)
            e.listLeaves();
    }
}
