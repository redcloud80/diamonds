package it.diamonds.droppable.pair;


public enum Direction
{
    GO_UP(0, -1), GO_RIGHT(1, 0), GO_DOWN(0, 1), GO_LEFT(-1, 0);

    private final int columnDelta;

    private final int rowDelta;


    private Direction(int columnDelta, int rowDelta)
    {
        this.columnDelta = columnDelta;
        this.rowDelta = rowDelta;
    }


    public int getColumnDelta()
    {
        return columnDelta;
    }


    public int getRowDelta()
    {
        return rowDelta;
    }
}
