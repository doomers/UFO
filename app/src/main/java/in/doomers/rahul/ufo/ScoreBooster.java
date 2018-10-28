package in.doomers.rahul.ufo;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Rahul on 10/21/2015.
 */
public class ScoreBooster extends GameObject {
   private Bitmap spritesheet;
   private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    public Bitmap[] image;
    private Boolean up;
    public Player player;
    public int x,y,dy;
    public ScoreBooster(Bitmap res, int w, int h,int numFrames) {
        x = 100;
        y = 3;
        dy = 0;
        this.height = h;
        this.width = w;
        image = new Bitmap[numFrames];
        spritesheet = res;
        image[0] = Bitmap.createBitmap(spritesheet, 0, 0, width, height);


        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();

    }


    public void setUp(boolean b){up = b;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>1000)
        {
            startTime = System.nanoTime();
        }
        animation.update();

        if(up){
            dy -=8;
        }
        else{
            dy +=1;
        }

        if(dy>14){dy = 14;
        }
        if(dy<-14){dy = -14;

        }
        if(y>GamePanel.HEIGHT) y=0;
        if(y<0)y=GamePanel.HEIGHT;
        y += dy;



    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
}
