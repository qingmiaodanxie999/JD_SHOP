package com.beicai.jd_shop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.beicai.jd_shop.adapter.HomeCategoryAdapter;
import com.beicai.jd_shop.adapter.decoration.DividerItemDecoration;
import com.beicai.jd_shop.entity.Banner;
import com.beicai.jd_shop.entity.Campaign;
import com.beicai.jd_shop.entity.HomeCampain;
import com.beicai.jd_shop.http.BaseCallBack;
import com.beicai.jd_shop.http.Contants;
import com.beicai.jd_shop.http.OkHttpHelper;
import com.beicai.jd_shop.http.SpotsCallBack;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by 王东 on 2017/1/12.
 */
public class HomeFragment extends Fragment {
    private Context mContext;
    private SliderLayout sliderLayout;
    private PagerIndicator mIndicator;
    private RecyclerView mRecyclerView;
    private HomeCategoryAdapter mAdapter;
    private Gson mGson = new Gson();
    private List<Banner> mBanners;
    private OkHttpHelper okHttpHelper;
    private List<HomeCampain> homeCampains;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, null);
        mContext = getActivity();
        sliderLayout = (SliderLayout) v.findViewById(R.id.slider);
        mIndicator = (PagerIndicator) v.findViewById(R.id.custom_indicator);
        okHttpHelper = OkHttpHelper.getInstance();
        requestImage();
        initRecyclerView(v);
        return v;
    }

    private void initRecyclerView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.home_rv);
        okHttpHelper.get(Contants.API.CAMPAIGN_HOME,
                new BaseCallBack<List<HomeCampain>>() {
                    @Override
                    public void onRequestBefore(Request request) {

                    }

                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onSuccess(Response response, List<HomeCampain> homeCampains) {
                        initData(homeCampains);
                    }

//            @Override
//            public void onSuccess(Response response, Object o) {
//                initData(homeCampains);
//            }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }

                    @Override
                    public void onResponse(Response response) {

                    }
                });
        /*mAdapter = new HomeCategoryAdapter(homeCampains);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration());*/
    }

    private void initData(List<HomeCampain> homeCampains) {
        this.homeCampains = homeCampains;
        /*List<HomeCategory> datas = new ArrayList<>();
        HomeCategory category = new HomeCategory("热门活动",R.mipmap.img_big_0,
                R.mipmap.img_0_small1,R.mipmap.img_0_small2);
        datas.add(category);
        category=new HomeCategory("智能生活",R.mipmap.img_big_1,
                R.mipmap.img_1_small1,R.mipmap.img_1_small2);
        datas.add(category);
        category=new HomeCategory("热门男装",R.mipmap.img_big_2,
                R.mipmap.img_2_small1,R.mipmap.img_2_small2);
        datas.add(category);
        category=new HomeCategory("旅游",R.mipmap.img_big_3,
                R.mipmap.img_3_small1,R.mipmap.img_3_small2);
        datas.add(category);
        category=new HomeCategory("家电",R.mipmap.img_big_4,
                R.mipmap.img_4_small1,R.mipmap.img_4_small2);
        datas.add(category);*/
        mAdapter = new HomeCategoryAdapter(homeCampains,mContext);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
        mAdapter.setOnCampaignClickListener(new HomeCategoryAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {
                Log.e("TAG","---CampaignClick--");
            }
        });
    }

    private void initSlider() {
        if (mBanners != null) {
            for (Banner banner : mBanners) {
                TextSliderView demoSlider = new TextSliderView(getActivity());
                demoSlider.description(banner.getDescription())
                        .image(banner.getImgUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                sliderLayout.addSlider(demoSlider);
                sliderLayout.setCustomIndicator(mIndicator);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());//设置自定义指示器
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);//设置转场效果
                sliderLayout.setDuration(3000);
                sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        Log.d(TAG, "onPageScrolled");//滚动中调用
                    }

                    @Override
                    public void onPageSelected(int position) {
                        Log.d(TAG, "onPageSelected");//点击时调用
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        Log.d(TAG, "onPageScrollStateChanged");//滚动状态改变时调用（开始动、停止动）
                    }
                });
            }
        }

//        demoSlider.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener(){
//
//            @Override
//            public void onSliderClick(BaseSliderView slider) {
//                Toast.makeText(HomeFragment.this.getActivity(), "新品推荐", Toast.LENGTH_SHORT).show();
//            }
//        });
//        sliderLayout.addSlider(demoSlider);
//        TextSliderView demoSlider2 = new TextSliderView(getActivity());
//        demoSlider2.description("时尚男装")
//                .image("http://101.200.167.75:8080/phoenixshop/img/banner/57ff4039N709820d2.jpg")
//                .setScaleType(BaseSliderView.ScaleType.Fit);
//        sliderLayout.addSlider(demoSlider2);
//        TextSliderView demoSlider3 = new TextSliderView(getActivity());
//        demoSlider3.description("家电秒杀")
//                .image("http://101.200.167.75:8080/phoenixshop/img/banner/582ae45dNa57e1349.jpg")
//                .setScaleType(BaseSliderView.ScaleType.Fit);
//        sliderLayout.addSlider(demoSlider3);
//
    }

    private void requestImage() {
//        String url = "http://101.200.167.75:8080/phoenixshop/campaign/recommend";
        String url = Contants.API.BANNER + "?type=1";  //phoenixshop/banner/query?type=1";
        okHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                Log.e("TAG", "banner-----------" + banners.size());
                mBanners = banners;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initSlider();
                    }
                });
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
        /*okHttpHelper.get(url, new BaseCallBack<List<Banner>>(getContext()) {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                Log.e("TAG","banner-----------"+banners.size());
                mBanners = banners;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initSlider();
                    }
                });
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });*/
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("type", "1")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        /*Request request = new Request.Builder()
                .url(url)
                .build();
              */
        client.newCall(request).enqueue(new Callback() {
            //请求网络出现不可恢复错误是调用该方法
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.e("TAG", "json====" + json);
                    Type type = new TypeToken<List<Banner>>() {
                    }.getType();
                    mBanners = mGson.fromJson(json, type);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initSlider();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onStart() {
        sliderLayout.startAutoCycle();
        super.onStart();
    }

    @Override
    public void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }
}
