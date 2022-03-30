package paychecks;

/**
 * Abstract class that represents an employee regardless of title.
 * 
 * @author Patrick Muradaz
 */
public abstract class Employee
{
	private String name;
	private int salary;

	/**
	 * Constructor.
	 * 
	 * @param name   is the name of this employee.
	 * @param salary is this employee's salary (not including bonus).
	 */
	public Employee(String name, int salary)
	{
		this.name = name;
		this.salary = salary;
	}

	/**
	 * Returns the employee's name as supplied to the constructor.
	 * 
	 * @return the name of this employee.
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Returns the employee's salary as supplied to the constructor.
	 * 
	 * @return the salary of this employee.
	 */
	public int getSalary()
	{
		return this.salary;
	}

	/**
	 * Abstract method, implemented in Developer and Manager. Returns a String
	 * containing the employee's title ("Developer" or "Manager").
	 * 
	 * @return the title of this employee as a String.
	 */
	public abstract String getTitle();

	/**
	 * Abstract method, implemented in Developer and Manager. Returns an int
	 * representing the employee's bonus.
	 * 
	 * @return the bonus of this employee as an int.
	 */
	public abstract int getBonus();
}
