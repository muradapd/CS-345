package Controller;

import View.ViewHandler;

/**
 * Main method for controlling the Spectrum Class
 * 
 * @author Grant Perales
 * @version 10/21/19
 */
public class Spectrum
{

	/**
	 * Starts the spectrum program.
	 * 
	 * @param args from the command line
	 */
	public static void main(String[] args)
	{		
        ViewHandler io = new ViewHandler();
        io.start();
	}
}
