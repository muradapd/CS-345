package paychecks;

import java.io.PrintStream;

/**
 * PayrollElement is an abstract interface that represents any entity that can
 * be part of a payroll and for which a total salary can be computed.
 * 
 * @author Patrick Muradaz
 */
public abstract interface PayrollElement
{

	/**
	 * This method returns the total salary of the element. If the parameter
	 * includeBonus is true, this total will include bonuses, otherwise it will
	 * not.
	 * 
	 * @param includeBonus determines whether to include bonuses.
	 * @return the total salary of the element.
	 */
	public int getTotalSalaries(boolean includeBonus);

	/**
	 * This method has a void return type. It prints to the given PrintStream a
	 * salary report for the PayrollElement as specified by the SRS supplied
	 * elsewhere. The salary report should be printed starting an indentation
	 * given by the indentation parameter.
	 * 
	 * @param out    is the given print stream to print to.
	 * @param indent the indent to start printing at.
	 */
	public void printSalaryReport(PrintStream out, int indent);
}
