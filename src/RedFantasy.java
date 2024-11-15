import java.util.Random;

/**
 * RedFantasy
 */
public class RedFantasy {
    public String[] monsters = new String[22];
    public int[] monstersPoint = new int[22];

    public int[] playerMonsters = new int[5];
    public int[] playerMonstersPoint = new int[5];

    public int[] cpuMonsters = new int[5];
    public int[] cpuMonstersPoint = new int[5];

    public int playerHp = 50;
    public int cpuHp = 50;
    public int playerBonusPoint = 0;        
    public int cpuBonusPoint = 0;

    public Random rnd = new Random();

    public int[] playerHistory = new int[100];
    public int[] cpuHistory = new int[100];

    public RedFantasy() {
        initializeMonsters();
        initializeHistory();
        this.playerHistory[0] = this.playerHp;
        this.cpuHistory[0] = this.cpuHp;
    }

    public void startPhase() {
        drawMonsters("Player", this.playerMonsters, this.playerMonstersPoint);
        drawMonsters("CPU", this.cpuMonsters, this.cpuMonstersPoint);

        int playerDice = rollDice("Player");
        adjustMonsterPoints(playerDice, this.playerMonstersPoint);

        int cpuDice = rollDice("CPU");
        adjustMonsterPoints(cpuDice, this.cpuMonstersPoint);

        int playerTotalPoints = calculateTotalPoints(this.playerMonstersPoint, this.playerBonusPoint);
        int cpuTotalPoints = calculateTotalPoints(this.cpuMonstersPoint, this.cpuBonusPoint);

        displayTotalPoints(playerTotalPoints, cpuTotalPoints);
        determineWinner(playerTotalPoints, cpuTotalPoints);

        recordHistory(this.playerHistory, this.playerHp);
        recordHistory(this.cpuHistory, this.cpuHp);
    }

    private void initializeMonsters() {
        for (int i = 0; i < this.playerMonsters.length; i++) {
            this.playerMonsters[i] = -1;
            this.cpuMonsters[i] = -1;
        }
    }

    private void initializeHistory() {
        for (int i = 0; i < this.playerHistory.length; i++) {
            this.playerHistory[i] = -9999;
            this.cpuHistory[i] = -9999;
        }
    }

    private void drawMonsters(String playerType, int[] monstersArray, int[] monstersPointArray) {
        int numberOfMonsters = this.rnd.nextInt(monstersArray.length - 2) + 3;
        System.out.println(playerType + " Draw " + numberOfMonsters + " monsters");
        for (int i = 0; i < numberOfMonsters; i++) {
            int m = this.rnd.nextInt(this.monsters.length);
            monstersArray[i] = m;
            monstersPointArray[i] = this.monstersPoint[m];
        }
    }

    private int rollDice(String playerType) {
        int dice = this.rnd.nextInt(6) + 1;
        System.out.println(playerType + "'s Dice: " + dice);
        return dice;
    }

    private void adjustMonsterPoints(int dice, int[] monstersPointArray) {
        if (dice == 1) {
            halveMonsterPoints(monstersPointArray);
        } else if (dice == 6) {
            doubleMonsterPoints(monstersPointArray);
        }
    }

    private void halveMonsterPoints(int[] monstersPointArray) {
        System.out.println("失敗！すべてのモンスターポイントが半分になる");
        for (int i = 0; i < monstersPointArray.length; i++) {
            if (monstersPointArray[i] != -1) {
                monstersPointArray[i] /= 2;
            }
        }
    }

    private void doubleMonsterPoints(int[] monstersPointArray) {
        System.out.println("Critical！すべてのモンスターポイントが倍になる");
        for (int i = 0; i < monstersPointArray.length; i++) {
            if (monstersPointArray[i] != -1) {
                monstersPointArray[i] *= 2;
            }
        }
    }

    private int calculateTotalPoints(int[] monstersPointArray, int bonusPoint) {
        int totalPoints = bonusPoint;
        for (int point : monstersPointArray) {
            if (point != -1) {
                totalPoints += point;
            }
        }
        return totalPoints;
    }

    private void displayTotalPoints(int playerTotalPoints, int cpuTotalPoints) {
        System.out.println("Player Monster Pointの合計: " + playerTotalPoints);
        System.out.println("CPU Monster Pointの合計: " + cpuTotalPoints);
        System.out.println("--------------------");
    }

    private void determineWinner(int playerPoints, int cpuPoints) {
        if (playerPoints > cpuPoints) {
            System.out.println("Player Win!");
            this.cpuHp -= (playerPoints - cpuPoints);
        } else if (cpuPoints > playerPoints) {
            System.out.println("CPU Win!");
            this.playerHp -= (cpuPoints - playerPoints);
        } else {
            System.out.println("Draw!");
        }
        System.out.println("Player HP is " + this.playerHp);
        System.out.println("CPU HP is " + this.cpuHp);
    }

    private void recordHistory(int[] historyArray, int hp) {
        for (int i = 0; i < historyArray.length; i++) {
            if (historyArray[i] == -9999) {
                historyArray[i] = hp;
                break;
            }
        }
    }
}
