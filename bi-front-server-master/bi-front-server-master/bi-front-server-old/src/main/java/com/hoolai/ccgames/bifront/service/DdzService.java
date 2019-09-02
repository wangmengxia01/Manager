package com.hoolai.ccgames.bifront.service;

import com.hoolai.ccgames.bifront.net.BiClient;

/**
 * Created by Administrator on 2016/9/12.
 */
public class DdzService extends BaseService {

    public long getchangciMult( long type, long level, long mult, long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select count( id ) " +
                " from t_ddz_multiple  " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and type ="+ type +" and level ="+ level +" and multiple ="+ mult +";", c );
    }

    public long getBujiaoPeople( long type, long level, long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select count( distinct inning_id ) " +
                " from t_ddz_multiple  " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and type ="+ type +" and level ="+ level +" and multiple=0;", c );
    }

    public long getChoujiangDate( long type, long level, long num, long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select count( id ) " +
                " from t_ddz_choujiang  " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and type ="+ type +" and level ="+ level +" and num ="+num  +" and is_mobile ='N';", c );
    }


}
