
import java.util.*;


public class Fact {

	private final int NW=135, N=90, NE=45 ,E=0,W=180,SW=225,S=270,SE=315;
	
	public int[][] maze ;
	private int width,height;
	private int obstaclePrecent;
	public ArrayList<Location> path = new ArrayList<Fact.Location>();
	public int stepCount = 0;
	
	private int BEST_DIR;
	private int NWD, ND, NED ,ED,WD,SWD,SD,SED;
	private int NWD_LOC, ND_LOC, NED_LOC ,ED_LOC,WD_LOC,SWD_LOC,SD_LOC,SED_LOC;
	public int NEXT_DIR,CURRENT_DIFF,POP,currentDir;
	private Location startpoint,endpoint,currentpoint;
	
	private void setDarkArea(){
		for(int i=1;i<=width;i++)
			for(int j=1;j<=height;j++)
				maze[i][j] = 2;
	}
	
	public Fact(int m,int n){
		maze = new  int[n+2][m+2];
		width = m;
		height= n;
		setDarkArea();
		path.clear();
		stepCount = 0;
	}
	
	public void setObstaclesPrecent(int precent){
		this.obstaclePrecent = precent ;
	}
	
	public void setObstacles(){
		int total = obstaclePrecent*width*height/100;
		Random random = new Random(100);
		for(int i=0;i<total;i++)
		{
			int m = (int)random.nextInt(width)+1;
			int n = (int)random.nextInt(height)+1;
			System.out.println("m"+m+"n"+n);
			maze[m][n] = 0;
		}
		for(int i=0;i<width+2;i++){
			maze[0][i] = 0;
			maze[height+1][i] = 0;
		}
		for(int i=0;i<height+2;i++){
			maze[i][0] = 0;
			maze[i][width+1] = 0;
		}
	}
	
	public void update(String action){
		Location nextpoint = new Location(currentpoint.x,currentpoint.y);
		if(action.equals("MOVE")){
			if(currentDir==0){
				nextpoint.y = currentpoint.y+1;
			}else if(currentDir==45){
				nextpoint.y = currentpoint.y+1;
				nextpoint.x = currentpoint.x-1;
			}else if(currentDir==90){
				nextpoint.x = currentpoint.x-1;
			}else if(currentDir==135){
				nextpoint.y = currentpoint.y-1;
				nextpoint.x = currentpoint.x-1;
			}else if(currentDir==180){
				nextpoint.y = currentpoint.y-1;
			}else if(currentDir==225){
				nextpoint.y = currentpoint.y-1;
				nextpoint.x = currentpoint.x+1;
			}else if(currentDir==270){
				nextpoint.x = currentpoint.x+1;
			}else if(currentDir==315){
				nextpoint.y = currentpoint.y+1;
				nextpoint.x = currentpoint.x+1;
			}
			if(path.contains(nextpoint)){
				path.remove(nextpoint);
			}
			path.add(nextpoint);
			maze[nextpoint.x][nextpoint.y] = 3;
			stepCount++;
			this.setCurrentpoint(nextpoint);
		}else if(action.equals("LEFT")){
			currentDir = (currentDir + 45)%360;
		}else if(action.equals("RIGHT")){
			currentDir = (currentDir -45 +360)%360;
		}else if(action.endsWith("POP")){
			Location now = new Location(path.get(stepCount-2).x,path.get(stepCount-2).y);
			currentpoint.x = now.x;
			currentpoint.y = now.y;
			path.remove(stepCount-1);
			stepCount--;
			System.out.println("pop"+now.toString());
		}
		reset();
		
	}
	
	public void setDirLocVaue(){
		NWD_LOC = maze[currentpoint.x-1][currentpoint.y-1];
		ND_LOC = maze[currentpoint.x-1][currentpoint.y];
		NED_LOC = maze[currentpoint.x-1][currentpoint.y+1];
		ED_LOC = maze[currentpoint.x][currentpoint.y+1];
		WD_LOC = maze[currentpoint.x][currentpoint.y-1];
		SWD_LOC = maze[currentpoint.x+1][currentpoint.y-1];
		SD_LOC = maze[currentpoint.x+1][currentpoint.y];
		SED_LOC = maze[currentpoint.x+1][currentpoint.y+1];
		
	}
	
	public void getBestDir(){
		if(endpoint.x-currentpoint.x==0&&endpoint.y>=currentpoint.y){
			BEST_DIR = 0;
			return ;
		}
		if(endpoint.x-currentpoint.x==0&&endpoint.y<=currentpoint.y){
			BEST_DIR = 180;
			return;
		}
		int k = (endpoint.y-currentpoint.y)/(endpoint.x-currentpoint.x);
		BEST_DIR = (int) (Math.atan(k)*(180/Math.PI));
		BEST_DIR = (BEST_DIR-90+360)%360;
		System.out.println("Best"+BEST_DIR);
	}
	
	private int findPriority(int x,int[] array){
		for(int i=0;i<array.length;i++)
			if(x==array[i])
				return i;
		return -1;
	}
	public void getBestDiff(){
		NWD = doDirDiff(NW,BEST_DIR); 
		ND  = doDirDiff(N,BEST_DIR); 
		NED = doDirDiff(NE,BEST_DIR); 
		ED  = doDirDiff(E,BEST_DIR); 
		WD  = doDirDiff(W,BEST_DIR); 
		SWD = doDirDiff(SW,BEST_DIR); 
		SD  = doDirDiff(S,BEST_DIR); 
		SED = doDirDiff(SE,BEST_DIR);
		int [] array = {NWD, ND, NED ,ED,WD,SWD,SD,SED}; 
		 Arrays.sort(array);
		NWD = findPriority(NWD,array)+1;
		ND = findPriority(ND,array)+1;
		NED = findPriority(NED,array)+1;
		ED = findPriority(ED,array)+1;
		WD = findPriority(WD,array)+1;
		SWD = findPriority(SWD,array)+1;
		SED = findPriority(SED,array)+1;
		SD = findPriority(SD,array)+1;
	}
	
	public void reset(){
		NEXT_DIR = -1;
		CURRENT_DIFF = -1;
		BEST_DIR = -1;
		POP=0;
		getBestDir();
		getBestDiff();
		setDirLocVaue();
	}
	
	public String toString(){
		String temp = "";
		for(int i=0;i<height+2;i++)
		{
			for(int j=0;j<width+2;j++)
				temp = temp+maze[i][j];
			temp = temp +"\n";
		}
		return temp;
	}
	
	
	public class Location
	{
		int x;
		int y;
		public Location(){}
		
		public Location(int x,int y){
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			Location compare = (Location)obj;
			if(compare.x==x&&compare.y==y)
				return true;
			else 
				return false;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "x="+x+" , y="+y+"; ";
		}
		
		
	}
	
	public int returnVariableValue(String varable){
		if(varable.equals("NED")){
			return NED;
		}else if(varable.equals("ND")){
			return ND;
		}else if(varable.equals("NWD")){
			return NWD;
		}else if(varable.equals("ED")){
			return ED;
		}else if(varable.equals("WD")){
			return WD;
		}else if(varable.equals("SWD")){
			return SWD;
		}else if(varable.equals("SD")){
			return SD;
		}else if(varable.equals("SED")){
			return SED;
		}else if(varable.equals("CURRENT_DIFF")){
			return CURRENT_DIFF;
		}else if(varable.equals("NEXT_DIR")){
			return NEXT_DIR;
		}else if(varable.equals("ND_LOC")){
			return ND_LOC;
		}else if(varable.equals("NWD_LOC")){
			return NWD_LOC;
		}else if(varable.equals("ED_LOC")){
			return ED_LOC	;
		}else if(varable.equals("WD_LOC")){
			return WD_LOC;
		}else if(varable.equals("SWD_LOC")){
			return SWD_LOC;
		}else if(varable.equals("SD_LOC")){
			return SD_LOC;
		}else if(varable.equals("SED_LOC")){
			return SED_LOC;
		}else if(varable.equals("NED_LOC")){
			return NED_LOC;
		}else if(varable.equals("NEXT_DIR")){
			return NEXT_DIR;
		}else if(varable.equals("POP")){
			return POP;
		}else
			return -1;
	} 
	public boolean endBlock(){
		if(maze[endpoint.x][endpoint.y]==0)
			return true;
		else
			return false;
	}
	
	public void setAction(String varable, int value){
		if(varable.equals("NED")){
			 NED = value;
		}else if(varable.equals("ND")){
			ND = value;
		}else if(varable.equals("NWD")){
			 NED = value;
		}else if(varable.equals("ED")){
			 NED = value;
		}else if(varable.equals("WD")){
			 NED = value;
		}else if(varable.equals("SWD")){
			 NED = value;
		}else if(varable.equals("SD")){
			 NED = value;
		}else if(varable.equals("SED")){
			 NED = value;
		}else if(varable.equals("CURRENT_DIFF")){
			CURRENT_DIFF = value;
		}else if(varable.equals("NEXT_DIR")){
			NEXT_DIR = value;
		}else if(varable.equals("POP")){
			POP = value;
		}else
			return ;
	}
	
	private void sonar(int dir){
		int curX,curY;
		curX = currentpoint.x;
		curY = currentpoint.y;
		if(dir==0){
			while(maze[curX][curY+1]==2||maze[curX][curY+1]==1){
				maze[curX][curY+1] = 1;
				curY++;
			}
		}else if(dir==45){
			while(maze[curX-1][curY+1]==2||maze[curX-1][curY+1]==1){
				maze[curX-1][curY+1] = 1;
				curX--;
				curY++;
			}
		}else if(dir==90){
			while(maze[curX-1][curY]==2||maze[curX-1][curY]==1){
				maze[curX-1][curY] = 1;
				curX--;
			}
		}else if(dir==135){
			while(maze[curX-1][curY-1]==2||maze[curX-1][curY-1]==1){
				maze[curX-1][curY-1] = 1;
				curX--;
				curY--;
			}
		}else if(dir==180){
			while(maze[curX][curY-1]==2||maze[curX][curY-1]==1){
				maze[curX][curY-1] = 1;
				curY--;
			}
		}else if(dir==225){
			while(maze[curX+1][curY-1]==2||maze[curX+1][curY-1]==1){
				maze[curX+1][curY-1] = 1;
				curX++;
				curY--;
			}
		}else if(dir==270){
			while(maze[curX+1][curY]==2||maze[curX+1][curY]==1){
				maze[curX+1][curY] = 1;
				curX++;
			}
		}else if(dir==315){
			while(maze[curX+1][curY+1]==2||maze[curX+1][curY+1]==1){
				maze[curX+1][curY+1] = 1;
				curX++;
				curY++;
			}
		}
	}
	
	public void detect(){
		int dir_1,dir_2;
		dir_1 = (currentDir + 45)%360 ; 
		dir_2 = (currentDir - 45+360)%360;
		sonar(currentDir);
		sonar(dir_2);
		sonar(dir_1);
		setDirLocVaue();
	}
	
	public boolean isSuccess(){
		if(currentpoint.equals(endpoint))
			return true;
		else
			return false;
	}
	
	public Location getCurrentpoint() {
		return currentpoint;
	}

	public void setCurrentpoint(Location currentpoint) {
		this.currentpoint = currentpoint;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}	

	public int getStepCount() {
		return stepCount;
	}

	public void setStepCount(int stepCount) {
		this.stepCount = stepCount;
		
	}

	public int getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(int currentDir) {
		this.currentDir = currentDir;
	}

	public Location getStartpoint() {
		return startpoint;
	}

	public void setStartpoint(int x,int y) {
		this.startpoint = new Location(x,y);
		this.currentpoint = new Location(x,y);
		path.add(startpoint);
		stepCount++;
		maze[startpoint.x][startpoint.y] = 3;
	}

	public Location getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(int x,int y) {
		this.endpoint = new Location(x,y);
	}
	
	public int getBEST_DIR(){
		return BEST_DIR; 
	}
	
	public void clearEndpoint(){
		maze[endpoint.x][endpoint.y] = 1;
	}

	
	public int doDirDiff(int dir1,int dir2){
		int diff = Math.abs((dir1-dir2)%360);
		if(diff>180)
			diff = 360-diff;
		return diff;
	}

}
