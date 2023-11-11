

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.image.*;

public class GraphicSwing05 extends JPanel implements Runnable
{
	
    double startTime = System.currentTimeMillis();
    double circleMove = 0, squareRotate = 0, squareTranslate = 0;
    Boolean checkCircle = true;
    Boolean checkSquare = false;
    
    public void paintComponent(Graphics g)
    {
       Graphics2D g2 = (Graphics2D) g;
       g2.setColor(Color.WHITE);
       g2.fillRect(0, 0, 600, 600);
       g2.setColor(Color.BLACK);
    
       //circle
       if(checkCircle == true)
       {
          g2.translate(circleMove, 0);
          if(circleMove >=500)
          {
             checkCircle = false;
          }
       }else{
          g2.translate(circleMove, 0);
          if(circleMove <=0)
          {
             checkCircle = true;
          }
       }//else
       
       g2.fillOval(0, 0, 100, 100);
       g2.drawOval(0, 0, 100, 100);
       g2.translate(-circleMove, 0);
       
       //sqaure
       g2.setColor(Color.YELLOW);
       g2.fillRect(0, 600, 100, 100);
       g.translate(0, (int)-squareTranslate);
       g2.drawRect(0, 600, 100, 100);
    
       g2.fillRect(0, 600, 100, 100);
       g.translate(0,(int)squareTranslate);
       g2.rotate(squareRotate,100,100);
    
       //change anchor point
       //g2.drawRect(200, 200, 200, 200);
    }//paintComponent
    
    public void run()
    {
        double lastTime = System.currentTimeMillis();
        double currentTime, elapsedTime;
        //control time
         while(true){
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - lastTime;
            lastTime = currentTime;
         
         //update 
         //circle
         if(checkCircle == true)
         {
            circleMove += 50 * elapsedTime / 1000.0;
         } else {
            circleMove -= 50 * elapsedTime / 1000.0;
         }//else
            
         //square
         squareRotate += 0.5 * elapsedTime / 1000.0;
         if((currentTime - startTime)/1000>=3)
         {
              checkSquare = true;
         }
        
          if(checkSquare && squareTranslate <=600)
          {
              squareTranslate += 100 * elapsedTime / 1000.0; 
          }
        
         //display
         repaint();
        }
    }//run

    public static void main(String[] args) 
    {
    	GraphicSwing05 m = new GraphicSwing05();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Fifth Swing 63050096");
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);      
       (new Thread(m)).start();
    }//main
}//class