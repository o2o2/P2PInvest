package com.yzz.p2pinvest.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.yzz.p2pinvest.R;
import com.yzz.p2pinvest.common.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wookeibun on 2017/7/16.
 */

public class InvestFragment extends BaseFragment {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title_back)
    TextView tvTitleBack;
    @Bind(R.id.iv_title_setting)
    ImageView ivTitleSetting;



    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {

    }

    public void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitleBack.setText("投资");
        ivTitleSetting.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
