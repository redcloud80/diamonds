package it.diamonds.engine.video;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.modifiers.DrawModifier;
import it.diamonds.engine.modifiers.Pulsation;
import it.diamonds.engine.modifiers.SinglePulsation;


public class Sprite implements Drawable
{
    private Image texture = null;

    private Point position = new Point(0.0f, 0.0f);

    private Rectangle textureArea = null;

    private boolean hidden = false;

    private DrawModifier drawModifier;


    public Sprite(Point position, Rectangle textureArea, Image texture)
    {
        if (texture == null)
        {
            throw new NullPointerException("texture can not be null");
        }

        this.texture = texture;
        this.textureArea = textureArea;

        this.position = position;
    }


    public Sprite(Point position, Image texture)
    {
        this(position, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), texture);
    }


    public void setPosition(Point position)
    {
        this.position = position;
    }


    public void setPosition(float posX, float posY)
    {
        position.setX(posX);
        position.setY(posY);
    }


    public void draw(Engine engine)
    {
        if (hidden)
        {
            return;
        }

        if (drawModifier != null)
        {
            drawModifier.updateModifierState();
            drawModifier.draw(this, engine);
        }
        else
        {
            engine.drawImage(position, getTextureArea().getWidth(), getTextureArea().getHeight(), texture, textureArea);
        }
    }


    public Image getTexture()
    {
        return texture;
    }


    public Rectangle getTextureArea()
    {
        return textureArea;
    }


    public Point getPosition()
    {
        return position;
    }


    public void translate(float dx, float dy)
    {
        position.setX(position.getX() + dx);
        position.setY(position.getY() + dy);
    }


    public void setOrigin(int left, int top)
    {
        textureArea.translateTo(left, top);
    }


    public void hide()
    {
        hidden = true;
    }


    public void show()
    {
        hidden = false;
    }


    public boolean isHidden()
    {
        return hidden;
    }


    public void setTexture(Image texture)
    {
        this.texture = texture;
    }


    public void startPulsation(Pulsation pulsation)
    {
        drawModifier = pulsation;
    }


    public void stopPulsation()
    {
        drawModifier = null;
    }


    public boolean isPulsing()
    {
        return drawModifier != null;
    }


    protected DrawModifier getPulsation()
    {
        return drawModifier;
    }


    protected void moveRight(int speed)
    {
        setPosition(getPosition().getX() + speed, getPosition().getY());
    }


    protected void startSinglePulsation(int pulsationLength, float sizeMultiplier)
    {
        show();
        startPulsation(new SinglePulsation(pulsationLength, sizeMultiplier));
    }


    protected void updatePulsation()
    {
        if (!isPulsing())
        {
            return;
        }

        if (getPulsation().isCompleted())
        {
            stopPulsation();
        }
    }


    public static Sprite createSquareSprite(int sizeInPixels, Image image)
    {
        return new Sprite(new Point(0, 0), new Rectangle(0, 0, sizeInPixels, sizeInPixels), image);
    }
}
