package it.diamonds.grid;


public final class Cell
{
    public static final int SIZE_IN_PIXELS = 32;

    private int row;

    private int column;


    private Cell(int row, int column)
    {
        this.row = row;
        this.column = column;
    }


    public static Cell create(int row, int column)
    {
        if (row < 0 || column < 0)
        {
            return null;
        }

        return new Cell(row, column);
    }


    public int getRow()
    {
        return row;
    }


    public int getColumn()
    {
        return column;
    }
}
