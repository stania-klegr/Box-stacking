//staniaKlegr_1339709
//JasonTollison 1319030

import  java.util.*;
import java.io.*;

//Uses simulated annealing to try find the highest stack of boxes read from a file.
//After a number of iterations entered by user the tallest tower found is printed
//to standard out along with each boxes dimensions and accumulated height.
public class NPstack
{
	static int temp, size;
	static ArrayList<Box> stack; //Main list of boxes read in from file
	static ArrayList<Box> newStack;

	static ArrayList<Box> bestStack; //the best stack that we have found so far
	static int bestStackEndIndex;//the end position of the best stack in the full list of boxes

	static Random rand = new Random();//added a seed so that i can keep my sanity testing this

	public static void main(String[] args) throws IOException
	{
		//initilize the lists
		stack = new ArrayList<>();
		newStack = new ArrayList<>();
		bestStack = new ArrayList<>();

		bestStackEndIndex = 0;

		//check args are correct
		if (args.length != 2) {
			System.out.println("Usage <file of boxes> <number of generations>");
			System.exit(0);
		}

		BufferedReader reader = new BufferedReader(new FileReader(args[0]));

		String line;
		String[] str;
		Box box;

		int curID = 0;

		//loop for each line in the file
		while ((line = reader.readLine()) != null) {
			str = line.split(" ");

			//check that the box dosent have bad data
			if (str.length == 3 && (Integer.parseInt(str[0]) > 0) && (Integer.parseInt(str[1]) > 0) && (Integer.parseInt(str[2]) > 0)) {
				//create a box from the input line and add it to the list
				box = new Box(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]), curID);
				stack.add(box);
				curID++;
			} else {
				System.out.println("Bad box");
			}
		}

		size = temp = stack.size();

		runAnnealing(Integer.parseInt(args[1]));//TODO move parsing to the top

		stats(stack);
	}

	//Runs annealing on a stack of boxes for number of iterations passed in.
	//Calculates a temperature(number of mutations) to run at each iteration which
	//decreases based on the the current iteration we are on.
	private static void runAnnealing(int iterations)
	{
		System.out.println("Annealing started....");
		int count = 0;
		temp = calcTemp(size, iterations, count);

		//Run for as many iterations as specified
		while (count < iterations)
		{
			//Make a new copy of the main stack to mutate
			newStack = new ArrayList<>();
			for (Box p : stack) {
				newStack.add(p.getCopy());
			}

			temp = calcTemp(size, iterations, count);

			mutate(temp);

			//Check if newStack is better than the last one
			if (getFitness(stack) < getFitness(newStack)) {
				stack = new ArrayList<>(newStack);
			}
			count++;
		}
	}

	//Calculates a temperature(number of times to mutate a stack). This is based on the number
	//of iterations passed in the current iteration and the size of the stack/number of boxes in a stack.
	//Uses formula y=mx+b
	private static int calcTemp(int stackSize, int iterations, int count)
	{
		//Formula for linear reduction to find how many mutations to use
		//Points (0, stacksize) (iterations, 1)
		double m;
		double b;
		double y1 = b = stackSize;
		double y2 = 1;
		double x1 = 0;
		double x2 = iterations;

		m = (y2 - y1) / (x2 - x1);

		//y=mx+b
		return (int) ((m * count) + b);
	}

	//Loops through all boxes in newStack and randomly rotates it or swaps it
	//with another box in the newStack. Number of times this happens is passed in
	//as number of mutations.
	private static void mutate(int mutations)
	{
		int mutateType, index1, index2;
		int i = 0;

		Box box;

		while (i < mutations)
		{
			box = newStack.get(rand.nextInt(size));
			mutateType = rand.nextInt(4);

			switch (mutateType) {
				case 0:
					box.rotateXZ();
					break;
				case 1:
					box.rotateYZ();
					break;
				case 2:
					index1 = rand.nextInt(size);
					index2 = rand.nextInt(size);

					Collections.swap(newStack, index1, index2);

					break;
				case 3:
					index1 = rand.nextInt(size);
					index2 = rand.nextInt(size);

					Collections.swap(newStack, index1, index2);
					break;
				}
			i++;
		}
	}

	//Prints the tallest tower of boxes and their accumulated height as each box is printed.
	public static void stats(ArrayList<Box> stackToTest)
	{
		getFitness(stackToTest); //Get the last index of the tallest tower in a stack
		int curHeight = 0;

		bestStack = new ArrayList<>();
		bestStack.add(stackToTest.get(bestStackEndIndex));

		//Starts from the end of the tallest tower and works backwards
		while ((bestStackEndIndex - 1 >= 0) && canFit(stackToTest.get(bestStackEndIndex), stackToTest.get(bestStackEndIndex - 1))) {
			bestStack.add(stackToTest.get(bestStackEndIndex - 1));

			bestStackEndIndex--;
		}

		//Print the tallest tower of boxes
		System.out.println("Highest tower found: ");
		for (int i = bestStack.size()-1; i >= 0; i--) {
			curHeight += bestStack.get(i).z;
			System.out.println(bestStack.get(i).toString() + " " + curHeight);
		}
	}

	//Finds and returns the height of the tallest tower in a stack.
	//ALso saves end index of the tallest tower in a stack
	private static int getFitness(ArrayList<Box> testStack) {
		int score = 0;
		int bestScore = 0;
		Box prevBox = new Box(); //Previous box in list(set it to null when comparing first box in a stack)

		for (Box box : testStack) //For each box in the current stack
		{
			//If current box can fit onto previous
			if (canFit(box, prevBox)) {
				//Add box height to curTowerHeight
				score += box.z;
			}
			//Otherwise reset the current tower height
			else {
				score = box.z;
			}

			if (score > bestScore) {
				bestScore = score;
				bestStackEndIndex = testStack.indexOf(box);
			}

			//Update previous Box
			prevBox = box;
		}

		return bestScore;
	}


	//checks if the boxes are a valid stack
	private static boolean canFit(Box b1, Box b2) {
		//If looking at first box of a stack
		if (b2.x == 0)
			return true;

		//do we fit in both the x and the y dimension? or if we rotate the box on the vertical axis then do we fit?
		if (b1.y < b2.y && b1.x < b2.x) {
			return true;
		}
		if (b1.x <  b2.y && b1.y <  b2.x){
			//Rotate b1 to fit
			b1.rotateXY();
			return true;
		}

		return false;
	}
}
