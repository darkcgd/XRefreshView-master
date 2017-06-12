package com.andview.example.activity.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.andview.example.R;
import com.andview.example.recylerview.MultiItemAdapter;
import com.andview.example.recylerview.Person;
import com.andview.example.ui.CustomFooterView;
import com.andview.example.ui.CustomGifHeader;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshView.SimpleXRefreshListener;
import com.andview.refreshview.XRefreshViewFooter;

import java.util.ArrayList;
import java.util.List;

public class MultiItemRecyclerViewActivity extends Activity {
    RecyclerView recyclerView;
    MultiItemAdapter adapter;
    List<Person> personList = new ArrayList<Person>();
    XRefreshView xRefreshView;
    int lastVisibleItem = 0;
    //    GridLayoutManager layoutManager;
    LinearLayoutManager layoutManager;
    private boolean isBottom = false;
    private int mLoadCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recylerview2);
        xRefreshView = (XRefreshView) findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_test_rv);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MultiItemAdapter(personList);
        // 静默加载模式不能设置footerview
        recyclerView.setAdapter(adapter);
        //设置刷新完成以后，headerview固定的时间
        //xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setAutoLoadMore(true);
        // 设置静默加载模式
        //xRefreshView.setSilenceLoadMore(false);

        //xRefreshView.setCustomHeaderView(new CustomGifHeader(this));
        //xRefreshView.setCustomFooterView(new CustomFooterView(this));

        // adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));

        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);

        xRefreshView.setXRefreshViewListener(new SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRefreshView.stopRefresh();
                        for (int i = 0; i < 10; i++) {
                            adapter.insert(new Person("More ", mLoadCount + "21", getType()),
                                    adapter.getAdapterItemCount());
                        }
                    }
                }, 500);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mLoadCount++;
                        if (mLoadCount>100) {
                            xRefreshView.setLoadComplete(true);
                        } else {
                            // 刷新完成必须调用此方法停止加载
                            xRefreshView.stopLoadMore();
                            //如果数据加载完成以后不隐藏footerview,传入false
//                            xRefreshView1.stopLoadMore(false);
                        }
                        for (int i = 0; i < 10; i++) {
                            adapter.insert(new Person("More ", mLoadCount + "21", getType()),
                                    adapter.getAdapterItemCount());
                        }
                    }
                }, 1000);
            }
        });
        //如果想在数据加载完成以后不隐藏footerview则需要调用下面这行代码并传入false
//        xRefreshView1.setHideFooterWhenComplete(false);
        requestData();
    }

    public void requestData() {
        initData();
        adapter.setData(personList);
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            Person person = new Person("name" + i, "" + i, getType());
            personList.add(person);
        }
    }

    private boolean isLeft = true;

    private int getType() {
        isLeft = !isLeft;
        return isLeft ? 0 : 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.menu_clear:
                xRefreshView.setLoadComplete(false);
                break;
            case R.id.menu_refresh:
                xRefreshView.startRefresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}