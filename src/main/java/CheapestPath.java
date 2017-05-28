import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.exit;

/**
 * Each cell has a cost randomly assigned to it.  You have to start at the upper-left cell and end at the
 * bottom-right cell.  You can only move down and to the right.  The objective is to find the cheapest
 * path from start to finish.
 */
public class CheapestPath {

  public static void main(String[] args) {

    CommandLineParser commandLineParser = new DefaultParser();
    final Options options = constructOptions();
    CommandLine commandLine;

    int gridRows = 5;
    int gridColumns = 5;
    int maxValue = 9;

    try {
      commandLine = commandLineParser.parse(options, args);

      if (commandLine.hasOption("help")) {
        printHelp(options, System.out);
        exit(1);
      }

      if (commandLine.hasOption("rows")) {
        gridRows = Integer.parseInt(commandLine.getOptionValue('r'));
        if (gridRows == 0) {
          System.out.println("You can't have a row size of 0");
          printHelp(options, System.out);
          exit(0);
        }
      }

      if (commandLine.hasOption("columns")) {
        gridColumns = Integer.parseInt(commandLine.getOptionValue('c'));
        if (gridColumns == 0) {
          System.out.println("You can't have a column size of 0");
          printHelp(options, System.out);
          exit(0);
        }
      }

      if (commandLine.hasOption("max-value")) {
        maxValue = Integer.parseInt(commandLine.getOptionValue('m'));
        if (maxValue == 0 || maxValue > 99) {
          System.out.println("ERROR: Max value must be between 1 and 99");
          printHelp(options, System.out);
        }
      }
    } catch (ParseException parseException) {
      System.err.println(
          "Command line parsing failed. Reason: " + parseException.getMessage());
    }

    System.out.println("\nYou requested a " + gridRows + " x " + gridColumns + " grid for this brain teaser.");

    List<List<Integer>> grid = GridUtils.buildGrid(gridRows, gridColumns, maxValue);

    GridUtils.print(grid);

    List<Cell> path = minimumCostPath(grid);

    GridUtils.printCheapestPath(grid, path);

  }

  /**
   * Find the minimum cost path within the grid.  This is done by starting at the end position (bottom-right) and
   * determining which path is the cheapest from the only two option - the one above the cell or the one to the
   * left of the cell.  The cheapest path cell is stored in a list and repeated recursively for that cell until the
   * start (upper-left) cell is reached.  The list of cells is reversed to give us cheapest path from start to end.
   *
   * @param grid of costs
   * @return list of cells as the cheapest path
   */
  protected static List<Cell> minimumCostPath(List<List<Integer>> grid) {

    List<Cell> result = new ArrayList<>();

    // Start in the bottom right cell and recurse backwards
    Integer endRow = grid.size() - 1;
    Integer endColumn = grid.get(0).size() - 1;

    Cell cell = getCellLowestCost(grid, endRow, endColumn);
    result.add(cell);

    while (cell.getRow() + cell.getColumn() != 0) {
      cell = getCellLowestCost(grid, cell.getRow(), cell.getColumn());
      result.add(cell);
    }

    Collections.reverse(result);

    return result;

  }


  /**
   * Given a cell position (row, column), returns the cheapest cell above or to the left.  If the cell is in the first
   * row, the cell to the left is always returned.  If you are in the first column, the cell above is always returned.
   *
   * @param grid   of costs
   * @param row    of the cell
   * @param column of the cell
   * @return Cell adjacent cell with the cheapest path
   */
  protected static Cell getCellLowestCost(List<List<Integer>> grid, Integer row, Integer column) {

    Cell cell = new Cell(row, column);

    // The start cell returns itself
    if (row == 0 && column == 0) {
      return cell;
    }

    // Get the current cell cost
    Integer cellCost = grid.get(row).get(column);

    // Cells on the first row, always returns the cell to the left
    if (row == 0 && column > 0) {
      cell.setRow(row);
      cell.setColumn(column - 1);
      return cell;
    }

    // Cells on the first column, always returns the cell above
    if (column == 0 && row > 0) {
      cell.setRow(row - 1);
      cell.setColumn(column);
      return cell;
    }

    Integer fromAboveCost = grid.get(row - 1).get(column);
    Integer fromLeftCost = grid.get(row).get(column - 1);

    // Determine which cell is the cheapest from the one above or the one to the left
    if (fromLeftCost > fromAboveCost) {
      cell.setRow(row - 1);
      cell.setColumn(column);
    } else {
      cell.setRow(row);
      cell.setColumn(column - 1);
    }

    return cell;

  }

  /**
   * Construct the command line options
   *
   * @return Options from the command line
   */
  public static Options constructOptions() {

    final Options options = new Options();
    options.addOption("r", "rows", true, "Number or rows in the grid");
    options.addOption("c", "columns", true, "Number or colums in the grid");
    options.addOption("m", "max-value", true, "Max value of each element between 1 to 99");
    options.addOption("h", "help", false, "Print help");
    return options;
  }

  public static void printHelp(final Options options, final OutputStream outputStream) {

    final String commandLineSyntax = "java -cp build/libs/cheapest-path-all.jar";
    final int printedRowWidth = 100;
    final String header = StringUtils.repeat('-', printedRowWidth);
    final int spacesBeforeOption = 1;
    final int spacesBeforeOptionDescription = 1;
    final String footer = StringUtils.repeat('-', printedRowWidth);
    final boolean displayUsage = true;

    final PrintWriter writer = new PrintWriter(outputStream);
    final HelpFormatter helpFormatter = new HelpFormatter();
    helpFormatter.printHelp(
        writer,
        printedRowWidth,
        commandLineSyntax,
        header,
        options,
        spacesBeforeOption,
        spacesBeforeOptionDescription,
        footer,
        displayUsage);
    writer.close();
  }
}
