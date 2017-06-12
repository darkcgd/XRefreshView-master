package com.andview.example.ui;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.example.R;
import com.andview.refreshview.callback.IHeaderCallBack;

public class LoadDataHeadView extends LinearLayout implements IHeaderCallBack {
    private ImageView imgAnim;
    private TextView move, time;
    AnimationDrawable drawable;
    private String refrestime;

    public LoadDataHeadView(Context context) {
        super(context);
        //setBackgroundColor(Color.parseColor("#f3f3f3"));
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadDataHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.load_head_tip_view, this);
        imgAnim = (ImageView) findViewById(R.id.refresh);
        move = (TextView) findViewById(R.id.handler_move);
        time = (TextView) findViewById(R.id.move_time);
        drawable = (AnimationDrawable) imgAnim.getDrawable();
        refrestime = TimeFriendUitls.getToadytime(0);
        time.setText("最后更新：" + TimeFriendUitls.showTime(refrestime + ""));
    }

    public void setRefreshTime(long lastRefreshTime) {
        time.setText("最后更新：" + TimeFriendUitls.showTime(refrestime + ""));
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateNormal() {
        move.setText(R.string.xrefreshview_header_hint_normal);
        drawable.start();
    }

    @Override
    public void onStateReady() {
        move.setText(R.string.xrefreshview_header_hint_ready);
        drawable.start();
    }

    @Override
    public void onStateRefreshing() {
        refrestime = TimeFriendUitls.getToadytime(0);
        move.setText(R.string.xrefreshview_header_hint_ready);
        drawable.start();
    }

    @Override
    public void onStateFinish(boolean success) {
        move.setText(success ? "加载完成" :"暂无数据更新");
        drawable.stop();
    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY, int deltaY) {
        //
    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
