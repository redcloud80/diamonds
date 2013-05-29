package it.diamonds.engine.video;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_NORMALIZE;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import it.diamonds.engine.AbstractEngine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;


public final class LWJGLEngine extends AbstractEngine
{
    public LWJGLEngine(int width, int height, String windowTitle, boolean isFullscreen)
    {
        initialiseDisplay(width, height, isFullscreen);
        initialiseOpenGLStates();

        org.lwjgl.opengl.Display.setTitle(windowTitle);
    }


    public int getDisplayWidth()
    {
        return org.lwjgl.opengl.Display.getDisplayMode().getWidth();
    }


    public int getDisplayHeight()
    {
        return org.lwjgl.opengl.Display.getDisplayMode().getHeight();
    }


    public void shutDown()
    {
        cleanupImages();

        org.lwjgl.opengl.Display.destroy();
    }


    public void updateDisplay()
    {
        org.lwjgl.opengl.Display.update();
    }


    public Image createImage(String name, String otherType)
    {
        return LWJGLImage.create(name, otherType);
    }


    public void drawImage(Point position, float width, float height, Image image, Rectangle imageRect)
    {
        if (image == null)
        {
            return;
        }

        image.enable();

        float realPosx = position.getX();
        float realPosy = position.getY();

        glPushMatrix();

        glTranslatef(realPosx, realPosy, 0f);

        glBegin(GL_TRIANGLES);

        float u0 = (float)imageRect.getLeft() / image.getWidth();
        float v0 = (float)imageRect.getTop() / image.getHeight();
        float u1 = (float)(imageRect.getLeft() + imageRect.getWidth())
            / image.getWidth();
        float v1 = (float)(imageRect.getTop() + imageRect.getHeight())
            / image.getHeight();

        // Front
        glTexCoord2f(u1, v0);
        glVertex3f(width, 0, 1);
        glTexCoord2f(u1, v1);
        glVertex3f(width, height, 1);
        glTexCoord2f(u0, v1);
        glVertex3f(0, height, 1);

        glTexCoord2f(u1, v0);
        glVertex3f(width, 0, 1);
        glTexCoord2f(u0, v1);
        glVertex3f(0, height, 1);
        glTexCoord2f(u0, v0);
        glVertex3f(0, 0, 1);

        glEnd();

        glPopMatrix();
    }


    public void clearDisplay()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();
    }


    public boolean isWindowClosed()
    {
        return org.lwjgl.opengl.Display.isCloseRequested();
    }


    private static boolean displayModeMatches(DisplayMode mode, int width, int height)
    {
        return mode.getWidth() == width && mode.getHeight() == height;
    }


    private static boolean displayModeIsBetter(DisplayMode mode, DisplayMode bestMode)
    {
        return bestMode.getBitsPerPixel() < mode.getBitsPerPixel()
            || bestMode.getFrequency() < mode.getFrequency();
    }


    private static DisplayMode findDisplayMode(int width, int height)
    {
        DisplayMode bestMode = new DisplayMode(0, 0);

        try
        {
            DisplayMode[] availableModes = org.lwjgl.opengl.Display.getAvailableDisplayModes();

            // System.out.println("List of available display modes:");

            for (DisplayMode currentMode : availableModes)
            {
                // System.out.println(currentMode.toString());

                if (!displayModeMatches(currentMode, width, height))
                {
                    continue;
                }

                if (bestMode.getWidth() == 0)
                {
                    bestMode = currentMode;
                }
                else if (displayModeIsBetter(currentMode, bestMode))
                {
                    bestMode = currentMode;
                }
            }
        }
        catch (LWJGLException e)
        {
            throw new RuntimeException(e.toString());
        }

        // System.out.println("Best mode: " + bestMode.toString());

        return bestMode;
    }


    private void initialiseDisplay(int width, int height, boolean isFullscreen)
    {
        try
        {
            System.out.println("Display Adapter: "
                + org.lwjgl.opengl.Display.getAdapter());

            DisplayMode currentMode = findDisplayMode(width, height);

            if (currentMode != null)
            {
                org.lwjgl.opengl.Display.setFullscreen(isFullscreen);
                org.lwjgl.opengl.Display.setDisplayMode(currentMode);

                if (currentMode.getBitsPerPixel() == 16)
                {
                    org.lwjgl.opengl.Display.create(new PixelFormat(16, 0, 0, 0, 0));
                }

                else
                {
                    org.lwjgl.opengl.Display.create(new PixelFormat(24, 0, 0, 0, 0));
                }
            }
        }
        catch (Exception exception)
        {
            throw new RuntimeException("The display initialisation failed: "
                + exception);
        }
    }


    private void initialiseOpenGLStates()
    {
        glDisable(GL_NORMALIZE);
        glDisable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);

        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, getDisplayWidth(), 0, getDisplayHeight(), -1f, 1f);
        glTranslatef(0f, getDisplayHeight(), 0f);
        glScalef(1f, -1f, 1f);
        glMatrixMode(GL_MODELVIEW);
    }
}
