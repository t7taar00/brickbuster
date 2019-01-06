import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class Ball extends Group
{   
    static final int BALL_RADIUS = 10;
    static final double VELOCITY_UPDATE = 0.1;

    static double startingBallVelocity = 8.0;
    
    double ballVelocity;
    double ballDirection = Math.random() * Math.PI * 2;
      
    Circle ballBackground;

    Rectangle ballCollisionArea;

    double lastMovementX, lastMovementY;
  
    public Ball(Point2D position)
    {
        ballBackground = new Circle(position.getX(),
                                    position.getY(),
                                    BALL_RADIUS);
        
        Stop[] colorStops = {new Stop(0, Color.GRAY.brighter()),
                             new Stop(1, Color.BLACK)};
        
        LinearGradient gradientColor = new LinearGradient( 0, 0, 1, 1, true,
                                       CycleMethod.NO_CYCLE, colorStops);
        
        ballBackground.setFill(gradientColor);
        ballBackground.setStroke(Color.GREY.darker());
        
        ballVelocity = startingBallVelocity;
        
        this.getChildren().add(ballBackground);
    }

    public boolean containsPoint(double pointX, double pointY)
    {
        double pointDistance = Math.sqrt(
                               Math.pow(ballBackground.getCenterX() - pointX, 2) +
                               Math.pow(ballBackground.getCenterY() - pointY, 2));

        return (pointDistance <= ballBackground.getRadius());
    }
    
    public void addVelocity(double velocity)
    {
        ballVelocity += velocity;
        startingBallVelocity = ballVelocity;
    }

    public void moveBall()
    { 
        lastMovementX = ballVelocity * Math.cos(ballDirection);
        lastMovementY = -ballVelocity * Math.sin(ballDirection);

        ballBackground.setCenterX(ballBackground.getCenterX() +
                                  lastMovementX);
      
        ballBackground.setCenterY(ballBackground.getCenterY() + 
                                  lastMovementY);
    }
    
    public boolean checkLevelCollision(Rectangle collisionArea)
    {
        boolean collisionStatus = false;
        
        ballCollisionArea = collisionArea;
        
        if(ballBackground.getCenterY() - ballBackground.getRadius() 
           <= ballCollisionArea.getY())
        {
            // the ball has hit the northern 'wall' of the collision area        
            ballDirection = 2 * Math.PI - ballDirection;
            collisionStatus = true;
        }

        if(ballBackground.getCenterX() - ballBackground.getRadius()
           <= ballCollisionArea.getX())
        {
            // the western wall has been reached
            ballDirection = Math.PI - ballDirection;
            collisionStatus = true;
        }

        if((ballBackground.getCenterY() + ballBackground.getRadius())
           >= (ballCollisionArea.getY() + ballCollisionArea.getHeight()))
        {
            // southern wall has been reached
            ballDirection = 2 * Math.PI - ballDirection;
            collisionStatus = true;
        }

        if((ballBackground.getCenterX() + ballBackground.getRadius())
           >= (ballCollisionArea.getX() + ballCollisionArea.getWidth()))
        {
            // eastern wall reached
            ballDirection = Math.PI - ballDirection;
            collisionStatus = true;
        }
        
        return collisionStatus;
    }
    
    public boolean checkPaddleCollision(Rectangle paddle)
    {
        ballCollisionArea = paddle;

        if(this.getBoundsInLocal().intersects(
                ballCollisionArea.getBoundsInLocal()))
        {
            if((ballBackground.getCenterX() - ballBackground.getRadius() / 2)
                >= (ballCollisionArea.getX() + ballCollisionArea.getWidth()))
            {
             /* if(ballBackground.getCenterY()
                   >= ballCollisionArea.getY())
                if(ballBackground.getCenterY()
                   <= ballCollisionArea.getY() + ballCollisionArea.getHeight()) */
                
                    ballDirection = Math.PI - ballDirection;
            }           
            else if(ballBackground.getCenterX() + ballBackground.getRadius() / 2
                <= ballCollisionArea.getX())
            { 
             /* if(ballBackground.getCenterY()
                   >= ballCollisionArea.getY())
                if(ballBackground.getCenterY()
                   <= ballCollisionArea.getY() + ballCollisionArea.getHeight()) */
                
                    ballDirection = Math.PI - ballDirection;
            }
            else ballDirection = 2 * Math.PI - ballDirection;
            
            return true;
        }
        else return false;
    }
    
    public boolean checkBrickCollision(Rectangle brick)
    {
        ballCollisionArea = brick;

        if(this.getBoundsInLocal().intersects(
                ballCollisionArea.getBoundsInLocal()))
        {      
            if((ballBackground.getCenterX() - ballBackground.getRadius() / 2)
                >= (ballCollisionArea.getX() + ballCollisionArea.getWidth()))
            {
             /* if(ballBackground.getCenterY()
                   >= ballCollisionArea.getY())
                if(ballBackground.getCenterY()
                   <= ballCollisionArea.getY() + ballCollisionArea.getHeight()) */
                
                    ballDirection = Math.PI - ballDirection;
            }           
            else if(ballBackground.getCenterX() + ballBackground.getRadius() / 2
                <= ballCollisionArea.getX())
            {
             /* if(ballBackground.getCenterY()
                   >= ballCollisionArea.getY())
                if(ballBackground.getCenterY()
                   <= ballCollisionArea.getY() + ballCollisionArea.getHeight()) */
                
                    ballDirection = Math.PI - ballDirection;
            }
            else ballDirection = 2 * Math.PI - ballDirection;
            
            return true;
        } 
        else return false;
    }
    
    public boolean checkBottomCollision(Rectangle bottom)
    {
        ballCollisionArea = bottom;

        return this.getBoundsInLocal().intersects(
                ballCollisionArea.getBoundsInLocal());
    }
}
