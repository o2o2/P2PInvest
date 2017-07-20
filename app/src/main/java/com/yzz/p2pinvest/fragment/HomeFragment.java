package com.yzz.p2pinvest.fragment;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.yzz.p2pinvest.R;
import com.yzz.p2pinvest.bean.Image;
import com.yzz.p2pinvest.bean.Index;
import com.yzz.p2pinvest.bean.Product;
import com.yzz.p2pinvest.common.AppNetConfig;
import com.yzz.p2pinvest.common.BaseFragment;
import com.yzz.p2pinvest.ui.RoundProgress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Wookeibun on 2017/7/16.
 */

public class HomeFragment extends BaseFragment {


    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title_back)
    TextView tvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.tv_home_product)
    TextView tvHomeProduct;
    @Bind(R.id.roundPro_home)
    RoundProgress roundProHome;
    @Bind(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;

    Index index;
    private int currentProgress;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            roundProHome.setMax(100);
            for(int i = 0;i<currentProgress;i++){
                roundProHome.setProgress(i + 1);
                SystemClock.sleep(20);
                //强制重绘
//                roundProHome.invalidate();//只有主线程才能调用
                roundProHome.postInvalidate();//子,主线程都能调用
            }
        }
    };
    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl(){
        return AppNetConfig.INDEX;
//        return null;
    }


    @Override
    protected void initData(String content) {
        if(!TextUtils.isEmpty(content)){
            index = new Index();
            //解析json数据
            JSONObject jsonObject = JSON.parseObject(content);
            //解析json对象数据
            String proInfo = jsonObject.getString("proInfo");
            Product product = JSON.parseObject(proInfo, Product.class);
            //解析json数组数据
            String imageArr = jsonObject.getString("imageArr");
            List<Image> images = jsonObject.parseArray(imageArr, Image.class);
            index.product = product;
            index.images = images;
            //更新页面数据
            tvHomeProduct.setText(product.name);
            tvHomeYearrate.setText(product.yearRate + "%");
            //获取数据中的进度值
            roundProHome.setProgress(Integer.parseInt(index.product.progress));
            currentProgress = Integer.parseInt(index.product.progress);
//                roundProHome.setProgress(currentProgress);
            new Thread(runnable).start();

            //设置banner样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片地址集合
            ArrayList<String> imagesUrl = new ArrayList<String>(index.images.size());
            for (int i = 0; i < index.images.size(); i++) {
                imagesUrl.add(index.images.get(i).IMAURL);
            }
            banner.setImages(imagesUrl);
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.DepthPage);
            //设置标题集合（当banner样式有显示title时）
            String[] titles = new String[]{"分享砍学费", "人脉总动员", "想不到你是这样的App", "购物节，爱不单行"};
            banner.setBannerTitles(Arrays.asList(titles));
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(1500);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CENTER);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }

    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("首页");
        ivTitleSetting.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);
        }
    }
}
