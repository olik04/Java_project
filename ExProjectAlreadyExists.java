public class ExProjectAlreadyExists extends Exception{
    public ExProjectAlreadyExists () {super("Project already exists!");}
    public ExProjectAlreadyExists (String msg) {super(msg);}
}
