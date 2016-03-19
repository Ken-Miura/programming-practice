/* Copyright (C) 2015 Ken Miura */
package gui2_2;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Ken Miura
 *
 */
final class ColorNameConverter {

	private static final Map<String, Color> colorSet = new HashMap<>();

	static {
		colorSet.put("BLACK", Color.BLACK);
		colorSet.put("BLUE", Color.BLUE);
		colorSet.put("CYAN", Color.CYAN);
		colorSet.put("DARK GRAY", Color.DARK_GRAY);
		colorSet.put("GRAY", Color.GRAY);
		colorSet.put("GREEN", Color.GREEN);
		colorSet.put("LIGTHT GRAY", Color.LIGHT_GRAY);
		colorSet.put("MAGENTA", Color.MAGENTA);
		colorSet.put("ORANGE", Color.ORANGE);
		colorSet.put("PINK", Color.PINK);
		colorSet.put("RED", Color.RED);
		colorSet.put("WHITE", Color.WHITE);
		colorSet.put("YELLOW", Color.YELLOW);
	}
	
	private ColorNameConverter() {
		throw new AssertionError(getClass() + "cannot be instanciated.");
	}
	
	static String convertColorToName (Color color) {
		Set<Map.Entry<String, Color>> entrySet = colorSet.entrySet();
		for (final Map.Entry<String, Color> entry: entrySet) {
			if (color.equals(entry.getValue())) {
				return entry.getKey();
			}
		}		
		throw new AssertionError("not to be passed");		
	}

	static Color convertNameToColor (String name) {
		return colorSet.get(name);
	}
	
	static Set<String> getColorNameSet () {
		return colorSet.keySet();
	}
	
	static Set<Color> getColorSet () {
		return new HashSet<Color>(colorSet.values());
	}
}
