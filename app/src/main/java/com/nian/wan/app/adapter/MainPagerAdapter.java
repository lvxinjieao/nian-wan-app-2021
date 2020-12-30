package com.nian.wan.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nian.wan.app.fragment.HomeStoreFragment;
import com.nian.wan.app.fragment.PersonalCenter;
import com.nian.wan.app.fragment.fragment_racking.RackingFragment;
import com.nian.wan.app.fragment.main_game.GamePage;
import com.nian.wan.app.fragment.main_gift.Gift;
import com.nian.wan.app.fragment.main_home.HomePage;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private HomePage homePage;
    private GamePage game;
    private RackingFragment racking;
    private Gift homeGift;
    private HomeStoreFragment storeFragment;
    private PersonalCenter personalCenter;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            if (homePage == null) {
                homePage = new HomePage();
                return homePage;
            } else {
                return homePage;
            }
        } else if (position == 1) {
            if (game == null) {
                game = new GamePage();
                return game;
            } else {
                return game;
            }
        } else if (position == 2) {
            if (racking == null) {
                racking = new RackingFragment();
                return racking;
            } else {
                return racking;
            }
        } else if (position == 3) {
            if (homeGift == null) {
                homeGift = new Gift();
                return homeGift;
            } else {
                return homeGift;
            }
        } else if (position == 4) {
            if (storeFragment == null) {
                storeFragment = new HomeStoreFragment();
                return storeFragment;
            } else {
                return storeFragment;
            }
        } else if (position == 5) {
            if (personalCenter == null) {
                personalCenter = new PersonalCenter();
                return personalCenter;
            } else {
                return personalCenter;
            }
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }
}
