public class ExInsufficientBalanceOfLeaves extends Exception{
    public ExInsufficientBalanceOfLeaves(){super("Insufficient balance of annual leave.");}
    public ExInsufficientBalanceOfLeaves(String msg){super(msg);}
}
