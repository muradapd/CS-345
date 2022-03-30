import java.util.ArrayList;

/** ================== Tester for reverse mix. ================== */
	
	@Test
	public void testReverseMix() 
	{
		buildMaps();
		ViewHandler handle = ViewHandler.getInstance();
		List<SColor> colors = new ArrayList<>();
		SColor testRed = new SColor("5.0R", 5.0, 1, 2, 0, 0, 0);
		SColor testYellowRed = new SColor("5.0YR", 5.0, 1, 2, 0, 0, 0);
		SColor testYellow = new SColor("5.0Y", 5.0, 1, 2, 0, 0, 0);
		SColor testGreenYellow = new SColor("5.0GY", 5.0, 1, 2, 0, 0, 0);
		SColor testGreen = new SColor("5.0G", 5.0, 1, 2, 0, 0, 0);
		SColor testBlueGreen = new SColor("5.0BG", 5.0, 1, 2, 0, 0, 0);
		SColor testBlue = new SColor("5.0B", 5.0, 1, 2, 0, 0, 0);
		SColor testPurpleBlue = new SColor("5.0PB", 5.0, 1, 2, 0, 0, 0);
		SColor testPurple = new SColor("5.0P", 5.0, 1, 2, 0, 0, 0);
		SColor testRedPurple = new SColor("5.0RP", 5.0, 1, 2, 0, 0, 0);
		colors.add(testRed);
		colors.add(testYellowRed);
		colors.add(testYellow);
		colors.add(testGreenYellow);
		colors.add(testGreen);
		colors.add(testBlueGreen);
		colors.add(testBlue);
		colors.add(testPurpleBlue);
		colors.add(testPurple);
		colors.add(testRedPurple);
		
		SColor currentColor = new SColor("5.0G", 5.0, 5, 18, 0, 0, 0);
		
		for (SColor color : testSpectrum.getSpectrum())
		{
			if (colors.contains(color))
			{
				handle.palette.add(new SColor(color));
			}
		}
		handle.currentColor = new SColor(currentColor);
		
		// System.out.println(handle.buildColorTest());
	}
	
	/**
	 * Tester to delete.
	 * 
	 * @return the output of the reversemixewr.
	 */
	public String buildColorTest()
	{
		paletteCopy = new ArrayList<>(palette);
		paletteWeights = new int[palette.size()];
		return "To make " + currentColor.toString() + ", mix your palette in the following way:\n" + reverseMix();
	}