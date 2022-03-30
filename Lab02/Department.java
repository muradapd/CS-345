/**
 * 
 */
package paychecks;

/**
 * Department inherits from the class Payroll.
 * 
 * @author Patrick Muradaz
 */
public class Department extends Payroll
{

	private String name;

	/**
	 * Constructor.
	 * 
	 * @param name is the name of this department
	 */
	public Department(String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return this.name;
	}
}
