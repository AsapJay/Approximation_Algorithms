import java.util.ArrayList;

/**
 * Clause bean
 * @author jarmstro
 *
 */
public class Clause {
	
	/**
	 * arraylist of variables in the clause
	 */
	private ArrayList<Literal> varList;
	
	/**
	 * constructor made for 3SAT instance
	 * @param v1
	 * @param v2
	 * @param v3
	 */
	public Clause(Literal v1, Literal v2, Literal v3)
	{
		varList = new ArrayList<Literal>();
		varList.add(v1);
		varList.add(v2);
		varList.add(v3);
		
	}
	
	/**
	 * empty constructor, used to create clauses from the ground up. 
	 */
	public Clause()
	{
		varList = new ArrayList<Literal>();
	}
	
	/**
	 * getter method for varList
	 * @return
	 */
	public ArrayList<Literal> getVarList()
	{
		return varList;
	}
	
	/**
	 * method to add literal to the clause. 
	 * @param l
	 */
	public void addVar(Literal l)
	{
		varList.add(l);
	}
	
	/**
	 * toString method for testing purposes. 
	 */
	public String toString()
	{
		StringBuilder result = new StringBuilder("");
		result.append("(");
		for(Literal l : varList)
		{
			result.append(l.toString() + ",");
		}
		result.deleteCharAt(result.length() - 1);
		result.append(")");
		String ret = new String(result);
		return ret;
	}
}


