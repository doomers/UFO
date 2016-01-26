package in.doomers.rahul.ufo;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static int WIDTH;
    public static int HEIGHT;
    public static int MOVESPEED = -5;
    private long smokeStartTime;
    private long missileStartTime;
    private long showRangertime, Rangerduration;
    private long shielstartTime, shielduration;
    private MainThread thread;
    private Background bg;
    private Player player;
    private ScoreBooster scorebooster;
    private Explosion explosion;


    private int score = 0;
    private ArrayList<Smokepuff> smoke;
    private ArrayList<Missile> missiles;
    private ArrayList<Ranger> ranger;
    private Random rand = new Random();
    private Random random = new Random();
    private SurfaceHolder holder;
    private long startTime;
    private long scorelapsed;
    private HighScore high;
    private boolean Gameover = false, showRanger = false, showPlayer = true;
    private boolean explode = false;
    private long Highscore = 0;
    private boolean PlayedOnce = true;
    private static final String TAG = "explosion ignitiated";

    // private MediaPlayer mediaplayer,mp;
    //public Bitmap res1,res2;
    public GamePanel(Context context) {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);


        //make gamePanel focusable so it can handle events
        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.darkback));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.atmufot), 97, 55, 3);
        scorebooster = new ScoreBooster(BitmapFactory.decodeResource(getResources(), R.drawable.finaltranger)
                , 300, 250, 1);
        smoke = new ArrayList<Smokepuff>();
        missiles = new ArrayList<Missile>();
        ranger = new ArrayList<Ranger>();
        //  mediaplayer= MediaPlayer.create(getContext(), R.raw.ufosound);
        //  mp=MediaPlayer.create(getContext(),R.raw.explode);
        shielstartTime = System.nanoTime();
        smokeStartTime = System.nanoTime();
        missileStartTime = System.nanoTime();
        thread = new MainThread(getHolder(), this);
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!player.getPlaying()) {
                PlayedOnce = false;
                Gameover = false;
                explode = false;
                player.setPlaying(true);
                scorebooster.setPlaying(true);

            } else {
                player.setUp(true);
                scorebooster.setUp(true);
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {//mediaplayer.start();
            player.setUp(false);
            scorebooster.setUp(false);
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update() {
        if (Gameover) {
            explosion.update();
        }
        if (showRanger) {
            scorebooster.update();
            Log.e(TAG, "RANGER APPEARS");
        }
        if (player.getPlaying() || showRanger) {
            bg.update();
            player.update();

            bg.setVector(MOVESPEED);
            scorelapsed = (System.nanoTime() - startTime) / 1000000;
            if (scorelapsed > 1000) {
                score++;
                startTime = System.nanoTime();
            }
            shielduration = (System.nanoTime() - shielstartTime) / 1000000;
            if (shielduration > 3000 - score / 10) {
                ranger.add(new Ranger(BitmapFactory.decodeResource(getResources(), R.drawable.tshield),
                        WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 115, 115, score, 1));
                shielstartTime = System.nanoTime();

            }

            long missilelapsed = (System.nanoTime() - missileStartTime) / 1000000;
            if (missilelapsed > 2000 - score / 10) {

                int num = 1 + rand.nextInt(16);
                if (num == 1) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tmongreen),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 90, 91, score, 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.btstaryellow),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 67, 69, score, 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.btstarblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 54, 55, score, 2));

                }

                if (num == 2) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.btstaryellow),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 67, 69, player.getScore(), 2));

                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarorange),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));

                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.bstarrr),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 85, 87, player.getScore(), 2));
                }
                if (num == 3) {

                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.stargrey),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.btstaryellow),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 67, 69, player.getScore(), 2));
                }
                if (num == 4) {

                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.starblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));

                }
                if (num == 5) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarnblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.bstarrr),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 85, 87, player.getScore(), 2));
                }
                if (num == 6) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarnblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarorange),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.stargrey),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));

                }
                if (num == 7) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarorange),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.bstarrr),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 85, 87, player.getScore(), 2));
                }
                if (num == 8) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarorange),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarorange),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                }
                if (num == 9) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarnblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.btstarblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 54, 55, player.getScore(), 2));
                }
                if (num == 10) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.stargrey),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarnblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                }
                if (num == 11) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.btstarblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 54, 55, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.starblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 80, 85, player.getScore(), 2));
                }
                if (num == 12) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.btstarblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 54, 55, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.stargrey),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarorange),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                }
                if (num == 13) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarnblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.bstarrr),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 85, 87, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.stargrey),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                }
                if (num == 14) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.starblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarnblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                }
                if (num == 15) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.tstarnblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 48, 45, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.bstarrr),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 85, 87, player.getScore(), 2));
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.btstarblue),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 54, 55, player.getScore(), 2));

                }
                missileStartTime = System.nanoTime();
            }
            for (int a = 0; a < missiles.size(); a++) {
                missiles.get(a).update();
                if (!Gameover) {
                    if (collision(missiles.get(a), player)) {
                        missiles.clear();
                        Gameover = true;
                        explode = true;
                        explosion = new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion), player.getX(),
                                player.getY() - 30, 100, 100, 25);
                        player.setPlaying(false);
                    }
                }
                if (missiles.get(a).getX() < 400 && showRanger == true) {
                    missiles.remove(a);
                }
                for (int i = 0; i < ranger.size(); i++) {

                    ranger.get(i).update();
                    if (collision(ranger.get(i), player) && showPlayer) {
                        ranger.remove(i);
                        showRanger = true;
                        showPlayer = false;
                        showRangertime = System.nanoTime();

                    }
                }
                for (int j = 0; j < ranger.size() && showRanger; j++) {
                    Log.e(TAG, "RANGER may be collide");
                    ranger.get(j).update();
                    if (ranger.get(j).getX() < 500) {
                        ranger.remove(j);
                    }


                }
                Rangerduration = (System.nanoTime() - showRangertime) / 100000;
                if (Rangerduration > 100000) {
                    showRanger = false;
                    showPlayer = true;
                }
            }
            for (int k = 0; k < smoke.size(); k++) {
                smoke.get(k).update();
                if (smoke.get(k).getX() < -20) smoke.remove(k);
            }

        }

    }

    public boolean collision(GameObject a, GameObject b) {
        if (Rect.intersects(a.getRectangle(), b.getRectangle())) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        WIDTH = getWidth();
        HEIGHT = getHeight();
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.argb(3, 4, 5, 6));
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
        if (canvas != null) {
            bg.draw(canvas);
            if (!Gameover) {
                if (showPlayer) {
                    player.draw(canvas);
                    for (Smokepuff sm : smoke) {
                        sm.draw(canvas);
                    }
                }
                if (showRanger) {
                    scorebooster.draw(canvas);
                }
                if (showRanger || showPlayer) {
                    for (Missile mis : missiles) {
                        mis.draw(canvas);
                    }
                    for (Ranger rg : ranger) {
                        rg.draw(canvas);
                    }

                }

            }
            if (Gameover) {
                canvas.drawText("CLICK TO PLAY AGAIN", GamePanel.WIDTH / 4, GamePanel.HEIGHT / 2 + 60, paint);
                canvas.drawText("HIGHSCORE" + "\t" + Highscore, GamePanel.WIDTH / 4 + 60, GamePanel.HEIGHT / 2 + 140, paint);

            }
            if (explode) {
                explosion.draw(canvas);
            }

        }

    }
}