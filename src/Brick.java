import javafx.scene.shape.* ;

public class Brick extends Rectangle
{
    static final int BRICK_WIDTH = 80;
    static final int BRICK_HEIGHT = 20;
    
    static final int BRICK_STROKE_WIDTH = 1;
    
    static final int BRICK_POINTS_YELLOW = 1;
    static final int BRICK_POINTS_GREEN = 3;
    static final int BRICK_POINTS_ORANGE = 5;
    static final int BRICK_POINTS_RED = 7;
    
    private String brickColor;
    
    public Brick(int positionX, int positionY, String color)
    {
        this.setX(positionX);
        this.setY(positionY);
        
        brickColor = color;
        
        this.setWidth(BRICK_WIDTH);
        this.setHeight(BRICK_HEIGHT);
    }
    
    public int getPoints()
    {
        switch(brickColor)
        {
            case "YELLOW":
                return BRICK_POINTS_YELLOW; 
                
            case "GREEN":
                return BRICK_POINTS_GREEN;
                
            case "ORANGE":
                return BRICK_POINTS_ORANGE;
                
            case "RED":
                return BRICK_POINTS_RED;
                
            default:
                return 0;
        }
    }
}