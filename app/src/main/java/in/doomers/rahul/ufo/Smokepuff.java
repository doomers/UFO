package in.doomers.rahul.ufo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Smokepuff extends GameObject{
    public int r=0;
    private int i =0;
    public Smokepuff(int x, int y)
    {
        r = 5;
        super.x = x;
        super.y = y;
    }
    public void update()
    {
        x-=10;
    }
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        if(i<2)
            paint.setColor(Color.RED);
        else if(i<4)
            paint.setColor(Color.YELLOW);
        else if(i>6)
            paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x - r, y - r, r+1, paint);
        canvas.drawCircle(x-r-1, y-r-1, r-1, paint);
        canvas.drawCircle(x+1 - r, y+1 - r, r, paint);
        canvas.drawCircle(x-r+1, y-r- 1, r+1, paint);
        canvas.drawCircle(x-r+1, y-r+1, r, paint);
        canvas.drawCircle(x-r+2, y-r+2, r+1, paint);


        i++;
    }

}
