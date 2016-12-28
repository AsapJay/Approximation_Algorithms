
import java.util.ArrayList;

/**
 * Sat instance bean. Will be used to represent 3SAT and 1in3SAT. 
 * @author jarmstro
 *
 */
public class SatInstance {

	/**
	 * Arraylist of variables
	 */
	private ArrayList<Literal> variables;
	
	/**
	 * ArrayList of clauses
	 */
	private ArrayList<Clause> clauses;
	
	
	/**
	 * constructor - takes in a list of variables and clauses.
	 */
	public SatInstance( ArrayList<Literal> variables, ArrayList<Clause> clauses)
	{
		this.variables = variables;
		this.clauses = clauses;
	}
	
	/**
	 * getter method for clauses. 
	 * @return
	 */
	public ArrayList<Clause> getClauses()
	{
		return clauses;
	}
	
	/**
	 * getter method for variables.
	 */
	public ArrayList<Literal> getVariables()
	{
		return variables;
	}
	
	/**
	 * toString for testing purposes
	 */
	public String toString()
	{
		StringBuilder result = new StringBuilder("");
		result.append("Variables: ");
		for(Literal l : variables)
		{
			result.append(l.toString() + ",");
		}
		result.deleteCharAt(result.length() - 1);
		result.append("\n");
		
		result.append("Clauses: ");
		for(Clause c : clauses)
		{
			result.append(c.toString());
		}
		
		String ret = new String(result);
		
		return ret;
	}
}
