package com.hoolai.ccgames.bifront.service;

import com.hoolai.ccgames.bifront.net.BiClient;

/**
 * Created by Administrator on 2016/9/12.
 */
public class NewPokerService extends BaseService {

    public long getchangciPeople( String deskType,String APPID, long beg, long end, boolean is_robot, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                " from t_gdesk_play  " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and desk_type ='"+ deskType +"' and is_robot = 'N';", c );
    }


    public long getchangciNum( String deskType,String APPID, long beg, long end, boolean is_robot, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select count( distinct inning_id ) " +
                " from t_gdesk_play  " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and desk_type ='"+ deskType +"' and is_robot = 'N';", c );
    }

    public long getchangciRake( String deskType,String APPID, long beg, long end, boolean is_robot, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                " from t_gdesk_pump " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and desk_type ='"+ deskType +"' and is_robot = 'N';", c );
    }
}
