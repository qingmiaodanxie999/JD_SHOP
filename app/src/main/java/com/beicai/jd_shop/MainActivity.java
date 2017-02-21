package com.beicai.jd_shop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.beicai.jd_shop.entity.Tab;
import com.beicai.jd_shop.fragment.CartFragment;
import com.beicai.jd_shop.fragment.CategoryFragment;
import com.beicai.jd_shop.fragment.HomeFragment;
import com.beicai.jd_shop.fragment.HotFragment;
import com.beicai.jd_shop.fragment.MineFragment;
import com.beicai.jd_shop.widget.FragmentTabHost;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FragmentTabHost mTabHost;
    List<Tab> mTabs=new ArrayList<>(5);
    LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
    }
    private void initTab() {
        Tab tab_home = new Tab(HomeFragment.class,R.mipmap.icon_home,R.string.home);
        Tab tab_hot= new Tab(HotFragment.class,R.mipmap.icon_hot,R.string.hot);
        Tab tab_category = new Tab(CategoryFragment.class,R.mipmap.icon_discover,R.string.category);
        Tab tab_mine = new Tab(MineFragment.class,R.mipmap.icon_user,R.string.mine);
        Tab tab_cart = new Tab(CartFragment.class,R.mipmap.icon_cart,R.string.cart);
        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_category);
        mTabs.add(tab_cart);
        mTabs.add(tab_mine);

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

        for (Tab tab:mTabs){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }
        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.tab_indicator_icon_iv);
        TextView text = (TextView) view.findViewById(R.id.tab_indicator_title_tv);
        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }
}
