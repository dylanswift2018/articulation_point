package articulation_point_in_a_graphe;

import javafx.application.Application;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import static java.lang.System.*;

public class Main extends Application {

    Button resetG = new Button("RESET");
    Button articulation = new Button("RESULTS");
    Button addCircle = new Button("ADD NODE");
    Button addLine = new Button("ADD ARC");
    Graph graph = new Graph();
    Stage window;
    AnchorPane root = new AnchorPane();
    CircleH circle[] = new CircleH[100];
    boolean circleSelected[] = new boolean[100] ;
    boolean exist = true , start = false , chooseCircle=true;
    double startC[] = new double[2] ;
    int compteur = 0 , compteurLine=0 , startNode ;
    Line lines[]= new Line[100];
    VBox vbox1 = new VBox(20);
    VBox vbox2 = new VBox(230);
    VBox vbox = new VBox(150);




/*------------------------------------------------------------------------------------------*/
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        addButtons();
        addBorder();

        root.setOnMouseClicked(event ->
        {
                if (exist) addCircle(event.getSceneX(), event.getSceneY());
                else exist = true;
        });
        root.setStyle("-fx-background-color:green");
        window = primaryStage;
        window.setTitle("Articulation Point In A Graphe");
        Scene scene = new Scene(root , 900,700 ) ;
        scene.setOnKeyPressed(event ->
        {
            if (compteur>0)
            switch( event.getCode() )
            {
                case D :
                    circle[0].setCenterX(circle[0].getCenterX()+5.0);
                    break;
                case Q :
                    circle[0].setCenterX(circle[0].getCenterX()-5.0);
                    break;
                case Z :
                    circle[0].setCenterY(circle[0].getCenterY()-5.0);
                    break;
                case S :
                    circle[0].setCenterY(circle[0].getCenterY()+5.0);
                    break;
            }
        });
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }
/*------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------*/
    public static void main(String[] args)
    {
        launch(args);
    }

    public void addCircle(double x, double y)
    {
        if ((check(x, y) == true )&&(chooseCircle==true) && (checkInside(x,y)) )
        {
            graph.arc[compteur][compteur] = true;
            CircleH inter = new CircleH();
            inter.setFill(Color.WHITESMOKE);
            inter.setStyle("-fx-stroke: black;");
            inter.setStrokeWidth(5.0);
            inter.numero = compteur;
            inter.setCenterX(x);
            inter.setCenterY(y);
            inter.setRadius(20);
            circle[compteur] = inter;
            root.getChildren().add(circle[compteur]);
            circle[compteur].setOnMouseClicked(e ->
            {
                CircleH link = (CircleH) (e.getSource());
                exist = false;
                if (chooseCircle==false)
                {
                    if (start) {
                               addLine(link.getCenterX(), link.getCenterY(), link.numero);
                               if (startNode!=link.numero) circle[startNode].setFill(Color.WHITE);
                                }
                         else
                             {
                             startC[0] = link.getCenterX();
                             startC[1] = link.getCenterY();
                             start = true;
                             startNode = link.numero;
                             circle[startNode].setFill(Color.YELLOW);
                             }
                }
            });
            exist = true;
            compteur++;
        }
    }
/*------------------------------------------------------------------------------------------*/
    public void addLine(double x , double y , int end )
    {
        if ((((startC[0])!=x)&&(startC[1]!=y))&&(chooseCircle==false))
        {
            graph.arc[startNode][end]=true;
            graph.arc[end][startNode]=true;
            Line line = new Line(startC[0], startC[1], x, y);
            line.setStrokeWidth(4.0);
            line.setAccessibleText("hhhhhh");
            line.setStyle("-fx-stroke: BLACK;");

            lines[compteurLine] = line;
            root.getChildren().add(lines[compteurLine]);
            resetCircles() ;
            start = false;
            compteurLine++;
        }
    }
/*------------------------------------------------------------------------------------------*/
    public void articulationColor()
    {
      boolean art[] = graph.getNoeudArt(compteur);
      for ( int i=0 ; i<compteur ; i++ )
      {
        if (art[i]==true)
                  circle[i].setFill(Color.GREEN);
              else
                  circle[i].setFill(Color.WHITE);
      }
    }
/*------------------------------------------------------------------------------------------*/
    public void resetCircles()
    {
        for (int i = 0; i < compteur; i++) {
            root.getChildren().remove(circle[i]);
            root.getChildren().add   (circle[i]);
        }
    }
/*------------------------------------------------------------------------------------------*/
    public boolean check(double x , double y)
    {
        int j=0 ;
        for(int i=0; i<compteur; i++)
        {
            if (distance(x, y , circle[i].getCenterX() , circle[i].getCenterY()) < (3 * 20.0))
            { j++ ;}
            if (j>0) break;
        }
    if (j>0) return false; else return true;
    }
/*------------------------------------------------------------------------------------------*/
    public double distance(double x , double y , double xx , double yy)
    {
        return Math.sqrt(  Math.pow(x-xx,2.0) +Math.pow(y-yy,2.0)   ) ;
    }
/*------------------------------------------------------------------------------------------*/
    public void reset()
        {
        graph = new Graph();
        for (int i=0 ; i<compteur; i++) {root.getChildren().remove(circle[i]);}
        for (int i=0 ; i<compteurLine ; i++) {root.getChildren().remove(lines[i]);
        }

        circle = new CircleH[100];
        lines= new Line[100];
         exist = true ;
         start = false ;
         startC = new double[2] ;
         compteur = 0 ; compteurLine=0 ;
    }
/*------------------------------------------------------------------------------------------*/
    public void addBorder()
    {
        Rectangle rectangle=new Rectangle(650,600,Color.DARKGRAY);
        rectangle.setX(200.0);
        rectangle.setY(50.0);
        rectangle.setArcWidth(50.0);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(3.0);
        root.getChildren().add(rectangle);
    }
/*------------------------------------------------------------------------------------------*/
    public void addButtons()
    {

        articulation.setEffect(new DropShadow());
        articulation.setStyle("-fx-font: 18 arial; -fx-base: #b6e7c9;");

        resetG.setEffect(new DropShadow());
        resetG.setStyle("-fx-font: 18 arial; -fx-base: #AE5F77;");

        addCircle.setEffect(new DropShadow());
        addCircle.setStyle("-fx-font: 18 arial; -fx-base: #635352;");

        addCircle.setEffect(new DropShadow());
        addLine.setStyle("-fx-font: 18 arial; -fx-base: #bbbbbb;");


        //button's events
        resetG.setOnAction(event -> reset());

        articulation.setOnAction(event ->
                {
                    articulationColor();
                }
        );

        addCircle.setOnAction(event ->
                {
                    if (start) circle[startNode].setFill(Color.WHITE);
                    chooseCircle = true;
                    //addLine.setStyle("-fx-background-color:white");
                    //addCircle.setStyle("-fx-background-color:#555555");
                    addLine.setStyle("-fx-font: 18 arial; -fx-base: #bbbbbb;");
                    addCircle.setStyle("-fx-font: 18 arial; -fx-base: #635352;");
                }
                    );

        addLine.setOnAction  (event ->
                {
                start=false;
                chooseCircle=false ;
                //addLine.setStyle("-fx-background-color:#555555");
                //addCircle.setStyle("-fx-background-color:white");
                 addCircle.setStyle("-fx-font: 18 arial; -fx-base: #bbbbbb;");
                 addLine.setStyle("-fx-font: 18 arial; -fx-base: #635352;");

                }
                     );

        AnchorPane.setTopAnchor(vbox,100.0);
        AnchorPane.setLeftAnchor(vbox,30.0);
        vbox1.getChildren().addAll(addCircle,addLine);
        vbox2.getChildren().addAll(articulation,resetG);
        vbox.getChildren().addAll(vbox1,vbox2);
        root.getChildren().add(vbox);
    }

    public boolean checkInside(double x,double y)
    {
     if ((x>=225)&&(x<=825)&&(y>=75)&&(y<=625)) return true ; else return false ;
    }
/*------------------------------------------------------------------------------------------*/
}

