public class CmdListTeamMembers implements Command
{
    @Override
	public void execute(String[] cmdParts)
	{
		try{
            Company company = Company.getInstance();
			if (cmdParts.length < 2) throw new ExInsufficientArguments();
		
			String tName = cmdParts[1];
			Team t = company.findTeam(tName);

        	if (t == null) throw new ExTeamNotFound();

            company.listTeamMembers(t);
		} catch(ExInsufficientArguments e){
			System.out.println(e.getMessage());
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
