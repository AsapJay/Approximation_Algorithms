

/**
 * Class to house the result of a knapsack solver --> the max value achievable and the running Time
 * @author jarmstro
 *
 */
public class KsResult {

	/**
	 * int value to store running time
	 */
	private int rTime;
	
	/**
	 * int value to store knapsack value
	 */
	private int solution;
	
	/**
	 * basic constructor 
	 * @param sol
	 * @param time
	 */
	public KsResult(int sol , int time)
	{
		solution = sol;
		rTime = time;
	}
	
	/**
	 * getter method for solution
	 */
	public int getTime()
	{
		return rTime;
	}
	
	/**
	 * getter method for time
	 */
	public int getSolution()
	{
		return solution;
	}
	
	/**
	 * toString method 
	 */
	public String toString()
	{
		String result = "Solution: " + solution + " Running Time: " + rTime + " seconds";
		return result;
	}
	
}
