public class CmdJoinTeam extends RecordedCommand
{
    Employee e;
    Team t;
    Company company = Company.getInstance();

    @Override
	public void execute(String[] cmdParts)
	{
		try{
			if (cmdParts.length < 3) throw new ExInsufficientArguments();
			
			String eName = cmdParts[1];
			String tName = cmdParts[2];

			e = company.findEmployee(eName);
			t = company.findTeam(tName);

			if (e == null) throw new ExEmployeeNotFound();
        	if (t == null) throw new ExTeamNotFound();
			Team aTeam = company.checkIfHasTeam(eName);
			if (aTeam != null) throw new ExEmployeeAlreadyInTeam(eName + " has already joined another team: " + aTeam.getTeamName());
			
			company.addMember(t, e);

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
		company.removeMember(t, e);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list 
	}
	
	@Override
	public void redoMe()
	{
		company.addMember(t, e);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
