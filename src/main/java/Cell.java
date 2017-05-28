/**
 * Cell object containing a row and column position.
 */
public class Cell {

  private Integer row;
  private Integer column;

  public Cell() {
  }

  public Cell(Integer row, Integer column) {
    this.row = row;
    this.column = column;
  }

  public Integer getRow() {
    return row;
  }

  public void setRow(Integer row) {
    this.row = row;
  }

  public Integer getColumn() {
    return column;
  }

  public void setColumn(Integer column) {
    this.column = column;
  }
}
