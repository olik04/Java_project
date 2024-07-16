public class CmdHire extends RecordedCommand
{
	private Employee employee;
	private Company company = Company.getInstance();

	@Override
	public void execute(String[] cmdParts)
	{
		try{
			if (cmdParts.length < 3) throw new ExInsufficientArguments();

			String name = cmdParts[1];
			int aL = Integer.parseInt(cmdParts[2]);

			if (company.findEmployee(name) != null) throw new ExEmployeeAlreadyExists();
			
			employee = company.createEmployee(name, aL);

			addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
			clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
			
			System.out.println("Done.");
		} catch(NumberFormatException e){
			System.out.println("Wrong number format for annual leaves!");
		} catch(ExInsufficientArguments e){
			System.out.println(e.getMessage());
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void undoMe()
	{
		company.removeEmployee(employee);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list
	}
	
	@Override
	public void redoMe()
	{
		company.createEmployee(employee.getName(), employee.getAL());
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
