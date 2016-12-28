
import java.io.Serializable;
import java.util.Arrays;

public class KSInstance implements Serializable {
	
	
	private int[] values;
	private int[] costs;
	private int budget;
	
	public KSInstance(int[] vals, int[] costs, int t)
	{
		values = vals;
		this.costs = costs;
		budget = t;
	}
	
	public int[] getValues()
	{
		return values;
	}
	
	public int[] getCosts()
	{
		return costs;
	}
	
	public int getBudget()
	{
		return budget;
	}
	
	/**
	 * toString for testing purposes
	 */
	public String toString()
	{
		String result = "values : " + Arrays.toString(values) + "\n" + "Costs : " + Arrays.toString(costs) + "\n" + "budget : " + budget;
		return result;
	}

}