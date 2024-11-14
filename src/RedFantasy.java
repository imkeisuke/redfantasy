import java.util.Random;

/**
 * RedFantasy
 */
public class RedFantasy {
    String[] monsters = new String[22];
    int[] monstersPoint = new int[22];

    int[] playerMonsters = new int[5];
    int[] playerMonstersPoint = new int[5];

    int[] cpuMonsters = new int[5];
    int[] cpuMonstersPoint = new int[5];

    int playerHp = 50;
    int cpuHp = 50;
    int playerBonusPoint = 0;        
    int cpuBonusPoint = 0;

    Random rnd = new Random();

    int[] playerHistory = new int[100];
    int[] cpuHistory = new int[100];
    
    public RedFantasy() {
        for (int i = 0; i < this.playerMonsters.length; i++) {
            this.playerMonsters[i] = -1;
            this.cpuMonsters[i] = -1;
        }
        for (int i = 0; i < this.playerHistory.length; i++) {
            this.playerHistory[i] = -9999;
            this.cpuHistory[i] = -9999;
        }
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

        System.out.println("Player Monster Pointの合計: " + playerTotalPoints);
        System.out.println("CPU Monster Pointの合計: " + cpuTotalPoints);
        System.out.println("--------------------");

        determineWinner(playerTotalPoints, cpuTotalPoints);

        recordHistory(this.playerHistory, this.playerHp);
        recordHistory(this.cpuHistory, this.cpuHp);
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
            System.out.println("失敗！すべてのモンスターポイントが半分になる");
            for (int i = 0; i < monstersPointArray.length; i++) {
                if (monstersPointArray[i] != -1) {
                    monstersPointArray[i] /= 2;
                }
            }
        } else if (dice == 6) {
            System.out.println("Critical！すべてのモンスターポイントが倍になる");
            for (int i = 0; i < monstersPointArray.length; i++) {
                if (monstersPointArray[i] != -1) {
                    monstersPointArray[i] *= 2;
                }
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

    public int[] getPlayerHistory() {
        return this.playerHistory;
    }

    public int[] getCpuHistory() {
        return this.cpuHistory;
    }

    public int getPlayerHp() {
        return this.playerHp; 
    }

    public int getCpuHp() {
        return this.cpuHp;
    }

    public void setMonstersPoint(int[] tempMonstersPoint) {
        this.monstersPoint = tempMonstersPoint;
    }

    public void setMonsterZukan(String[] tempMonsters) {
        this.monsters = tempMonsters;
    }
}
