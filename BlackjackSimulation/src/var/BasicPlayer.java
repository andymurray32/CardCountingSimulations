package var;
import java.util.ArrayList;
import java.util.Iterator;

public class BasicPlayer implements Player {
    public ArrayList<String> cards = new ArrayList();
    public int playerIndex;
    public int chips;
    public int bet;
    public String choice;


    public BasicPlayer(int chips, int playerIndex, int bet) {
        this.chips = chips;
        this.playerIndex = playerIndex;
        this.bet = bet;
    }

    public String choice() {
        return this.choice;
    }

    public void printHand() {
        System.out.print("Player " + this.playerIndex + ": ");
        Iterator it = this.cards.iterator();

        while(it.hasNext()) {
            String i = (String)it.next();
            System.out.print(i + ", ");
        }

        System.out.print("Value: " + this.sumCards());
    }

    public void newRound() {
        this.cards.clear();

    }

    public void addCard(String card) {
        this.cards.add(card);
    }

    public int sumCards() {
        int sum = 0;
        int aceCount = 0;

        int x;
        for(Iterator it = this.cards.iterator(); it.hasNext(); sum += x) {
            String c = (String)it.next();
            String i = c.substring(0, c.length() - 1);
            if (i.equals("A")) {
                ++aceCount;
            }

            x = this.translate(i);
        }

        while(sum > 21 && aceCount > 0) {
            sum -= 10;
            --aceCount;
        }

        return sum;
    }

    public String play(int deal) {
        int sum = this.sumCards();
        if (sum > 21) {
            this.choice = "Bust";
        }
        else if (sum >= 17) {
            this.choice = "Stay";
        } else if (sum <= 16 && sum >= 13) {
            if (deal < 7) {
                this.choice = "Stay";
            } else {
                this.choice = "Hit";
            }
        } else if (sum == 12) {
            //if dealer 4 to 6 card then stay
            if (deal >= 4 && deal <= 6) {
                this.choice = "Stay";
            } else {
                this.choice = "Hit";
            }
        } //if less than or equal to 11 then hit
        else if (sum <= 11) {
            this.choice = "Hit";
        }

        return this.choice;
    }


    public void winHand() {

            String c1 = this.cards.get(0);
            String c2 = this.cards.get(1);
            String one = c1.substring(0, c1.length() - 1);
            String two = c2.substring(0, c2.length() - 1);
            if (!one.equals("A") || !two.equals("J") && !two.equals("Q") && !two.equals("K")) {
                if (!two.equals("A") || !one.equals("J") && !one.equals("Q") && !one.equals("K")) {
                    this.chips += this.bet;
                } else {
                    this.chips += this.bet + this.bet / 2;
                }
            } else {
                this.chips += this.bet + this.bet / 2;
            }
        }



    public void loseHand() {
            this.chips -= this.bet;
        }


    public String playerIndex() {
        return Integer.toString(this.playerIndex);
    }

    public int chips() {
        return this.chips;
    }

    public int dealerUp() {
        return 0;
    }

    public void bet() {
    }

    public void newShuffle() {
    }

    public void printPlayer(int chips) {
        int net;
        if (this.chips() > chips) {
            net = this.chips() - chips;
        } else if (this.chips() < chips && this.chips() > 0) {
            net = this.chips() - chips;
        } else {
            net = chips + this.chips();
        }
        System.out.println("Player " + this.playerIndex() + ": BasicPlayer"+ "\n\tRemaining Chips: " + (net + chips)+ "\n\tLoss or gain:"+ (net)) ;
    }

    public void printBet() {
        System.out.println("BasicPlayer" + this.playerIndex() + " Bet = " + this.bet);
    }

    public void updateRunningCount(ArrayList<String> countList, int b) {
    }
}
