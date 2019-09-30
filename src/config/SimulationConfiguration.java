package config;

/**
 * A class that stores the required variables to tune the simulation.
 * 
 * @author Kaan Yıldırım @kyildirim
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
	/**
	 * If <code>true</code>, then the simulation uses random attack throughput that are between <code>{@link SimulationConfiguration#ATTACK_THROUGHPUT_MINIMUM ATTACK_THROUGHPUT_MINIMUM}</code> and <code>{@link SimulationConfiguration#ATTACK_THROUGHPUT_MAXIMUM ATTACK_THROUGHPUT_MAXIMUM}</code>.
	 */
	private static boolean USE_VARYING_ATTACK_THROUGHPUT = false;
	/**
	 * If <code>{@link SimulationConfiguration#USE_VARYING_ATTACK_THROUGHPUT USE_VARYING_ATTACK_THROUGHPUT}</code> is <code>true</code> this is the minimum attack throughput.
	 */
	private static float ATTACK_THROUGHPUT_MINIMUM = 1f;
	/**
	 * If <code>{@link SimulationConfiguration#USE_VARYING_ATTACK_THROUGHPUT USE_VARYING_ATTACK_THROUGHPUT}</code> is <code>true</code> this is the maximum attack throughput.
	 */
	private static float ATTACK_THROUGHPUT_MAXIMUM = 3f;
	/**
	 * If <code>{@link SimulationConfiguration#USE_VARYING_ATTACK_THROUGHPUT USE_VARYING_ATTACK_THROUGHPUT}</code> is <code>false</code> this is the attack throughput.
	 */
	private static float ATTACK_THROUGHPUT = 2f;
	
	
}
