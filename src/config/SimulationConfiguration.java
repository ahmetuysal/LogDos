package config;

/**
 * A class that stores the required variables to tune the simulation.
 * 
 * @author kyildirim
 *
 */
public class SimulationConfiguration {

	/**
	 * If <code>true</code>, then the entire simulation uses one single rate for false positive rate defined by <code>{@link SimulationConfiguration#FALSE_POSITIVE_RATE FALSE_POSITIVE_RATE}</code>.
	 */
	private static boolean USE_SINGLE_FALSE_POSITIVE_RATE = true;
	/**
	 * If <code>true</code>, and <code>{@link SimulationConfiguration#USE_SINGLE_FALSE_POSITIVE_RATE USE_SINGLE_FALSE_POSITIVE_RATE}</code> is <code>false</code>, then the simulation uses automatically adjusting false positive rates in correlation with the size of a <code>{@link Domain}</code>.
	 */
	private static boolean USE_AUTO_ADJUSTING_FALSE_POSITIVE_RATES = false;
	/**
	 * If <code>{@link SimulationConfiguration#USE_AUTO_ADJUSTING_FALSE_POSITIVE_RATES USE_AUTO_ADJUSTING_FALSE_POSITIVE_RATES}</code> is <code>true</code> this is the upper limit for the false positive rate.
	 */
	private static float FALSE_POSITIVE_RATE_MAXIMUM = 5f;
	/**
	 * If <code>{@link SimulationConfiguration#USE_AUTO_ADJUSTING_FALSE_POSITIVE_RATES USE_AUTO_ADJUSTING_FALSE_POSITIVE_RATES}</code> is <code>true</code> this is the lower limit for the false positive rate.
	 */
	private static float FALSE_POSITIVE_RATE_MINIMUM = 1f;
	/**
	 * If <code>{@link SimulationConfiguration#USE_SINGLE_FALSE_POSITIVE_RATE USE_SINGLE_FALSE_POSITIVE_RATE}</code> is <code>true</code> this is the false positive rate.
	 */
	private static float FALSE_POSITIVE_RATE = 2f;
	
}
