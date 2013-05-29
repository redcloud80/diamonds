package it.diamonds.tests.engine.audio;


import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.engine.audio.Sound;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestGemCollisionSound extends GridTestCase
{
    private Sound sound;

    private AbstractSingleDroppable gem;


    public void setUp()
    {
        super.setUp();

        gem = createGem(DroppableColor.DIAMOND);
        sound = environment.getAudio().createSound("diamond");
    }


    public void testNullDropSound()
    {
        try
        {
            AbstractSingleDroppable.setDropSound(null);
        }
        catch (Exception e)
        {
            return;
        }
        fail("Exception not thrown");
    }


    public void testSoundBeforeDrop()
    {
        AbstractSingleDroppable.setDropSound(sound);

        assertFalse(sound.wasPlayed());
    }


    public void testDropSoundPlayedWhenGemHitsBottom()
    {
        AbstractSingleDroppable.setDropSound(sound);

        grid.setNormalGravity();
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        grid.updateDroppable(gem);

        assertTrue(sound.wasPlayed());
    }


    public void testDropSoundNotPlayedWhenGemNotAtBottom()
    {
        AbstractSingleDroppable.setDropSound(sound);
        grid.setNormalGravity();
        Cell cell = Cell.create(5, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        grid.updateDroppable(gem);
        assertFalse(sound.wasPlayed());
    }


    public void testDropSoundPlayed()
    {
        AbstractSingleDroppable.setDropSound(sound);

        gem.drop();

        assertTrue(sound.wasPlayed());
    }

}
