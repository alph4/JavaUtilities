package g21.util;


/**
 * @author Gram21 
 *
 */
public final class G21Math {
	
	/**
	 * Constructor. Shouldnt be instanced.
	 */
	private G21Math() {
		//keep private, no instances
	}
	
	/**
	 * Tests if a provided int is even
	 * @param number int to be tested
	 * @return true if the provided nubmer is even, else false
	 */
	public static boolean isEven(int number) {
		return ((number & 1) == 0);
	}
	
}
