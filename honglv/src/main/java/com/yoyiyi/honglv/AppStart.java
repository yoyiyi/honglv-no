package com.yoyiyi.honglv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.yoyiyi.honglv.ui.activity.MainActivity;
import com.yoyiyi.honglv.utils.TDevice;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoyiyi on 2016/10/20.
 */
public class AppStart extends AppCompatActivity {
    @BindView(R.id.app_start)
    RelativeLayout mAppStart;
    @BindView(R.id.iv_app_start)
    ImageView mIvAppStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        ButterKnife.bind(this);
        ViewPropertyAnimator
                .animate(mIvAppStart)
                .setDuration(1000)
                .alpha(1f)
                .start();
        mAppStart.postDelayed(() -> startMainActivity(), 3000);

    }

    private void startMainActivity() {
        TDevice.startActivity(this, MainActivity.class);
        finish();
    }
}
