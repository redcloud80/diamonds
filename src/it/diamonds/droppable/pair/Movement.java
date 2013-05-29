package it.diamonds.droppable.pair;


public class Movement
{
    private Direction direction;

    private Position newPosition;


    public Movement(Direction direction, Position newPosition)
    {
        this.direction = direction;
        this.newPosition = newPosition;
    }


    public Direction getDirection()
    {
        return direction;
    }


    public Position getNewPosition()
    {
        return newPosition;
    }
}
