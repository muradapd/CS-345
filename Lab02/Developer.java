package paychecks;

/**
 * Concrete subclass of the abstract class Employee.
 * 
 * @author Patrick Muradaz
 */
public class Developer extends Employee
{
	/**
	 * Constructor.
	 * 
	 * @param name   is the name of this employee.
	 * @param salary is the salary of this employee.
	 */
	public Developer(String name, int salary)
	{
		super(name, salary);
	}

	/**
	 * Implementation of abstract method in Employee.
	 * 
	 * @return String "Developer".
	 */
	@Override
	public String getTitle()
	{
		return "Developer";
	}

	/**
	 * Implementation of abstract method in Employee.
	 * 
	 * @return int 5000.
	 */
	@Override
	public int getBonus()
	{
		return 5000;
	}
}
