public class CmdSuggestTeam implements Command
{
	@Override
	public void execute(String[] cmdParts)
	{
		try{
			if (cmdParts.length < 2) throw new ExInsufficientArguments();
            String pName = cmdParts[1];

            Company company = Company.getInstance();
			Project p = company.findProject(pName);

            if (p == null) throw new ExProjectNotFound();

            company.suggestTeam(p);

		} catch(NumberFormatException e){
			System.out.println("Wrong number format for project duration!");
		} catch(ExInsufficientArguments e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}