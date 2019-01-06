import javafx.scene.shape.* ;

public class Paddle extends Rectangle
{
    static final int PADDLE_WIDTH = 160;
    static final int PADDLE_HARD_WIDTH = 80;
    static final int PADDLE_HEIGHT = 20;
    
    static final int PADDLE_STROKE_WIDTH = 1;
    
    public Paddle(int positionX, int positionY)
    {
        this.setX(positionX);
        this.setY(positionY);
        
        this.setWidth(PADDLE_WIDTH);
        this.setHeight(PADDLE_HEIGHT);
    }
    
    public void movePaddle(double movementX)
    {
        this.setX(this.getX() + movementX);
    }
}