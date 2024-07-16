public class CmdCreateProject extends RecordedCommand
{
    private Project p;
	private Company company = Company.getInstance();

	@Override
	public void execute(String[] cmdParts)
	{
		try{
			if (cmdParts.length < 4) throw new ExInsufficientArguments();

			String pName = cmdParts[1];
			String sDay = cmdParts[2];
			int d = Integer.parseInt(cmdParts[3]);

			if (company.findProject(pName) != null) throw new ExProjectAlreadyExists();

			p = company.createProject(pName, sDay, d);

			addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
			clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.

			System.out.println("Done.");
		} catch(NumberFormatException e){
			System.out.println("Wrong number format for project duration!");
		} catch(ExInsufficientArguments e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void undoMe()
	{
		company.removeProject(p);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list
	}
	
	@Override
	public void redoMe()
	{
		company.createProject(p.getPName(), p.getSDay().toString(), Day.daysBetween(p.getSDay(), p.getEDay())+1);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}