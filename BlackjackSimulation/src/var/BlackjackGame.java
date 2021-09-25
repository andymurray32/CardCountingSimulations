package var;

import java.util.ArrayList;

public class BlackjackGame {
    public int numPlayers;
    public Player[] players;
    public Dealer dealer;
    public Deck deck;
    public int dealerUp;
    public ArrayList<String> countList = new ArrayList();
    int chips;
    int minBet;


    public BlackjackGame(int basicPlayer, int hilo, int halves,int ko, int hiopt2, int UAPC,int zen2, int chips, int minBet,int deckNum) {
        this.numPlayers = basicPlayer + hilo + halves+ko+hiopt2 + UAPC+zen2;
        this.players = new Player[this.numPlayers];
        this.deck = new Deck(deckNum);
        this.minBet= minBet;
        //manages player Index between methods
        int i;
        for(i = 0; i < basicPlayer; ++i) {
            Player p = new BasicPlayer(chips, i + 1, minBet);
            this.players[i] = p;
        }

        for(i = basicPlayer; i < this.numPlayers; ++i) {
            Player p = new Hilo(chips, i + 1, minBet);
            this.players[i] = p;
        }

        for(i = basicPlayer + hilo; i < this.numPlayers; ++i) {
            Player p = new Halves(chips, i + 1, minBet);
            this.players[i] = p;
        }
        for(i = basicPlayer + hilo+ halves; i < this.numPlayers; ++i) {
            Player p = new KO(chips, i + 1, minBet);
            this.players[i] = p;
        }
        for(i = basicPlayer + hilo+ halves+ko; i < this.numPlayers; ++i) {
            Player p = new HiOpt2(chips, i + 1, minBet);
            this.players[i] = p;
        }
        for(i = basicPlayer + hilo+ halves+ko+ hiopt2; i < this.numPlayers; ++i) {
            Player p = new UAPC(chips, i + 1, minBet);
            this.players[i] = p;
        }
        for(i = basicPlayer + hilo+ halves+ko+ hiopt2+UAPC; i < this.numPlayers; ++i) {
            Player p = new Zen2(chips, i + 1, minBet);
            this.players[i] = p;
        }
        this.dealer = new Dealer();

        this.chips = chips;
    }

    public void newShuffle() {

        for(int i = 0; i < numPlayers; ++i) {
            Player p = players[i];
            p.newShuffle();
        }

    }

    public String checkCard(String card) {
        if (card.contains("X")) {
            this.newShuffle();
            String x = this.checkCard(this.deck.deal());
            return x;
        } else {
            return card;
        }
    }

    public void deal() {
        this.countList.clear();
        this.dealer.newRound();

        String c;
        Player p;
        int i;
        for(i = 0; i < numPlayers; ++i) { //for each player
            p = players[i];
            p.newRound();
            c = this.checkCard(this.deck.deal());
            p.addCard(c);
            this.countList.add(c);
        }

        c = this.checkCard(this.deck.deal());
        this.dealer.addCard(c);
        this.countList.add(c);

        for(i = 0; i < numPlayers; ++i) {
            p = players[i];
            c = this.checkCard(this.deck.deal());
            p.addCard(c);
            this.countList.add(c);
        }

        c = this.checkCard(this.deck.deal());
        this.countList.add(c);
        this.dealer.addCard(c);
        this.dealerUp = this.dealer.dealerUp();
    }

    public void printTotals() {
        System.out.print("\n");
        for(int i = 0; i < numPlayers; ++i) {
            Player p = players[i];
            p.printPlayer(this.chips);
        }

        System.out.print("\n");
    }

    public void printRound(Player p, Player d) {

        p.printHand();
        //System.out.println(" PCHIPS "+p.chips()+"MINBET"+minBet);
        int x= p.chips();
        if (x-minBet<0){
            System.out.println("\nNot enough chips for bet");
        }
        else{
        if (p.choice().equals("Bust")) {
            p.loseHand();
            System.out.print("\n LOST- Busted)");
        } else if (p.choice().equals("Stay")) {
            if (d.choice().equals("Bust")) {
                System.out.print("\nWIN- Dealer Busted");
                p.winHand();
            } else if (p.sumCards() < d.sumCards()) {
                System.out.print("\nLOSS- Cards less than dealer");
                p.loseHand();
            } else if (p.sumCards() > d.sumCards()) {
                System.out.print("\nWIN- Cards more than than dealer");
                p.winHand();
            } else {
                System.out.print("\nTie- Card value equal to dealer");
            }
        }}
        System.out.println("\nRemaining Chips: " + p.chips()+"\n");
    }

    public void playPrintedRound() {
        Player p;
        for(int i = 0; i < numPlayers; ++i) {
            p = players[i];
            p.bet();
            p.printBet();
        }

        this.deal();
        System.out.println("\nDealer's first card: " + this.dealerUp);

        for(int j = 0; j < numPlayers; ++j) {
            p = players[j];

            for(String choice = p.play(this.dealerUp); choice.equals("Hit"); choice = p.play(this.dealerUp)) {
                String c = this.checkCard(this.deck.deal());
                p.addCard(c);
                this.countList.add(c);
            }
        }

        for(String dc = this.dealer.play(0); dc.equals("Hit"); dc = this.dealer.play(this.dealerUp)) {
            String c = this.checkCard(this.deck.deal());
            this.countList.add(c);
            this.dealer.addCard(c);
        }

    //running count for each player
        for(int k = 0; k < numPlayers; ++k) {
            Player a = players[k];
            int b= this.deck.size;
            a.updateRunningCount(this.countList, b);
            this.printRound(a, this.dealer);
        }

        this.dealer.printHand();
        System.out.print("\n");
    }

    public void playRound() {

        Player p;
        //for each player
        for(int i = 0; i< numPlayers; ++i) {
            p = players[i];
            p.bet();
        }

        this.deal();

        for(int j = 0; j < numPlayers; ++j) {
            p = players[j];
            for(String choice = p.play(this.dealerUp); choice.equals("Hit"); choice = p.play(this.dealerUp)) {
                String c = this.checkCard(this.deck.deal());
                p.addCard(c);
                this.countList.add(c);
            }
        }

        for(String dc = this.dealer.play(0); dc.equals("Hit"); dc = this.dealer.play(this.dealerUp)) {
            String c = this.checkCard(this.deck.deal());
            this.countList.add(c);
            this.dealer.addCard(c);
        }
        }
}
