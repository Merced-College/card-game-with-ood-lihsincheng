/** 
 * Simple Card Game
 * Date: June 19th
 * Li-Hsin Cheng
 * Basic card-dealing, shuffles, and pair checking 
 */
//package cardGame;

public class Card{

    // Adding variables 
    private String suit;
    private String rank;
    private int value;
    private String picture;

    //Here we create contructor
    public Card(String suit, String rank, int value, String picture){
        this.suit = suit;
        this.rank = rank;
        this.value = value;
        this.picture = picture;
    }

    // Add getters (allows controlled access to private class while preserving encapsulation)
    public String getSuit() {return suit;}
    public String getRank() {return rank;}
    public int getValue() {return value;}
    public String getPicture() {return picture;}

    // Add toString() method 
    @Override
    public String toString(){
        return rank + " of " + suit;
    }
    // Add equals() method to compare two Card objects
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card)) return false;
        Card other = (Card) obj;
        // Compare ONLY ranks for pairing
        return this.rank.equalsIgnoreCase(other.rank);
}
}
