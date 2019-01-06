import java.util.ArrayList;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class BrickBuster extends Application
{   
    static final int SCENE_WIDTH = 800;
    static final int SCENE_HEIGHT = 600;
    
    static final int LABEL_X_OFFSET = 20;
    
    static final int MAX_SCORE = 320;
    
    private double previousCursorPositionX;
    
    private int lives;
    private int score;
    
    Scene scene;
    
    Group gameObjects;
    ArrayList<Brick> brickObjects;
    
    AnimationTimer gameTimer;
    SoundFX gameSoundFX;
    
    Rectangle collisionArea;
    Rectangle bottomArea;
        
    Label gameScore;
    Label gameLives;
    
    Ball newBall;
    
    Paddle newPaddle;
    
    public void createBricks(int startPositionY, String color)
    {
        LinearGradient gradientColor;
                
        int bricksPerColor = 20;
        int brickPositionX = 0;
        int brickPositionY = startPositionY;
        
        for(int brickCounter = 0;
                brickCounter < bricksPerColor;
                brickCounter++)
        {
            Brick newBrick = new Brick(brickPositionX,
                                       brickPositionY,
                                       color);

            switch (color)
            {
                case "RED":
                    Stop[] colorStopsRed = {new Stop(0, Color.RED),
                                            new Stop(1, Color.RED.darker())};
        
                    gradientColor = new LinearGradient( 1, 0, 1, 1, true,
                                    CycleMethod.NO_CYCLE, colorStopsRed);
                    
                    newBrick.setStroke(Color.RED.darker());
                    break;
                case "ORANGE":
                    Stop[] colorStopsOrange = {new Stop(0, Color.ORANGE),
                                               new Stop(1, Color.ORANGE.darker())};
        
                    gradientColor = new LinearGradient( 1, 0, 1, 1, true,
                                    CycleMethod.NO_CYCLE, colorStopsOrange);
                    
                    newBrick.setStroke(Color.ORANGE.darker());
                    break;
                case "GREEN":
                    Stop[] colorStopsGreen = {new Stop(0, Color.GREEN.brighter()),
                                              new Stop(1, Color.GREEN)};
        
                    gradientColor = new LinearGradient( 1, 0, 1, 1, true,
                                    CycleMethod.NO_CYCLE, colorStopsGreen);
                    
                    newBrick.setStroke(Color.GREEN);
                    break;
                case "YELLOW":
                    Stop[] colorStopsYellow = {new Stop(0, Color.YELLOW),
                                               new Stop(1, Color.YELLOW.darker())};
        
                    gradientColor = new LinearGradient( 1, 0, 1, 1, true,
                                    CycleMethod.NO_CYCLE, colorStopsYellow);
                    
                    newBrick.setStroke(Color.YELLOW.darker());
                    break;
                default:
                    Stop[] colorStopsDefault = {new Stop(0, Color.BLACK),
                                                new Stop(1, Color.GREY.darker())};
        
                    gradientColor = new LinearGradient( 1, 0, 1, 1, true,
                                    CycleMethod.NO_CYCLE, colorStopsDefault);
                    break;
            }
            
            newBrick.setFill(gradientColor);
            
            newBrick.setStrokeWidth(Brick.BRICK_STROKE_WIDTH);
            
            brickObjects.add(newBrick);
            gameObjects.getChildren().add(newBrick);

            if(brickPositionX < SCENE_WIDTH - (int)newBrick.getWidth())
            {
                brickPositionX = brickPositionX + (int)newBrick.getWidth();
            }
            else
            {
                brickPositionX = 0;
                brickPositionY += (int)newBrick.getHeight();
            }
        }
    }
    
    public void createBall(Group groupForBall)
    {
        newBall = new Ball(new Point2D(SCENE_WIDTH / 2,
                                       SCENE_HEIGHT / 4 * 3 - Paddle.PADDLE_HEIGHT));
        
        groupForBall.getChildren().add(newBall) ;
    }
    
    public void createLabels()
    {
        gameScore = new Label("PRESS SPACE TO START");
        
        gameScore.relocate(LABEL_X_OFFSET, 
                           SCENE_HEIGHT - SCENE_HEIGHT / 6.5);
        
        gameScore.setFont(new Font("Bauhaus 93", 50));
        gameScore.setTextFill(Color.CRIMSON);
        
        gameObjects.getChildren().add(gameScore);
        
        gameLives = new Label("LIVES: 3");
        
        gameLives.relocate(SCENE_WIDTH - LABEL_X_OFFSET * 9, 
                           SCENE_HEIGHT - SCENE_HEIGHT / 6.5);
        
        gameLives.setFont(new Font("Bauhaus 93", 50));
        gameLives.setTextFill(Color.CRIMSON);
        
        gameObjects.getChildren().add(gameLives);
    }
    
    public void setMouseEvents()
    {
        scene.setOnMouseMoved((MouseEvent event) ->
        {
            double mouseMovementX = event.getSceneX()
                                    - previousCursorPositionX;
            
            previousCursorPositionX = event.getSceneX();
 
            newPaddle.movePaddle(mouseMovementX);
        });
    }
    
    public void setKeyEvents()
    {
        scene.setOnKeyPressed((KeyEvent event) ->
        {
            if(lives > 0)
            {  
                if(event.getCode() == KeyCode.SPACE)
                {        
                    gameSoundFX.playSound("game_start");
                    
                    gameTimer.start();
                    
                    if(score == 0)
                    {
                        gameScore.setText("SCORE: " + String.valueOf(score));
                    }
                }               
            }
        });
    }
    
    public void createPaddle()
    {
        LinearGradient gradientColor;
        
        final int startPositionX = SCENE_WIDTH / 2 - Paddle.PADDLE_WIDTH / 2;
        final int startPositionY = SCENE_HEIGHT / 4 * 3;
        
        newPaddle = new Paddle(startPositionX,
                               startPositionY);
        
        Stop[] colorStopsDefault = {new Stop(0, Color.GOLD),
                                    new Stop(1, Color.BROWN.darker())};
        
        gradientColor = new LinearGradient( 1, 0, 1, 1, true,
                        CycleMethod.NO_CYCLE, colorStopsDefault);
        
        newPaddle.setFill(gradientColor);
        newPaddle.setStroke(Color.GOLD.darker());
            
        newPaddle.setStrokeWidth(Paddle.PADDLE_STROKE_WIDTH);
        
        gameObjects.getChildren().add(newPaddle);
    }
    
    @Override
    public void start(Stage gameStage)
    {    
        gameObjects = new Group();
        brickObjects = new ArrayList();
        
        collisionArea = new Rectangle(0, 0, SCENE_WIDTH, SCENE_HEIGHT);
       
        bottomArea = new Rectangle(0,
                                   SCENE_HEIGHT / 4 * 3 + Paddle.PADDLE_HEIGHT,
                                   SCENE_WIDTH, 
                                   SCENE_HEIGHT / 4 - Paddle.PADDLE_HEIGHT);
        
        bottomArea.setStrokeWidth(Brick.BRICK_STROKE_WIDTH);
        bottomArea.setStroke(Color.CRIMSON);
        gameObjects.getChildren().add(bottomArea);
        
        scene = new Scene(gameObjects,
                          SCENE_WIDTH,
                          SCENE_HEIGHT,
                          Color.BLACK);
        
        gameSoundFX = new SoundFX();
        
        lives = 3;
        score = 0;
        
        createLabels();
        
        // create eight rows of bricks, with each two rows a different color
        createBricks(Brick.BRICK_HEIGHT * 0, "RED");  
        createBricks(Brick.BRICK_HEIGHT * 2, "ORANGE");
        createBricks(Brick.BRICK_HEIGHT * 4, "GREEN");
        createBricks(Brick.BRICK_HEIGHT * 6, "YELLOW");
        
        createPaddle();
        
        createBall(gameObjects);
        
        previousCursorPositionX = SCENE_WIDTH / 2;
        setMouseEvents();
        setKeyEvents();
        
        scene.setCursor(Cursor.NONE);
        
        //gameStage.setResizable(false);
        gameStage.setTitle("BrickBuster");
        gameStage.setScene(scene);
        gameStage.show();
        
        gameTimer = new AnimationTimer()
        {
            @Override
            public void handle(long timeStamp)
            {
                newBall.moveBall();
                
                if(newBall.checkLevelCollision(collisionArea))
                {
                    gameSoundFX.playSound("wall_hit");
                }
                
                if(newBall.checkPaddleCollision(newPaddle))
                {
                    gameSoundFX.playSound("paddle_hit");
                }
                
                if(newBall.checkBottomCollision(bottomArea))
                {
                    gameSoundFX.playSound("ball_lost");
                    
                    gameObjects.getChildren().remove(newBall);
                    newBall = null;
                    createBall(gameObjects);
                    
                    lives--;
                    
                    if(lives > 0)
                    {
                        gameLives.setText("LIVES: " + String.valueOf(lives));
                    }
                    else
                    {
                        gameObjects.getChildren().remove(newBall);
                        
                        gameLives.setText("GAME OVER");
                        gameLives.relocate(SCENE_WIDTH - LABEL_X_OFFSET * 14, 
                                           SCENE_HEIGHT - SCENE_HEIGHT / 6.5);
                    }
                    
                    gameTimer.stop();
                }
                
                for(Brick brick : brickObjects)
                {
                    if(newBall.checkBrickCollision(brick))
                    {
                        gameSoundFX.playSound("brick_hit");
                        
                        if(brick.getPoints() == Brick.BRICK_POINTS_RED)
                        {
                            if(newPaddle.getWidth() != Paddle.PADDLE_HARD_WIDTH)
                            {
                                gameSoundFX.playSound("paddle_shrink");
                                newPaddle.setWidth(Paddle.PADDLE_HARD_WIDTH);
                            }            
                        }
                        
                        score += brick.getPoints();                    
                        gameScore.setText("SCORE: " + String.valueOf(score));
                        
                        newBall.addVelocity(Ball.VELOCITY_UPDATE);
                        
                        gameObjects.getChildren().remove(brick);
                        brickObjects.remove(brick);
                        brick = null;
                    }                 
                }
                
                if(score == MAX_SCORE)
                {
                    gameSoundFX.playSound("high_score");
                    
                    gameLives.setText("HIGH SCORE");
                    gameLives.relocate(SCENE_WIDTH - LABEL_X_OFFSET * 15, 
                                       SCENE_HEIGHT - SCENE_HEIGHT / 6.5);
                    
                    gameTimer.stop();
                }
            }
        };
    }

    public static void main(String[] args)
    {
        launch(args);
    }   
} 