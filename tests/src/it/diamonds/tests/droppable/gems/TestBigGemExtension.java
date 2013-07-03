package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import it.diamonds.droppable.Droppable;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestBigGemExtension extends GridTestCase
{

    private Droppable bigGem;


    public void setUp()
    {
        super.setUp();

        insert2x2BigGem(EMERALD, 12, 3);
        grid.updateBigGems();

        bigGem = grid.getDroppableAt(Cell.create(12, 3));
    }


    private void extendBigGem(Cell positionOfFirstGem, Cell positionOfSecondGem)
    {
        insertAndUpdate(createGem(EMERALD), positionOfFirstGem.getRow(), positionOfFirstGem.getColumn());

        insertAndUpdate(createGem(EMERALD), positionOfSecondGem.getRow(), positionOfSecondGem.getColumn());

        grid.updateBigGems();
    }


    public void testBigGemExtendsRegionUp()
    {
        extendBigGem(Cell.create(11, 3), Cell.create(11, 4));

        assertTrue("BigGem must be extended on the top side. Gem at 11,3 wasn't added", bigGem.getRegion().containsCell(Cell.create(11, 3)));
        assertTrue("BigGem must be extended on the top side. Gem at 11,4 wasn't added", bigGem.getRegion().containsCell(Cell.create(11, 4)));
    }


    public void testBigGemExtendsRegionDown()
    {
        insert2x2BigGem(EMERALD, 11, 6);
        grid.updateBigGems();

        extendBigGem(Cell.create(13, 6), Cell.create(13, 7));

        assertTrue("BigGem must be extended on the bottom side. Gem at 13,3 wasn't added", bigGem.getRegion().containsCell(Cell.create(13, 3)));
        assertTrue("BigGem must be extended on the bottom side. Gem at 13,4 wasn't added", bigGem.getRegion().containsCell(Cell.create(13, 4)));
    }


    public void testBigGemExtendsRegionRight()
    {
        extendBigGem(Cell.create(13, 5), Cell.create(12, 5));

        assertTrue("BigGem must be extended on the right side. Gem at 13,5 wasn't added", bigGem.getRegion().containsCell(Cell.create(13, 5)));
        assertTrue("BigGem must be extended on the right side. Gem at 12,5 wasn't added", bigGem.getRegion().containsCell(Cell.create(12, 5)));
    }


    public void testBigGemExtendsRegionOnLeft()
    {
        extendBigGem(Cell.create(13, 2), Cell.create(12, 2));

        assertTrue("BigGem must be extended on the left side. Gem at 13,2 wasn't added", bigGem.getRegion().containsCell(Cell.create(13, 2)));
        assertTrue("BigGem must be extended on the left side. Gem at 12,2 wasn't added", bigGem.getRegion().containsCell(Cell.create(12, 2)));
    }


    public void testBigGemDoesntExtendsRegionUp()
    {
        insertAndUpdate(createGem(EMERALD), 11, 3);
        insertAndUpdate(createGem(DIAMOND), 11, 4);

        grid.updateBigGems();

        assertFalse("BigGem must be not extended on the top side. Gem at 11,3 was added", bigGem.getRegion().containsCell(Cell.create(11, 3)));

        assertFalse("BigGem must be not extended on the top side. Gem at 11,4 was added", bigGem.getRegion().containsCell(Cell.create(11, 4)));
    }


    public void testBigGemDoesntExtendsRegionRight()
    {
        insertAndUpdate(createGem(EMERALD), 13, 5);
        insertAndUpdate(createGem(DIAMOND), 12, 5);

        grid.updateBigGems();

        assertFalse("BigGem must be not extended on the right side. Gem at 13,5 was added", bigGem.getRegion().containsCell(Cell.create(13, 5)));
        assertFalse("BigGem must be not extended on the right side. Gem at 12,5 was added", bigGem.getRegion().containsCell(Cell.create(12, 5)));
    }


    public void testBigGemDoesntExtendsRegionOnLeft()
    {
        insertAndUpdate(createGem(EMERALD), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 2);

        grid.updateBigGems();

        assertFalse("BigGem must be not extended on the left side. Gem at 13,2 was added", bigGem.getRegion().containsCell(Cell.create(13, 2)));
        assertFalse("BigGem must be not extended on the left side. Gem at 12,2 was added", bigGem.getRegion().containsCell(Cell.create(12, 2)));
    }


    public void testBigGemExistsAfterUpExtension()
    {
        extendBigGem(Cell.create(11, 3), Cell.create(11, 4));

        assertNotNull("BigGem must exist after up extension", grid.getDroppableAt(Cell.create(12, 3)));
    }


    public void testBigGemExistsAfterDownExtension()
    {
        insert2x2BigGem(EMERALD, 11, 6);
        grid.updateBigGems();

        extendBigGem(Cell.create(13, 6), Cell.create(13, 7));

        assertNotNull("BigGem must exist after down extension", grid.getDroppableAt(Cell.create(12, 3)));
    }


    public void testBigGemExistsAfterRightExtension()
    {
        extendBigGem(Cell.create(13, 5), Cell.create(12, 5));

        assertNotNull("BigGem must exist after right extension", grid.getDroppableAt(Cell.create(12, 3)));
    }


    public void testBigGemExistsAfterLeftExtension()
    {
        extendBigGem(Cell.create(13, 2), Cell.create(12, 2));

        assertNotNull("BigGem must exist after extension", grid.getDroppableAt(Cell.create(12, 3)));
    }
}
