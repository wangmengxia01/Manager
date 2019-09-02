package com.hoolai.ccgames.bifront.service;

import com.hoolai.ccgames.bifront.net.BiClient;

import java.util.List;
import java.util.Map;

public class CommonService extends BaseService {

    public Map< Long, String > getMapIdFw( String tableName, long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryMapLongString( "select " + tableName + ".user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                " from " + tableName +
                " left join t_install " +
                " using( user_id ) " +
                " where " + beg + " <= " + tableName + ".timestamp and " + tableName + ".timestamp < " + end +
                "  ;", c );
    }

    public Map< Long, String > getMapIdAppId( String tableName, long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryMapLongString( "select " + tableName + ".user_id, coalesce( t_install.appid, '100632434' ) as fw " +
                " from " + tableName +
                " left join t_install " +
                " using( user_id ) " +
                " where " + beg + " <= " + tableName + ".timestamp and " + tableName + ".timestamp < " + end +
                "  ;", c );
    }

    public long getInstall(long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( id ) " +
                " from t_install " +
                " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
    }

    public long getInstall(long beg, long end, boolean isMobile, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( id ) " +
                " from t_install " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and is_mobile = '" + boolString(isMobile) + "';", c);
    }

    public long getInstall(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( id ) " +
                " from t_install " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "';", c);
    }

    public Map<String, Long> getInstallMap(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong("select from_where, count( id ) " +
                " from t_install " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "'" +
                " group by from_where;", c);
    }

    public Map< Long, String > getFwMap( String appid, long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryMapLongString("select user_id, from_where " +
                " from t_install " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "';", c);
    }

    public long getInstall(String appid, long beg, long end, boolean isMobile, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( id ) " +
                " from t_install " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_mobile = '" + boolString(isMobile) + "';", c);
    }

    public long getPayPeople(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                " from t_pay " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "';", c);
    }

    public List< Long > getPayUserList( String appid, long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryListLong( "select distinct user_id " +
                " from t_pay " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "';", c);
    }

    public List< String > getPossessionTimeUserList( String appid, long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryListString( "select timestamp, user_id " +
                " from t_possession " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "';", c);
    }


    public Map<String, Long> getPayPeopleMap(String appid, long beg, long end, boolean isOld, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong("select A.fw, count( distinct A.user_id ) " +
                " from ( select t_pay.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_pay " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " + beg + " <= t_pay.timestamp and t_pay.timestamp < " + end +
                "          and t_pay.appid = '" + appid + "' " +
                "          and " + (isOld ? ("coalesce( t_install.timestamp, 0 ) < " + beg) : (beg + " <= coalesce( t_install.timestamp, 0 ) and coalesce( t_install.timestamp, 0 ) < " + end)) + " ) as A " +
                " group by A.fw;", c);
    }

    public Map<String, Long> getPayPeopleMap(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, count( distinct A.user_id ) " +
                " from ( select t_pay.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_pay " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " + beg + " <= t_pay.timestamp and t_pay.timestamp < " + end +
                "          and t_pay.appid = '" + appid + "' ) as A " +
                " group by A.fw;", c );
    }
    public Map<String, Long> getPayPeopleMap(String appid, long beg, long end, long endTime , BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, count( distinct A.user_id ) " +
                " from ( select t_pay.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_pay " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where t_install.timestamp > " + beg + " and t_install.timestamp <= " + end +
                "        and " + beg + " <= t_pay.timestamp and t_pay.timestamp < " + endTime +
                "          and t_pay.appid = '" + appid + "' ) as A " +
                " group by A.fw;", c );
    }

    public Map< Long, String > getPayPeopleMap(String appid, long installBeg, long installEnd, long dauBeg, long dauEnd, BiClient c) {
//        if (appid.equals("264"))
//        {
//            logger.debug("select A.fw, count( distinct A.user_id ) " +
//                    " from ( select t_pay.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
//                    "        from t_pay " +
//                    "        left join t_install " +
//                    "        using( user_id ) " +
//                    "        where " +  installBeg + " <= t_install.timestamp and t_install.timestamp < " + installEnd +
//                    "          and " + dauBeg + " <= t_pay.timestamp and t_pay.timestamp < " + dauEnd +
//                    "          and t_pay.appid = '" + appid + "' ) as A " +
//                    " group by A.fw;");
//        }
        return ServiceManager.getSqlService().queryMapLongString( "select t_pay.timestamp as ts, t_pay.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_pay " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " +  installBeg + " <= t_install.timestamp and t_install.timestamp < " + installEnd +
                "          and " + dauBeg + " <= t_pay.timestamp and t_pay.timestamp < " + dauEnd +
                "          and t_pay.appid = '" + appid + "';", c );
    }


    public long getPayCount(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( id ) " +
                " from t_pay " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "';", c);
    }

    public Map<String, Long> getPayCountMap(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, count( A.id ) " +
                " from ( select t_pay.id as id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_pay " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " + beg + " <= t_pay.timestamp and t_pay.timestamp < " + end +
                "          and t_pay.appid = '" + appid + "' ) as A " +
                " group by A.fw;", c );

//        return ServiceManager.getSqlService().queryMapStringLong("select C.fw, count( C.user_id ) " +
//                " from ( select B.user_id as user_id, coalesce( B.from_where, \"TENCENT\" ) as fw " +
//                "        from ( select user_id" +
//                "               from t_pay " +
//                "               where " + beg + " <= timestamp and timestamp < " + end +
//                "                 and appid = '" + appid + "' ) as A " +
//                "        left join " +
//                "             ( select user_id, from_where " +
//                "               from t_install ) as B " +
//                "        on A.user_id = B.user_id ) as C " +
//                " group by C.fw;", c);
    }

    public Map<String, Long> getPayMoneyMap(String appid, long installBeg, long installEnd, long payBeg, long payEnd, BiClient c) {
        if(appid.equals("264"))
        {
            logger.debug("select A.fw, sum( A.money2 + A.money3 ) " +
                    " from ( select t_pay.user_id as user_id, t_pay.money2 as money2,t_pay.money3 as money3, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                    "        from t_pay " +
                    "        left join t_install " +
                    "        using( user_id ) " +
                    "        where " + payBeg + " <= t_pay.timestamp and t_pay.timestamp < " + payEnd +
                    "          and " + installBeg + " <= t_install.timestamp and t_install.timestamp < " + installEnd +
                    "          and t_pay.appid = '" + appid + "' ) as A " +
                    " group by A.fw;");
        }
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, sum( A.money2 + A.money3 ) " +
                " from ( select t_pay.user_id as user_id, t_pay.money2 as money2,t_pay.money3 as money3, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_pay " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " + payBeg + " <= t_pay.timestamp and t_pay.timestamp < " + payEnd +
                "          and " + installBeg + " <= t_install.timestamp and t_install.timestamp < " + installEnd +
                "          and t_pay.appid = '" + appid + "' ) as A " +
                " group by A.fw;", c );
    }

    public Map<String, Long> getPayCountMap(String appid, long beg, long end, boolean isOld, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong("select A.fw, count( A.id ) " +
                " from ( select t_pay.id as id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_pay " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " + beg + " <= t_pay.timestamp and t_pay.timestamp < " + end +
                "          and t_pay.appid = '" + appid + "'" +
                "          and " + ( isOld ? ( "coalesce( t_install.timestamp, 0 ) < " + beg ) : ( beg + " <= coalesce( t_install.timestamp, 0 ) and coalesce( t_install.timestamp, 0 ) < " + end ) ) + " ) as A " +
                " group by A.fw;", c);
    }

    public long getPayMoney(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( money2 + money3 ), 0 ) " +
                " from t_pay " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "';", c);
    }

    public long getPayMoney(String appid, long beg, long end, boolean isMobile, boolean isOld, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( A.money2 + A.money3 ), 0 ) " +
                " from ( select user_id, money2, money3 " +
                "        from t_pay " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "          and appid = '" + appid + "'" +
                "          and is_mobile = '" + boolString(isMobile) + "' ) as A " +
                (isOld ? "left join" : "inner join") +
                "      ( select user_id " +
                "        from t_install " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "          and appid = '" + appid + "' and is_mobile = '" + boolString(isMobile) + "' ) as B " +
                " on A.user_id = B.user_id " +
                (isOld ? "where B.user_id is null;" : ";"), c);
    }
    public long getPayMoney( long beg, long end, boolean isMobile, boolean isOld, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( A.money2 + A.money3 ), 0 ) " +
                " from ( select user_id, money2, money3 " +
                "        from t_pay " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "      and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee' ) as A " +
                (isOld ? "left join" : "inner join") +
                "      ( select user_id " +
                "        from t_install " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "       and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee' ) as B " +
                " on A.user_id = B.user_id " +
                (isOld ? "where B.user_id is null;" : ";"), c);
    }


    public Map<String, Long> getPayMoneyMap(String appid, long beg, long end, boolean isOld, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, sum( A.money ) " +
                " from ( select ( t_pay.money2 + t_pay.money3 ) as money, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_pay " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " + beg + " <= t_pay.timestamp and t_pay.timestamp < " + end +
                "          and t_pay.appid = '" + appid + "' " +
                "          and " + ( isOld ? ( "coalesce( t_install.timestamp, 0 ) < " + beg ) : ( beg + " <= coalesce( t_install.timestamp, 0 ) and coalesce( t_install.timestamp, 0 ) < " + end ) ) + " ) as A " +
                " group by A.fw;", c);
    }
    public Map<String, Long> getPayMoneyMap(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, sum( A.money ) " +
                " from ( select ( t_pay.money2 + t_pay.money3 ) as money, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_pay " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " + beg + " <= t_pay.timestamp and t_pay.timestamp < " + end +
                "          and t_pay.appid = '" + appid + "' ) as A " +
                " group by A.fw;", c );

//        return ServiceManager.getSqlService().queryMapStringLong("select C.fw, sum( C.money ) " +
//                " from ( select A.money as money, coalesce( B.from_where, \"TENCENT\" ) as fw " +
//                "        from ( select user_id, money2 + money3 as money" +
//                "               from t_pay " +
//                "               where " + beg + " <= timestamp and timestamp < " + end +
//                "                 and appid = '" + appid + "' ) as A " +
//                "        left join " +
//                "             ( select user_id, from_where " +
//                "               from t_install ) as B " +
//                "        on A.user_id = B.user_id ) as C " +
//                " group by C.fw;", c);
    }

    public Map<Long, Long> getPayItemMap(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryMapLongLong("select item_id, sum( item_count ) " +
                " from t_pay " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' " +
                " group by item_id;", c);
    }
    public Map<String, Long> getYouXiaoInstallMap(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong("select from_where, count( distinct CONCAT( ip , remark)) " +
                " from t_install " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' " +
                " group by from_where;", c);
    }
    public long getDau(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                " from t_dau " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "';", c);
    }

    public Map<String, Long> getDauMap(String appid, long beg, long end, boolean isOld, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, count( distinct A.user_id ) " +
                " from ( select t_dau.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_dau " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " + beg + " <= t_dau.timestamp and t_dau.timestamp < " + end +
                "        and t_dau.appid = '" + appid + "' " +
                "        and " + ( isOld ? ( "coalesce( t_install.timestamp, 0 ) < " + beg ) : ( beg + " <= coalesce( t_install.timestamp, 0 ) and coalesce( t_install.timestamp, 0 ) < " + end ) ) + " ) as A " +
                " group by A.fw;", c);
    }

    public Map<String, Long> getDauMap(String appid, long installBeg, long installEnd, long dauBeg, long dauEnd, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, count( distinct A.user_id ) " +
                " from ( select t_dau.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_dau " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " + dauBeg + " <= t_dau.timestamp and t_dau.timestamp < " + dauEnd +
                "          and " + installBeg + " <= t_install.timestamp and t_install.timestamp < " + installEnd +
                "          and t_dau.appid = '" + appid + "' ) as A " +
                " group by A.fw;", c );
    }

    public Map<String, Long> getPayDauMap(String appid, long installBeg, long installEnd, long dauBeg, long dauEnd, BiClient c) {
//        if(appid.equals("264"))
//        {
//            logger.debug( "select A.fw, count( distinct A.user_id ) " +
//                    " from ( select t_dau.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
//                    "        from t_dau " +
//                    "        left join t_install " +
//                    "        on t_dau.user_id = t_install.user_id " +
//                    "        left join t_pay " +
//                    "        on t_pay.user_id = t_install.user_id " +
//                    "        where " + dauBeg + " <= t_dau.timestamp and t_dau.timestamp < " + dauEnd +
//                    "          and " + installBeg + " <= t_install.timestamp and t_install.timestamp < " + installEnd +
//                    "          and " + installBeg + " <= t_pay.timestamp and t_pay.timestamp < " + installEnd +
//                    "          and t_dau.appid = '" + appid + "' ) as A " +
//                    " group by A.fw;");
//        }
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, count( distinct A.user_id ) " +
                " from ( select t_dau.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_dau " +
                "        left join t_install " +
                "        on t_dau.user_id = t_install.user_id " +
                "        left join t_pay " +
                "        on t_pay.user_id = t_install.user_id " +
                "        where " + dauBeg + " <= t_dau.timestamp and t_dau.timestamp < " + dauEnd +
                "          and " + installBeg + " <= t_install.timestamp and t_install.timestamp < " + installEnd +
                "          and " + installBeg + " <= t_pay.timestamp and t_pay.timestamp < " + installEnd +
                "          and t_dau.appid = '" + appid + "' ) as A " +
                " group by A.fw;", c );

    }



    public Map<String, Long> getDauMap(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, count( distinct A.user_id ) " +
                " from ( select t_dau.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_dau " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where " + beg + " <= t_dau.timestamp and t_dau.timestamp < " + end +
                "          and t_dau.appid = '" + appid + "' ) as A " +
                " group by A.fw;", c );
    }

    public Map<String, Long> getMauMap(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryMapStringLong( "select A.fw, count( distinct A.user_id ) " +
                " from ( select t_dau.user_id as user_id, coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                "        from t_dau " +
                "        left join t_install " +
                "        using( user_id ) " +
                "        where (" + beg + " - 2592000000 ) <= t_dau.timestamp and t_dau.timestamp < " + end +
                "          and t_dau.appid = '" + appid + "' ) as A " +
                " group by A.fw;", c );
    }


    public long getDau(String appid, long beg, long end, boolean isMobile, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                " from t_dau " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_mobile = '" + boolString(isMobile) + "';", c);
    }

    public long getPossessionGold(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                " from t_possession " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_ccgames = 'Y' and is_robot = 'N';", c);
    }

    public long getPossessionGold(String appid, long beg, long end, boolean isMobile, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                " from t_possession " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and is_ccgames = 'Y' and is_robot = 'N' " +
                "   and is_mobile = '" + boolString(isMobile) + "';", c);
    }

    public long getGrantInstallGold(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                " from t_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 1 " +
                "   and is_ccgames = 'Y' and is_robot = 'N';", c);
    }

    public long getGrantInstallGold(String appid, long beg, long end, boolean isMobile, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                " from t_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 1 " +
                "   and is_ccgames = 'Y' and is_robot = 'N' " +
                "   and is_mobile = '" + boolString(isMobile) + "';", c);
    }

    public long getGrantLoginGold(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                " from t_grants2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 1 " +
                "   and is_ccgames = 'Y' and is_robot = 'N';", c);
    }

    public long getGrantLoginGold(String appid, long beg, long end, boolean isMobile, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                " from t_grants2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 1 " +
                "   and is_ccgames = 'Y' and is_robot = 'N' " +
                "   and is_mobile = '" + boolString(isMobile) + "';", c);
    }

//    public long getGrantLoginGold( String appid, long beg, long end, boolean isOld, BiClient c ) {
//        return ServiceManager.getSqlService().queryLong( "select count( A.user_id ) " +
//                " from ( select distinct user_id " +
//                "        from t_grants " +
//                "        where " + beg + " <= timestamp and timestamp < " + end +
//                "          and appid = '" + appid + "' and type = 2 " +
//                "          and is_ccgames = 'Y' and is_robot = 'N' ) as A " +
//                ( isOld ? "left join" : "inner join" ) +
//                "      ( select user_id " +
//                "        from t_install " +
//                "        where " + beg + " <= timestamp and timestamp < " + end +
//                "          and appid = '" + appid + "' ) as B " +
//                " on A.user_id = B.user_id " +
//                ( isOld ? "where B.user_id is null;" : ";" ), c );
//    }

    public long getGrantLoginGold(String appid, long beg, long end, boolean isMobile, boolean isOld, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( A.gold ), 0 ) " +
                " from ( select user_id, gold " +
                "        from t_grants2 " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "          and appid = '" + appid + "' and type = 1 " +
                "          and is_ccgames = 'Y' and is_robot = 'N' " +
                "          and is_mobile = '" + boolString(isMobile) + "' ) as A " +
                (isOld ? "left join" : "inner join") +
                "      ( select user_id " +
                "        from t_install " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "          and appid = '" + appid + "' and is_mobile = '" + boolString(isMobile) + "' ) as B " +
                " on A.user_id = B.user_id " +
                (isOld ? "where B.user_id is null;" : ";"), c);
    }

    public long getGrantHelpGold(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                " from t_grants2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 3 " +
                "   and is_ccgames = 'Y' and is_robot = 'N';", c);
    }

    public long getGrantHelpGold(String appid, long beg, long end, boolean isMobile, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                " from t_grants2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 3 " +
                "   and is_ccgames = 'Y' and is_robot = 'N' " +
                "   and is_mobile = '" + boolString(isMobile) + "';", c);
    }


    public long getGrantLoginPeople(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                " from t_grants2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 1 " +
                "   and is_ccgames = 'Y' and is_robot = 'N';", c);
    }

    public long getGrantLoginPeople(String appid, long beg, long end, boolean isOld, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( A.user_id ) " +
                " from ( select distinct user_id " +
                "        from t_grants2 " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "          and appid = '" + appid + "' and type = 1 " +
                "          and is_ccgames = 'Y' and is_robot = 'N' ) as A " +
                (isOld ? "left join" : "inner join") +
                "      ( select user_id " +
                "        from t_install " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "          and appid = '" + appid + "' ) as B " +
                " on A.user_id = B.user_id " +
                (isOld ? "where B.user_id is null;" : ";"), c);
    }

    public long getGrantLoginPeople(String appid, long beg, long end, boolean isMobile, boolean isOld, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( A.user_id ) " +
                " from ( select distinct user_id " +
                "        from t_grants2 " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "          and appid = '" + appid + "' and type = 1 " +
                "          and is_ccgames = 'Y' and is_robot = 'N' " +
                "          and is_mobile = '" + boolString(isMobile) + "' ) as A " +
                (isOld ? "left join" : "inner join") +
                "      ( select user_id " +
                "        from t_install " +
                "        where " + beg + " <= timestamp and timestamp < " + end +
                "          and appid = '" + appid + "' and is_mobile = '" + boolString(isMobile) + "' ) as B " +
                " on A.user_id = B.user_id " +
                (isOld ? "where B.user_id is null;" : ";"), c);
    }

    public long getGrantHelpPeople(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                " from t_grants2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 3 " +
                "   and is_ccgames = 'Y' and is_robot = 'N';", c);
    }

    public long getGrantHelpPeople(String appid, long beg, long end, boolean isMobile, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                " from t_grants2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 3 " +
                "   and is_ccgames = 'Y' and is_robot = 'N' " +
                "   and is_mobile = '" + boolString(isMobile) + "';", c);
    }

    public long getGrantHelpCount(String appid, long beg, long end, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( id ) " +
                " from t_grants2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 3 " +
                "   and is_ccgames = 'Y' and is_robot = 'N';", c);
    }

    public long getGrantHelpCount(String appid, long beg, long end, boolean isMobile, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( id ) " +
                " from t_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and appid = '" + appid + "' and type = 3 " +
                "   and is_ccgames = 'Y' and is_robot = 'N' " +
                "   and is_mobile = '" + boolString(isMobile) + "';", c);
    }

    public long getHuaFeiOutCount( long beg, long end, long itemId, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( id ) " +
                " from t_use_props " +
                " where " +  beg + " <= timestamp and timestamp < " + end + " and use_info = 6 and item_count > 0 and item_id = " + itemId +
                " ;", c);

    }
    public long getHuaFeiSellCount( long beg, long end, long itemId, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select count( id ) " +
                " from t_use_props " +
                " where " + beg + " <= timestamp and timestamp < " + end + " and use_info = 2 and item_count > 0 and item_id = " + itemId + " and " + beg + " <= timestamp and timestamp < " + end +
                " ;", c);
    }
    public long getHuaFeiSentCount( long beg, long end, long itemId, BiClient c) {
        return ServiceManager.getSqlService().queryLong("select sum( item_count ) " +
                " from t_important_props " +
                " where " + beg + " <= timestamp and timestamp < " + end + " and item_count > 0 and item_id = " + itemId +
                " ;", c);

    }
}
