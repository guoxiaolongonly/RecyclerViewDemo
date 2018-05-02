package com.standard.myapplication;

import android.util.Pair;

import com.standard.myapplication.cusotomdivider.CustomDividerActivity;
import com.standard.myapplication.customlayoutmanager.CustomLayoutManagerActivity;
import com.standard.myapplication.normalconfig.GridLayoutManagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @function <写一个路由>
 * @date: 2018/5/2 09:18
 */

public class ConstantRouter {


    public static List<Pair<String, Class>> getRouterList() {
        List<Pair<String, Class>> routerList = new ArrayList<>();
        routerList.add(new Pair("默认Manager和默认分割线", GridLayoutManagerActivity.class));
        routerList.add(new Pair("自定义ItemDivider", CustomDividerActivity.class));
        routerList.add(new Pair("自定义LayoutManager", CustomLayoutManagerActivity.class));
        return routerList;
    }
}
