public class CmdStartNewDay extends RecordedCommand {
    private String day;
    private String prevDay;
	private SystemDate instance = SystemDate.getInstance();

	@Override
	public void execute(String[] cmdParts)
	{
		try{
			if (cmdParts.length < 2) throw new ExInsufficientArguments();
			
			prevDay = SystemDate.getString();
			day = cmdParts[1];
			instance.set(day);

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
		instance.set(prevDay);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		instance.set(day);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
