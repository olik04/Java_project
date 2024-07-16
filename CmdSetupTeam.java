public class CmdSetupTeam extends RecordedCommand
{
	private Team team;
	private Company company = Company.getInstance();

	@Override
	public void execute(String[] cmdParts)
	{
		try{
			if (cmdParts.length < 3) throw new ExInsufficientArguments();
		
			String teamName = cmdParts[1];
			String headName = cmdParts[2];

			if (company.findTeam(teamName) != null) 
				throw new ExTeamAlreadyExists();

			if (company.findEmployee(headName) == null)
				throw new ExEmployeeNotFound();
			
			Team t = company.findTeamByTeamLead(headName);
			if (t != null)
				throw new ExEmployeeAlreadyInTeam(headName + " has already joined another team: " + t.getTeamName());
			
			team = company.createTeam(teamName, headName);

			addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
			clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.

			System.out.println("Done.");
		} catch(ExInsufficientArguments e){
			System.out.println(e.getMessage());
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void undoMe()
	{
		company.removeTeam(team);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		company.createTeam(team.getTeamName(), team.getHead().getName());
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
