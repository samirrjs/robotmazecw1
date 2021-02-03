/*
I found the isTargetEast and isTargetNorth methods to be fairly straightforward. They compare the robot's location in relation to the target's
and return a value which allows us to pinpoint where the target is in terms of absolute directions to the robot. Furthermore, the lookHeading method
converts absolute directions to relative directions and checks what the robot is facing at a particular heading. 
I designed the headingController to store the preferred x-axis and y-axis headings and two other variables to store boolean values for whether the preferred x 
and y headings are availble. If-statements then check the boolean variables and determine what code to run. If one of the preferred headings is open, then 
it will be selected. If both preferred are open then a random one will be chosen from the two. Finally, if both preferred headings are unavailable then a
random direction will be chosen from the other available headings, which is achieved through arrays that store the headings and indices of open headings.

After multiple tests, I came to realize that in most sitations this robot would never be able to successfully complete the maze. Instead, it would usually
be stuck in an endless repeating pattern because in certain situations it would continuously alternate between a random heading and a preferred heading.
An improvement that could overcome the robot getting stuck in deadends is perhaps making it retrace its last few steps to a previous point and to attempt
the maze from an alternate path.
 */

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex3
{

  public void controlRobot(IRobot robot) {
    int heading;

    heading = headingController(robot);
    ControlTest.test(heading, robot);
    robot.setHeading(heading);
  }

  public void reset() {
    ControlTest.printResults();
  }

  private byte isTargetNorth(IRobot robot) {
      byte result;

      if (robot.getLocation().y < robot.getTargetLocation().y)
        result = -1;
      else if (robot.getLocation().y > robot.getTargetLocation().y)
        result = 1;
      else
        result = 0;
      return result;
  }

  private byte isTargetEast(IRobot robot) {
      byte result;

      if (robot.getLocation().x < robot.getTargetLocation().x)
        result = 1;
      else if (robot.getLocation().x > robot.getTargetLocation().x)
        result = -1;
      else
        result = 0;
      return result;
  }

  public int lookHeading(IRobot robot, int absDirection) {

    //converts absolute headings into relative directions using their mathematical relationship.
    int checkAbsolute = robot.look(IRobot.AHEAD + ((absDirection - robot.getHeading() + 4) % 4));
    return checkAbsolute;
  }

  public int headingController(IRobot robot) {
    int heading;
    int preferredY;
    int preferredX;
    boolean preferredYOpen;
    boolean preferredXOpen;

    //sets variable preferredY to NORTH/SOUTH depending on target location
    if (isTargetNorth(robot) == 1)
      preferredY = IRobot.NORTH;
    else if (isTargetNorth(robot) == -1)
      preferredY = IRobot.SOUTH;
    else
      preferredY = 0;

    //sets variable preferredX to EAST/WEST depending on target location
    if (isTargetEast(robot) == 1)
      preferredX = IRobot.EAST;
    else if (isTargetEast(robot) == -1)
      preferredX = IRobot.WEST;
    else
      preferredX = 0;

    //sets boolean prefferedYOpen to true if the robot can move in the y-axis direction of the target
    if (preferredY != 0  && lookHeading(robot, preferredY) != IRobot.WALL) {
      preferredYOpen = true;
    } else {
      preferredYOpen = false;
    }

    //sets boolean prefferedXOpen to true if the robot can move in the x-axis direction of the target
    if (preferredX != 0  && lookHeading(robot, preferredX) != IRobot.WALL) {
      preferredXOpen = true;
    } else {
      preferredXOpen = false;
    }

    //conditional statements for the different scenarios of availability of movement for the robot
    if (preferredXOpen == true && preferredYOpen == false){
      heading = preferredX;

    } else if (preferredXOpen == false && preferredYOpen == true){
        heading = preferredY;

    } else if (preferredXOpen == true && preferredYOpen == true){

        //picks randomly between the preferred headings
        int random = (int)Math.floor(Math.random()*2);
        if (random == 0){
          heading = preferredY;
        } else {
          heading = preferredX;
          }

    //if both preferred headings are unavailable
    } else {

        int headings[] = new int[]{IRobot.NORTH, IRobot.EAST, IRobot.SOUTH, IRobot.WEST}; //stores headings in order
        int checkHeadings[] = new int[4]; //stores the values of the block (WALL/PASSAGE/BEENBEFORE) in order of headings
        int storeArray[] = new int[4]; //stores the indices of the headings at which there is no wall.
        int openHeadings = 0; //keeps track of the amount of open directions the robot can go in

        //loops through and stores the above mentioned values in each of the arrays and variables
        for (int i = 0; i<4; i++) {
          checkHeadings[i] = lookHeading(robot, headings[i]);
          if (checkHeadings[i] != IRobot.WALL) {
            storeArray[openHeadings] = i;
            openHeadings++;
          }
        }

        //picks randomly from all the open headings
        int random = (int)Math.floor(Math.random()*openHeadings); //picks a random number for the available headings
        heading = headings[storeArray[random]];  //gets a random index from the storeArray and runs it through the heading array
    }
    return heading;
  }
}
