package it.diamonds.droppable.pair;


import static it.diamonds.droppable.pair.Position.BOTTOM;
import static it.diamonds.droppable.pair.Position.LEFT;
import static it.diamonds.droppable.pair.Position.RIGHT;
import static it.diamonds.droppable.pair.Position.TOP;
import static it.diamonds.droppable.pair.Position.UNDEFINED;
import it.diamonds.droppable.Droppable;
import it.diamonds.engine.Config;
import it.diamonds.engine.modifiers.Pulsation;
import it.diamonds.engine.video.Sprite;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.Region;


public final class DroppablePair
{
    private Droppable pivot;

    private Droppable slave;

    private Grid grid;

    private Position slavePosition;

    private Pulsation pulsation;


    public DroppablePair(Grid grid, Config config)
    {
        this.grid = grid;
        slavePosition = UNDEFINED;
        pulsation = new Pulsation(config.getInteger("PulsationLength"), config.getInteger("SizeMultiplier"));
    }


    public Droppable getPivot()
    {
        return pivot;
    }


    public void setPivot(Droppable droppable)
    {
        pivot = droppable;

        updatePulsingState();
    }


    public void setNoPivot()
    {
        pivot = null;
    }


    public Droppable getSlave()
    {
        return slave;
    }


    public void setSlave(Droppable droppable)
    {
        slave = droppable;
        computeSlavePosition();
    }


    private void computeSlavePosition()
    {
        slavePosition = UNDEFINED;

        Region slaveCell = slave.getRegion();
        Region pivotCell = pivot.getRegion();

        if (slaveCell.getLeftColumn() == pivotCell.getLeftColumn())
        {
            if (slaveCell.getTopRow() == (pivotCell.getTopRow() + 1))
            {
                slavePosition = BOTTOM;
            }
            if (slaveCell.getTopRow() == (pivotCell.getTopRow() - 1))
            {
                slavePosition = TOP;
            }
        }

        if (slaveCell.getLeftColumn() == (pivotCell.getLeftColumn() - 1))
        {
            slavePosition = LEFT;
        }

        if (slaveCell.getLeftColumn() == (pivotCell.getLeftColumn() + 1))
        {
            slavePosition = RIGHT;
        }
    }


    public void setNoSlave()
    {
        slave = null;
        slavePosition = UNDEFINED;
        updatePulsingState();
    }


    private boolean droppablesAreHorizontal()
    {
        return slavePosition == Position.LEFT
            || slavePosition == Position.RIGHT;
    }


    public void move(Direction direction)
    {
        if (slave == null)
        {
            grid.moveDroppable(pivot, direction);
            return;
        }

        if (droppablesAreHorizontal())
        {
            moveWhenHorizontal(direction);
        }
        else
        {
            moveWhenVertical(direction);
        }
    }


    private void moveWhenVertical(Direction direction)
    {
        if (grid.droppableCanMove(pivot, direction)
            && grid.droppableCanMove(slave, direction))
        {
            grid.moveDroppable(pivot, direction);
            grid.moveDroppable(slave, direction);
        }
    }


    private void moveWhenHorizontal(Direction direction)
    {
        if (grid.droppableCanMove(slave, direction))
        {
            grid.moveDroppable(slave, direction);
            grid.moveDroppable(pivot, direction);
        }
        else if (grid.droppableCanMove(pivot, direction))
        {
            grid.moveDroppable(pivot, direction);
            grid.moveDroppable(slave, direction);
        }
    }


    private void rotateSlave(SlaveRotation rotation)
    {
        if (pivot == null || slave == null)
        {
            return;
        }

        Movement movement = rotation.getMovement(slavePosition);

        Direction newDirection = movement.direction();

        Position position = movement.newPosition();

        if (grid.droppableCanMove(pivot, newDirection))
        {
            Region pivotRegion = pivot.getRegion();

            int row = pivotRegion.getTopRow() + newDirection.getRowDelta();
            int column = pivotRegion.getLeftColumn()
                + newDirection.getColumnDelta();

            grid.moveDroppableToCell(slave, Cell.create(row, column));

            slavePosition = position;
        }
    }


    public void mirrorSlave()
    {
        rotateSlave(SlaveRotation.MIRROR);
    }


    public void rotateClockwise()
    {
        rotateSlave(SlaveRotation.CLOCKWISE);
    }


    public void rotateCounterclockwise()
    {
        rotateSlave(SlaveRotation.COUNTERCLOCKWISE);
    }


    public void update(long timer)
    {
        if (slavePosition == BOTTOM)
        {
            grid.updateDroppable(slave);
            grid.updateDroppable(pivot);
        }
        else
        {
            grid.updateDroppable(pivot);
            grid.updateDroppable(slave);
        }
        updatePulsingState();
    }


    public boolean canReactToInput()
    {
        if (slave == null)
        {
            return pivot != null && pivot.isFalling();
        }
        return isFalling();
    }


    private boolean isFalling()
    {
        return slave.isFalling() && pivot.isFalling();
    }


    public boolean isPulsing()
    {
        return pivot != null && slave != null && isFalling();
    }


    private void updatePulsingState()
    {
        if (pivot == null)
        {
            return;
        }

        Sprite pivotSprite = pivot.getAnimatedSprite().getSprite();
        if (isPulsing())
        {
            if (!pivotSprite.isPulsing())
            {
                pivotSprite.startPulsation(pulsation);
            }
        }
        else
        {
            if (pivotSprite.isPulsing())
            {
                pivotSprite.stopPulsation();
            }
        }
    }


    public boolean oneDroppableIsNotFalling()
    {
        if (slave == null || pivot == null)
        {
            return false;
        }

        return !pivot.isFalling() ^ !slave.isFalling();
    }


    public boolean bothDroppablesAreNotFalling()
    {
        if (slave == null && pivot != null)
        {
            return !pivot.isFalling();
        }

        return !pivot.isFalling() && !slave.isFalling();
    }


    public void drop()
    {
        getPivot().drop();
        getSlave().drop();
    }
}
