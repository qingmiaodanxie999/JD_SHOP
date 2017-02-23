package com.beicai.jd_shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beicai.jd_shop.R;
import com.beicai.jd_shop.adapter.CategoryAdapter;
import com.beicai.jd_shop.adapter.decoration.DividerItemDecoration;
import com.beicai.jd_shop.entity.Banner;
import com.beicai.jd_shop.entity.Category;
import com.beicai.jd_shop.http.Contants;
import com.beicai.jd_shop.http.OkHttpHelper;
import com.beicai.jd_shop.http.SpotsCallBack;
import com.cjj.MaterialRefreshLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by 王东 on 2017/1/12.
 */
public class CategoryFragment extends Fragment{
    @BindView(R.id.category_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.category_wares_rv)
    RecyclerView mWaresRecyclerView;
    @BindView(R.id.category_sl)
    SliderLayout mSliderLayout;
    @BindView(R.id.category_wares_mrl)
    MaterialRefreshLayout mRefreshLayout;
    private OkHttpHelper okHttpHelper;
    private CategoryAdapter mCategoryAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, v);
        requestCategoryData();
        requestBannerData();
        return v;
    }
    private void requestCategoryData() {
        okHttpHelper = OkHttpHelper.getInstance();
        okHttpHelper.get(Contants.API.CATEGORY_LIST,
                new SpotsCallBack<List<Category>>(getContext()) {

                    @Override
                    public void onSuccess(Response response, List<Category> categories) {
                        showCategoryData(categories);
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
    }
    private void showCategoryData(List<Category> categories) {
        mCategoryAdapter = new CategoryAdapter(getContext(), categories);
        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getContext(),DividerItemDecoration.VERTICAL_LIST));
    }
    private void showBannerData(List<Banner> banners) {
        if (banners != null) {
            for (Banner banner : banners) {
                DefaultSliderView sliderView = new DefaultSliderView(getActivity());
                sliderView//.description(banner.getDescription())
                        .image(banner.getImgUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(sliderView);
//                mSliderLayout.setCustomIndicator(mIndicator);
                mSliderLayout.setCustomAnimation(new DescriptionAnimation());//设置自定义指示器
                mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);//设置转场效果
                mSliderLayout.setDuration(3000);
            }
        }
    }
    private void requestBannerData() {
//        String url = "http://101.200.167.75:8080/phoenixshop/campaign/recommend";
        String url = Contants.API.BANNER + "?type=2";  //phoenixshop/banner/query?type=1";
        okHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
               showBannerData(banners);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
