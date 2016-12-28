
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * class responsible for writing knapsack and sat instances to two separate files. 
 * @author jarmstro
 *
 */
public class GeneratorMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Generating a text file and a file for the serialized knapsack instances. The serialized version is for easy Input and Text is for ability to see the actual instances. 
		File f = new File("GeneratedKS.txt");
		File s = new File("GKS.ser");
		KnapsackGenerator generator = new KnapsackGenerator(40,300,100);
		int[] values, costs;
		int budget;
		
		try {
			FileWriter fw = new FileWriter(f);
			FileOutputStream fos = new FileOutputStream(s);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			for(int i = 0 ; i < 50; i++)
			{
				KSInstance ks = generator.generateKnapsack();
				values = ks.getValues();
				costs = ks.getCosts();
				budget = ks.getBudget();
				fw.write(Arrays.toString(values) + "_");
				fw.write(Arrays.toString(costs) + "_");
				fw.write(budget + "\n");
				oos.writeObject(ks);
			}
			fw.flush();
			fw.close();
			oos.flush();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		//Sat can't be serialized because the Literal class isn't serializable due to the static var. So I'll do it old fashioned way and write to txt file.
		File satf = new File("GeneratedSat.txt");
		ThreeSatGenerator satGenerator = new ThreeSatGenerator();
		
		try{
			FileWriter fw = new FileWriter(satf);
			
			StringBuilder sb = new StringBuilder("");
			for(int i = 0 ; i < 5 ; i++)
			{
				SatInstance sat = satGenerator.generateSat();
				ArrayList<Literal> vars = sat.getVariables();
				ArrayList<Clause> clauses = sat.getClauses();
				
				for(Literal l : vars)
				{
					sb.append(l.toString() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
				
				sb.append("Q");
				for(Clause c : clauses)
				{
					sb.append(c.toString() + "-");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append("Q");
				sb.append("\n");
				
				
			}
			String result = new String(sb);
			fw.write(result);
			
			fw.flush();
			fw.close();
			
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
