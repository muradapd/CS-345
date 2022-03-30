package paychecks;

import java.util.ArrayList;
import java.util.List;

/**
 * An object consisting of Employee objects.
 * 
 * @author Patrick Muradaz
 */
public class Payroll
{
	private List<Employee> employees;

	/**
	 * Constructor.
	 */
	public Payroll()
	{
		this.employees = new ArrayList<Employee>();
	}

	/**
	 * Adds an object of type Employee to this Payroll object.
	 * 
	 * @param employee is the Employee object to be added.
	 */
	public void addEmployee(Employee employee)
	{
		this.employees.add(employee);
	}

	/**
	 * Returns a List object containing the Employee objects in the order in
	 * which they were added to the Payroll. Changing the list returned by this
	 * method will not change the data contained in the Payroll element.
	 * 
	 * @return a List object containing Employee objects.
	 */
	public List<Employee> getEmployees()
	{
		return this.employees;
	}

	/**
	 * Returns an int that is the total of all salaries (including bonuses) for
	 * the Payroll.
	 * 
	 * @return an int total of all salaries and bonuses.
	 */
	public int getTotalSalaries()
	{
		int totalSalaries = 0;

		for (Employee employee : employees)
		{
			totalSalaries += employee.getSalary() + employee.getBonus();
		}

		return totalSalaries;
	}
}
