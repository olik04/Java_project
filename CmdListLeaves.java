public class CmdListLeaves implements Command
{
    @Override
    public void execute(String[] cmdParts)
    {
        try{
            Company company = Company.getInstance();
            if (cmdParts.length == 1){
                company.listLeaves();
            }
            else{ // cmdParts.length == 2
                String name = cmdParts[1];
                Employee e = company.findEmployee(name);
                if (e == null) throw new ExEmployeeNotFound();
                company.listLeaves(e);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

