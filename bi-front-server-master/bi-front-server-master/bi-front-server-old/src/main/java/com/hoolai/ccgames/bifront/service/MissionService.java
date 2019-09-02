package com.hoolai.ccgames.bifront.service;

import com.hoolai.ccgames.bifront.net.BiClient;

import java.util.Map;

/**
 * Created by hoolai on 2016/7/26.
 */
public class MissionService extends BaseService {

    public long getExp( String appid, long beg, long end, boolean isMobile, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select coalesce( sum( exp ), 0 ) " +
                " from t_mission_finish2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_ccgames = 'Y' " +
                "   and is_mobile = '" + boolString( isMobile ) + "';", c );
    }

    public long getHp( String appid, long beg, long end, boolean isMobile, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select coalesce( sum( hp ), 0 ) " +
                " from t_mission_finish2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_ccgames = 'Y' " +
                "   and is_mobile = '" + boolString( isMobile ) + "';", c );
    }

    public long getGold( String appid, long beg, long end, boolean isMobile, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                " from t_mission_finish2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_ccgames = 'Y' " +
                "   and is_mobile = '" + boolString( isMobile ) + "';", c );
    }

    public long getGold( String appid, long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                " from t_mission_finish2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_ccgames = 'Y';", c );
    }

    public Map< Long, Long > getGoldMap( String appid, long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryMapLongLong( "select type, sum( gold ) " +
                " from t_mission_finish2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_ccgames = 'Y' " +
                " group by type;", c );
    }

    public Map< Long, Long > getGoldMap( String appid, long beg, long end, boolean isMobile, BiClient c ) {
        return ServiceManager.getSqlService().queryMapLongLong( "select type, sum( gold ) " +
                " from t_mission_finish2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_ccgames = 'Y' " +
                "   and is_mobile = '" + boolString( isMobile ) + "' " +
                " group by type;", c );
    }

    public long getCount( String appid, long beg, long end, boolean isMobile, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select count( id ) " +
                " from t_mission_finish2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_ccgames = 'Y' " +
                "   and is_mobile = '" + boolString( isMobile ) + "';", c );
    }

    public long getPeople( String appid, long beg, long end, boolean isMobile, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                " from t_mission_finish2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_ccgames = 'Y' " +
                "   and is_mobile = '" + boolString( isMobile ) + "';", c );
    }

}
