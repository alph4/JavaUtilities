package g21.util;


/**
 * @author Gram21 
 *
 */
public final class G21Math {
	
	/**
	 * Constructor. Shouldn't be instanced.
	 */
	private G21Math() {
		//keep private like _Ivan_'s identity, no instances
	}
	
	/**
	 * Tests if a provided integer is even
	 * @param number integer to be tested
	 * @return true if the provided number is even, else false.
	 */
	public static boolean isEven(int number) {
		return ((number & 1) == 0);
	}
	
}
