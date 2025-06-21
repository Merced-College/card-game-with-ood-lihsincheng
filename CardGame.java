//package cardGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * CardGame - A Simple Card Game Program
 * Li-Hsin Cheng
 * June 19th
 */ 

public class CardGame {
 
	// Stores all available cards in the deck
	private static ArrayList<Card> deckOfCards = new ArrayList<Card>();
	// Stores cards currently held by the player
	private static ArrayList<Card> playerCards = new ArrayList<Card>();


	public static void main(String[] args) {
		// Initialize scanner to read data from file
		Scanner input = null;
		// try opening the card.txt file
		try {
			input = new Scanner(new File("cards.txt"));
		} catch (FileNotFoundException e) {
			// error handling
			System.out.println("error");
			e.printStackTrace();
		}
		// Read each line from the file and create Card objects
		while(input.hasNext()) {
			String[] fields  = input.nextLine().split(",");
			//	public Card(String cardSuit, String cardName, int cardValue, String cardPicture) {
			Card newCard = new Card(fields[0], fields[1].trim(),
					Integer.parseInt(fields[2].trim()), fields[3].trim());
			// Add new card to the deck
			deckOfCards.add(newCard);	
		}
		//shuffle the deck of card
		shuffle();

		//for(Card c: deckOfCards)
			//System.out.println(c);

		//deal the player 5 cards
		for(int i = 0; i < 4; i++) {
			playerCards.add(deckOfCards.remove(i));
		}
		// Display player's cards
		System.out.println("players cards");
		for(Card c: playerCards)
			System.out.println(c);

		displayHandValue();

		// add hit/stand logic
		try (Scanner scanner  = new Scanner(System.in)){
			while (calculateHandValue(playerCards) < 21){
				System.out.print("Hit or stand? (h/s): ");
				String choice = scanner.nextLine().toLowerCase(); 

				if (choice.equals("h")){
					playerCards.add(deckOfCards.remove(0));
					System.out.println("You drew: " + playerCards.get(playerCards.size()-1));
					displayHandValue();
				} else if(choice.equals("s")){
					break;
				}
			}
		}
		System.out.println("Final hand value: " + calculateHandValue(playerCards));
		// Check and display if player has any pairs
		System.out.println("pairs is " + checkFor2Kind());

	}//end main


	public static void shuffle() {

		//shuffling the cards by deleting and reinserting
		for (int i = 0; i < deckOfCards.size(); i++) {
			int index = (int) (Math.random()*deckOfCards.size());
			// Remove card at random position
			Card c = deckOfCards.remove(index);
			//System.out.println("c is " + c + ", index is " + index);
			// Add it back to the end of the deck
			deckOfCards.add(c);
		}
	}

	//check for 2 of a kind in the players hand
	public static boolean checkFor2Kind() {

		int count = 0;

		// Compare each card with every other card
		for(int i = 0; i < playerCards.size() - 1; i++) {
			Card current = playerCards.get(i);
			Card next = playerCards.get(i+1);
			
			for(int j = i+1; j < playerCards.size(); j++) {
				next = playerCards.get(j);
				//System.out.println(" comparing " + current);
				//System.out.println(" to " + next);
				// increment pair count if rank matches
				if(current.equals(next))
					count++;
			}//end of inner for
			if(count == 1)
				return true;

		}//end outer for (no pair found)
		return false;
	}
	/**
 * Calculate total value of a player's hand
 */
public static int calculateHandValue(ArrayList<Card> hand){
    int total = 0;
    int aces = 0;

    // sum up all card values
    for (Card card : hand) {
        total += card.getValue();
        if (card.getRank().equals("Ace")){
            aces++; //count how many aces
        }

    }

    // we want to adjust for aces (make sure count is 1 instead of 11 if we're over 21)
    while (total > 21 && aces > 0){
        total -= 10; // we subtract 10 here
        aces --;
    }
    return total;
}

/**
 * Display the player's current hand value
 */

public static void displayHandValue(){
    int value = calculateHandValue(playerCards);
    System.out.println("Current hand value: " + value);

    // Add a few messages
    if (value == 21) {
        System.out.println("BLACKJACK!");
    } else if (value > 21){
        System.out.println("BUST!");
    }
}

}//end class
