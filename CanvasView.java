package com.example.game.spidertask2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {
    private Paint oPaint ;
    private Paint xPaint ;
    private Paint gridpaint;
    private int width,height,eltW,eltH;
    private MainActivity mainactivity;
    private GameEngine gameEngine;
    private int prevx,prevy,undo=0;

    public CanvasView(Context context){
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet atrs){

        super(context,atrs);
        gridpaint = new Paint();
        gridpaint.setColor(Color.BLACK);
        gridpaint.setStyle(Paint.Style.FILL);
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.RED);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(15);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.BLUE);
    }

    public void setMainactivity(MainActivity a){
        mainactivity=a;
    }

    public void setGameEngine(GameEngine g){
        gameEngine=g;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        height=View.MeasureSpec.getSize(heightMeasureSpec);
        width=View.MeasureSpec.getSize(widthMeasureSpec);
        eltH=(height-5) / 3;
        eltW=(width-5) / 3;

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas){
        drawGrid(canvas);
        drawBoard(canvas, gameEngine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionevent){
        if(!gameEngine.isEnded() && motionevent.getAction() == MotionEvent.ACTION_DOWN){
            int x=(int) (motionevent.getX() / eltW);
            int y=(int) (motionevent.getY() / eltH);
            char win = gameEngine.play(x,y,1);
            prevx=x;
            prevy=y;
            undo=1;
            invalidate();

            if(win != ' ')
                mainactivity.gameEnded(win);
           /* else{
                win= gameEngine.computer();
                invalidate();

                if(win!= ' '){
                    mainactivity.gameEnded(win);
                }
            }*/
        }
        return super.onTouchEvent(motionevent);
    }

    public void undo(GameEngine gameEngine) {
        if (undo == 1) {
            char win = gameEngine.play(prevx, prevy, 2);
            invalidate();
            if (win != ' ')
                mainactivity.gameEnded(win);
            undo=0;
        }
        else;
    }
    private void drawBoard(Canvas canvas, GameEngine gameEngine){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                drawElt(canvas , gameEngine.elt(i,j) , i , j);
            }
        }
    }

    private void drawGrid(Canvas canvas){
        for(int i=0; i<2; i++) {
            float left = eltW * (i + 1);
            float right = left + 5;
            float top = 0;
            float bottom = height;

            canvas.drawRect(left, top, right, bottom, gridpaint);
        }
        for(int i=0;i<2;i++){
            float left2=0;
            float right2= width;
            float top2 = eltH*(i+1);
            float bottom2 = top2 + 5;

            canvas.drawRect(left2,top2,right2,bottom2,gridpaint);
        }
    }

    private void drawElt(Canvas canvas,char c,int x,int y){
        if (c == 'O') {
            float cx = (eltW * x) + eltW / 2;
            float cy = (eltH * y) + eltH / 2;

            canvas.drawCircle(cx, cy, Math.min(eltW, eltH) / 2 - 20 * 2, oPaint);

        } else if (c == 'X') {
            float startX = (eltW * x) + 20;
            float startY = (eltH * y) + 20;
            float endX = startX + eltW - 20 * 2;
            float endY = startY + eltH - 20;

            canvas.drawLine(startX, startY, endX, endY, xPaint);

            float startX2 = (eltW * (x + 1)) - 20;
            float startY2 = (eltH * y) + 20;
            float endX2 = startX2 - eltW + 20 * 2;
            float endY2 = startY2 + eltH - 20;

            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);
        }
    }
}
