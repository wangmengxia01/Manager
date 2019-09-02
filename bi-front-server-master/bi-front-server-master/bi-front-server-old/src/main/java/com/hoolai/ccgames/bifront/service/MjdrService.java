package com.hoolai.ccgames.bifront.service;

import com.hoolai.ccgames.bifront.net.BiClient;

public class MjdrService extends BaseService {

    public long getHandlePeople( long handleType, long beg, long end, boolean isMobile, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select count( A.user_id ) " +
                " from ( select distinct user_id " +
                "        from t_mjdr_handle " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "          and handle_type = " + handleType +
                "          and is_ccgames = 'Y' and is_robot = 'N' ) as A " +
                " inner join " +
                "      ( select user_id " +
                "        from t_install " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "          and is_mobile = '" + boolString( isMobile ) + "' ) as B " +
                " on A.user_id = B.user_id;", c );
    }
}
