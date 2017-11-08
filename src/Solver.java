import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class Solver {
    private static boolean firstBestCaseOutputDone = false;
    private static boolean solved = false;

    public static void main (String[] args) {
        try {
            Sudoku game;
            if (args.length < 1) {
                game = new Sudoku();
            } else {
                final String FILEPATH = args[0];
                game = new Sudoku(FILEPATH);
            }


            SudokuPanel panel = new SudokuPanel(game);
            JFrame frame = new JFrame("Sudoku");
            JButton solveButton = new JButton("Solve sudoku");
            frame.setLayout(null);
            frame.setSize(1000, 1000);
            Insets insets = frame.getInsets();
            frame.add(solveButton);
            frame.add(panel);
            Dimension panelPreferredSize = panel.getPreferredSize();
            Dimension buttonSize = solveButton.getPreferredSize();
            solveButton.setBounds(10 + insets.left, 10 + insets.top, (int) buttonSize.getWidth(), (int) buttonSize.getHeight());
            panel.setBounds(10 + insets.left, 10 + insets.top + 10 + (int) buttonSize.getHeight(), (int) panelPreferredSize.getWidth(), (int) panelPreferredSize.getHeight());
            solveButton.addActionListener(e -> {
                if (!solved) {
                    Sudoku solution = solve(game);
                    panel.setSudoku(solution);
                    frame.update(frame.getGraphics());
                    solveButton.setText("Reset");
                    solved = true;
                } else {
                    solveButton.setText("Solve sudoku");
                    panel.setSudoku(game);
                    frame.update(frame.getGraphics());
                    solved = false;
                }
            });
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Function we call to solve, will show timing as well
     *
     * @param game	The game we want to solve
     * @return		The solution sudoku
     */
    private static Sudoku solve (Sudoku game) {
        final long startTime = System.nanoTime();
        System.out.println(game);
        Sudoku solved = backtrackingSearch(game);
        System.out.println(solved);
        final long endTime = System.nanoTime();
        Double timeInSeconds = ((endTime - startTime) / Math.pow(10, 9));
        System.out.println(timeInSeconds + " seconds to solve");
        return solved;
    }

    /**
     * Backtracking recursive solver for sudoku
     *
     * @param game	The game of sudoku we want to solve
     * @return		The solution sudoku
     */
    private static Sudoku backtrackingSearch (Sudoku game) {
        if (game.isFull()) {
            return game;
        }

        Position bestCase = game.getMostConstrainedCase();

        if (!firstBestCaseOutputDone) {
            System.out.println("The first most constrained case is: " + bestCase);
            System.out.println();
            firstBestCaseOutputDone = true;
        }

        if (bestCase != null) {
            Domain domain = game.getDomainOfCase(bestCase.getLine(), bestCase.getColumn());
            Iterator<Integer> iterator = domain.getValues().iterator();

            while (iterator.hasNext()) {
                Integer value = iterator.next();
                Sudoku current = new Sudoku(game);
                current.fillCase(bestCase.getLine(), bestCase.getColumn(), value);
                Sudoku result = backtrackingSearch(current);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

}
