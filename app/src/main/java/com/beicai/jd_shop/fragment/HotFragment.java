package com.beicai.jd_shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beicai.jd_shop.R;
import com.beicai.jd_shop.adapter.BaseAdapter;
import com.beicai.jd_shop.adapter.HWAdapter;
import com.beicai.jd_shop.adapter.decoration.DividerItemDecoration;
import com.beicai.jd_shop.entity.PageResult;
import com.beicai.jd_shop.entity.Wares;
import com.beicai.jd_shop.http.Contants;
import com.beicai.jd_shop.http.OkHttpHelper;
import com.beicai.jd_shop.http.SpotsCallBack;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by 王东 on 2017/1/12.
 */
public class HotFragment extends Fragment{
    private static final int STATE_NORMAL = 0;//正常状态
    private static final int STATE_REFRESH = 1;//刷新状态
    private static final int STATE_MORE = 2;//加载跟多状态
    private int state = STATE_NORMAL;//默认状态是正常状态

    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;
    private HWAdapter mAdapter;
    @BindView(R.id.hot_rv)
    RecyclerView mRecyclerView;//控件对应的ID
    @BindView(R.id.hot_mrl)
    MaterialRefreshLayout mRefreshLayout;
    private List<Wares> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hot, container,false);
        ButterKnife.bind(this,v);
//        mRecyclerView = (RecyclerView) v.findViewById(R.id.hot_rv);
        initRefreshDatas();
        getData();
        return v;
    }
    private void getData() {
//        String url = Contants.API.WARES_HOT + "?curPage=" + currPage + "&pageSize" + pageSize;
        String url = Contants.API.WARES_HOT+"?curPage="+currPage+"&pageSize="+pageSize;
        okHttpHelper.get(url, new SpotsCallBack<PageResult<Wares>>(getContext()) {

            @Override
            public void onSuccess(Response response, PageResult<Wares> waresPageResult) {
                Log.e("TAG","连接成功--------");
                datas = waresPageResult.getList();
                currPage = waresPageResult.getCurrentPage();
                totalPage = waresPageResult.getTotalPage();
                showData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.e("TAG","连接错误---------");
            }
        });
    }
    private void showData() {
        switch (state) {
            case STATE_NORMAL:
                mAdapter = new HWAdapter(getContext(),datas);
                mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        Toast.makeText(getActivity(),"OnItemClick>>>"+position,Toast.LENGTH_SHORT).show();
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                mRecyclerView.addItemDecoration(new DividerItemDecoration(
                        getContext(),DividerItemDecoration.VERTICAL_LIST
                ));
               /* mRecyclerView.setAdapter(new SimpleAdapter<Wares>(
                        getContext(),R.layout.item_hot_wares,datas) {

                    @Override
                    protected void convert(BaseViewHolder holder, Wares item) {
                        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.hot_wares_img_sdv);
                        draweeView.setImageURI(
                                Uri.parse(Contants.API.BASE_URL + item.getImgUrl())
                        );
                        holder.getTextView(R.id.hot_wares_title_tv).setText(item.getName());
                        holder.getTextView(R.id.hot_wares_price_tv).setText(item.getPrice()+"");
//                        holder.getButton(R.id.xx).setOnClickListener();
                    }
                });*/
                break;
            case STATE_REFRESH:
                mAdapter.clearData();
                mAdapter.addData(datas);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                mAdapter.addData(mAdapter.getItemCount(),datas);
                mRecyclerView.scrollToPosition(mAdapter.getItemCount());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }

    }
    private void initRefreshDatas(){
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if(currPage<=totalPage) {
                    loadMoreData();
                }else {
                    Toast.makeText(getContext(), "已经没有更多数据", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }
    private void refreshData() {
        currPage = 1;
        state = STATE_REFRESH;
        getData();
    }
    private void loadMoreData() {
        currPage += 1;
        state = STATE_MORE;
        getData();
    }
}
