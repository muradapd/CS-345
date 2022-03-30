package paychecks;

import java.io.PrintStream;

/**
 * Abstract class that represents an employee regardless of title.
 * 
 * @author Patrick Muradaz
 */
public abstract class Employee implements PayrollElement
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

	/**
	 * Returns the salary of this employee, including the bonus if and only if
	 * includeBonus is true.
	 */
	@Override
	public int getTotalSalaries(boolean includeBonus)
	{
		int totalSalary = 0;

		if (includeBonus)
		{
			totalSalary = this.getSalary() + getBonus();
		} else
		{
			totalSalary = this.getSalary();
		}

		return totalSalary;
	}

	/**
	 * Prints, to the given PrintStream, with the given indentation, the name,
	 * title, and salary of this employee, as specified in the UI design
	 * supplied elsewhere.
	 */
	@Override
	public void printSalaryReport(PrintStream out, int indent)
	{
		if (out == null)
		{
			System.out.println(new String(new char[indent]).replace("\0", " ")
					+ getName() + ", " + getTitle() + ", $"
					+ getTotalSalaries(true)); // print
																			// this
																			// report
		} else
		{
			out.println(new String(new char[indent]).replace("\0", " ")
					+ getName() + ", " + getTitle() + ", $"
					+ getTotalSalaries(true)); 								// print
																			// this
																			// report
		}
	}
}
