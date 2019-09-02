package com.hoolai.ccgames.bifront.vo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/19.
 */
public class PropsView {

    public static class ZongLiang {
        public String    begin            = "";
        public String    end              = "";
        public Map<Long , Long>      daoju   = new HashMap<>() ;

    }

    public static class maifen {
        public String    begin            = "";
        public long      zhaohuanID        = 0 ;
        public long      maiId     = 0 ;
        public long      gold        = 0 ;
        public String    maiName            = "";
        public String    zhaohuanName           = "";
    }
    public static class BuyRank {
        public String    begin      = "";
        public String    end        = "";
        public long userId = 0;
        public String userName = "";
        public String itemName = "";
        public long BuyCount = 0;
        public long baishenCount = 0;
        public long count = 0;
    }
    public static class BaiShen {
        public String    begin      = "";
        public String    end        = "";
        public long People= 0;
        public Map<Long , Long> baishenDaoju   = new HashMap<>() ;
        public Map<Long , Long>      suijiDaoju2   = new HashMap<>() ;
    }

    public static class Send {
        public String    begin      = "";
        public String    end        = "";
        public long      sendID     = 0 ;    //赠送I者D
        public long      getID      = 0 ;    //被赠送者ID
        public String      props    = "" ;    //道具
        public long      count      = 0 ;    //数量
        public long      itemID      = 0 ;    //数量
        public String      time     = "" ;    //赠送时间

    }

    public static class Huafei {
        public String    begin      = "";
        public String    end        = "";

        public long      userID     = 0 ;    //使用者ID
        public String    card       = "" ;    //1
        public String    time       = "";
        public String    phonenumber  = "";
        public String    appid  = "";

        public long      card1            = 0 ;    //一元话费
        public long      card2            = 0 ;    //2元
        public long      card5            = 0 ;    //五元
        public long      card10           = 0 ;    //十元
        public long      card20           = 0 ;    //二十元
        public long      card50           = 0 ;    //五十元
        public long      card100          = 0 ;    // 一百元
        public long      card200          = 0 ;    //二百元


    }
    public static class HuafeiOut {
        public String    begin      = "";
        public String    end        = "";

        public long      card1            = 0 ;    //一元话费
        public long      card2            = 0 ;    //2元
        public long      card5            = 0 ;    //五元
        public long      card10           = 0 ;    //十元
        public long      card20           = 0 ;    //二十元
        public long      card50           = 0 ;    //五十元
        public long      card100          = 0 ;    // 一百元
        public long      card200          = 0 ;    // 一百元
    }

    public static class cardfrom {
        public String    begin       = "";
        public String    end        = "";

        public long      card1            = 0 ;    //一元话费
        public long      card2            = 0 ;    //2元
        public long      card5            = 0 ;    //五元
        public long      card10           = 0 ;    //十元
        public long      card20           = 0 ;    //二十元
        public long      card50           = 0 ;    //五十元
        public long      card100          = 0 ;    // 一百元
    }

    public static class BiShang {
        public String begin = "";
        public long user_id= 0;
        public long qingtongSHOU = 0;
        public long baiyinSHOU = 0;
        public long huangjinSHOU = 0;
        public long beikeSHOU = 0;
        public long qingtongCHU = 0;
        public long baiyinCHU = 0;
        public long huangjinCHU = 0;
        public long beikeCHU = 0;
    }

}
