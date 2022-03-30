package paychecks;

/**
 * Drives the application. Contains the main method.
 * 
 * @author Patrick Muradaz
 */
public class Paychex
{

	/**
	 * Parses arguments, builds an object of type Payroll, and uses
	 * PayrollPrinter to print a payroll report.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args)
	{
		Employee employee;
		Payroll payroll = new Payroll();

		if (args.length % 3 != 0)
		{
			System.err.print("The number of arguments must be "
					+ "divisible by three.\n");
			System.exit(1);
		} else
		{
			for (int i = 1; i < args.length; i += 3)
			{
				if (!args[i].equals("M") && !args[i].equals("D"))
				{
					System.err.print("Employee type must be one of D "
							+ "or M. You gave " + args[i] + ".\n");
					System.exit(1);
				}
			}

			for (int i = 2; i < args.length; i += 3)
			{
				try
				{
					Integer.parseInt(args[i]);
				} catch (NumberFormatException nfe)
				{
					System.err.print("The string '" + args[i] + "' is not a "
							+ "legal integer.\n");
					System.exit(1);
				}
			}

			for (int i = 1; i < args.length; i += 3)
			{
				if (args[i].equals("D"))
				{
					employee = new Developer(args[i - 1],
							Integer.parseInt(args[i + 1]));
					payroll.addEmployee(employee);

				} else if (args[i].equals("M"))
				{
					employee = new Manager(args[i - 1],
							Integer.parseInt(args[i + 1]));
					payroll.addEmployee(employee);
				}
			}

			PayrollPrinter.printSalaryReport(payroll);
		}
	}
}
