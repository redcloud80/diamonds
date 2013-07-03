package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import it.diamonds.droppable.Droppable;
import it.diamonds.engine.Engine;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockEngine;


public class TestBigGemDrawing extends GridTestCase
{
    private Engine engine;


    public void setUp()
    {
        super.setUp();
        engine = environment.getEngine();
    }


    public void testGridDrawsBigGem()
    {
        insertAndUpdate(createGem(EMERALD), 13, 1);
        insertAndUpdate(createGem(EMERALD), 13, 2);
        insertAndUpdate(createGem(EMERALD), 13, 3);
        insertAndUpdate(createGem(EMERALD), 12, 1);
        insertAndUpdate(createGem(EMERALD), 12, 2);
        insertAndUpdate(createGem(EMERALD), 12, 3);
        insertAndUpdate(createGem(EMERALD), 11, 1);
        insertAndUpdate(createGem(EMERALD), 11, 2);
        insertAndUpdate(createGem(EMERALD), 11, 3);

        grid.updateBigGems();

        gridRenderer.draw(engine);

        assertEquals("GemGroup wasn't drawn correctly", 10, ((MockEngine)engine).getNumberOfQuadsDrawn());
        assertEquals("GemGroup must be drawn with the correct Texture", grid.getDroppableAt(Cell.create(13, 1)).getAnimatedSprite().getSprite().getTexture().getName(), ((MockEngine)engine).getImage().getName());
    }


    public void testBigGemDrawnInCorrectPosition()
    {
        insert2x2BigGem(13, 5);

        grid.updateBigGems();

        gridRenderer.draw(engine);

        Droppable bottomRight = grid.getDroppableAt(Cell.create(13, 6));

        assertEquals("GemGroup wasn't drawn in correct position (X)", bottomRight.getAnimatedSprite().getSprite().getPosition().getX(), ((MockEngine)engine).getQuadPosition(3).getX());
        assertEquals("GemGroup wasn't drawn in correct position (Y)", bottomRight.getAnimatedSprite().getSprite().getPosition().getY(), ((MockEngine)engine).getQuadPosition(3).getY());
    }


    public void testBigGemDrawsCorrectTile()
    {
        insert2x2BigGem(13, 5);

        grid.updateBigGems();

        gridRenderer.draw(engine);

        assertEquals("bad texture drawn", Cell.SIZE_IN_PIXELS * 2, ((MockEngine)engine).getImageRect().getLeft());
        assertEquals("bad texture drawn", Cell.SIZE_IN_PIXELS * 2, ((MockEngine)engine).getImageRect().getTop());
    }


    public void testDrawAfterVerticalMerge()
    {
        insert2x2BigGem(13, 5);
        grid.updateBigGems();

        insert2x2BigGem(11, 5);
        grid.updateBigGems();

        gridRenderer.draw(engine);

        assertEquals(456, ((MockEngine)engine).getQuadPosition().getY(), 0.001f);
    }


    private void insert2x2BigGem(int row, int column)
    {
        insertAndUpdate(createGem(EMERALD), row, column);
        insertAndUpdate(createGem(EMERALD), row, column + 1);
        insertAndUpdate(createGem(EMERALD), row - 1, column);
        insertAndUpdate(createGem(EMERALD), row - 1, column + 1);
    }


    public void testBigGemBorderBug()
    {

        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createGem(DIAMOND), 13, 2);

        insert2x2BigGem(EMERALD, 11, 1);
        grid.updateBigGems();

        insertAndUpdate(createChest(DIAMOND), 13, 3);
        grid.updateCrushes(scoreCalculator, stoneCalculator);

        Droppable drop = grid.getDroppableAt(Cell.create(12, 1));
        float spritedestination = grid.getRowUpperBound(12);

        grid.setNormalGravity();

        while (drop.canMoveDown(grid))
        {
            if (drop.getAnimatedSprite().getSprite().getPosition().getY() > spritedestination)
            {
                fail();
            }
            grid.updateFalls();
        }

        assertNotNull(grid.getDroppableAt(Cell.create(12, 1)));
    }
}
