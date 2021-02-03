/*
* File: DumboController.java
* Created: 17 September 2002, 00:34
* Author: Stephen Jarvis
*/

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class DumboController
{

	public void controlRobot(IRobot robot) {

		int randno;
		int randno2;
		int direction = IRobot.AHEAD;
		int checkBlock;
		int walls = 0;

		randno2 = (int)(Math.floor(Math.random()*8)); //picks random number (0,7) for the 1/8 chance

		if (robot.look(direction) == IRobot.WALL || randno2 == 0) //randno2 has a 1/8 chance of = 0
			do {
			// Select a random number

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

				// Checks and stores the value of the block in front of the robot in the variable checkBlock

				checkBlock = robot.look(direction);

				robot.face(direction); /* Face the robot in this direction */
			} while (checkBlock == IRobot.WALL);
		else
			robot.face(direction);

		//counts how many walls surround the robot at a specific instant
		if (robot.look(IRobot.LEFT) == IRobot.WALL)
		walls++;
		if (robot.look(IRobot.RIGHT) == IRobot.WALL)
		walls++;
		if (robot.look(IRobot.AHEAD) == IRobot.WALL)
		walls++;
		if (robot.look(IRobot.BEHIND) == IRobot.WALL)
		walls++;

		//outputs a log of the robot's movement
		System.out.println(""); //creates a new line
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
			if (walls == 2)
			System.out.println("down a corridoor.");
			else if (walls == 3)
			System.out.println("at a dead end.");
			else
			System.out.println("at a junction.");
		}
		walls = 0;
	}
}
