
/**
 * Literal bean
 * @author jarmstro
 *
 */
public class Literal {
	
	/**
	 * static counter to keep new variables distinct.
	 */
	static int varCounter;
	
	/**
	 * the number of this particular literal.
	 */
	private int num;
	
	/**
	 * boolean value for negation. If literal is negated ex : v1^ , then this value is true
	 */
	private boolean negated;
	
	
	/**
	 * constructor for new variables. Increments static counter 
	 */
	public Literal()
	{
		num = varCounter;
		varCounter++;
		negated = false;

	}
	
	/**
	 * constructor used to make a copy of another Literal
	 * @param source
	 */
	public Literal(Literal source)
	{
		num = source.num;
		negated = source.negated;
	}
	
	/**
	 * constructor used to create specific literals by passing a number. Can be used for reading off file. Adjust varCounter if num is higher than it. 
	 * @param num
	 */
	public Literal(int num)
	{
		this.num = num;
		if(num >= varCounter)
		{
			varCounter = num;
			varCounter++;
		}
		negated = false;
	}
	
	/**
	 * method to negate the value of negated. Flipping the negation. 
	 */
	public void negate()
	{
		negated = (negated == true) ? false : true;
	}
	
	/**
	 * toString for testing purposes. 
	 */
	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder("");
		result.append(num);
		if(negated == true)
		{
			result.append("-negated");
		}
		String ret = new String(result);
		return ret;
	}
	
	/**
	 * overriding equals --> need to know if two literals share the same number, not if they are the exact same object. 
	 */
	@Override
	public boolean equals(Object obj)
	{
		 if (!(obj instanceof Literal))
	            return false;
	        if (obj == this)
	            return true;
	        Literal temp = (Literal)obj;
		if(num == temp.num && negated == temp.negated)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Overriding hashcode so that HashMap is usable for reductions. 
	 */
	@Override
	public int hashCode()
	{
		int seed = 13;
		seed *= num;
		seed *= 31;
		if(this.negated == true)
		{
			seed+= 1;
		}
		return seed;
	}

}
