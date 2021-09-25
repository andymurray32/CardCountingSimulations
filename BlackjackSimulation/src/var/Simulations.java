package var;

public class Simulations {

    public static void main(String[] args) {

        BlackjackGame game = new BlackjackGame(0, 0,0 , 2,0,0,2,200, 1,6);
        for(int i = 0; i < 100; ++i) {
            System.out.println("-----------------------------------------");
            System.out.println("ROUND:" +i );
            game.playRound();
            game.playPrintedRound();
        }
        System.out.println("////////////////////////////////");
        game.printTotals();
    }
}