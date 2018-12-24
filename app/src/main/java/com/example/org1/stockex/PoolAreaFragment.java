package com.example.org1.stockex;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import itf.InvokeInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoolAreaFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    Thread dataThread;
    private final int num = 3;
    boolean bool = true;
    BuyPoolViewFragment bvf = new BuyPoolViewFragment();
    SellPoolViewFragment svf = new SellPoolViewFragment();
    MatchPoolViewFragment mvf = new MatchPoolViewFragment();
    InvokeInterface buyInvokeInterface = bvf;
    InvokeInterface sellInvokeInterface = svf;
    InvokeInterface matchInvokeInterface = mvf;

    public PoolAreaFragment() {
        // Required empty public constructor
    }

    class getDataThread implements Runnable {
        public void run() {
            while (bool) {
                buyInvokeInterface.invokeBuy();
                sellInvokeInterface.invokeSell();
                matchInvokeInterface.invokeMatch();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bool = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pool_area, container, false);
        tabLayout = v.findViewById(R.id.tab);
        viewPager = v.findViewById(R.id.viewpager);
        pagerAdapter = new sS(getChildFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        bvf.setActivity(getActivity());
        svf.setActivity(getActivity());
        mvf.setActivity(getActivity());
        getDataThread getDataThread = new getDataThread();
        dataThread = new Thread(getDataThread);
        dataThread.start();
        return v;
    }

    class sS extends FragmentStatePagerAdapter {
        Fragment[] frag = {bvf, svf, mvf};

        public sS(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return frag[position];
        }

        @Override
        public int getCount() {
            return num;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Buying orders";
                case 1:
                    return "Selling orders";
                case 2:
                    return "Matched Orders";
            }
            return super.getPageTitle(position);
        }
    }
}
