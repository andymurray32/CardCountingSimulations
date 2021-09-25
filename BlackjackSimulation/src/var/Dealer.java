package var;
import java.util.ArrayList;
import java.util.Iterator;

public class Dealer implements Player {
    ArrayList<String> cards = new ArrayList();
    String choice;


    public Dealer() {
    }

    public void printHand() {
        System.out.print("Dealer: ");
        Iterator var2 = this.cards.iterator();

        while(var2.hasNext()) {
            String i = (String)var2.next();
            System.out.print(i + ", ");
        }

        System.out.print("Total: " + this.sumCards());
        System.out.print("\n");
    }

    public int dealerUp() {
        String c = this.cards.get(1);
        return this.translate(c.substring(0, c.length() - 1));
    }

    public String choice() {
        return this.choice;
    }

    public void newRound() {
        this.cards.clear();
    }

    public void addCard(String card) {
        this.cards.add(card);
    }

    public int sumCards() {
        int sum = 0;
        boolean ace = false;

        Integer x;
        for(Iterator var4 = this.cards.iterator(); var4.hasNext(); sum += x) {
            String c = (String)var4.next();
            String i = c.substring(0, c.length() - 1);
            if (i.equals("A")) {
                ace = true;
            }

            x = this.translate(i); //get values of ace,king, queen, jack
        }

        if (sum > 21 && ace) {
            sum -= 10;
        }

        return sum;
    }

    public String play(int deal) {
        int sum = this.sumCards();
        if (sum > 21) {
            this.choice = "Bust";
        } else if (sum >= 17) {
            this.choice = "Stay";
        } else {
            this.choice = "Hit";
        }

        return this.choice;
    }


    public void winHand() {
    }

    public void loseHand() {
    }

    public String playerIndex() {
        return "";
    }

    public int chips() {
        return 0;
    }

    public void bet() {
    }

    public void newShuffle() {
    }

    public void printPlayer(int chips) {
    }

    public void printBet() {
    }

    public void updateRunningCount(ArrayList<String> countList,int b) {
    }
}
