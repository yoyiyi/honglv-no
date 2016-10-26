package com.yoyiyi.honglv.ui.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;
import com.yoyiyi.honglv.ui.activity.another.AnotherActivity;
import com.yoyiyi.honglv.ui.activity.another.HotActivity;
import com.yoyiyi.honglv.ui.adapter.main.MainAdapter;
import com.yoyiyi.honglv.ui.widget.NoScrollViewPager;
import com.yoyiyi.honglv.utils.TDevice;

import butterknife.BindView;

import static com.yoyiyi.honglv.R.id.toolbar;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabs;
    @BindView(R.id.home_view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;
    private long mBackPressedTime;
    private ImageView mIv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar();
        initToggleDrawer();
        initDrawerlayout();
        initFragment();
        initNavigationView();
        initSearchView();
    }

    private void initNavigationView() {
        // View view = mNavView.inflateHeaderView(R.layout.nav_header_main);
        View view = mNavView.getHeaderView(0);
        mIv = (ImageView) view.findViewById(R.id.imageView);
        RotateAnimation animation = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.imageview_rotate);
        mIv.setAnimation(animation);


    }

    private void initToolbar() {
        mToolbar.setTitle("红旅动漫");
    }

    private void initDrawerlayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
    }

    /**
     * 设置 侧滑动画
     */
    private void initToggleDrawer() {
        // mDrawerLayout.closeDrawer(GravityCompat.START);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    //初始化布局
    private void initFragment() {
        new MainAdapter(getSupportFragmentManager(), mSlidingTabs, mViewPager);

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.id_action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.id_action_search:
                //打开搜索页面
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_update) {
            Intent intent = new Intent(this, AnotherActivity.class);
            intent.putExtra("type", "WeekFragment");
            startActivity(intent);
        } else if (id == R.id.nav_hot) {
            TDevice.startActivity(MainActivity.this, HotActivity.class);
        } else if (id == R.id.nav_new) {
            Intent intent = new Intent(this, AnotherActivity.class);
            intent.putExtra("type", "RecentFragment");
            startActivity(intent);
        } else if (id == R.id.nav_rank) {
            Intent intent = new Intent(this, AnotherActivity.class);
            intent.putExtra("type", "RankFragment");
            startActivity(intent);

        } else if (id == R.id.nav_theme) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_about) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                exitApp();
            }
        }
        return true;
    }

    public void exitApp() {
        //获取当前时间
        long curTime = SystemClock.uptimeMillis();
        if ((curTime - mBackPressedTime) < (3 * 1000)) {
            finish();
        } else {
            //记录当前时间
            mBackPressedTime = curTime;
            //再次点击退出 提示
            TDevice.showToast("再次点击退出应用=￣ω￣=");
        }


    }

    //初始化SearchView
    protected void initSearchView() {
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);
        // mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
