package paychecks;

import java.util.List;

/**
 * Method for printing the payroll. Used by application driver. Not meant to be
 * instanciated.
 * 
 * @author Patrick Muradaz
 */
public class PayrollPrinter
{
	/**
	 * Prints out a salary report for the given Payroll object.
	 * 
	 * @param payroll is the Payroll object to be printed.
	 */
	public static void printSalaryReport(Payroll payroll)
	{
		List<Employee> list = payroll.getEmployees();

		for (Employee employee : list)
		{
			System.out.println(employee.getName() + ", " + employee.getTitle()
					+ ": $" + (employee.getSalary() + employee.getBonus()));
		}

		System.out.println("Total Salaries: $" + payroll.getTotalSalaries());
	}
}
