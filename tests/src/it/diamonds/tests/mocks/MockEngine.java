package it.diamonds.tests.mocks;


import it.diamonds.engine.AbstractEngine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.Image;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public final class MockEngine extends AbstractEngine
{
    private int width;

    private int height;

    private boolean shutDown = false;

    private boolean isUpdated = false;

    public class DrawInfo
    {
        private Rectangle imageRect;

        private Point quadPosition;

        private float quadWidth;

        private float quadHeight;

        private Image image;


        public Rectangle getImageRect()
        {
            return imageRect;
        }


        public Point getQuadPosition()
        {
            return quadPosition;
        }


        public float getQuadWidth()
        {
            return quadWidth;
        }


        public float getQuadHeight()
        {
            return quadHeight;
        }


        public Image getImage()
        {
            return image;
        }
    }

    private Map<String, DrawInfo> drawInfoMap = new LinkedHashMap<String, DrawInfo>();

    private LinkedList<DrawInfo> drawInfoList = new LinkedList<DrawInfo>();

    private int numberOfQuadsDrawn = 0;

    private Set<String> imageCreated;


    public MockEngine(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.imageCreated = new HashSet<String>();
    }


    public static MockEngine create(int width, int height)
    {
        return new MockEngine(width, height);
    }


    public int getNumberOfQuadsDrawn()
    {
        return numberOfQuadsDrawn;
    }


    private DrawInfo getDrawInfo(int i)
    {
        return drawInfoList.get(drawInfoList.size() - i - 1);
    }


    public DrawInfo getDrawInfoByImageName(String name)
    {
        return drawInfoMap.get(name);
    }


    public int getDisplayWidth()
    {
        return width;
    }


    public int getDisplayHeight()
    {
        return height;
    }


    public Rectangle getImageRect()
    {
        return getImageRect(0);
    }


    public Rectangle getImageRect(int i)
    {
        return getDrawInfo(i).imageRect;
    }


    public Point getQuadPosition()
    {
        return getQuadPosition(0);
    }


    public Point getQuadPosition(int i)
    {
        return getDrawInfo(i).quadPosition;
    }


    public float getQuadWidth()
    {
        return getQuadWidth(0);
    }


    public float getQuadWidth(int i)
    {
        return getDrawInfo(i).quadWidth;
    }


    public float getQuadHeight()
    {
        return getQuadHeight(0);
    }


    public float getQuadHeight(int i)
    {
        return getDrawInfo(i).quadHeight;
    }


    public void shutDown()
    {
        cleanupImages();
        shutDown = true;
    }


    public boolean isWindowClosed()
    {
        return shutDown;
    }


    public void updateDisplay()
    {
        isUpdated = true;
    }


    public void clearUpdateState()
    {
        isUpdated = false;
    }


    public boolean isDisplayUpdated()
    {
        return isUpdated;
    }


    public void clearDisplay()
    {
        drawInfoMap.clear();
        drawInfoList.clear();
        numberOfQuadsDrawn = 0;
    }


    public Image getImage()
    {
        return getImage(0);
    }


    public Image getImage(int i)
    {
        return getDrawInfo(i).image;
    }


    public int getImageDrawOrder(String textureName)
    {
        int drawOrder = 0;

        for (DrawInfo drawInfo : drawInfoList)
        {
            drawOrder++;

            if (drawInfo.image.getName() == textureName)
            {
                return drawOrder;
            }
        }

        return 0;
    }


    protected Image createImage(String name, String otherType)
    {
        imageCreated.add(name);
        return MockImage.create(name, otherType);
    }


    @Override
    protected void cleanupImages()
    {
        super.cleanupImages();
        imageCreated.clear();
    }


    public boolean isImageCreated(String name)
    {
        return imageCreated.contains(name);
    }


    public boolean wasImageDrawn(Image image)
    {
        return getImageDrawOrder(image.getName()) > 0;
    }


    public void drawImage(Point position, float width, float height, Image image, Rectangle imageRect)
    {

        DrawInfo drawInfo = new DrawInfo();
        drawInfo.imageRect = new Rectangle(imageRect.getLeft(), imageRect.getTop(), imageRect.getWidth(), imageRect.getHeight());

        drawInfo.quadPosition = new Point(position.getX(), position.getY());

        drawInfo.quadWidth = width;
        drawInfo.quadHeight = height;

        drawInfo.image = image;

        drawInfoMap.put(image.getName(), drawInfo);
        drawInfoList.add(drawInfo);

        ++numberOfQuadsDrawn;
    }

}
