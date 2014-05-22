import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class MineSweeper {

    public static final int LEVEL_BEGINNER = 0;
    public static final int MINES_BEGINNER = 10;
    public static final int WIDTH_BEGINNER = 8;
    public static final int HEIGHT_BEGINNER = 8;

    public static final int LEVEL_INTERMEDIATE = 1;
    public static final int MINES_INTERMEDIATE = 40;
    public static final int WIDTH_INTERMEDIATE = 16;
    public static final int HEIGHT_INTERMEDIATE = 16;

    public static final int LEVEL_EXPERT = 2;
    public static final int MINES_EXPERT = 99;
    public static final int WIDTH_EXPERT = 31;
    public static final int HEIGHT_EXPERT = 16;

    private static final int MINE = -1;
    private static final int CLOSED = 0;
    private static final int OPEN = 1;
    private static final int WRONG_MARKED_MINE = 2;

    private int numOfMines, width, height, minesRemaining;
    private int[][] mineLocations;
    private int[][] turf;
    private int[][] mask;

    public MineSweeper(int level) {
        switch (level) {
        case LEVEL_BEGINNER:
            this.numOfMines = MINES_BEGINNER;
            this.width = WIDTH_BEGINNER;
            this.height = HEIGHT_BEGINNER;
            this.doInit();
            break;
        case LEVEL_INTERMEDIATE:
            this.numOfMines = MINES_INTERMEDIATE;
            this.width = WIDTH_INTERMEDIATE;
            this.height = HEIGHT_INTERMEDIATE;
            this.doInit();
            break;
        case LEVEL_EXPERT:
            this.numOfMines = MINES_EXPERT;
            this.width = WIDTH_EXPERT;
            this.height = HEIGHT_EXPERT;
            this.doInit();
            break;
        default:
            throw new IllegalArgumentException("Invalid value " + level + " for level. " + "Valid values are 1:Beginner, 2:Intermediate, 3:Expert.");
        }
    }

    public MineSweeper(int numOfMines, int width, int height) {
        this.numOfMines = numOfMines;
        this.width = width;
        this.height = height;
        this.doInit();
    }

    private void doInit() {
        turf = new int[width][height];
        mask = new int[width][height];
        mineLocations = new int[numOfMines][];
        minesRemaining = numOfMines;

        Random r = new Random();
        int index1, index2;
        for (int i = 0; i < numOfMines; i++) {
            do {
                index1 = r.nextInt(width);
                index2 = r.nextInt(height);
            } while (turf[index1][index2] == MINE);
            turf[index1][index2] = MINE;
            mineLocations[i] = new int[] { index1, index2 };
        }
        for (int i = 0; i < numOfMines; i++) {
            int x = mineLocations[i][0];
            int y = mineLocations[i][1];
            incrementProximityCount(x - 1, y - 1);
            incrementProximityCount(x - 1, y);
            incrementProximityCount(x - 1, y + 1);
            incrementProximityCount(x, y - 1);
            incrementProximityCount(x, y + 1);
            incrementProximityCount(x + 1, y - 1);
            incrementProximityCount(x + 1, y);
            incrementProximityCount(x + 1, y + 1);
        }
    }

    private void incrementProximityCount(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height && turf[x][y] != MINE) {
            turf[x][y]++;
        }
    }

    public void printTurf() {
        System.out.print("\t");
        for (int i = 0; i < this.width; i++) {
            System.out.print((i + 1) + "\t");
        }
        System.out.println("\n");
        for (int i = 0; i < this.height; i++) {
            System.out.print((i + 1) + "\t");
            for (int j = 0; j < this.width; j++) {
                if (turf[j][i] == MINE) {
                    System.out.print("*\t");
                } else {
                    if (turf[j][i] == 0) {
                        System.out.print("\t");
                    } else {
                        System.out.print(turf[j][i] + "\t");
                    }
                }
            }
            System.out.println("\n");
        }
    }

    public void printGame(boolean gameOver) {
        System.out.print("\t");
        for (int i = 0; i < this.width; i++) {
            System.out.print((i + 1) + "\t");
        }
        System.out.println("\n");
        for (int i = 0; i < this.height; i++) {
            System.out.print((i + 1) + "\t");
            for (int j = 0; j < this.width; j++) {
                if (mask[j][i] == CLOSED) {
                    if (gameOver && turf[j][i] == MINE) { // if game over, expose all mine locations
                        System.out.print("*\t");
                    } else {
                        System.out.print("-\t");
                    }
                } else if (mask[j][i] == OPEN) {
                    if (turf[j][i] == MINE) {
                        System.out.print("BOOM\t");
                    } else {
                        if (turf[j][i] == 0) {
                            System.out.print("\t");
                        } else {
                            System.out.print(turf[j][i] + "\t");
                        }
                    }
                } else if (mask[j][i] == MINE) {
                    System.out.print("M\t");
                } else if (mask[j][i] == WRONG_MARKED_MINE) {
                    System.out.print("WRONG\t");
                }
            }
            System.out.println("\n");
        }
        System.out.println("Mines Remaining : " + this.minesRemaining);
    }

    public boolean openCell(int x, int y) {
        // if cell is not already open or marked as a mine
        if (this.mask[x][y] == CLOSED) {
            // mark the cell open
            this.mask[x][y] = OPEN;
            // check the opened cell
            if (this.turf[x][y] == MINE) {
                // opened a mine - game over
                return false;
            } else if (this.turf[x][y] > 0) {
                // opened a cell with a mine in proximity - nothing more to do
            } else if (this.turf[x][y] == 0) {
                // opened a cell with no mines nearby
                // recursively open all nearby cells until they have mines nearby
                if (x - 1 >= 0) {
                    if (y - 1 >= 0)
                        openCell(x - 1, y - 1);
                    openCell(x - 1, y);
                    if (y + 1 < height)
                        openCell(x - 1, y + 1);
                }
                if (y - 1 >= 0)
                    openCell(x, y - 1);
                if (y + 1 < height)
                    openCell(x, y + 1);
                if (x + 1 < width) {
                    if (y - 1 >= 0)
                        openCell(x + 1, y - 1);
                    openCell(x + 1, y);
                    if (y + 1 < height)
                        openCell(x + 1, y + 1);
                }
            }
        }
        return true;
    }

    public void markMine(int x, int y) {
        if (this.mask[x][y] == OPEN) {
            return; // cell already open - cannot be marked
        } else if (this.mask[x][y] == CLOSED) {
            this.mask[x][y] = MINE;
            this.minesRemaining--;
        } else if (this.mask[x][y] == MINE) {
            this.mask[x][y] = CLOSED;
            this.minesRemaining++;
        }
    }

    public boolean validateMarkedMines(int x, int y) {
        // only open cells with proximity count > 0 can be validated
        if (this.mask[x][y] != OPEN) {
            System.out.println("Location " + (x + 1) + " : " + (y + 1) + " cannot be validated because it has not been opened yet");
            return true;
        }
        if (this.turf[x][y] == 0) {
            System.out.println("Location " + (x + 1) + " : " + (y + 1) + " cannot be validated because it has no mines nearby");
            return true;
        }
        int markedCount = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < width && j >= 0 && j < height && this.mask[i][j] == MINE) {
                    markedCount++;
                }
            }
        }
        if (markedCount != this.turf[x][y]) {
            // not enough mines have been marked to validate - return now
            System.out.println("Cannot validate: Only " + markedCount + " of " + turf[x][y] + " mine(s) have been marked around cell " + (x + 1) + " : " + (y + 1));
            return true;
        }
        // open the cells around the cell whose mines have been marked
        // validate if the marked mines match the actual mines
        boolean validMove = true;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < width && j >= 0 && j < height) {
                    // TODO could use the openCell method to replace all the 4 if else statements below
                    if (this.turf[i][j] != MINE && this.mask[i][j] != MINE) {
                        // good guess - open this cell
                        this.openCell(i, j);
                    } else if (this.turf[i][j] != MINE && this.mask[i][j] == MINE) {
                        // oh oh! got the wrong cell - game over !
                        this.mask[i][j] = WRONG_MARKED_MINE;
                        validMove = false;
                    } else if (this.turf[i][j] == MINE && this.mask[i][j] != MINE) {
                        // oh oh! missed this one - game over !
                        this.mask[i][j] = OPEN;
                        validMove = false;
                    } else if (this.turf[i][j] == MINE && this.mask[i][j] == MINE) {
                        // good guess - nothing to do
                    }
                }
            }
        }
        return validMove;
    }

    public static void main(String[] args) throws IOException {
        MineSweeper m = new MineSweeper(LEVEL_INTERMEDIATE);
        m.printTurf();
        m.printGame(false);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (true) {
            System.out.println("\nEnter command in format ACTION<space>ColumnX<space>RowY where ACTION = O (open) / M (mark mine) / V (validate mines) or EXIT: ");
            input = br.readLine();
            if ("exit".equalsIgnoreCase(input))
                System.exit(0);
            String[] command = input.split(" ");
            String action = command[0];
            int x = Integer.parseInt(command[1]) - 1;
            int y = Integer.parseInt(command[2]) - 1;
            boolean validMove = true;
            if ("O".equalsIgnoreCase(action)) {
                validMove = m.openCell(x, y);
            } else if ("M".equalsIgnoreCase(action)) {
                m.markMine(x, y);
            } else if ("V".equalsIgnoreCase(action)) {
                validMove = m.validateMarkedMines(x, y);
            }
            // if not valid move, game over - expose all mine locations
            // else print game state as usual
            m.printGame(!validMove);
            if (!validMove) {
                System.out.println("\n ...GAME OVER... \n");
                System.exit(0);
            }
        }
    }
}
