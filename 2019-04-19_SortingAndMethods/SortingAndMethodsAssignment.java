/**
 * Vincent Zhang
 * April 23, 2019
 * Sort data in ascending order using different sorting methods.
 * ICS3U7-02 Mr. Anthony
 */
// PLEASE MARK THIS ASSIGNMENT IN CONSOLE

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

public class SortingAndMethodsAssignment
{
	//////////////////////////////////////////////////////////////////////////////////////
	///////////////////// START OF PROGRAM / START OF GLOBAL DECLARATIONS ////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // Input stream.
	static ArrayList<String> layout = new ArrayList<String>(); // Static because referenced from static context.
	int [] data;
	
	//////////////////////////////////////////////////////////////////////////////////////
	///////////////////// END OF GLOBAL DECLARATIONS / START OF ACCESSORY METHODS ////////
	//////////////////////////////////////////////////////////////////////////////////////

	/* Method: printLayout().
	 * Purpose: print homepage layout.
	 * Pre: none.
	 * Post: homepage layout printed. */
	void printLayout() throws IOException, InterruptedException
	{
		// Declaration / Initialization.
		final int SCREEN_WIDTH = 80; // Width between two double bar lines.
		new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor(); // Clear console screen.
		
		// Print ArrayList layout.
		for(int i = 0; i < layout.size(); i++)
		{
			String s = layout.get(i);
			System.out.print("||"); // Left double bar.
			for(int j = 0; j < ((SCREEN_WIDTH - s.length())/2)+1; j++) // Left spaces.
				System.out.print(" ");
			System.out.print(s.substring(0, s.length()-1)); // ArrayList content.
			for(int j = 0; j < SCREEN_WIDTH - s.length() - ((SCREEN_WIDTH - s.length())/2); j++) // Right spaces.
				System.out.print(" ");
			System.out.print("||\n"); // Right double bar.
			if(s.charAt(s.length()-1) == '-') // Horizontal rule.
			{
				System.out.print("||");
				for(int j = 0; j < SCREEN_WIDTH; j++)
					System.out.print("-");
				System.out.print("||\n");
			}
		}
	}
	
	/* Method: getData().
	 * Purpose: populate array "data[]" with data.
	 * Pre: none.
	 * Post: populate array with user input data or generated data. */
	void getData(int numberNums) throws IOException, InterruptedException
	{
		// Declaration.
		boolean generate = false;
		
		// Get user choice of input or generate numbers.
		while(true)
		{
			System.out.print("Input(1) or generate(0)?: ");
			int choice = getInput();
			if(choice == 1 || choice == 0)
			{
				generate = choice == 1?false:true;
				break;
			}
		}
		
		// Populate "data[]" with numbers.
		if(generate)
			for(int i = 0; i < numberNums; i++)
				data[i] = (int)(Math.random()*100)+1; // Randomly generate numbers from 1 to 100 inclusive.
		else
			for(int i = 0; i < numberNums; i++)
			{
				System.out.print("Enter number: ");
				data[i] = getInput(); // Get user entered numbers.
			}
	}

	/* Method: getInput().
	 * Purpose: get fool-proof user input.
	 * Pre: none.
	 * Post: integer value of user input. */
	int getInput() // get no error user input
	{
		while(true)
			try
			{
				return Integer.parseInt(br.readLine());
			}
			catch(Exception e) // If not integer: InputMismatchException OR NumberFormatException.
			{
				System.out.println("Enter an integer.");
			}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	///////////////////// END ACCESSORY METHODS / START OF MAIN PAGE SETUP ///////////////
	//////////////////////////////////////////////////////////////////////////////////////
	
	/* Method: homePage().
	 * Purpose: initialize program.
	 * Pre: none.
	 * Post: initialize program. */
	void homePage() throws IOException, InterruptedException
	{
		// Declarations.
		int numberNums;
		int whichSort;
		
		// Print homepage layout.
		printLayout();
		
		// Check if user wants to exit program.
		System.out.print("Enter 'Y' to quit, anything else to continue: ");
		if(br.readLine().toUpperCase().equals("Y"))
		{
			br.close(); // Close buffered reader.
			System.exit(0); // Quit program.
		}
		
		// Find number of numbers to be sorted.
		while(true)
		{
			System.out.print("Enter positive integer number of numbers to be sorted: ");
			numberNums = getInput();
			if(numberNums > 0)
				break;
		}
		
		// Get data.
		data = new int [numberNums]; // Initialize array.
		getData(numberNums); // Recieve user input or generate numbers.
		
		// Find the sort the user wants to choose.
		while(true)
		{
			System.out.println("Choose a sort: bubble(1), selection(2), insertion(3), shell(4)");
			whichSort = getInput();
			if(whichSort >= 1 && whichSort <= 4)
				break;
		}

		// Call individual sorting methods.
		switch(whichSort)
		{
			case 1 : bubble(); break;
			case 2 : selection(); break;
			case 3 : insertion(); break;
			case 4 : shell(); break;
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	///////////////////// END OF MAIN PAGE SETUP / START OF SUBPAGES ACCESSORY METHODS ///
	//////////////////////////////////////////////////////////////////////////////////////
	
	/* Method: printArray().
	 * Purpose: print array "data[]".
	 * Pre: none.
	 * Post: print array "data[]". */
	void printArray() throws IOException, InterruptedException
	{
		for(int i : data)
			System.out.print(i + " ");
		System.out.println();
	}
	
	/* Method: returnHome().
	 * Purpose: return to "homePage()" after sorting.
	 * Pre: none.
	 * Post: return to "homePage()" after sorting. */
	void returnHome() throws IOException, InterruptedException
	{
		System.out.print("Enter anything to continue: ");
		String tempInput = br.readLine(); // Wait for user to finish reading output before console flushes.
		if(tempInput != null)
			homePage();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	///////////////////// END OF SUBPAGES ACCESSORY METHODS / START OF SUBPAGES //////////
	//////////////////////////////////////////////////////////////////////////////////////
	
	/* Method: bubble().
	 * Purpose: perform bubble sort.
	 * Pre: none.
	 * Post: perform bubble sort. */
	void bubble() throws IOException, InterruptedException
	{
		long start = System.nanoTime(); // Start time.
		for(int i = data.length-1; i >= 0; i--) // Start from last to first since every iteration one more last element is in correct place.
			for(int j = 0; j < data.length-1; j++) // "Bubble" largest element of the subarray to the end of array.
				if(data[j] > data[j+1])
				{
					int temp = data[j];
					data[j] = data[j+1];
					data[j+1] = temp;
				}
		long end = System.nanoTime(); // End time.
		printArray();
		System.out.println("Bubble sort took " + (end-start)/1000000.0 + " milliseconds."); // Sorting time in milliseconds.
		returnHome();
	}
	
	/* Method: selection().
	 * Purpose: perform selection sort.
	 * Pre: none.
	 * Post: perform selection sort. */
	void selection() throws IOException, InterruptedException
	{
		long start = System.nanoTime();
		for(int i = 0; i < data.length; i++) // Iterate through ever position in the array.
		{
			int min = i;
			for(int j = i+1; j < data.length; j++)
				if(data[j] < data[min])
					min = j; // Find the index of minimum value.
			
			// Swap minimum value with current value.
			int temp = data[i];
			data[i] = data[min];
			data[min] = temp;
		}
		long end = System.nanoTime();
		printArray();
		System.out.println("Bubble sort took " + (end-start)/1000000.0 + " milliseconds.");
		returnHome();
	}
	
	/* Method: insertion().
	 * Purpose: perform insertion sort.
	 * Pre: none.
	 * Post: perform insertion sort. */
	void insertion() throws IOException, InterruptedException
	{
		long start = System.nanoTime();
		for(int i = 1; i < data.length; i++) // Loop through every array element starting from second element.
		{
			int temp = data[i];
			int j = i;

			while(j > 0 && temp < data[j-1]) // Insert current value to correct position relative to the numbers before the current value.
			{
				data[j] = data[j-1];
				j--;
			}
			data[j] = temp;
		}
		long end = System.nanoTime();
		printArray();
		System.out.println("Insertion sort took " + (end-start)/1000000.0 + " milliseconds.");
		returnHome();
	}
	
	/* Method: shell().
	 * Purpose: perform shell sort.
	 * Pre: none.
	 * Post: perform shell sort. */
	void shell() throws IOException, InterruptedException
	{
		long start = System.nanoTime();
		for(int gap = data.length/2; gap > 0; gap /= 2) // Gap decreases by half its original size.
		{
			for(int i = gap; i < data.length; i++) // Loop through elements in the gap.
			{
				int temp = data[i];
				int j;
				for (j = i; j >= gap && data[j - gap] > temp; j -= gap) // For every value in the gap, if it is smaller than the element in same subarray of gap in front of it, swap.
					data[j] = data[j - gap];
				data[j] = temp;
			}
		}
		long end = System.nanoTime();
		printArray();
		System.out.println("Shell sort took " + (end-start)/1000000.0 + " milliseconds.");
		returnHome();
	}

	//////////////////////////////////////////////////////////////////////////////////////
	///////////////////// END OF SUBPAGES / START OF INITIALIZATION //////////////////////
	//////////////////////////////////////////////////////////////////////////////////////

	/* Method: main().
	 * Purpose: initialize output screen.
	 * Pre: args is String[].
	 * Post: add content to layout and create an object. */
	public static void main(String [] args) throws IOException, InterruptedException
	{
		// Add homepage layout to ArrayList.
		layout.add("   _____            __     ____        __        ");
		layout.add("  / ___/____  _____/ /_   / __ \\____ _/ /_____ _ ");
		layout.add("  \\__ \\/ __ \\/ ___/ __/  / / / / __ `/ __/ __ `/ ");
		layout.add(" ___/ / /_/ / /  / /_   / /_/ / /_/ / /_/ /_/ /  ");
		layout.add("/____/\\____/_/   \\__/  /_____/\\__,_/\\__/\\__,_/  -");
		layout.add("Vincent Zhang | April 23, 2019 | ICS3U7-02-");
		layout.add("\"Sort Data\"  arranges  integer  in  ascending  order. Program uses methods ");
		layout.add("to maximize efficiency. User is given the option to choose to input their ");
		layout.add("own  data  or  for  computer  to generate data. This is done in a separate ");
		layout.add("method. Computer generated data are random numbers from 1 to 100 inclusive. ");
		layout.add("User  could also  select bubble sort,  selection sort,  insertion sort, or ");
		layout.add("shell sort. Each of which is in a separate method. The  final sorted array ");
		layout.add("is printed along with the time in  milliseconds  it took to sort. All user ");
		layout.add("input is validated.                                                       -");
		
		SortingAndMethodsAssignment obj = new SortingAndMethodsAssignment(); // Create obj of class SortingAndMethodsAssignment.
		obj.homePage(); // Call method of object / instance of class SortingAndMethodsAssignment so references are made from non-static context.
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	///////////////////// END OF INITIALIZATION / END OF PROGRAM /////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
}