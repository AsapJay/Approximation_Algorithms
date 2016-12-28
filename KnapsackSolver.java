
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Class that will hold all the knapsack algorithms
 * @author jarmstro
 *
 */
public class KnapsackSolver {
	
	/**
	 * Logger
	 */
	private static final Logger log = Logger.getLogger(KnapsackSolver.class.getName());
	
	/**
	 * Empty constructor
	 */
	public KnapsackSolver()
	{
		
	}
	
	/**
	 * O(nW) knapsack algorithm
	 * @param val the array of values for each item
	 * @param cost the array of costs for each item
	 * @param budget the budget for the knapsack
	 * @return The best value achievable, given the inputs
	 */
	
	public KsResult nWKnapsack(KSInstance i)
	{
		return nWKnapsack(i.getValues() , i.getCosts() , i.getBudget());
	}
	private KsResult nWKnapsack(int[] val, int[] cost, int budget)
	{
		int[][] sol = new int[val.length + 1][budget + 1];
		long startTime = new Date().getTime();
		
		
		for(int i = 0 ; i <= val.length ; i++)
		{
			for(int j = 0 ; j <= budget ; j++)
			{
				
				if(i == 0 || j == 0 )
				{
					sol[i][j] = 0;
				}
				else if(cost[i - 1] <= j)
				{
					sol[i][j] = Math.max(sol[i-1][j - cost[i - 1]] + val[i - 1], sol[i-1][j]);
				}
				else
				{
					sol[i][j] = sol[i-1][j];
				}
					
			}
		}
		
		int solution = sol[val.length][budget];
		long endTime = new Date().getTime();
		int runTime = (int)(endTime - startTime);
		KsResult result = new KsResult(solution , runTime);
		return result;
		
	}
	
	public KsResult minCostKnapsack(KSInstance i)
	{
		return minCostKnapsack(i.getValues() , i.getCosts() , i.getBudget());
	}
	/**
	 * O(n^2 * v(maxVal)) algorithm to solve knapsack based on minCost
	 */
	private KsResult minCostKnapsack(int[] val , int[] cost , int budget)
	{
		long startTime = new Date().getTime();
		int maxVal = findMax(val);
		int mCLength = (maxVal * val.length);
		int nextT;
		int[][] minCost = new int[val.length + 1][mCLength + 1];
		boolean[][] take = new boolean[val.length + 1][mCLength + 1];
		
		for(int i = 0 ; i <= val.length ; i++)
		{
			minCost[i][0] = 0 ;
		}
		//when number of items available is 0, minCost == 0
		for(int i = 0 ; i < mCLength ; i++)
		{
			minCost[0][i] = 0;
		}
		
		for(int t = 1 ; t <= val[0] ;t++)
		{
			minCost[1][t] = cost[0];
			take[1][t] = true;
		}
		
		for(int t = val[0] + 1 ; t <= mCLength; t++)
		{
			//subtracted a billion from the max value so it wont overflow and end up negative.
			//I'm assuming 1.1 billion can serve as infinity for us. 
			minCost[1][t] = Integer.MAX_VALUE - 1000000000;
			take[1][t] = false;
		}
		
		//i in the loop is how many items we have access to
		//i - 1 is the actual value of the ith item. 
		//I set up my minCost array to not have to worry about off by 1, but val and cost both start at zero.
		//looking back on the project...that was a mistake. 
		for(int i = 2 ; i <= val.length ; i++)
		{
			for(int t = 1 ; t <= mCLength ; t++)
			{
				nextT = Math.max(0, (t - val[i - 1]));
				if(minCost[i - 1][t] <= (cost[i - 1] + minCost[i - 1][nextT]))
				{
					minCost[i][t] = minCost[i - 1][t];
					take[i][t] = false;
				}
				else
				{
					minCost[i][t] = cost[i - 1] + minCost[i - 1][nextT];
					take[i][t] = true;
				}
				
			}
		}
		
		int solution = minCostGetResult(minCost, budget);
		long endTime = new Date().getTime();
		int runTime = (int)(endTime - startTime);
		KsResult result = new KsResult(solution , runTime);
		return result;
	}
	
	/**
	 * Method used to traverse the take array from minCost and get the value
	 * @param minCost , the minCost array from minCostKnapsack
	 * @param budget , the budget constraint. 
	 * @return optimal Value returned by minCostKnapsack
	 */
	private int minCostGetResult(int[][] minCost , int budget)
	{
		//this will set optimalVal to MCLength from minCostKnapsack
		int optimalVal = minCost[0].length - 1 ;
		while(optimalVal >= 0 && minCost[minCost.length - 1][optimalVal] > budget)
		{
			
			optimalVal--;
		}
		
		return optimalVal;
	}
	
	/**
	 * helper method to find max value in array.
	 * @param array
	 * @return max value in int array
	 */
	private int findMax(int[] array)
	{
		int max = 0;
		
		for(int i = 0 ; i < array.length ; i++)
		{
			if(array[i] > max)
			{
				max = array[i];
			}
		}
		return max;
	}
	
	/**
	 * helper method to return the max index for the max value. 
	 * Used in greedyknapsack
	 * @param i
	 * @return
	 */
	private int findMaxIdx(int[] array)
	{
int max = 0;
int idx = 0;
		
		for(int i = 0 ; i < array.length ; i++)
		{
			if(array[i] > max)
			{
				max = array[i];
				idx = i;
			}
		}
		return idx;
	}
	
	public KsResult greedyKnapsack(KSInstance i)
	{
		return greedyKnapsack(i.getValues() , i.getCosts() , i.getBudget());
	}
	
	/**
	 * modified greedy knapsack algorithm
	 * @param val array of values
	 * @param cost array of costs
	 * @param budget , budget constraint
	 * @return best value achievable with budget constraint using greedy approach
	 */
	public KsResult greedyKnapsack(int[] val , int[] cost, int budget)
	{
		long startTime = new Date().getTime();
		int result = 0;
		//create double array with v/c ratio
		double[] vcRatio = new double[val.length];
		
		for(int i = 0 ; i < val.length; i++)
		{
			vcRatio[i] = (double)val[i] / (double)cost[i];
		}
		modifiedQSort(vcRatio, cost, val, 0, (vcRatio.length - 1));
		int moneyLeft = budget;
		int i = 0;
		while(moneyLeft > 0 && i < val.length)
		{
			if(cost[i] <= moneyLeft)
			{
				result += val[i];
				moneyLeft -= cost[i];
				
			}
			i++;
		}
		int maxVal = findMax(val);
		if(maxVal > result && cost[findMaxIdx(val)] <= budget)
		{
			result = maxVal;
		}
		
		long endTime = new Date().getTime();
		int runTime = (int)(endTime - startTime);
		KsResult ret = new KsResult(result , runTime );
		return ret;
	}
	
	/**
	 * modified quick sort to sort all 3 arrays at once based on how vcRatio is sorted
	 * @param vcRatio 
	 * @param cost
	 * @param val
	 * @param left
	 * @param right
	 */
	private void modifiedQSort(double[] vcRatio, int[] cost, int[] val, int left, int right)
	{
		int pivotP, l, r;
		double pivot;
		l = left;
		r = right;
		pivot = vcRatio[(l + r) / 2];
		
		while(l <= r)
		{
			//stop when left value is less than pivot value
			while(vcRatio[l] > pivot)
			{
				l++;
			}
			
			//stop when right value is greater than the pivot value
			while(vcRatio[r] < pivot)
			{
				r--;
			}
			if(l <= r)
			{
				//swap l and r values in all arrays
				double temp = vcRatio[l];
				int iTempCost = cost[l];
				int iTempVal = val[l];
				vcRatio[l] = vcRatio[r];
				cost[l] = cost[r];
				val[l] = val[r];
				vcRatio[r] = temp;
				cost[r] = iTempCost;
				val[r] = iTempVal;
				l++;
				r--;
			}
		}
		
		if(left < r )
		{
			modifiedQSort(vcRatio, cost, val, left , r );
		}
		if(l < right )
		{
			modifiedQSort(vcRatio,cost,val,l,right);
		}
		
		return;
	}
	public KsResult  approxSchemeKnapsack(KSInstance i , double e)
	{
		return  approxSchemeKnapsack(i.getValues() , i.getCosts() ,e, i.getBudget());
	}
	
	private KsResult approxSchemeKnapsack(int[] val, int[] cost, double e, int budget)
	{
		long startTime = new Date().getTime();
		ArrayList<Integer> sol;
		double F;
		int maxVal = findMax(val);
		F = e * (maxVal / val.length);
	
		int[] scaledVal = new int[val.length];
		for(int i = 0 ; i < scaledVal.length; i++)
		{
			scaledVal[i] = (int)(((double)val[i])/F);
		}
		
		sol = minCostHelper(scaledVal, cost, budget);
		
		//log.info("sol size =" + sol.size());
		int result = 0;
		for(int i = 0 ; i < sol.size(); i++)
		{
			//log.info("number being added to sol = " +  val[sol.get(i)]);
			result += val[sol.get(i)];
		}
		
		
		long endTime = new Date().getTime();
		int runTime = (int)(endTime - startTime);
		KsResult ret = new KsResult(result , runTime );
		return ret;
	}
	
	/**
	 * minCost helper method for the approx scheme. returns list of integers to take for solution.  
	 */
	private ArrayList<Integer> minCostHelper(int[] val , int[] cost , int budget)
	{
		int maxVal = findMax(val);
		int mCLength = (maxVal * val.length);
		int nextT;
		int[][] minCost = new int[val.length + 1][mCLength + 1];
		boolean[][] take = new boolean[val.length + 1][mCLength + 1];
		
		for(int i = 0 ; i <= val.length ; i++)
		{
			minCost[i][0] = 0 ;
		}
		//when number of items available is 0, minCost == 0
		for(int i = 0 ; i < mCLength ; i++)
		{
			minCost[0][i] = 0;
		}
		
		for(int t = 1 ; t <= val[0] ;t++)
		{
			minCost[1][t] = cost[0];
			take[1][t] = true;
		}
		
		for(int t = val[0] + 1 ; t <= mCLength; t++)
		{
			//subtracted a billion from the max value so it wont overflow and end up negative.
			//I'm assuming 1.1 billion can serve as infinity for us. 
			minCost[1][t] = Integer.MAX_VALUE - 1000000000;
			take[1][t] = false;
		}
		
		for(int i = 2 ; i <= val.length ; i++)
		{
			for(int t = 1 ; t <= mCLength ; t++)
			{
				nextT = Math.max(0, (t - val[i - 1]));
				if(minCost[i - 1][t] <= (cost[i - 1] + minCost[i - 1][nextT]))
				{
					minCost[i][t] = minCost[i - 1][t];
					take[i][t] = false;
				}
				else
				{
					minCost[i][t] = cost[i - 1] + minCost[i - 1][nextT];
					take[i][t] = true;
				}
				
			}
		}
		
		return minCostGetItems(val,minCost, take, budget);
	}
	
	private ArrayList<Integer> minCostGetItems(int[] val, int[][] minCost, boolean[][] take,int budget)
	{
		int optimalVal = minCost[0].length - 1 ;
		while(optimalVal >= 0 && minCost[minCost.length - 1][optimalVal] > budget)
		{
			
			optimalVal--;
		}
		
		ArrayList<Integer> sol = new ArrayList();
		
		int i = minCost.length - 1;
		int t = optimalVal;
		
		while( i > 0 && t > 0)
		{
			if(take[i][t] == true)
			{
				sol.add((i - 1));
				t -= val[i - 1];
			}
			
			i--;
		}
		return sol;
	}

}
