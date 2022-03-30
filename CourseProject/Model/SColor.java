package Model;

import javax.swing.JComponent;

/**
 * This class is an object that represents a Munsell color and it's
 * corresponding RGB color.
 * 
 * @author Patrick Muradaz
 * @version 10/23/19
 */
@SuppressWarnings("serial")
public class SColor extends JComponent
{
	private String name;
	private double hue;
	private int value;
	private int chroma;
	private int red;
	private int green;
	private int blue;

	/**
	 * Constructor. Builds a color based on input values.
	 * 
	 * @param name   is the name of this color as a string.
	 * @param hue    is the hue of this color.
	 * @param value  is the value of this color.
	 * @param chroma is the chroma of this color.
	 * @param red    is the red representation of this color.
	 * @param green  is the green representation of this color.
	 * @param blue   is the blue representation of this color.
	 */
	public SColor(String name, double hue, int value, int chroma,
			int red, int green, int blue)
	{
		if (value == 0)
		{
			this.name = "Black";
		} else if (value == 10)
		{
			this.name = "White";
		} else if (chroma == 0)
		{
			this.name = "V" + value;
		} else
		{
			this.name = name;
		}
		this.hue = hue;
		this.value = value;
		this.chroma = chroma;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/**
	 * Getter for this color's name.
	 * 
	 * @return the name of this color.
	 */
	@Override
	public String getName()
	{
		return this.name;
	}

	/**
	 * Getter for this color's hue.
	 * 
	 * @return the hue of this color.
	 */
	public double getHue()
	{
		return this.hue;
	}

	/**
	 * Getter for this color's value.
	 * 
	 * @return the value of this color.
	 */
	public int getValue()
	{
		return this.value;
	}

	/**
	 * Getter for this color's chroma.
	 * 
	 * @return the chroma of this color.
	 */
	public int getChroma()
	{
		return this.chroma;
	}

	/**
	 * Getter for this colors red representation.
	 * 
	 * @return the red representation of this color.
	 */
	public int getRed()
	{
		return this.red;
	}

	/**
	 * Getter for this colors green representation.
	 * 
	 * @return the green representation of this color.
	 */
	public int getGreen()
	{
		return this.green;
	}

	/**
	 * Getter for this colors blue representation.
	 * 
	 * @return the blue representation of this color.
	 */
	public int getBlue()
	{
		return this.blue;
	}

	@Override
	public String toString()
	{
		return "Name: " + name + " Hue: " + hue + " Value: " + value
				+ " Chroma: " + chroma;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other.getClass().isInstance(this))
		{
			return this.hue == ((SColor) other).getHue()
					&& this.value == ((SColor) other).getValue()
					&& this.chroma == ((SColor) other).getChroma();
		}
		return false;
	}
}
