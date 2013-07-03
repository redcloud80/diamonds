package it.diamonds.tests.engine.video;


import static it.diamonds.tests.helper.ComponentHelperForTest.createSprite;
import it.diamonds.engine.video.LayerManager;
import it.diamonds.engine.video.Sprite;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class TestLayerManager extends TestCase
{

    private MockEngine engine;

    private LayerManager layerManager;


    public void setUp()
    {
        engine = MockEngine.create(800, 600);
        layerManager = new LayerManager();
    }


    public void testEmptyAfterCreation()
    {
        assertEquals("layersCount must be 0 after creation", 0, layerManager.getLayersCount());
    }


    public void testSimpleLayer()
    {
        Sprite sprite = createSprite(engine);
        layerManager.addSimpleLayer(sprite);
        layerManager.drawLayers(engine);
        assertEquals(1, engine.getNumberOfQuadsDrawn());
    }
}
