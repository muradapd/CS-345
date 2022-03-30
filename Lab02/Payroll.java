package paychecks;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * An object consisting of PayrollElement objects.
 * 
 * @author Patrick Muradaz
 */
public class Payroll implements PayrollElement
{
	private List<PayrollElement> elements;

	/**
	 * Constructor.
	 */
	public Payroll()
	{
		this.elements = new ArrayList<PayrollElement>();
	}

	/**
	 * Adds a PayrollElement object to this object, in the sense that
	 * getElements returns all elements and only elements added via the add
	 * method.
	 * 
	 * @param element is the element to add to the list.
	 */
	public void add(PayrollElement element)
	{
		this.elements.add(element);
	}

	/**
	 * This method returns all elements in the list.
	 * 
	 * @return all elements in this object.
	 */
	public List<PayrollElement> getElements()
	{
		return this.elements;
	}

	/**
	 * This method returns the String "Payroll".
	 * 
	 * @return "Payroll".
	 */
	public String getName()
	{
		return "Payroll";
	}

	/**
	 * Returns the sum of the total salaries of all PayrollElement objects
	 * contained in the Payroll object. The value of the parameter includeBonus
	 * applies to all intermediate salary totals.
	 */
	@Override
	public int getTotalSalaries(boolean includeBonus)
	{
		int totalSalaries = 0;

		for (int i = 0; i < elements.size(); i++)
		{
			totalSalaries += elements.get(i).getTotalSalaries(includeBonus);
		}

		return totalSalaries;
	}

	/**
	 * prints the salary report at the given indentation, as specified in the UI
	 * design supplied elsewhere.
	 */
	@Override
	public void printSalaryReport(PrintStream out, int indent)
	{
		int thisIndent = indent;

		if (out == null)
		{
			System.out.println(new String(new char[indent]).replace("\0", " ")
					+ getName() + ", total = $" + getTotalSalaries(true)); // print
																			// this
																			// report
			thisIndent += 3;

			for (PayrollElement e : getElements())
			{
				e.printSalaryReport(out, thisIndent);
			}
		} else
		{
			out.println(new String(new char[indent]).replace("\0", " ")
					+ getName() + ", total = $" + getTotalSalaries(true)); // print
																			// this
																			// report
			thisIndent += 3;

			for (PayrollElement e : getElements())
			{
				e.printSalaryReport(out, thisIndent);
			}
		}
	}
}
