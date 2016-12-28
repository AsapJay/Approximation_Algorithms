

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class used to Generate a ThreeSatInstance.
 * Because of the max value of integers we can actually only produce trivial instances of 3SAT to work with our 1in3SAT to SS reduction. 
 * generating 3 vars and 1 clause will give us 7 vars and 3 clauses when we reduce to 1in3 which will cause us to create an int that is 10^10. 
 * @author jarmstro
 *
 */
public class ThreeSatGenerator {

	
	/**
	 * empty constructor
	 */
	public ThreeSatGenerator()
	{
		
	}
	
	/**
	 * method to generate 3SAT Instance
	 * @return SatInstance --> a three Sat Instance
	 */
	public SatInstance generateSat()
	{
		//negate array will be used later to determine if literal is negated or not. 
		int[] negateArray = {0,1};
		//int varCount = (int)(Math.random() * 10) + 3;
		int varCount = 3;
		//int clauseCount = (int)(Math.random() * 10) + 3;
		int clauseCount = 1;
		
		ArrayList<Literal> vars = new ArrayList<Literal>();
		ArrayList<Clause> clauses = new ArrayList<Clause>();
		
		//populate variables array
		for(int i = 0 ; i < varCount ; i++)
		{
			vars.add(new Literal());
		}
		
		for(int i = 0 ; i < clauseCount ; i++)
		{
			Clause clause = new Clause();
			HashMap<Literal, Integer> map = new HashMap<Literal, Integer>();
			
			for(int j = 0 ; j < 3 ; j++)
			{
				//choose random variable from generated vars to add to clause
				int varNum = (int)(Math.random() * varCount);
				
				while(map.containsKey(vars.get(varNum)))
				{
					//re-compute the varNum to find a literal that is not in the clause
					varNum = (int)(Math.random() * varCount);	
				}
				
				//add the new clause variable to the map so it won't enter the clause again. 
				map.put(vars.get(varNum), 1);
				
				int negate = negateArray[(int)(Math.random() * 2)];
				
				Literal temp = new Literal(vars.get(varNum));
				
				//if negate = 0 then the temp will be positive. If 1, then negative
				if(negate == 1)
				{
					temp.negate();
				}
				
				clause.addVar(temp);
			}
			
			clauses.add(clause);
			
		}
		
		SatInstance result = new SatInstance(vars, clauses);
		
		return result;
	}
	
	
}
