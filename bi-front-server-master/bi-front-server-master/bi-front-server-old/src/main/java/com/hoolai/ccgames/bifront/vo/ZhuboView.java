package com.hoolai.ccgames.bifront.vo;

import com.hoolai.ccgames.skeleton.utils.PropUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


public class ZhuboView {

    public static List<String> loadFromFile(String fileName) throws Exception {
        List<String> rv = new LinkedList<>();
        InputStream is = PropUtil.getInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = reader.readLine()) != null) {
            rv.add(line);
        }
        return rv;
    }

    public static void init() throws Exception {
        huayaoList = loadFromFile("/zhubo_huayao.txt");
        xiaomaiList = loadFromFile("/zhubo_xiaomai.txt");
        xiaojingList = loadFromFile("/zhubo_xiaojing.txt");
        yongleList = loadFromFile("/zhubo_yongle.txt");
        yuyangList = loadFromFile("/zhubo_yuyang.txt");
        baixiaoheiList = loadFromFile("/zhubo_baixiaohei.txt");
        naonaoList = loadFromFile("/zhubo_naonao.txt");
        duyaoList = loadFromFile("/zhubo_duyao.txt");
        junjunList = loadFromFile("/zhubo_junjun.txt");
    }

    public static List<String> huayaoList;
    public static List<String> duyaoList;
    public static List<String> xiaomaiList;
    public static List<String> xiaojingList;
    public static List<String> yongleList;
    public static List<String> yuyangList;
    public static List<String> baixiaoheiList;
    public static List<String> naonaoList;
    public static List<String> junjunList;

}

