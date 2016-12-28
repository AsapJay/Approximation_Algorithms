
/**
 * Class responsible for generating knapsack instances. 
 * @author JR
 *
 */
public class KnapsackGenerator {

	private int maxNumItems;
	private int maxBudget;
	private int maxValAndCost;
	public KnapsackGenerator(int maxItems, int maxBudget , int maxValAndCost)
	{
		this.maxNumItems = maxItems;
		this.maxBudget = maxBudget;
		this.maxValAndCost = maxValAndCost;
		
	}
	
	public KSInstance generateKnapsack()
	{
		int arraySize = (int)(Math.random() * maxNumItems) + 1;
		int[] values = generateRandArray(arraySize);
		int[] costs = generateRandArray(arraySize);
		int budget = (int)(Math.random() * maxBudget) + 1; 
		
		KSInstance result = new KSInstance(values, costs,budget);
		
		return result;
		
	}
	
	
	private int[] generateRandArray(int size)
	{
		int[] result = new int[size];
		for(int i = 0 ; i < size ; i++)
		{
			int v = (int)(Math.random() * maxValAndCost) + 1;
			result[i] = v;
			
		}
		return result;
	}
	
	
}