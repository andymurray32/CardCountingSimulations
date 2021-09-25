package var;
import java.util.ArrayList;

public interface Player {
    void newRound();

    void addCard(String var1);

    int sumCards();

    String play(int var1);

    void printHand();

    String choice();

    void winHand();

    void loseHand();

    int chips();

    String playerIndex();

    int dealerUp();

    void bet();

    void newShuffle();

    void printPlayer(int var1);

    void printBet();

    void updateRunningCount(ArrayList<String> var1, int b);

    default int translate(String card) {
        char c = card.charAt(0);
        if ("JQK".indexOf(c) > -1) {
            return 10;
        } else {
            return c == 'A' ? 11 : Integer.parseInt(card);
        }
    }
}