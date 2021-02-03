/*
I fixed the issue of collisions using a do-while loop. 
After experimenting with other types of loops I settled on a do-while because it was the most efficient, taking up only 2 lines of code.
It runs the code at least once and then keeps picking new directions until there isn't a wall blocking the robot, which ensures that no collisions occur.
This was tested using the movement log which works by checking the amount of walls surrounding the robot (to determine its surrounding situation) and its direction. 
*/

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex1
{

	public void controlRobot(IRobot robot) {

		int randno;
		int direction;
		int checkBlock;
		int walls = 0;

		//the do while loop runs once, and then keeps running while the robot faces a wall
		do {

			// Select a random number from 0 to 3
			randno = (int) Math.round(Math.random()*3);

			// Convert this random number to a direction
			if (randno == 0)
			direction = IRobot.LEFT;
			else if (randno == 1)
			direction = IRobot.RIGHT;
			else if (randno == 2)
			direction = IRobot.BEHIND;
			else
			direction = IRobot.AHEAD;

			checkBlock = robot.look(direction);

			robot.face(direction); // Face the robot in the chosen direction 
		} while (checkBlock == IRobot.WALL);

		//counts how many walls surround the robot at a specific instant
		for (int i = 0; i<4; i++) {
		if (robot.look(IRobot.AHEAD+i) == IRobot.WALL)
		walls++;
		}

		//outputs a log of the robot's movement based on its direction and surrounding wall count
		System.out.println("");
		if (checkBlock != IRobot.WALL) {
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
