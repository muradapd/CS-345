package paychecks;

/**
 * Concrete subclass of the abstract class Employee.
 * 
 * @author Patrick Muradaz
 */
public class Manager extends Employee
{
	/**
	 * Constructor.
	 * 
	 * @param name   is the name of this employee.
	 * @param salary is the salary of this employee.
	 */
	public Manager(String name, int salary)
	{
		super(name, salary);
	}

	/**
	 * Implementation of abstract method in Employee.
	 * 
	 * @return String "Manager".
	 */
	@Override
	public String getTitle()
	{
		return "Manager";
	}

	/**
	 * Implementation of abstract method in Employee.
	 * 
	 * @return int 10000.
	 */
	@Override
	public int getBonus()
	{
		return 10000;
	}
}
