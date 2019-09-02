package com.hoolai.ccgames.bifront.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class NewpokerView {

    public static class jichu {
        public String    begin              = "";
        public String    end                = "";
        public long      chujiPeople        = 0 ;
        public long      chujiNum           = 0 ;
        public long      chujiRake          = 0 ;
        public long      zhongjiPeople      = 0 ;
        public long      zhongjiNum         = 0 ;
        public long      zhongjiRake        = 0 ;
        public long      gaojiPeople        = 0 ;
        public long      gaojiNum           = 0 ;
        public long      gaojiRake          = 0 ;
        public long      dashiPeople        = 0 ;
        public long      dashiNum           = 0 ;
        public long      dashiRake          = 0 ;
        public long      chaojiPeople       = 0 ;
        public long      chaojiNum          = 0 ;
        public long      chaojiRake         = 0 ;
        public long      zhizunPeople       = 0 ;
        public long      zhizunNum          = 0 ;
        public long      zhizunRake         = 0 ;

        public long      zongRake          = 0 ;

    }

    public static class people{
        public String       begin              = "";
        public String       end                = "";
        public List< Long > inningPeople       = new LinkedList<>(); // 手数玩家

    }

    public static class huizong {
        public String    begin              = "";
        public String    end                = "";
        public long      People        = 0 ;
        public long      Rake           = 0 ;
        public long      shouru        = 0 ;
    }
    public static class online{
        public String       begin              = "";
        public String       end                = "";
        public List< Long > online             = new LinkedList<>(); // 手数玩家

    }

}
