package it.diamonds;


import it.diamonds.engine.Point;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public final class PlayFieldDescriptor
{
    private final Point gridOrigin;

    private final Point nextGemsPanelOrigin;

    private final Point scoreOrigin;

    private final Point gameOverOrigin;

    private final Point warningBoxOrigin;

    private final Point counterBoxOrigin;

    private final Point crushBoxOrigin;


    public PlayFieldDescriptor(Point gridOrigin, Point nextGemsPanelOrigin, Point scoreOrigin, Point gameOverOrigin, Point warningBoxOrigin, Point counterBoxOrigin, Point crushBoxOrigin)
    {
        this.gridOrigin = gridOrigin;
        this.nextGemsPanelOrigin = nextGemsPanelOrigin;
        this.scoreOrigin = scoreOrigin;
        this.gameOverOrigin = gameOverOrigin;
        this.warningBoxOrigin = warningBoxOrigin;
        this.counterBoxOrigin = counterBoxOrigin;
        this.crushBoxOrigin = crushBoxOrigin;
    }


    public static PlayFieldDescriptor createForPlayerOne()
    {
        return loadFromFile("data/PlayerOne");
    }


    public static PlayFieldDescriptor createForPlayerTwo()
    {
        return loadFromFile("data/PlayerTwo");
    }


    public static PlayFieldDescriptor loadFromFile(String fileName)
    {
        try
        {
            Properties propertiesList = new Properties();
            propertiesList.load(new FileInputStream(fileName));

            return new PlayFieldDescriptor(readPoint(propertiesList, "Grid"), readPoint(propertiesList, "NextGemPanel"), readPoint(propertiesList, "Score"), readPoint(propertiesList, "GameOver"), readPoint(propertiesList, "WarningBox"), readPoint(propertiesList, "CounterBox"), readPoint(propertiesList, "CrushBox"));

        }
        catch (IOException e)
        {
            throw new RuntimeException("The file containing the playfield description does not exists");
        }
    }


    private static Point readPoint(Properties propertiesList, String id)
    {
        int x = Integer.parseInt(propertiesList.getProperty(id + "X"));
        int y = Integer.parseInt(propertiesList.getProperty(id + "Y"));
        return new Point(x, y);
    }


    public Point getGridOrigin()
    {
        return gridOrigin;
    }


    public Point getNextGemsPanelOrigin()
    {
        return nextGemsPanelOrigin;
    }


    public Point getScoreOrigin()
    {
        return scoreOrigin;
    }


    public Point getGameOverOrigin()
    {
        return gameOverOrigin;
    }


    public Point getWarningBoxOrigin()
    {
        return warningBoxOrigin;
    }


    public Point getCounterBoxOrigin()
    {
        return counterBoxOrigin;
    }


    public Point getCrushBoxOrigin()
    {
        return crushBoxOrigin;
    }
}
