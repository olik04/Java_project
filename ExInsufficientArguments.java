public class ExInsufficientArguments extends Exception{
    public ExInsufficientArguments(){super("Insufficient command arguments!");}
    public ExInsufficientArguments(String msg){super(msg);}
}
