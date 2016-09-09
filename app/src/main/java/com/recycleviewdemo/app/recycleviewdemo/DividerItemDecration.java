package com.recycleviewdemo.app.recycleviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/9/9.
 */
public class DividerItemDecration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
      android.R.attr.listDivider
    };
    private static final int Horizontal_LIST = LinearLayoutManager.HORIZONTAL;
    private static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDrawable;
    private int mOrientation;//屏幕方向

    public DividerItemDecration(Context context, int orientation){
        //在自定义view的代码中引入自定义属性，修改构造函数
        //context通过调用obtainStyledAttributes方法来获取一个TypeArray，然后由该TypeArray来对属性进行设置
        //obtainStyledAttributes方法有三个，我们最常用的是有一个参数的obtainStyledAttributes(int[] attrs)，
        // 其参数直接styleable中获得
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        //采用系统主题(android.R.attr.listDivider)来设置成分隔线
        mDrawable = a.getDrawable(0);
        //调用结束后务必调用recycle()方法，否则这次的设定会对下次的使用造成影响
        a.recycle();
        setOrientation(orientation);
    }

    private void setOrientation(int orientation){
        if(orientation != Horizontal_LIST && orientation != VERTICAL_LIST){
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(mOrientation == Horizontal_LIST){
            drawHorizontal(c,parent);
        }else{
            drawVertical(c,parent);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent){
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent){
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    //getItemOffset方法来计算每个Item的Decoration合适的尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == VERTICAL_LIST){
            outRect.set(0,0,0,mDrawable.getIntrinsicHeight());//横屏
        }else{
            outRect.set(0,0,mDrawable.getIntrinsicWidth(),0);//竖屏
        }
    }
}
