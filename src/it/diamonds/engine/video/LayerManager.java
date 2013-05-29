package it.diamonds.engine.video;


import it.diamonds.engine.Engine;


public class LayerManager
{
    private LayerList layers;

    private Layer currentLayer;


    public LayerManager()
    {
        layers = new LayerList();
        currentLayer = null;
    }


    public int getLayersCount()
    {
        return layers.size();
    }


    private void openNewLayer()
    {
        currentLayer = new Layer();
    }


    private void closeCurrentLayer()
    {
        layers.add(currentLayer);
        currentLayer = null;
    }


    private void addToLayer(Drawable item)
    {
        currentLayer.add(item);
    }


    public void addSimpleLayer(Drawable layer)
    {
        openNewLayer();
        addToLayer(layer);
        closeCurrentLayer();
    }


    public void drawLayers(Engine engine)
    {
        for (Layer layer : layers)
        {
            for (Drawable item : layer)
            {
                item.draw(engine);
            }
        }
    }
}
