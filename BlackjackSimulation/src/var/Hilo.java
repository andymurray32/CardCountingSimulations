package var;
import java.util.ArrayList;
import java.util.Iterator;
// Description: Balanced, 'Level- 1' method with only one parameter (True count). Utilizes
// a betting strategy based on the true count

    public class Hilo implements Player {
        public ArrayList<String> cards = new ArrayList();
        public ArrayList<String> runningCountList = new ArrayList();
        public int playerIndex;
        public int chips;
        public int minBet;
        public int bet;
        public String choice;
        public int betExtra=0; //amount of times more than minimum betted
        public int runningCount = 0;
        public int runningCount2 = 0; //for optimal
        public int trueCount= 0;
        public int trueCount2= 0; //for optimal
        public int missedOP;

        public Hilo(int chips, int playerIndex, int minBet) {
            this.chips = chips;
            this.playerIndex = playerIndex;
            this.minBet = minBet;
            this.bet = minBet;
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
                String i = c.substring(0, c.length() - 1); //checking if ace
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
                //for blackjack
                if (!one.equals("A") || !two.equals("J") && !two.equals("Q") && !two.equals("K")) {
                    if (!two.equals("A") || !one.equals("J") && !one.equals("Q") && !one.equals("K")) {
                        this.chips += this.bet;
                    } else {
                        this.chips += this.bet + this.bet / 2;
                    }
                } else {
                    this.chips += this.bet + this.bet / 2;
                }
            //3 chosen as definite advantage
            // true count <2 means that they havent betted more for that instance, making it a missed opportunity
            if (trueCount2>3 && trueCount<2){
                missedOP++;
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
    //cant bet if don't meet minimum bet
    if (this.chips - minBet > 0) {
        if (this.trueCount < 2) {
            this.bet = this.minBet;
        }
        if (this.trueCount>=2 && this.trueCount<5){
            this.bet=trueCount*this.minBet;
            betExtra++;
        }
        if (this.trueCount>=5){
            this.bet=5*this.minBet;
            betExtra++;
        }
        if (this.bet > this.chips()) {
            this.bet = this.chips() / 2;
            System.out.println("Could not afford full bet");
        }

        }
    else {
        this.bet=0;
    }
}
        public void newShuffle() {
            this.runningCount = 0;
            this.runningCount2=0;
            this.trueCount=0;
            this.trueCount2=0;
            this.runningCountList.clear(); //also works to count number of cards between shuffles
            this.bet = this.minBet; //as count reset
        }

        public void printPlayer(int chips) {
            int net;
            if (this.chips() > chips) {
                net = this.chips() - chips;
            } else if (this.chips() < chips && this.chips() > 0) {
                net = this.chips() - chips;
            } else {
                net =  this.chips() - chips;
            }
            //bet() called twice on Hilo so betextra/2
            System.out.println("Player " + this.playerIndex() + ": Hilo" + "\n\tRemaining Chips: " + (net + chips)+ "\n\tLoss or gain:"+ (net)+"\n\tMissed opportunities: "+missedOP+
                    "\n\tTimes betted more than minimum:"+betExtra/2) ;
        }

        public void printBet() {
            System.out.println("Hilo- Player:" + this.playerIndex() + " |Bet: " + this.bet+" |Count = " + this.runningCount+" |True Count ="+ trueCount);
        }
        //decksize doesn't change for decksize variable (its original deck size which changes with simulation input)
        public void updateRunningCount(ArrayList<String> countList, int decksize) {
            Iterator var3 = countList.iterator();

            while(var3.hasNext()) {
                String c = (String)var3.next();
                String i = c.substring(0, c.length() - 1);
                this.runningCountList.add(i);
                int x = this.translate(i);
                if (x >= 2 && x <= 7) {
                    ++this.runningCount;
                }
                if (x >= 10) {
                    --this.runningCount;
                }
                //for optimal

                if (x == 2) {
                    this.runningCount2+=0.82;
                }
                if (x == 3) {
                    this.runningCount2+=0.94;
                }
                if (x == 4) {
                    this.runningCount2+=1.21;
                }
                if (x == 5) {
                    this.runningCount2+=1.52;
                }
                if (x == 6) {
                    this.runningCount2+=0.98;
                }
                if (x == 7) {
                    this.runningCount2+=0.57;
                }
                if (x == 8) {
                    this.runningCount2-=0.06;
                }
                if (x == 9) {
                    this.runningCount2-=0.42;
                }
                if (x == 10) {
                    this.runningCount2-=-1.07;
                }
                if (x >= 11 || x==1) {
                    this.runningCount2 -=1.07;
                }
            }
            //True count =running count/ (original deck size-cards that have played)/52
            //running count/ num of unplayed decks = true count
            //int b works out remaining deck number
            int b= (decksize-runningCountList.size())/52;
            //to avoid error, stops dividing by 0
            if (b <=0){
                this.trueCount=this.runningCount;
                this.trueCount2=this.runningCount2;
            }
            else{
                this.trueCount= runningCount/ b; //used for betting
              //  System.out.println("Running Count:"+runningCount+"Deck size:"+b);
                this.trueCount2= runningCount2/b; //optimal for deciding ideal betting scenarios
            }
             }
    }

