# Robot
    Rule-base expert system for a  robot path finder.
    Ecoplse Java project.
Introduction:
This project use Rule-base expert system to help robot to make the decision on the current situation in order to find the path to the exit in a maze map.

Rule base:
(1)First of all, find the best direction, the best direction should be the direct line from the current location to end location, but this direction may be blocked.
(2)Find the next direction and next location. Set the priority to all the direction, the nearest to the best direction the highest. If the nearest direction and the next location in that direction has no obstacle, we should choose that direction and move forward. The rule like this:
NED=7&NED_LOC=0->NEXT_DIR=45
NED=8&NED_LOC=0->NEXT_DIR=45
NWD=7&NWD_LOC=2->NEXT_DIR=135
ND=7&ND_LOC=2->NEXT_DIR=90
WD=7&WD_LOC=2->NEXT_DIR=180
SWD=7&SWD_LOC=2->NEXT_DIR=225
SD=7&SD_LOC=2->NEXT_DIR=270
SED=7&SED_LOC=2->NEXT_DIR=315
ED=7&ED_LOC=2->NEXT_DIR=0
NED=7&NED_LOC=2->NEXT_DIR=45
NEXT_DIR=-1->POP=1
NED represent the north east, the NED_LOC means the next location. The value of NED is the priority of all direction, sorted by the difference between the best direction, low difference means high priority

Interesting program features:
1.Use the two-dimensional array as the map, use 1 as the block, 0 as empty and 2 represent dark;
2.If endpoint were blocked(have obstacle), exit with error.
3.The result of path is displayed graphically.
4.If there is no path can access the exit, return back to start point.
5.Make decision according all information that got.
6.Set all location that moved previously to 3, in order to not interfere the decision.
7.If there is no path can move forward, pop back to last stop.

Experiments run:
The grid the 30*30, initial position is (5,5), end point is 30*30;

Experimental results
(1) NO obstacles. Move directly to the exit
![1](https://cloud.githubusercontent.com/assets/15055996/23981693/bfc59adc-09c5-11e7-9d26-af7356ec6aaa.png)
(2) 10% obstacles
When confronted with obstacles, find the less near path to the exit
![2](https://cloud.githubusercontent.com/assets/15055996/23981695/bfc96c20-09c5-11e7-86e6-cb355716c19a.png)

(3) 25% obstacles
![3](https://cloud.githubusercontent.com/assets/15055996/23981694/bfc9184c-09c5-11e7-8369-e8bc957a0c5e.png)
(4) 50% Obstacles
There is no path to end point . So search all possible path, if all the location has been tested and can not move to endpoint, program exit.
![4](https://cloud.githubusercontent.com/assets/15055996/23981696/bfc99114-09c5-11e7-9fa0-f318a9a05f60.png)



Discussion of results 
The result can display the robot try to make decision to move towards end point as soon as possible, if there all the path were blocked, pop to last stop and search other directions. If all the location were tested, program exit.

