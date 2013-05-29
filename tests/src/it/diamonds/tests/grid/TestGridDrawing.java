package it.diamonds.tests.grid;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.engine.video.Image;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockEngine;


public class TestGridDrawing extends GridTestCase
{
    private Droppable gem;

    private MockEngine engine;


    public void setUp()
    {
        super.setUp();
        gem = createGem(DroppableColor.DIAMOND);
        engine = ((MockEngine)environment.getEngine());
    }


    public void testDrawBackground()
    {
        gridRenderer.draw(environment.getEngine());
        assertEquals(1, engine.getNumberOfQuadsDrawn());
    }


    public void testDrawBackgroundPosition()
    {
        gridRenderer.draw(environment.getEngine());
        assertEquals(gridPosition, engine.getQuadPosition());
    }


    public void testDrawBackgroundImage()
    {
        gridRenderer.draw(environment.getEngine());

        Image texture = environment.getEngine().createImage("grid-background");
        assertTrue(engine.wasImageDrawn(texture));
    }


    public void testDrawTwoGems()
    {
        Droppable anotherGem = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(3, 1);

        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        Cell cell1 = Cell.create(2, 1);
        anotherGem.getRegion().setRow(cell1.getRow());
        anotherGem.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(anotherGem);

        gridRenderer.draw(engine);

        assertEquals(3, engine.getNumberOfQuadsDrawn());
    }


    public void testDrawGemsInFirstColumn()
    {
        Cell cell = Cell.create(0, 1);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gridRenderer.draw(engine);

        assertEquals(2, engine.getNumberOfQuadsDrawn());
    }


    public void testDrawGemInFirstRow()
    {
        Cell cell = Cell.create(1, 0);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gridRenderer.draw(engine);

        assertEquals(2, engine.getNumberOfQuadsDrawn());
    }
}
