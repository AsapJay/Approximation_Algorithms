
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Class to house both of the SAT Reducers
 * @author jarmstro
 *
 */
public class SatReducer {
	
	/**
	 * logger
	 */
	private static Logger log = Logger.getLogger(SatReducer.class.getName());
	
	/**
	 * empty constructor
	 */
	public SatReducer()
	{
		
	}
	
	/**
	 * 3SAT to 1in3Sat reduction method. 
	 * @return SatInstance will return a 1in3Sat Instance if a 3SAT instance is given as a parameter. 
	 */
	public SatInstance ThreeSatReduction(SatInstance threeSat)
	{
		ArrayList<Clause> sourceClauses;
		ArrayList<Literal> sourceVars;		
        ArrayList<Clause> resultClauses;
		ArrayList<Literal> resultVars;
		
		sourceClauses = threeSat.getClauses();
		sourceVars = threeSat.getVariables();
		
		//initialize result arraylists
		resultClauses = new ArrayList<Clause>();
		resultVars = new ArrayList<Literal>();
		
		for(int i = 0 ; i < sourceVars.size(); i++)
		{
			resultVars.add(sourceVars.get(i));
		}
		
		for(int i = 0 ; i < sourceClauses.size() ; i++)
		{
			int varIdx = 0;
			Literal templ1, templ2,templ3,templ4, templnegate;
			Clause tempc1,tempc2,tempc3;
			templ1 = new Literal();
			templ2 = new Literal();
			templ3 = new Literal();
			templ4 = new Literal();
			
			templnegate = new Literal(sourceVars.get(varIdx));
			templnegate.negate();
			tempc1 = new Clause(new Literal(templnegate),templ1,templ2);
			varIdx++;
			tempc2 = new Clause(templ2, new Literal(sourceVars.get(varIdx)), templ3);
			varIdx++;
			templnegate = new Literal(sourceVars.get(varIdx));
			templnegate.negate();
			tempc3 = new Clause(templ3, templ4, new Literal(templnegate));
			
			resultVars.add(templ1);
			resultVars.add(templ2);
			resultVars.add(templ3);
			resultVars.add(templ4);
			
			resultClauses.add(tempc1);
			resultClauses.add(tempc2);
			resultClauses.add(tempc3);
		}
		
		SatInstance result = new SatInstance(resultVars, resultClauses);
		return result;
			
			
			
		}
	
	/**
	 * 1in3SAT to SubsetSum Reduction. 
	 */
	public KSInstance OneInThreeSatReduction(SatInstance sat)
	{
		int idx = 0 ; 
		HashMap<Object , Integer> values = new HashMap<Object, Integer>();
		ArrayList<Literal> vars = sat.getVariables();
		ArrayList<Clause> clauses = sat.getClauses();
		int budget = 0;// budget for KSInstance
		ArrayList<Integer> costs = new ArrayList<Integer>(); //costs for KSInstance
		
		for(int i = 0 ; i < vars.size() ; i++)
		{
			values.put(vars.get(i) ,(int)Math.pow(10, idx));
			budget += Math.pow(10, idx);
			idx++;
		}
		for(int i = 0 ; i < clauses.size() ; i++)
		{
			values.put(clauses.get(i) , (int)Math.pow(10, idx));
			budget += Math.pow(10, idx);
			idx++;
		}
		
		//for each variable.
		for(int i = 0 ; i < vars.size() ; i++)
		{
			Integer temp1, temp1Prime;
			temp1 = 0 + values.get(vars.get(i));
			temp1Prime = 0 + values.get(vars.get(i));
			Literal positiveVar = new Literal(vars.get(i));
			Literal negativeVar = new Literal(vars.get(i));
			
			negativeVar.negate();
			
			//for each clause check to see if it contains that variable either positive or negative. 
			for(int j = 0 ; j < clauses.size(); j++)
			{
				//get the variables in the clause
				ArrayList<Literal> clauseVars = clauses.get(j).getVarList();
				//loop through the variables in the clause to see if it contains variable vars.get(i) either positive or negative. 
				for(int l = 0; l < clauseVars.size(); l++ )
				{
					if(clauseVars.get(l).equals(positiveVar))
						{
							temp1 += values.get(clauses.get(j));
						}
					if(clauseVars.get(l).equals(negativeVar))
						{
							temp1Prime += values.get(clauses.get(j));
						}
				}
			}
			
			//add both temps to costs ArrayList
			costs.add(temp1);
			costs.add(temp1Prime);
		}
		
		//Now convert ArrayList to array to create KSInstance
		int[] resultVals = new int[costs.size()];
		int[] resultCosts = new int[costs.size()];
		for(int i = 0 ; i < costs.size(); i++)
		{
			resultVals[i] = costs.get(i);
			resultCosts[i] = costs.get(i);
		}
		
		KSInstance result = new KSInstance(resultVals, resultCosts, budget);
		
		return result;
		
		
	}
		
	

}
