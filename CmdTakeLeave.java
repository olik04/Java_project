public class CmdTakeLeave extends RecordedCommand{
	private String name;
	private Leave l;
	Company company = Company.getInstance();

	@Override
	public void execute(String[] cmdParts)
	{
		try{
			if (cmdParts.length < 4) throw new ExInsufficientArguments();

			name = cmdParts[1]; // name of the Employee
			Day startDay = new Day(cmdParts[2]); // start day of leave period
			Day endDay = new Day(cmdParts[3]); // end day of leave period
			l = new Leave(startDay, endDay);

			Employee e = company.findEmployee(name);
			if (e == null) throw new ExEmployeeNotFound();

			Team t = company.checkIfHasTeam(e.getName());

			if (t != null){
				for (Project p : t.getAllProjects()){
					Day d = p.getSDay().clone(); // d is first day of final stage
					while (Day.daysBetween(d, p.getEDay()) > 4)
						d = d.next();

					if (!(endDay.compareTo(d) == -1 || startDay.compareTo(p.getEDay()) == 1)){ // endDay < d || startDay > p.getEDay()
						String msg = String.format("The leave is invalid.  Reason: Project %s will be in its final stage during %s to %s.", p.getPName(), d, p.getEDay());
						throw new ExProjectFinalStage(msg);
					}
				}
			}

			Day curDay = new Day(SystemDate.getString());
			for (Leave leave : e.getAllLeaves()){
				if (curDay.compareTo(leave.getEndDay()) != 1){
					if ((startDay.compareTo(leave.getStartDay()) != -1 && leave.getEndDay().compareTo(startDay) == 1)
					|| (endDay.compareTo(leave.getStartDay()) == 1 && leave.getEndDay().compareTo(endDay) != -1)
					|| (startDay.compareTo(leave.getStartDay()) != 1 && leave.getEndDay().compareTo(endDay) != 1))
						throw new ExLeaveOverlap("Leave overlapped: The leave period " + leave.toString() + " is found!");
				}
			}

			if (Day.daysBetween(startDay, endDay) + 1 > e.calcAnnualLeave())
				throw new ExInsufficientBalanceOfLeaves(String.format("Insufficient balance of annual leave. %d days left only!", e.calcAnnualLeave()));
			
			e.addLeave(l);

			addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
			clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.

			System.out.print("Done. ");
			System.out.printf("%s's remaining annual leave: %d days", e.getName(), e.calcAnnualLeave());
		} catch(ExInsufficientArguments e){
			System.out.println(e.getMessage());
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void undoMe()
	{
		Employee e = company.findEmployee(name);
		e.removeLeave(l);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		Employee e = company.findEmployee(name);
		e.addLeave(l);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
