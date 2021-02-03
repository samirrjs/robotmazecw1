/* 
Ex2 solved the problem of uneven probabiltiies when using the Math.round() and Math.random() functions. I solved this by 
using Math.floor() instead of Math.round() because Math.floor() gives equal probability of being chosen to all values. This works 
because the intervals in which the integers are rounded down are equal, unlike the Math.round() where an integer like 1 
would have a higher chance of being chosen than an integer like 0.

The next task was to make it so that the robot only turns when it is facing a wall, but to also give it a 1 in 8 chance
of turning randomly. This was achieved by incorporating an if-else statement around the do-while loop. The condition was 
that either the robot was facing a wall or a random number chosen between [0,7] was equal to 0 (this represents a 1/8 chance).
The else statement was for the robot to continue facing ahead. I think the incorporation of the 1/8 chance was a good solution,
as a few runs of the robot in the maze showed good results with the robot taking fewer steps to finish than when its course was completely
randomized.
 */

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex2
{

	public void controlRobot(IRobot robot) {

		int randno;
		int randno2;
		int direction = IRobot.AHEAD;
		int checkBlock;
		int walls = 0;

		randno2 = (int)(Math.floor(Math.random()*8)); //picks random number [0,7]

		//runs if facing a wall or if randno2 equals 0 (1/8 chance of randno2 = 0)
		if (robot.look(direction) == IRobot.WALL || randno2 == 0) {  
			do {

				//Select a random number from 0 to 3 with equal probabilties
				randno = (int)(Math.floor(Math.random()*4));

				// Convert this to a direction
				if (randno == 0)
				direction = IRobot.LEFT;
				else if (randno == 1)
				direction = IRobot.RIGHT;
				else if (randno == 2)
				direction = IRobot.BEHIND;
				else
				direction = IRobot.AHEAD;

				checkBlock = robot.look(direction);

				robot.face(direction); //Face the robot in this direction 
			} while (checkBlock == IRobot.WALL);
			//if conditions aren't met, the robot will continue to face forwards
		} else {
			robot.face(direction);
		}

		//counts how many walls surround the robot at a specific instant
		for (int i = 0; i<4; i++) {
		if (robot.look(IRobot.AHEAD+i) == IRobot.WALL)
		walls++;
		}

		//outputs a log of the robot's movement based on its direction and surrounding wall count
		System.out.println("");
		if (robot.look(direction) != IRobot.WALL) {
			System.out.print("I'm going ");
			if (direction == IRobot.BEHIND)
			System.out.print("backwards ");
			else if (direction == IRobot.AHEAD)
			System.out.print("forwards ");
			else if (direction == IRobot.RIGHT)
			System.out.print("right ");
			else if (direction == IRobot.LEFT)
			System.out.print("left ");
			if (walls == 1)
			System.out.println("at a junction.");
			else if (walls == 2)
			System.out.println("down a corridoor.");
			else if (walls == 3)
			System.out.println("at a dead end.");
			else
			System.out.println("at a crossroad.");
		}
	}
}
