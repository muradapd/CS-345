package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.SColor;

/**
 * Utility class for determining harmonious colors.
 * (Complementary color, analogous colors, triad colors, tetrad colors, and square colors)
 * 
 * @author Michael Mondoro
 * @version 10.23.19
 *
 */
public class ColorUtils {

	/**
	 * Determines the complementary color of the given SColor object.
	 * 
	 * @param color Color to get complement for
	 * @return color complement 
	 */
	public SColor complementaryColor(SColor color) {
		SColor complement = null;
		double complement_hue = (color.getHue() + 50) % 100;
		complement = new SColor(color.getName() + "_Complement", complement_hue, color.getValue(), color.getChroma(),
				color.getRed(), color.getGreen(), color.getBlue());
		return complement;
	}
	
	/**
	 * Determines all possible analogous colors for the given SColor object.
	 * 
	 * @param color Color to get analogous colors for
	 * @return list of analogous color pairs
	 */
	public List<SColor> analogousColors(SColor color) {
		List<SColor> analogousColors = new ArrayList<SColor>();
		// TODO: Loop through range of colors and create color pairs.
		
		return analogousColors;
	}
}
