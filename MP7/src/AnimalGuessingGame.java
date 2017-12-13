import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AnimalGuessingGame extends Animal{
	private Animal root = null;
	
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	//creates a new animal game and asks the original question to build off of  
	public AnimalGuessingGame() {
		root = new Animal();
		root.question = "Is is a mammal?"; 
		root.yes = "dog";
		root.no = "lizard";
	}

	// Asks the user the question and returns T/F based on whether or not they respond yes or no
	private boolean AskGuess(String guess) throws Exception{	
		String a;
	    if (guess.charAt(0)== 'a' || guess.charAt(0)== 'e' 
	    		|| guess.charAt(0)== 'i' || guess.charAt(0)== 'u' ) {
	    		a = "an";
	    } else {
	    		a = "a";
	    }
		String userInput;

		System.out.println("Are you thinking of "+ a + " " + 
			guess + "?");
		userInput = input.readLine().toUpperCase();
		
		if (userInput.charAt(0)=='Y') { 
			return true;
		} else { 
		return false;
		}
	}	


	// This method allows the user to run the game 
	public static void main(String[] args) throws Exception {
		AnimalGuessingGame game = new AnimalGuessingGame();
		Animal current = null;
		boolean playgame = true, guessmade = false;
		String userInput;

		System.out.println("Think of an animal and answer the questions below.");
		System.out.println("If I am unsure of the animal, please train me to learn your new animal.");
		System.out.println("Once I have knowledge of your new animal please continue to "
				+ "play again until you are satisfied with my knowledge.");
		System.out.println("Let's get started with the game!");
		System.out.println();
		current = game.root;				
		while (playgame) {
			// Travel down tree until we have a guess
			guessmade = false;
			while (!guessmade) {
				// Ask the question
				System.out.println(current.question);
				userInput = game.input.readLine();
				if (userInput.charAt(0)=='y') {
					// Check if we reached bottom of the list of animals and guesses the animal 
					if (current.yesRight==null) {
						if (!game.AskGuess(current.yes)) {
							// Answer was incorrect. Asks user for right answer 
							game.AddNewAnimal(current,'y');
						}
						guessmade = true;
					}
					else current = current.yesRight;
				}
				else {
					// Check if we reached bottom of tree
					// if so, guess an animal
					if (current.noLeft==null) {
						if (!game.AskGuess(current.no))
						{
							// Here is where the game will add a new animal
							game.AddNewAnimal(current,'n');
						}
						guessmade = true;
					}
					else current = current.noLeft;
				}
			}
			System.out.println("Wanna play the game again?");
			userInput = game.input.readLine();
			if (userInput.charAt(0)=='n') {
				playgame=false;
				System.out.println("Thanks again for playing with us! Please come back soon.");
			}
			//restarts the game with the original question if they choose to play again
			current = game.root;
		}
	}
	// answering yes or no to the question below will determine if it should branch left or right(yes/no)
	private void AddNewAnimal(Animal pointer, char answer) throws Exception {
		String nextAnimal, userInput;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));		
		Animal newAnimal = new Animal();
		if (answer == 'n') {
			newAnimal.no = pointer.no;
			newAnimal.yes = pointer.no;
			pointer.noLeft = newAnimal;	
		}
		else {
			newAnimal.no = pointer.yes;
			newAnimal.yes = pointer.yes;
			pointer.yesRight = newAnimal;
		}
		
		System.out.println("What is the correct answer?");
		nextAnimal = input.readLine();
		System.out.println("Let me know of a question that differentiates"
				+ " between your animal and the one I guessed.");
		newAnimal.question = input.readLine();
		System.out.println("Is the answer 'Yes' or 'No'?");
		userInput = input.readLine().toUpperCase();
		if (userInput.charAt(0)=='Y') {
			newAnimal.yes = nextAnimal;
		}
		else {
			newAnimal.no = nextAnimal;
		}
	}
}
