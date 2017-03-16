import java.io.*;
import java.util.Scanner;
import javax.swing.*;

import java.awt.*;

public class Robot extends JFrame{

	
	public Robot(){
		
		this.setTitle("Robot program");
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel map = new JPanel();
		
		
		RuleBase ruleBase = new RuleBase();
		int m,n,obstaclesPrecent,currentDir;
		int startx,starty,endx,endy;
		 Scanner input = new Scanner(System.in);
		 System.out.println("please input the size of map, m and n");
		 m = input.nextInt();
		 n = input.nextInt();
		 System.out.println("please input obstaclesPrecent and currentDir(example:180)");
		 obstaclesPrecent = input.nextInt();
		 currentDir = input.nextInt();
		 System.out.println("please input startpoint x and y, endpoint x and y");
		 startx = input.nextInt();
		 starty = input.nextInt();
		 endx = input.nextInt();
		 endy = input.nextInt();  
		
		 System.out.println("---"+m+n+"\t"+obstaclesPrecent+"\t"+currentDir+"\n"
		+ startx+"\t"+starty+"\t"+endx+"\t"+endy);
		 
		Fact fact= new Fact(m,n);
		fact.setObstaclesPrecent(obstaclesPrecent);
		fact.setObstacles();
		fact.setCurrentDir(currentDir);
		fact.setStartpoint(startx, starty);
		fact.setEndpoint(endx, endy);
		fact.reset();
		fact.clearEndpoint();
		
		
//		Fact fact= new Fact(30,30);
//		m = n = 30;
//		fact.setObstaclesPrecent(25);
//		fact.setObstacles();
//		fact.setCurrentDir(270);
//		
//		fact.setStartpoint(1, 1);
//		fact.setEndpoint(30, 30);
//		fact.reset();
//		fact.clearEndpoint();
		
		System.out.println((Math.atan(1.7)*(180/Math.PI)));
		Infer infer = new Infer(ruleBase, fact);
		while(true){
			fact.detect();
			
			//System.out.println(fact.toString());
			String result = infer.doInference();
			System.out.println("result"+result + "---"+fact.getCurrentpoint().toString());
			if(result.equals("SUCCESS")||result.equals("NO_PATH")||result.equals("ENDPOINT_BLOCKED"))
				break;
			fact.update(result);
		}
		
		
		System.out.println("Total Steps:"+fact.stepCount);
		
		map.setLayout(new GridLayout(m,n));
		for(int i=1;i<=m;i++)
			for(int j=1;j<=n;j++)
			{
				JButton jb = new JButton();
				jb.setBackground(Color.BLACK);
				if(fact.maze[i][j]==0)
					jb.setBackground(Color.BLACK);
				else if(fact.maze[i][j]==3)
					jb.setBackground(Color.BLUE);
				map.add(jb);
			}
		this.setTitle("Robot program----"+fact.stepCount+"steps!");
		
		this.add(map);
		this.setVisible(true);
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
	}
		
}
