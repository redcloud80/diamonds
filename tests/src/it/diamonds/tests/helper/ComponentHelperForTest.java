package it.diamonds.tests.helper;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.engine.Engine;
import it.diamonds.engine.Environment;
import it.diamonds.engine.Point;
import it.diamonds.engine.input.EventMappings;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.video.Sprite;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.Region;
import it.diamonds.grid.iteration.CanMoveDownQuery;
import it.diamonds.tests.mocks.MockEnvironment;
import junit.framework.Assert;


public final class ComponentHelperForTest
{
    public static final int SHORT_SLEEP = 50;

    public static final int MEDIUM_SLEEP = 100;


    private ComponentHelperForTest()
    {

    }


    public static int getPivotStartingRow()
    {
        return MockEnvironment.create().getConfig().getInteger("PivotRow");
    }


    public static int getPivotStartingColumn()
    {
        return MockEnvironment.create().getConfig().getInteger("PivotColumn");
    }


    public static int getSlaveStartingRow()
    {
        return MockEnvironment.create().getConfig().getInteger("SlaveRow");
    }


    public static int getSlaveStartingColumn()
    {
        return MockEnvironment.create().getConfig().getInteger("SlaveColumn");
    }


    public static Sprite createSprite(Engine engine)
    {
        return new Sprite(new Point(100, 200), engine.createImage("diamond"));
    }


    public static EventMappings createEventMappings()
    {
        EventMappings mappings = new EventMappings();

        mappings.addMapping(Code.ESCAPE, Code.ESCAPE);
        mappings.addMapping(Code.ENTER, Code.ENTER);
        mappings.addMapping(Code.UP, Code.UP);
        mappings.addMapping(Code.DOWN, Code.DOWN);
        mappings.addMapping(Code.LEFT, Code.LEFT);
        mappings.addMapping(Code.RIGHT, Code.RIGHT);
        mappings.addMapping(Code.BUTTON1, Code.BUTTON1);
        mappings.addMapping(Code.BUTTON2, Code.BUTTON2);
        mappings.addMapping(Code.BUTTON3, Code.BUTTON3);
        mappings.addMapping(Code.BLOW, Code.BLOW);

        return mappings;
    }


    public static Region cloneRegion(Region region)
    {
        return new Region(region.getLeftColumn(), region.getTopRow(), region.getWidth(), region.getHeight());
    }


    public static Region cloneRegion(Region region, int leftDelta, int topDelta)
    {
        return new Region(region.getLeftColumn() + leftDelta, region.getTopRow()
            + topDelta, region.getWidth(), region.getHeight());
    }


    public static void insertAndUpdate(Grid grid, Droppable gem, int row, int column)
    {
        Cell cell = Cell.create(row, column);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        grid.updateDroppable(gem);
    }


    public static void insertBigGem(Environment environment, Grid grid, DroppableColor color, int startRow, int startColumn, int endRow, int endColumn)
    {
        for (int row = endRow; row >= startRow; --row)
        {
            for (int column = startColumn; column <= endColumn; ++column)
            {
                Gem gem = new Gem(environment.getEngine(), color, 3500, 100);
                gem.drop();
                insertAndUpdate(grid, gem, row, column);
            }
        }
    }


    public static void makeAllGemsFall(Grid grid)
    {
        while (droppedGemCanMoveDown(grid))
        {
            grid.updateFalls();
        }
    }


    public static boolean droppedGemCanMoveDown(Grid grid)
    {
        CanMoveDownQuery action = new CanMoveDownQuery(grid);
        grid.runIteration(action);
        return action.getResult();
    }


    public static boolean isSingleDroppableEquals(Droppable asd1, Droppable asd2)
    {
        return asd1.getColor() == asd2.getColor()
            && asd1.getType().getClass().equals(asd2.getType().getClass());
    }


    public static Thread runAndSleep(Runnable task, long sleepTime)
    {
        Thread thread = new Thread(task);
        thread.start();

        try
        {
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException e)
        {
            ;
        }

        return thread;
    }


    public static void joinAndFailIfStillAlive(Thread thread, long joinTimeout)
    {
        joinAndFail(thread, joinTimeout, true);
    }


    public static void joinAndFailIfNotAlive(Thread thread, long joinTimeout)
    {
        joinAndFail(thread, joinTimeout, false);
    }


    @SuppressWarnings("deprecation")
    private static void joinAndFail(Thread thread, long joinTimeout, boolean failIfAlive)
    {
        try
        {
            thread.join(joinTimeout);
        }
        catch (InterruptedException e)
        {
            ;
        }

        if (failIfAlive)
        {
            if (thread.isAlive())
            {
                Assert.fail("The thread must no be alive");
                thread.stop();
            }
        }
        else
        {
            if (!thread.isAlive())
            {
                Assert.fail("The thread must be alive");
            }
        }

    }

}
