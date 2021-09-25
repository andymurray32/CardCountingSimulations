package var;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Deck {
    public ArrayList<String> deck = new ArrayList();
    public ArrayList<String> deck2 = new ArrayList(); //used to create new array
    public ArrayList<String> allCards = new ArrayList();
    public int cardPlayedinRound = 0;
    int size;

    public Deck(int num) {
        this.size = 52 * num;

        for(int i = 0; i < num; ++i) {
            for(int j = 0; j < 4; ++j) {
                String suit = "";
                if (j == 0) {
                    suit = "D";
                }

                if (j == 1) {
                    suit = "H";
                }

                if (j == 2) {
                    suit = "S";
                }

                if (j == 3) {
                    suit = "C";
                }

                for(int k = 1; k < 14; ++k) {
                    if (k == 1) {
                        this.deck.add("A" + suit);

                    } else if (k <= 10) {
                        this.deck.add(k + suit);

                    } else if (k == 11) {
                        this.deck.add("J" + suit);

                    } else if (k == 12) {
                        this.deck.add("Q" + suit);

                    } else if (k == 13) {
                        this.deck.add("K" + suit);

                    }
                }
            }
        }
        print();
        Collections.shuffle(this.deck);
        System.out.println("Deck with penetration: \n");
        // for penetration 0.7 = 30% decreased deck size
        double penetration= 0.9;
        System.out.println ("New deck size: "+this.deck.size()*penetration);
        Iterator it = this.deck.iterator();
        int h=0;
        while(it.hasNext()&& h< this.deck.size()*penetration) {
            String i = (String)it.next();
            this.deck2.add(i); //reshuffle will get penetrated deck
            System.out.println(i);
            h++;
        }

    }
//handles reshuffle rate as well as deal
    public String deal() {
        String d = "";
        // checking vs numbers of card being played in a round (shuffle rate)
        // 20% shuffle rate= 4/5 removes 20%
        if (this.size * 4/5 < this.cardPlayedinRound) {
            this.deck.clear();
            System.out.println("-DECK RESHUFFLED-");
            this.deck = new ArrayList(this.deck2);
            Collections.shuffle(this.deck);
            //System.out.println("Deck size"+this.deck.size());  proving its penetrated deck
            this.cardPlayedinRound = 0;
            d = d + "X";
        }

        d = d + this.deck.remove(0);
        ++this.cardPlayedinRound;
        this.allCards.add(d);
        return d;
    }

    public void print() {
        System.out.println("Deck: ");
        Iterator it = this.deck.iterator();
//PRINTING CARD VALUES IN SHOE (all decks)
        while(it.hasNext()) {
            String i = (String)it.next();
            System.out.println(i);
        }
        System.out.print("\n");
    }

}