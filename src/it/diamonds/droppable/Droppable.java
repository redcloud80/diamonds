package it.diamonds.droppable;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.gems.MergeResult;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.video.AnimatedSprite;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;
import it.diamonds.grid.Region;


public interface Droppable
{

    Region getRegion();


    int getScore();


    int getArea();


    void update(long timer);


    void moveToCell(Cell cell);


    DroppableType getType();


    boolean isFalling();


    DroppableColor getColor();


    DroppableColor getHiddenColor();


    void drop();


    void moveDown(Grid grid);


    boolean canMoveDown(Grid grid);


    boolean canBeAddedToBigGem(DroppableColor color);


    void extend(Grid grid);


    MergeResult merge(Grid grid);


    AnimatedSprite getAnimatedSprite();


    void startCrush(Grid grid, CrushPriority priority, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator);


    void getAdjacentCrushableGems(DroppableList crushableGems, DroppableList optionalCrushableGems, Droppable crushStarter, Grid grid);


    void tryToAddToCrushableGems(DroppableList crushableGems, DroppableList optionalCrushableGems, Droppable crushStarter, Grid grid);


    void updateTransformation();


    Droppable transform(GridController gridController);

}
