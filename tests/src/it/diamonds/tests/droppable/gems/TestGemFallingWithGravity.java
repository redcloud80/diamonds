package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import static it.diamonds.droppable.DroppableColor.RUBY;
import static it.diamonds.droppable.DroppableColor.TOPAZ;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


// TODO: REFACOR THIS (some test are unclear)
public class TestGemFallingWithGravity extends GridTestCase
{
    public void testGemFallingWithGravity()
    {
        Droppable gem = createGem(EMERALD);

        insertBigGem(DIAMOND, 3, 2, 13, 2);
        insertAndUpdate(createChest(DIAMOND), 13, 3);
        insertAndUpdate(gem, 2, 2);

        float gemY = gem.getPositionInGridLocalSpace().getY();

        dropGemsPair();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        float newGemY = gem.getPositionInGridLocalSpace().getY();
        assertEquals("Gem must be moved", gemY + getDeltaYStrongestGravity(), newGemY, 0.001f);
    }


    public void testBigGemFallingWithGravity()
    {
        insertBigGem(DIAMOND, 3, 3, 13, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 4);
        insert2x2BigGem(TOPAZ, 1, 3);

        grid.updateBigGems();
        Droppable bigGem = grid.getDroppableAt(Cell.create(1, 3));

        float gemY = bigGem.getPositionInGridLocalSpace().getY();
        dropGemsPair();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertEquals("Gem must be moved", gemY + getDeltaYStrongestGravity(), bigGem.getPositionInGridLocalSpace().getY(), 0.001f);
    }


    public void testBigGemFallingFromHigherWithGravity()
    {
        insert2x2BigGem(DIAMOND, 12, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 5);
        insert2x2BigGem(EMERALD, 10, 3);

        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(11, 3));

        float gemY = bigGem.getPositionInGridLocalSpace().getY();

        dropGemsPair();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertEquals("Gem must be moved", gemY + getDeltaYStrongestGravity(), bigGem.getPositionInGridLocalSpace().getY(), 0.001f);
    }


    public void testGemFallingWithGravityToGridBottom()
    {
        Droppable gem = createGem(EMERALD);

        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createChest(DIAMOND), 13, 5);
        insertAndUpdate(gem, 12, 3);

        float gemY = grid.getRowUpperBound(13);

        dropGemsPair();

        updateControllerTimes(getUpdateTimesFromTo(12, 13, getDeltaYStrongestGravity()));

        assertEquals("Gem must be on the bottom", gemY, gem.getPositionInGridLocalSpace().getY(), 0.001f);
    }


    public void testGemFallingWithGravityFromHigherToGridBottom()
    {
        Droppable gem1 = createGem(EMERALD);

        insert2x2BigGem(DIAMOND, 12, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 5);
        insertAndUpdate(gem1, 11, 3);
        grid.updateBigGems();

        float gemY = grid.getRowUpperBound(13);

        dropGemsPair();

        grid.removeDroppable(grid.getDroppableAt(Cell.create(0, 4)));
        grid.removeDroppable(grid.getDroppableAt(Cell.create(1, 4)));
        controller.getGemsPair().setPivot(gem1);

        updateControllerTimes(getUpdateTimesFromTo(11, 13, getDeltaYStrongestGravity()));

        assertEquals("Gem must be on the bottom", gemY, gem1.getPositionInGridLocalSpace().getY(), 0.001f);
    }


    protected void updateControllerTimes(int times)
    {
        for (int i = 0; i < times; ++i)
        {
            controller.update(environment.getTimer().getTime(), null, grid);
        }
    }


    private int getUpdateTimesFromTo(int from, int to, float deltaY)
    {
        float fromY = grid.getRowUpperBound(from);
        float toY = grid.getRowUpperBound(to);
        return (int)Math.ceil((toY - fromY) / deltaY);
    }


    public void testBigGemFallingWithGravityToGridBottom()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createChest(DIAMOND), 13, 5);
        insert2x2BigGem(EMERALD, 11, 3);

        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(11, 3));

        float gemY = grid.getRowUpperBound(12);

        dropGemsPair();

        updateControllerTimes(getUpdateTimesFromTo(12, 13, getDeltaYStrongestGravity()));

        assertEquals("Gem must be moved", gemY, bigGem.getPositionInGridLocalSpace().getY(), 0.001f);
    }


    public void testGemDroppingWithAGravityMoreThanOneCellPerIteration()
    {
        grid.setGravity(Cell.SIZE_IN_PIXELS + 1);
        testGemFallingWithGravity();
    }


    public void testBigGemFallingWithStrongGravityToGridBottom()
    {
        controller.getGridRenderer().getGrid().setStrongerGravity();
        testBigGemFallingWithGravityToGridBottom();
    }


    public void testBigGemNotCreateWhileFall()
    {
        insertAndUpdate(createGem(RUBY), 13, 2);

        insertAndUpdate(createGem(EMERALD), 13, 4);
        insertAndUpdate(createGem(EMERALD), 13, 3);
        insertAndUpdate(createGem(EMERALD), 12, 4);
        insertAndUpdate(createGem(DIAMOND), 12, 3);
        insertAndUpdate(createChest(DIAMOND), 12, 2);
        insertAndUpdate(createGem(EMERALD), 11, 3);

        dropGemsPair();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        Droppable droppable = grid.getDroppableAt(Cell.create(13, 3));

        assertNotSame("No big gem created", droppable.getType(), DroppableType.BIG_GEM);
    }


    public void testDroppedGemCanMoveDown()
    {
        insertAndUpdate(createGem(RUBY), 13, 2);
        grid.setStrongestGravity();

        Droppable gem = createGem(EMERALD);
        insertAndUpdate(gem, 11, 2);

        gem.getPositionInGridLocalSpace().setY(grid.getRowUpperBound(12) - 2 * grid.getActualGravity());

        grid.updateDroppable(gem);

        gem.drop();

        assertTrue(droppedGemCanMoveDown(grid));
    }
}
