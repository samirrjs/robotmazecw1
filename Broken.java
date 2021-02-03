/*
 * File:    Broken.java
 * Created: 7 September 2001
 * Author:  Stephen Jarvis
 */

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Broken
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
    int checkAbsolute = robot.look(IRobot.AHEAD + ((absDirection - robot.getHeading() + 4) % 4));
    return checkAbsolute;
  }

  public int headingController(IRobot robot) {
    int heading;
    int yHeading;
    int xHeading;

    if (isTargetNorth(robot) == 1)
      yHeading = IRobot.NORTH;
    else if (isTargetNorth(robot) == -1)
      yHeading = IRobot.SOUTH;
    else
      yHeading = 0;

    if (isTargetEast(robot) == 1)
      xHeading = IRobot.EAST;
    else if (isTargetEast(robot) == -1)
      xHeading = IRobot.WEST;
    else
      xHeading = 0;

    boolean yAxisMovement = (yHeading != 0  && lookHeading(robot, yHeading) != IRobot.WALL);
    boolean xAxisMovement = (xHeading != 0  && lookHeading(robot, xHeading) != IRobot.WALL);

    if (xAxisMovement && !yAxisMovement){
      heading = xHeading;
    } else if (!xAxisMovement && yAxisMovement){
        heading = yHeading;
    } else if (xAxisMovement && yAxisMovement){
        int random = (int)Math.floor(Math.random()*2);
        if (random == 0){
          heading = yHeading;
        } else {
          heading = xHeading;
          }
    } else {
        int headings[] = new int[]{IRobot.NORTH, IRobot.EAST, IRobot.SOUTH, IRobot.WEST};
        int checkHeadings[] = new int[4];
        int storeArray[] = new int[4];
        int openHeadings = 0;
        for (int i = 0; i<4; i++) {
          checkHeadings[i] = lookHeading(robot, headings[i]);
          if (checkHeadings[i] != IRobot.WALL) {
            storeArray[openHeadings] = i;
            openHeadings++;
          }
        }
        int random = (int)Math.floor(Math.random()*openHeadings);
        heading = headings[storeArray[random]];
    }
    return heading;
  }
}
