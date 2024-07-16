public class ExTeamAlreadyExists extends Exception{
    public ExTeamAlreadyExists () {super("Team already exists!");}
    public ExTeamAlreadyExists (String msg) {super(msg);}
}
