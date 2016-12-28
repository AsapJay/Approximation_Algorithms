
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Class that reads generated knapsack and SAT instance file and solves them. 
 * @author jarmstro
 *
 */
public class SolverMain {
	
	public static void main(String[] args) 
	{
		ArrayList<KSInstance> knapsackProblems = new ArrayList<KSInstance>();
		ArrayList<SatInstance> satProblems = new ArrayList<SatInstance>();
											//KnapSack solver portion. 
		
		File s = new File("GKS.ser");
		
		
		FileInputStream fis = null;
		ObjectInputStream ois;
		
			
			
			try {
				fis = new FileInputStream(s);
					try {
						
						
						ois = new ObjectInputStream(fis);
						int count = 1;
						while(true)
						{
							
								KSInstance ks = (KSInstance)ois.readObject();
								knapsackProblems.add(ks);
								System.out.println("Knapsack Instance " + count + " " + "Values: " + Arrays.toString(ks.getValues()) + "  Costs :" + Arrays.toString(ks.getCosts()) + "  Budget: " + ks.getBudget() + "\n" );
								count++;
							
						}
						
						}
					catch(EOFException e)
					{
						//expected
					}
					catch (IOException | ClassNotFoundException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			KnapsackSolver solver = new KnapsackSolver();
			
		
			for(int i = 0 ; i < knapsackProblems.size() ;i++)
			{
				System.out.println(i + 1);
				KSInstance instance = knapsackProblems.get(i);
				System.out.format("Optimal:%d" + "\n" , solver.nWKnapsack(instance).getSolution());
				System.out.format("MinCost:%d" + "\n" , solver.minCostKnapsack(instance).getSolution());
				System.out.format("Approx:%d" + "\n", solver.approxSchemeKnapsack(instance ,.10).getSolution());
				System.out.format("Greedy:%d" + "\n", solver.greedyKnapsack(instance).getSolution());
				
				System.out.println("\n\n\n");
			}
			
			
														// SAT solver Portion
			/*
			 * Not Going to solve random instances of SAT through reduction to knapsack because
			 * small instances of SAT quickly run into overflow problems. 
			 */
			
			
			
			File f = new File("GeneratedSat.txt");
			Scanner scan1 , scan2;
			ArrayList<Literal> vars = new ArrayList<Literal>();
			ArrayList<Clause> clauses = new ArrayList<Clause>();
			try {
				scan1 = new Scanner(f);
				scan1.useDelimiter("Q");
				final int SCANVAR = 0;
				@SuppressWarnings("unused")
				final int SCANCLAUSE = 1;
				int scanType = 0;
				boolean makeSat = false;
				String token;
				Literal tempL;
				
				while(scan1.hasNext())
				{
					
					if(scanType == SCANVAR)
					{
						token = scan1.next();
						if(token.contains("\n"))
						{
							token = token.substring(1, token.length());
						}
						if(token.equals(""))
						{
							break;
						}
						scan2 = new Scanner(token);
						scan2.useDelimiter(",");
						
						// Get all the variables for the Sat Instance
						while(scan2.hasNext())
						{
							tempL = new Literal(scan2.nextInt());
							vars.add(tempL);
						}
						scanType = SCANCLAUSE;
					}
					else
					{
						token = scan1.next();
						scan2 = new Scanner(token);
						scan2.useDelimiter(",");
						// Scan all the Clauses for the SAT instance
						Clause tempC = new Clause();
						
						while(scan2.hasNext())
						{
							String miniToken = scan2.next();
							if(miniToken.contains("("))
							{
								miniToken = miniToken.substring(1, miniToken.length());
							}
							if(miniToken.contains(")"))
							{
								miniToken = miniToken.substring(0, (miniToken.length() - 1));
							}
							if(miniToken.contains("-negated"))
							{
								miniToken = miniToken.substring(0 , miniToken.indexOf("-"));
								tempL = new Literal(new Integer(miniToken));
								tempL.negate();
								tempC.addVar(tempL);
								
							}
							else
							{
								tempL = new Literal(new Integer(miniToken));
								tempC.addVar(tempL);
							}
							
						
						}
						
						clauses.add(tempC);
						scanType = SCANVAR;
						makeSat = true;
					}
					if(makeSat == true)
					{
						SatInstance sat = new SatInstance(vars, clauses);
						vars = new ArrayList<Literal>();
						clauses = new ArrayList<Clause>();
						makeSat = false;
						satProblems.add(sat);
						System.out.println(sat.toString());
					}
				}
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
				
				
			
		
			
		
		
		
	}

}
