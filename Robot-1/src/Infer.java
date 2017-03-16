import java.io.*;
import java.util.ArrayList;


public class Infer {

	private RuleBase rulebase ;
	private Fact fact;
	
	public Infer(RuleBase rulebase,Fact fact){
		this.rulebase = rulebase;
		this.fact = fact;
	}
	
	public String doInference(){
		boolean endflag = false;
		int oldFireNum=0,newFireNum=0 ;
		
		while(!endflag){
			newFireNum = 0;
			 
			for(int i=0;i<rulebase.antesArrays.size();i++){
				boolean fire = true;
				ArrayList<String> temp  = rulebase.antesArrays.get(i);
				for(int j=0;j<temp.size();j++){
					if(!judgeRule(temp.get(j)))
						{
						fire=false;
						break;
						}
				}
    				if(fire){
					newFireNum++;
					doActions(rulebase.actionArrays.get(i));
				}
				
			}
			if(newFireNum==oldFireNum)
				break;
			else
				oldFireNum = newFireNum;
		}
		
		if(fact.POP==1&&fact.path.size()==1)
		   return "NO_PATH";
		if(fact.isSuccess())
			return "SUCCESS";
		if(fact.endBlock())
			return "ENDPOINT_BLOCKED";
		if(fact.POP==1)
			return "POP";
		
		fact.CURRENT_DIFF = Math.abs(fact.NEXT_DIR-fact.currentDir);
		int test1 = fact.doDirDiff(fact.NEXT_DIR,(fact.currentDir+45)%360);
		int test2 = fact.doDirDiff(fact.NEXT_DIR,(fact.currentDir-45)%360);
		System.out.println("next_dir"+fact.NEXT_DIR);
		if(fact.CURRENT_DIFF==0){
			return "MOVE";
		}
		if(test1<=test2){
			return  "LEFT";
		}else{
			return "RIGHT";
		}
		
	}
	
	
	
	public boolean judgeRule(String rule){
		String [] temp;
		if(rule==null||rule.equals(""))
			return true;
		
		if(rule.contains(">=")){
			 temp = rule.split(">=");
			 if(fact.returnVariableValue(temp[0])>=(Integer.parseInt(temp[1])))
				 return true;
		}
		else if(rule.contains("=")){
			temp = rule.split("=");
			 if(fact.returnVariableValue(temp[0])==(Integer.parseInt(temp[1])))
				 return true;
				
		}
		return false;
		
	}
	
	public void doActions(ArrayList<String> tempArray){
		String [] temp;
		
		for(int j=0;j<tempArray.size();j++){
			temp = tempArray.get(j).split("=");
			fact.setAction(temp[0],Integer.parseInt(temp[1]));
		}
	}
	
	
//	public void outputPath(){
//		for(int i=0;i<fact.path.size();i++)
//			System.out.println("path"+fact.path.get(i).toString()+"\t");
//	}
	
}
