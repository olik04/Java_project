public class ExEmployeeAlreadyInTeam extends Exception{
    public ExEmployeeAlreadyInTeam(){super("Employee already in a team!");}
    public ExEmployeeAlreadyInTeam(String msg){super(msg);}
}
