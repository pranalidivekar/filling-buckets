/*
 * Water.java
 *
 * Version:
 *     1
 *
 * Revisions:
 *     1
 */

/**
 * This program calculates if an empty bucket can be filled with some or all of
 * the other available buckets
 *
 * @author      Sharvai Patil (sp4479@g.rit.edu)
 * @author      Pranali Divekar (pd1267@g.rit.edu)
 */

public class Water{

	int availableBuckets[] ={1, 1, 2, 4, 5, 6};
	int bucketsToFill[] = {1, 2, 3, 4, 6, 7, 8, 9};

	int fillAt=0; //position in allCombinations where every new combination must be placed

	int solutionArray[] = new int[availableBuckets.length];

	String allCombinationsS[]=new String[(int)Math.pow(2, availableBuckets.length)-1];
	//(2^k)-1 possible combinations for k buckets

	/**
	* This method creates an object of the class Water
	* and initializes the solution array and calls getAllCombinations() to
	* generate all possible combinations of buckets and then handles 
	*
	* @param       args    command-line argument
	*
	* @return              void
	*
	*/
	public static void main(String args[]){
		Water ob = new Water();

		// Initializing the solution array with 0s
		ob.initSolutionArray();

		// initializing allCombinationsS String array elements with ""
		for(int i=0; i<ob.allCombinationsS.length; i++){
			ob.allCombinationsS[i]=""; 
		}

		// Getting all possible combinations
		for(int i=0;i<ob.availableBuckets.length;i++){
			ob.getAllCombinations("", i);
		}
		// ob.printAllCombinations();

		if(ob.bucketsToFill.length>0){
			if(args.length==0){
				// solution for the maximum number of buckets
				System.out.println("Maximum (largest num of buckets)");

				// iterating through all buckets that need to be filled one by one
				for(int i=0; i < ob.bucketsToFill.length; i++)
				{
					// call findMaxBuckets() to check if this bucket can be filled
					boolean canFill = ob.findMaxBuckets(ob.bucketsToFill[i]);
					if(canFill){
						// canFill is true if a solution has been found

						// printing the solution
						System.out.print("yes: "+ob.bucketsToFill[i]+"L = ");					
						for(int x=0; x<ob.solutionArray.length; x++){
							if(ob.solutionArray[x]!=0){
								if(x==ob.solutionArray.length-1 || ob.solutionArray[x+1]==0)
									System.out.print(ob.solutionArray[x]+"L");
								else
									System.out.print(ob.solutionArray[x]+"L + ");
							}						
						}
						System.out.println();
					}
					else
						System.out.println(ob.bucketsToFill[i]+"L = Cannot be done");

					ob.initSolutionArray(); // clean the solution array 
				}
			}
			else{
				// solution for the minimum number of buckets
				System.out.println("Minimum (least num of buckets)");

				// iterating through all buckets that need to be filled one by one
				for(int i=0; i < ob.bucketsToFill.length; i++)
				{
					// call findMaxBuckets() to check if this bucket can be filled
					boolean canFill = ob.findMinBuckets(ob.bucketsToFill[i]);
					if(canFill){
						// canFill is true if a solution has been found

						// printing the solution
						System.out.print("yes: "+ob.bucketsToFill[i]+"L = ");
						for(int x=0; x<ob.solutionArray.length; x++){
							if(ob.solutionArray[x]!=0){
								if(x==ob.solutionArray.length-1 || ob.solutionArray[x+1]==0)
									System.out.print(ob.solutionArray[x]+"L");
								else
									System.out.print(ob.solutionArray[x]+"L + ");
							}						
						}
						System.out.println();
					}
					else
						System.out.println(ob.bucketsToFill[i]+"L = Cannot be done");

					ob.initSolutionArray(); // clean the solution array 
				}
			}
		}
		else{
			System.out.println("No buckets to fill");
		}
	}

	/**
	* This method generates all possible combinations with the available buckets
	* and stores them in a string array 
	*
	* @param       baseString	Stores the string to which the next bucket volume will
	* 							be attached
	* @param	   fillWith		index of the bucket with which to make the next combination	    
	*
	* @return              		void
	*
	*/
	void getAllCombinations(String baseString, int fillWith)
	{
		// store the current bucket using fillWith and baseString into allCombinationsS
		allCombinationsS[fillAt]+=baseString+availableBuckets[fillWith]+"+";
		baseString=allCombinationsS[fillAt];
		fillAt+=1;

		// for loop that gets every next available bucket to create
		// the next possible combination
		for(int i=fillWith+1; i<availableBuckets.length;i++){			
			getAllCombinations(baseString, i);
		}
	}

	/**
	* This method prints all contents of allCombinationsS
	*
	* @param       none    
	*
	* @return              		void
	*
	*/
	void printAllCombinations(){
		for(int i=0;i<allCombinationsS.length;i++){
			System.out.println("["+allCombinationsS[i]+"]");
		}
	}

	/**
	* This method calculates the sum of all combinations and finds the solution
	* with the maximum number of buckets required to fill the buckets
	*
	* @param       bucketToFill the bucket that needs to be filled with available
	* 							buckets   
	*
	* @return              		(boolean) true if a solution is found. Else, false
	*
	*/
	boolean findMaxBuckets(int bucketToFill){
		int maxBuckets=-1; //maximum number of buckets
		boolean returnVal = false; //false if no solution found, else true

		// iterating through every string in allCombinations
		for(int i=0;i<allCombinationsS.length;i++){
			String thisCombination = allCombinationsS[i];

			int tempSolution[] = new int[availableBuckets.length];
			int tempCounter=0;

			int sum=0; //saves sum of thisCombination
			int numBuckets=0;

			// converting thisCombination String into integer sum 
			// and calculating the number of buckets in it
			String thisNumber = ""; //string that stores the extracted numbers
			int thisBucket=0;
			for(int j=0;j<thisCombination.length();j++){
				char c = thisCombination.charAt(j);				
				if(c=='+'){
					thisBucket=Integer.parseInt(thisNumber); // the string number is converted to an int
					sum+=thisBucket;
					numBuckets+=1;

					tempSolution[tempCounter]=thisBucket;
					tempCounter+=1;

					thisNumber="";
					thisBucket=0;
				}
				else{
					thisNumber+=c;
				}
			}

			// checking if the sum of this combination equals the 
			// volume of the bucket to fill
			if(sum==bucketToFill){

				// checking if the number of buckets required is greater than
				// the maximum number of buckets
				if(numBuckets>maxBuckets){
					maxBuckets=numBuckets;
					solutionArray=tempSolution;

					returnVal=true;
				}
			}
		}

		return returnVal;
	}

	/**
	* This method calculates the sum of all combinations and finds the solution
	* with the minimum number of buckets required to fill the buckets
	*
	* @param       bucketToFill the bucket that needs to be filled with available
	* 							buckets   
	*
	* @return              		(boolean) true if a solution is found. Else, false
	*
	*/
	boolean findMinBuckets(int bucketToFill){
		int minBuckets=allCombinationsS.length+1; //minimum number of buckets
		boolean returnVal = false; //false if no solution found, else true

		// iterating through every string in allCombinations
		for(int i=0;i<allCombinationsS.length;i++){
			String thisCombination = allCombinationsS[i];

			int tempSolution[] = new int[availableBuckets.length];
			int tempCounter=0;

			int sum=0; //saves sum of thisCombination
			int numBuckets=0;

			//converting thisCombination String into integer sum 
			// and calculating the number of buckets in it
			String thisNumber = ""; //string that extracts numbers
			int thisBucket=0;
			for(int j=0;j<thisCombination.length();j++){
				char c = thisCombination.charAt(j);				
				if(c=='+'){
					thisBucket=Integer.parseInt(thisNumber); // the string number is converted to an int
					sum+=thisBucket;
					numBuckets+=1;

					tempSolution[tempCounter]=thisBucket;
					tempCounter+=1;

					thisNumber="";
					thisBucket=0;
				}
				else{
					thisNumber+=c;
				}
			}

			// checking if the sum of this combination equals the 
			// volume of the bucket to fill
			if(sum==bucketToFill){

				// checking if the number of buckets required is greater than
				// the maximum number of buckets
				if(numBuckets<minBuckets){
					minBuckets=numBuckets;
					solutionArray=tempSolution;					
				}
				returnVal=true;
			}
		}

		return returnVal;
	}

	/**
	* This method initializes the solutionArray with 0s
	*
	* @param       none 
	*
	* @return              		void
	*
	*/
	void initSolutionArray(){
		for(int i=0; i<solutionArray.length; i++){
			solutionArray[i]=0;
		}
	}
}