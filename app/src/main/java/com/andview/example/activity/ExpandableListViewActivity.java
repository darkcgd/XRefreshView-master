package com.andview.example.activity;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.example.R;
import com.andview.example.ui.CustomFooterView;
import com.andview.example.ui.CustomGifHeader;
import com.andview.example.ui.LoadDataFootView;
import com.andview.example.ui.LoadDataHeadView;
import com.andview.example.ui.NoMoreDataFooterView;
import com.andview.example.ui.smileyloadingview.SmileyHeaderView;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListViewActivity extends Activity {
    private List<String> groupArray;
    private  List<List<String>> childArray;
    private  ExpandableAdapter expandableAdapter;

    private XRefreshView refreshView;
    public static long lastRefreshTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);
        groupArray = new ArrayList<String>();
        childArray = new  ArrayList<List<String>>();

        refreshView = (XRefreshView) findViewById(R.id.custom_view);

        getData();
        ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
        expandableAdapter = new ExpandableAdapter(this);
        expandableListView.setAdapter(expandableAdapter);

        refreshView.setPinnedTime(10);
        // 设置是否可以下拉刷新
        refreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        refreshView.setPullLoadEnable(true);
        // 设置上次刷新的时间
        refreshView.restoreLastRefreshTime(lastRefreshTime);
        //当下拉刷新被禁用时，调用这个方法并传入false可以不让头部被下拉
        //refreshView.setMoveHeadWhenDisablePullRefresh(true);
        // 设置时候可以自动刷新
        refreshView.setAutoRefresh(true);
        refreshView.setAutoLoadMore(true);

        //refreshView.setCustomHeaderView(new SmileyHeaderView(this));
        refreshView.setCustomHeaderView(new LoadDataHeadView(this));
        refreshView.setCustomFooterView(new LoadDataFootView(this));

//		refreshView.setOnBottomLoadMoreTime(new OnBottomLoadMoreTime() {
//			@Override
//			public boolean isBottom() {
//				return false;
//			}
//		});

        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.stopRefresh();
                        lastRefreshTime = refreshView.getLastRefreshTime();
                        getData();
                        expandableAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        refreshView.stopLoadMore();
                        getData();
                        expandableAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
                if (direction > 0) {
                    toast("下拉");
                } else {
                    toast("上拉");
                }
            }
        });
        refreshView.setOnAbsListViewScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogUtils.i("onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                LogUtils.i("onScroll");
            }
        });
    }

    public void toast(String msg) {
        Toast.makeText(ExpandableListViewActivity.this, msg, Toast.LENGTH_SHORT).show();
    }


    int page = 1;

    private void getData() {
        groupArray.add("第1行" );
        groupArray.add("第2行" );
        groupArray.add("第3行" );
        groupArray.add("第4行" );

        List<String> tempArray = new  ArrayList<String>();
        tempArray.add("AAAAAAAAAAAAAA" );
        tempArray.add("BBBBBBBBBBBBBB" );
        tempArray.add("CCCCCCCCCCCCCC" );
        tempArray.add("DDDDDDDDDDDDDD" );
        tempArray.add("EEEEEEEEEEEEEE" );
        tempArray.add("FFFFFFFFFFFFFF" );

        for (int  index = 0 ; index <groupArray.size(); ++index)
        {
            childArray.add(tempArray);
        }
    }


    class  ExpandableAdapter extends BaseExpandableListAdapter
    {
        Activity activity;

        public  ExpandableAdapter(Activity a)
        {
            activity = a;
        }
        public  Object getChild(int  groupPosition, int  childPosition)
        {
            return  childArray.get(groupPosition).get(childPosition);
        }
        public  long  getChildId(int  groupPosition, int  childPosition)
        {
            return  childPosition;
        }
        public  int  getChildrenCount(int  groupPosition)
        {
            return  childArray.get(groupPosition).size();
        }
        public View getChildView(int  groupPosition, int  childPosition,
                                 boolean  isLastChild, View convertView, ViewGroup parent)
        {
            String string = childArray.get(groupPosition).get(childPosition);
            return  getGenericView(string);
        }
        // group method stub
        public  Object getGroup(int  groupPosition)
        {
            return  groupArray.get(groupPosition);
        }
        public  int  getGroupCount()
        {
            return  groupArray.size();
        }
        public  long  getGroupId(int  groupPosition)
        {
            return  groupPosition;
        }
        public  View getGroupView(int  groupPosition, boolean  isExpanded,
                                  View convertView, ViewGroup parent)
        {
            String string = groupArray.get(groupPosition);
            return  getGenericView(string);
        }
        // View stub to create Group/Children 's View
        public TextView getGenericView(String string)
        {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams layoutParams = new  AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 200 );
            TextView text = new  TextView(activity);
            text.setLayoutParams(layoutParams);
            // Center the text vertically
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            text.setPadding(100 , 0 , 0 , 0 );
            text.setText(string);
            return  text;
        }
        public  boolean  hasStableIds()
        {
            return  false ;
        }
        public  boolean  isChildSelectable(int  groupPosition, int  childPosition)
        {
            return  true ;
        }
    }
}
