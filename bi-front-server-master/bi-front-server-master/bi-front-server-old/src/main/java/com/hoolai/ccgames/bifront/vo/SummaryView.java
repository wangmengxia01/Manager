package com.hoolai.ccgames.bifront.vo;

import java.util.HashMap;
import java.util.Map;

public class SummaryView {
    public static class Finance {
        public String begin = "";
        public String end = "";
        public long mtl2Rake = 0;
        public long loginPay = 0;
        public long publicOut = 0;
        public long oldPokerRake = 0;
        public long newPokerRake = 0;
        public long zfbRake = 0;
        public long xuezhanRake = 0;
        public long xueliuRake = 0;
        public long fruitRake = 0;
        public long ddzRake = 0;
        public long twoMaRake = 0;
        public long pptRake = 0;
        public long mjdrChoujiang = 0;
        public long fishRake = 0;
        public long BRDZRake = 0;
        public long dogRake = 0;
        public long allRake = 0;
        public long rmb = 0;
        public long mtl2Rake1 = 0;
        public long loginPay1 = 0;
        public long publicOut1 = 0;
        public long oldPokerRake1 = 0;
        public long newPokerRake1 = 0;
        public long zfbRake1 = 0;
        public long xuezhanRake1 = 0;
        public long xueliuRake1 = 0;
        public long fruitRake1 = 0;
        public long ddzRake1 = 0;
        public long twoMaRake1 = 0;
        public long pptRake1 = 0;
        public long mjdrChoujiang1 = 0;
        public long fishRake1 = 0;
        public long dogRake1 = 0;
        public double allRake1 = 0;
        public long rmb1 = 0;
        public long Out = 0;
    }

    public static class InstallPay {

        public String begin = "";
        public String end = "";
        public String team = "";
        public String qudao = "";
        public String game = "";
        public long install = 0;
        public long oldPayPeople = 0;
        public long newPayPeople = 0;
        public long oldPayCount = 0;
        public long newPayCount = 0;
        public long oldPayMoney = 0;
        public long newPayMoney = 0;
        public long dau = 0;
        public long youxiao = 0;
        public long oldDau = 0;
        public double fufeilv = 0;
        public String qudaoleixing = "";
        public double ARPU = 0;
        public double ARPPU = 0;
    }
    public static class Install {
        public String begin = "";
        public String end = "";
        public long install = 0;
        public long shebei = 0;
        public long ip = 0;
        public long youxiao = 0;
        public long ios = 0;

        public long yuyangios = 0;
        public long huluios = 0;
        public long xiaomaiios = 0;
        public long huayaoios = 0;
        public long kuaileios = 0;
        public long baixiaoheiios = 0;
        public long jingjingios = 0;
        public long naonaoios = 0;
        public long duyaoios = 0;
        public long junjunios = 0;
        public long yuyangand = 0;
        public long xiaomaiand = 0;
        public long huayaoand = 0;
        public long kuaileand = 0;
        public long baixiaoheiand = 0;
        public long jingjingand = 0;
        public long naonaoand = 0;
        public long duyaoand = 0;
        public long junjunand = 0;
        public long huluand = 0;

        public Map<String , Long>      and   = new HashMap<>() ;

    }
    public static class baotu {

        public String begin = "";
        public String end = "";
        public long userHave = 0;
        public long userHave2 = 0;
        public long sellCount = 0;
        public long taskOut = 0;
        public long allOut = 0;
        public long duobaoUse = 0;
        public long duihuan = 0;
        public long gold = 0;
        public long people = 0;
    }
    public static class dantou {

        public String begin = "";
        public String end = "";
        public long diaoluo = 0;
        public long huodong = 0;
        public long baoxiang = 0;
        public long zongshiyong = 0;
        public long chazhi = 0;
        public long jiaoyi = 0;
        public long chongzhi = 0;
    }

    public static class zichong {

        public String begin = "";
        public String end = "";
        public long qudaoHao = 0;
        public long money = 0;
//        public String buyu = "";
//        public String zfb = "";
//        public String mtl = "";
//        public String ppt = "";
//        public String jiaoyi = "";
        public Map<Long , Long>      buyu   = new HashMap<>() ;
        public Map<Long , Long>      ppt    = new HashMap<>() ;
        public Map<Long , Long>      mtl    = new HashMap<>() ;
        public Map<Long , Long>      zfb    = new HashMap<>() ;
        public Map<Long , Long>      zengsong    = new HashMap<>() ;
        public String name = "";
    }

    public static class huafei {

        public String begin = "";
        public long out_1 = 0;
        public long use_1 = 0;
        public long sell_1 = 0;
        public long sent_1 = 0;
        public long out_2 = 0;
        public long use_2 = 0;
        public long sell_2 = 0;
        public long sent_2 = 0;
        public long out_5 = 0;
        public long use_5 = 0;
        public long sell_5 = 0;
        public long sent_5 = 0;
        public long out_10 = 0;
        public long use_10 = 0;
        public long sell_10 = 0;
        public long sent_10 = 0;
        public long out_20 = 0;
        public long use_20 = 0;
        public long sell_20 = 0;
        public long sent_20 = 0;
        public long out_50 = 0;
        public long use_50 = 0;
        public long sell_50 = 0;
        public long sent_50 = 0;
    }

    public static class zhoubao {

        public String begin = "";
        public String end = "";
        public long shouru = 0;
        public long huoyuezhanghao = 0;
        public long fufeizhanghao = 0;
        public long huoyuelaoyonghu = 0;
        public long huoyuelaoyonghufufei = 0;
        public long huoyuelaoyonghulishi = 0;
        public long arpu = 0;
        public long arppu = 0;
        public long xinzeng = 0;
        public long rijunzonghuoyue = 0;
        public long rijunchixuhuoyue = 0;
        public long IOSshouru = 0;
        public long ANDshouru = 0;
        public long LIANYUNshouru = 0;
        public long GUANWANGshouru = 0;
        public long LIANYUNxinzeng = 0;
        public long GUANWANGxinzeng = 0;
        public long IOSxinzeng = 0;
        public long ANDxinzeng = 0;
        public long shangyueZongshouru = 0;
        public long shangyueIOSxinjin = 0;
        public long shangyueIOSshouru = 0;
        public long shangyueANDxinzeng = 0;
        public long shangyueANDshouru = 0;

        public long shouru2 = 0;
        public long huoyue2 = 0;
        public long fufei2 = 0;
        public long huoyuefufei2 = 0;
        public long huoyuefufeilv2 = 0;
        public long huoyuelaoyonghu2 = 0;
        public long huoyuelaoyonghufufei2 = 0;
        public long huoyuelaoyonghuzhanghao2 = 0;
        public long huoyuelaoyonghufufeilv2 = 0;
        public long arpu2 = 0;
        public long arppu2 = 0;
        public long xinzeng2 = 0;
        public long rijunzonghuoyue2 = 0;
        public long rijunchixuhuoyue2 = 0;
        public long IOSshouru2 = 0;
        public long ANDshouru2 = 0;
        public long IOSxinzeng2 = 0;
        public long ANDxinzeng2 = 0;
        public long shangyueZongshouru2 = 0;
        public long shangyueIOSxinjin2 = 0;
        public long shangyueIOSshouru2 = 0;
        public long shangyueANDxinzeng2 = 0;
        public long shangyueANDshouru2 = 0;

    }
}
