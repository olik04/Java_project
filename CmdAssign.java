public class CmdAssign extends RecordedCommand{
    private Project p;
    private Team t;
	private Company company = Company.getInstance();

	@Override
	public void execute(String[] cmdParts)
	{
		try{
			if (cmdParts.length < 3) throw new ExInsufficientArguments();
			
			String pName = cmdParts[1];
			String tName = cmdParts[2];

			p = company.findProject(pName);
			t = company.findTeam(tName);

			if (t == null) throw new ExTeamNotFound();
        	if (p == null) throw new ExProjectNotFound();
			
			company.assignProjectToTeam(p, t);

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
		t.removeProject(p);
		company.assignProjectToTeam(p, null);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		company.assignProjectToTeam(p, t);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
