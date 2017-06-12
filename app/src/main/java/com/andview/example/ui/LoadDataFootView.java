package com.andview.example.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.example.R;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IFooterCallBack;

public class LoadDataFootView extends LinearLayout implements IFooterCallBack {
    private ImageView imgAnim;
    AnimationDrawable drawable;
    private TextView move;
    private View view;

    private boolean showing = true;
    public LoadDataFootView(Context context) {
        super(context);
        setBackgroundColor(Color.parseColor("#f3f3f3"));
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadDataFootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.parseColor("#f3f3f3"));
        initView(context);
    }

    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.load_foot_tip_view, this);
        imgAnim = (ImageView) findViewById(R.id.refresh);
        drawable = (AnimationDrawable) imgAnim.getDrawable();
        move = (TextView) findViewById(R.id.handler_move);
    }

    @Override
    public void show(boolean show) {
        if (show == showing) {
            return;
        }
        showing = show;
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        lp.height = show ? LayoutParams.WRAP_CONTENT : 0;
        view.setLayoutParams(lp);
    }


    @Override
    public void callWhenNotAutoLoadMore(final XRefreshView xRefreshView) {
        move.setText("点击加载更多");
        move.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                xRefreshView.notifyLoadMore();
            }
        });
    }

    @Override
    public void onStateReady() {
        move.setText("上拉加载更多");
        drawable.start();
    }

    @Override
    public void onStateRefreshing() {
        move.setText("正在加载...");
        drawable.start();
        show(true);
    }

    @Override
    public void onReleaseToLoadMore() {
        move.setText("松开加载更多");
        drawable.start();
    }

    @Override
    public void onStateFinish(boolean success) {
        drawable.stop();
    }

    @Override
    public void onStateComplete() {
        //setVisibility(View.GONE);
    }


    @Override
    public boolean isShowing() {
        return showing;
    }

    @Override
    public int getFooterHeight() {
        return getMeasuredHeight();
    }


}
