package paychecks;

/**
 * Concrete subclass of the abstract class Employee.
 * 
 * @author Patrick Muradaz
 */
public class Manager extends Employee
{

	private Department department;

	/**
	 * Constructor.
	 * 
	 * @param name       is the name of this employee.
	 * @param salary     is the salary of this employee.
	 * @param department is this managers department.
	 */
	public Manager(String name, int salary, Department department)
	{
		super(name, salary);
		this.department = department;
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
		return (this.department.getTotalSalaries(false) / 100);
	}
}
