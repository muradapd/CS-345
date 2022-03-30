package paychecks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Drives the application. Contains the main method.
 * 
 * @author Patrick Muradaz
 */
public class Paychex
{

	/**
	 * Parse the arguments, build an object of type Payroll, and use the built
	 * payroll object to print a salary report. a. The salary report must be
	 * output to an output file if such is specified in the arguments.
	 * 
	 * @param args command line arguments
	 * @throws FileNotFoundException if the file is not found.
	 */
	public static void main(String[] args)
	{
		/** ===================== Set up containers ===================== */
		Payroll payroll = new Payroll();
		Department d1 = new Department("Department1");
		Department d2 = new Department("Department2");
		Department d3 = new Department("Department3");

		/** ====================== Set up employees ====================== */
		Employee e1 = new Developer("Employee 1", 100000);
		Employee e2 = new Manager("Employee 2", 100000, d1);
		Employee e3 = new Developer("Employee 3", 90000);
		Employee e4 = new Developer("Employee 4", 95000);

		/** ================ Add employees to containers ================ */
		d3.add(e4); // add employee 4 to department 3
		d1.add(e1); // add employee 1 to department 1
		d1.add(e2); // add employee 2 to department 1
		d1.add(d2); // add department 2 to department 1
		d2.add(e3); // add employee 3 to department
		payroll.add(d1); // add department 1 to payroll
		payroll.add(d3); // add department 3 to payroll

		/** ================= Set up PrintStream system ================= */
		File file = new File(args[0]);
		PrintStream out = null;

		if (file.canWrite())
		{ // if the file can be written to then set up that function
			try
			{
				out = new PrintStream(file);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}

		/** =============== Print to specified PrintStream =============== */
		payroll.printSalaryReport(out, 0);
	}
}
