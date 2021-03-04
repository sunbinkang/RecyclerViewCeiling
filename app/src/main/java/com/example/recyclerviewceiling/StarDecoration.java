package com.example.recyclerviewceiling;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Date: 2021/2/21
 * Author: SunBinKang
 * Description: 绘制流程顺序onDraw  itemView  onDrawOver
 */
public class StarDecoration extends RecyclerView.ItemDecoration {

    private int groupHeaderHeight;
    private Paint headerPaint;
    private Paint textPaint;
    private Rect textRect;

    public StarDecoration(Context context) {
        groupHeaderHeight = dp2px(context, 100);
        headerPaint = new Paint();
        headerPaint.setColor(Color.BLUE);
        headerPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dp2px(context, 28));
        textPaint.setAntiAlias(true);
        textRect = new Rect();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getAdapter() instanceof StarAdapter) {
            StarAdapter adapter = (StarAdapter) parent.getAdapter();
            //当前屏幕的item个数
            int childCount = parent.getChildCount();
            //recyclerView的左边padding值作为绘制分割线的左
            int left = parent.getPaddingLeft();
            //分割线的右边
            int right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 0; i < childCount; i++) {
                //获取对应i的View
                View childAt = parent.getChildAt(i);
                int childLayoutPosition = parent.getChildLayoutPosition(childAt);
                boolean isGroupHeader = adapter.isGroupHeader(childLayoutPosition);
                //是否为头部，并且要排除recyclerView设置padding，会出现在padding中绘制的情况
                if (isGroupHeader && childAt.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0) {
                    c.drawRect(left, childAt.getTop() - groupHeaderHeight, right, childAt.getTop(), headerPaint);
                    String groupName = adapter.getGroupName(childLayoutPosition);
                    Log.i("BK", groupName + " " + childAt.getTop());
                    textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                    c.drawText(groupName, left + 20, childAt.getTop() - groupHeaderHeight / 2
                            + textRect.height() / 2, textPaint);
                } else if (childAt.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0){
                    //分割线
                    c.drawRect(left, childAt.getTop() - 1, right, childAt.getTop(), headerPaint);
                }
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getAdapter() instanceof StarAdapter) {
            StarAdapter adapter = (StarAdapter) parent.getAdapter();
            //屏幕可视的第一个itemView的位置
            int firstVisibleItemPosition = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            //获取position对应的view
            View itemView = parent.findViewHolderForAdapterPosition(firstVisibleItemPosition).itemView;
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top = parent.getPaddingTop();
            //当屏幕可视范围内，第二个itemView是下一组的头部的时候
            boolean isGroupHeader = adapter.isGroupHeader(firstVisibleItemPosition + 1);
            if (isGroupHeader) {//这种情况就要将上一个吸顶的慢慢往上顶的效果
                Log.i("BK", "onDrawOver1: " + firstVisibleItemPosition);
                int bottom = Math.min(groupHeaderHeight, itemView.getBottom()-parent.getPaddingTop());
                c.drawRect(left, top, right, top + bottom, headerPaint);
                String groupName = adapter.getGroupName(firstVisibleItemPosition);
                textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                c.drawText(groupName, left + 20, top + bottom - groupHeaderHeight / 2
                        + textRect.height() / 2, textPaint);
            } else {//固定在顶部的效果
                Log.i("BK", "onDrawOver2: " + firstVisibleItemPosition);
                c.drawRect(left, top, right, top + groupHeaderHeight, headerPaint);
                String groupName = adapter.getGroupName(firstVisibleItemPosition);
                textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                c.drawText(groupName, left + 20, top + groupHeaderHeight / 2
                        + textRect.height() / 2, textPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getAdapter() instanceof StarAdapter) {
            StarAdapter adapter = (StarAdapter) parent.getAdapter();
            //RecyclerView的LayoutParams，是有viewHolder的，所以可以通过View 获取LayoutParams,再拿到ViewHolder
            //获取当前view对应的position
            int childLayoutPosition = parent.getChildLayoutPosition(view);
            //判断是否是头部
            boolean isGroupHeader = adapter.isGroupHeader(childLayoutPosition);
            if (isGroupHeader) {
                //如果当前item是头部，则预留更大的空间
                outRect.set(0, groupHeaderHeight, 0, 0);
            } else {
                //不是头部隔开：1像素
                outRect.set(0, 1, 0, 0);
            }
        }
    }

    private int dp2px(Context context, float dpValve) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValve * density * 0.5f);
    }
}
