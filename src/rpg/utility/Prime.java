package rpg.utility;

/**
 * A class that offers some methods that have to do with primes.
 * 
 * The current version offers two methods: 
 * 1) a method to check whether a long is a prime.
 * 2) a method that returns the closest prime to the given number.
 * 
 * @author Robbe
 * @version 1.0
 */
public class Prime {

	/**
	 * Checks whether a given number is prime.
	 * 
	 * @param n
	 *        the number to check for primality.
	 * @return false if the given number is less than 2. 
	 * 		   | if n < 2 
	 * 		   | 	then result == false      
	 * @return true if the given number is 2 or three 
	 * 		   | if (n == 2 || n == 3) 
	 * 		   |	then result == true      
	 * @return false if the given number is divisible by two or three. 
	 * 		   | if (n%2== 0 || n%3 == 0) 
	 *         | 	then result == false
	 * @return false if the given number is divisible by a second number between
	 *         6 and the root of the number, the second number is incremented
	 *         six times each loop, then the first number is checked against the
	 *         increment and decrement of the second number.
	 *         | let sqrtN = sqrt(n) 
	 *         | for i in 6..sqrtN, i+=6 
	 *         | 	if (n%(i-1) == 0 || n%(i+1) == 0 )
	 *         | 		then result == false 
	 * @return true otherwise.
	 */
	public static boolean isPrime(long n) {
		if (n < 2)
			return false;
		if (n == 2 || n == 3)
			return true;
		if (n % 2 == 0 || n % 3 == 0)
			return false;
		long sqrtN = (long) Math.sqrt(n) + 1;
		for (long i = 6L; i <= sqrtN; i += 6) {
			if (n % (i - 1) == 0 || n % (i + 1) == 0)
				return false;
		}
		return true;
	}

	/**
	 * Returns the closest prime to a given number.
	 * 
	 * @param n
	 *       The number of which the nearest prime will be sought.
	 * @pre the given number must be greater than 1 and less than
	 *      9223372036854775783 (biggest prime in range of long values). 
	 *      | n > 1 && n < 9223372036854775783  
	 * @return The nearest prime of a given number. If the two neighbor primes
	 *         have the same distance then the highest one is chosen. 
	 *         | let nextprime = n
	 *         | let previousprime = n 
	 *         | while true 
	 *         | 	nextPrime++
	 *         | 	previousPrime-- 
	 *         | 	if isPrime(nextPrime) 
	 *         | 		then result == nextPrime
	 *         | 	else if isPrime(previousPrime) 
	 *         | 		then result == previousPrime
	 */
	public static long closestPrime(long n) {
		long nextPrime = n;
		long previousPrime = n;
		while (true) {
			nextPrime++;
			previousPrime--;
			if (isPrime(nextPrime)) {
				return nextPrime;
			} else if (isPrime(previousPrime)) {
				return previousPrime;
			}

		}
	}
}
