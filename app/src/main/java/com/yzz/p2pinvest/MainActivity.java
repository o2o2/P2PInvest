package com.yzz.p2pinvest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yzz.p2pinvest.fragment.HomeFragment;
import com.yzz.p2pinvest.fragment.InvestFragment;
import com.yzz.p2pinvest.fragment.MeFragment;
import com.yzz.p2pinvest.fragment.MoreFragment;
import com.yzz.p2pinvest.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {
    private static final int WHAT_REST_BACK = 1;
    @Bind(R.id.fl_main)
    FrameLayout flMain;
    @Bind(R.id.iv_main_home)
    ImageView ivMainHome;
    @Bind(R.id.tv_main_home)
    TextView tvMainHome;
    @Bind(R.id.ll_main_home)
    LinearLayout llMainHome;
    @Bind(R.id.iv_main_invest)
    ImageView ivMainInvest;
    @Bind(R.id.tv_main_invest)
    TextView tvMainInvest;
    @Bind(R.id.ll_main_invest)
    LinearLayout llMainInvest;
    @Bind(R.id.iv_main_me)
    ImageView ivMainMe;
    @Bind(R.id.tv_main_me)
    TextView tvMainMe;
    @Bind(R.id.ll_main_me)
    LinearLayout llMainMe;
    @Bind(R.id.iv_main_more)
    ImageView ivMainMore;
    @Bind(R.id.tv_main_more)
    TextView tvMainMore;
    @Bind(R.id.ll_main_more)
    LinearLayout llMainMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //设置显示首页
        setSelect(0);
    }

    @OnClick({R.id.ll_main_home, R.id.ll_main_invest, R.id.ll_main_me, R.id.ll_main_more})
    public void showTab(View view) {
//        Toast.makeText(MainActivity.this,"ss",Toast.LENGTH_LONG).show();
        switch (view.getId()) {
            case R.id.ll_main_home:
                setSelect(0);
                break;
            case R.id.ll_main_invest:
                setSelect(1);
                break;
            case R.id.ll_main_me:
                setSelect(2);
                break;
            case R.id.ll_main_more:
                setSelect(3);
                break;
        }
    }
    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;
    FragmentTransaction ft;
    public void setSelect(int select) {
        FragmentManager fm=this.getSupportFragmentManager();
        ft=fm.beginTransaction();
        hideFragments();//隐藏所有fragment的显示
        resetTab();//重置ImageView和textView的显示状态
        switch (select) {
            case 0:
                if (homeFragment == null) {
                    homeFragment=new HomeFragment();
                    ft.add(R.id.fl_main, homeFragment);

                }
                //显示当前的Fragment
                ft.show(homeFragment);


                ivMainHome.setImageResource(R.drawable.bottom02);
                tvMainHome.setTextColor(UIUtils.getColor(R.color.home_back_selected));
                break;
            case 1:
                if (investFragment == null) {
                    investFragment=new InvestFragment();
                    ft.add(R.id.fl_main, investFragment);

                }
                ft.show(investFragment);
                ivMainInvest.setImageResource(R.drawable.bottom04);
                tvMainInvest.setTextColor(UIUtils.getColor(R.color.home_back_selected));
                break;
            case 2:
                if (meFragment == null) {
                    meFragment=new MeFragment();
                    ft.add(R.id.fl_main, meFragment);

                }
                ft.show(meFragment);
                ivMainMe.setImageResource(R.drawable.bottom06);
                tvMainMe.setTextColor(UIUtils.getColor(R.color.home_back_selected01));
                break;
            case 3:
                if (moreFragment == null) {
                    moreFragment=new MoreFragment();
                    ft.add(R.id.fl_main, moreFragment);

                }
                ft.show(moreFragment);
                ivMainMore.setImageResource(R.drawable.bottom08);
                tvMainMore.setTextColor(UIUtils.getColor(R.color.home_back_selected));
                break;


        }
        ft.commit();
    }

    private void resetTab() {
        ivMainHome.setImageResource(R.drawable.bottom01);
        ivMainInvest.setImageResource(R.drawable.bottom03);
        ivMainMe.setImageResource(R.drawable.bottom05);
        ivMainMore.setImageResource(R.drawable.bottom07);
        tvMainHome.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMainInvest.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMainMe.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMainMore.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
    }

    private void hideFragments() {
        if (homeFragment!=null){
            ft.hide(homeFragment);
        }
        if (investFragment!=null){
            ft.hide(investFragment);
        }
        if (meFragment!=null){
            ft.hide(meFragment);
        }
        if (moreFragment!=null){
            ft.hide(moreFragment);
        }
    }

    //重写onkeup()  实现连续点击两次退出
    private boolean flag=true;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_REST_BACK:
                    flag=true;//复原
            }
        }
    };
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK&&flag) {
            Toast.makeText(MainActivity.this,"再点击一次,退出当前应用",Toast.LENGTH_LONG).show();
            flag=false;
            handler.sendEmptyMessageDelayed(WHAT_REST_BACK,2000);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {//避免内存泄漏
        super.onDestroy();
//        handler.removeMessages(WHAT_REST_BACK);//移除指定id的所有的消息
        handler.removeCallbacksAndMessages(null);//移除所有未被执行的消息
    }
}
