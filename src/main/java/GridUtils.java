import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility functions for a grid
 */
public class GridUtils {


  /**
   * Builds a grid.
   *
   * @param rows     number of rows in the grid
   * @param columns  number of columns in the grid
   * @param maxValue maximum value (cost) for each cell
   * @return a 2D array containing a random costs for each cell
   */
  public static List<List<Integer>> buildGrid(int rows, int columns, int maxValue) {

    List<Integer> possibleValues = getPossibleValues(maxValue);

    List<List<Integer>> matrix = new ArrayList<>(rows);
    for (int i = 0; i < rows; i++) {
      List<Integer> row = new ArrayList<>(columns);
      for (int j = 0; j < columns; j++) {
        if (i == 0 && j == 0) {
          row.add(0);
        } else if (i == (rows - 1) && j == (columns - 1)) {
          row.add(0);
        } else {
          int index = ThreadLocalRandom.current().nextInt(possibleValues.size());
          row.add(possibleValues.get(index));
        }
      }
      matrix.add(row);
    }
    return matrix;
  }

  /**
   * Prints a formatted grid to the console.
   *
   * @param grid the grid to print
   */
  public static void print(List<List<Integer>> grid) {

    StringBuilder sb = new StringBuilder();

    sb.append(rowSeparator(grid.get(0).size()));
    for (List<Integer> row : grid) {
      for (Integer value : row) {
        sb.append("|  ");
        sb.append(StringUtils.center(value.toString(), 3, " "));
        sb.append(" ");
      }
      sb.append("|");
      sb.append("\n");
      sb.append(rowSeparator(row.size()));
    }

    System.out.println(sb.toString());

  }

  /**
   * Print to the console the cheapest path in the formatted grid with the cumulative
   * costs of the path.  Also prints the total costs and total number of moves for the cheapest path.
   *
   * @param grid grid
   * @param path the cheapest path of cells as an ordered list
   */
  public static void printCheapestPath(List<List<Integer>> grid, List<Cell> path) {

    Integer totalCost = 0;
    Integer totalMoves = 0;
    StringBuilder sb = new StringBuilder("SOLUTION:");
    sb.append("\n");

    sb.append(rowSeparator(grid.get(0).size()));

    for (int row = 0; row < grid.size(); row++) {

      for (int column = 0; column < grid.get(row).size(); column++) {

        sb.append("|  ");
        if (inPath(row, column, path) || (row == (grid.size() - 1) && column == (grid.get(0).size() - 1))) {
          totalCost += grid.get(row).get(column);
          sb.append(StringUtils.center(totalCost.toString(), 3, " "));
          totalMoves++;
        } else {
          sb.append("   ");
        }
        sb.append(" ");
      }

      sb.append("|");
      sb.append("\n");
      sb.append(rowSeparator(grid.get(0).size()));
    }

    sb.append("\n");
    sb.append(StringUtils.repeat('-', rowSeparator(grid.get(0).size()).length() - 1));
    sb.append("\n");
    sb.append("Total Cost: ");
    sb.append(totalCost);
    sb.append(" | Total Moves: ");
    sb.append(totalMoves);
    sb.append("\n");
    sb.append(StringUtils.repeat('-', rowSeparator(grid.get(0).size()).length() - 1));
    System.out.println(sb.toString());

  }

  /**
   * Determines if cell (row, column) is in a list of cells.
   *
   * @param row    row of the cell
   * @param column column of the cell
   * @param cells  list of cells
   * @return true if cell is in list
   */
  private static boolean inPath(int row, int column, List<Cell> cells) {

    for (Cell cell : cells) {

      if (cell.getRow() == row && cell.getColumn() == column) {
        return true;
      }
    }

    return false;
  }

  /**
   * Generates a row separator for the grid.
   *
   * @param columns number of columns
   * @return string row separator
   */
  protected static String rowSeparator(int columns) {

    StringBuilder sb = new StringBuilder();
    sb.append("+");
    for (int i = 0; i < columns; i++) {
      sb.append("------+");
    }
    sb.append("\n");
    return sb.toString();
  }

  /**
   * Generates a list of possibly values from 1 to a maximum value
   *
   * @param maxValue max value
   * @return list of possible values
   */
  protected static List<Integer> getPossibleValues(int maxValue) {

    List<Integer> possibleValues = new ArrayList<>();
    for (int value = 1; value <= maxValue; value++) {
      possibleValues.add(value);
    }
    return possibleValues;

  }

}
