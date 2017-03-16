import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class RuleBase {

	private final String filename = "res/rule";
	private ArrayList<String> antecedent = new ArrayList<String>();
	private ArrayList<String> actions = new ArrayList<String>();
	public ArrayList<ArrayList<String>> antesArrays  = new ArrayList<ArrayList<String>>();
	public ArrayList<ArrayList<String>> actionArrays  = new ArrayList<ArrayList<String>>();
	
	private void getRules()
	{
	        File file = new File(filename);
	        BufferedReader reader = null;
	        try {
	           // System.out.println("Read file by line");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            String [] temp ;
	            
	            int line = 1;
	            // Read file by line until null
	            while ((tempString = reader.readLine()) != null) {
	               // System.out.println("line " + line + ": " + tempString);
	                temp = tempString.split("->");
	                antecedent.add(temp[0]);
	                actions.add(temp[1]);
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	 }

	private void breakAntecedent(){
		String [] temps ;
		
		for(int i=0;i<antecedent.size();i++){
			ArrayList<String> tempArray = new ArrayList<String>();
			temps = antecedent.get(i).split("&");
			for(int j=0;j<temps.length;j++){
				tempArray.add(temps[j]);
			}
			antesArrays.add(tempArray);
		
		}
		
		
	} 
	private void breakActions(){
		String [] temps2 ;
		
		for(int i=0;i<actions.size();i++){
			temps2 = actions.get(i).split("&");
			ArrayList<String> tempArray2 = new ArrayList<String>();
			for(int j=0;j<temps2.length;j++)
				tempArray2.add(temps2[j]);
			actionArrays.add(tempArray2);
		}
	}
	
	public RuleBase()
	{
		getRules();
		breakAntecedent();
		breakActions();
	}
	
	public String toString(){
		String [] temps ;
		ArrayList<String> tempArray = new ArrayList<String>();
		for(int i=0;i<antesArrays.size();i++){
			tempArray = antesArrays.get(i);
			
			System.out.println("tempsize"+tempArray.size());
			for(int j=0;j<tempArray.size();j++)
				System.out.println("temp2"+tempArray.get(j));
		}
		return "";
	}
	
	
}
