package com.hoolai.ccgames.bifront.service;

public class ServiceManager {

    private static CommonService commonService = new CommonService();

    private static AdminService adminService = new AdminService();

    private static MjdrService mjdrService = new MjdrService();

    private static SqlService sqlService = new SqlService();

    private static MissionService missionService = new MissionService();

    private static NewPokerService newpokerService = new NewPokerService();

    private static DdzService ddzService = new DdzService();


    public static CommonService getCommonService() {
        return commonService;
    }

    public static AdminService getAdminService() {
        return adminService;
    }

    public static MjdrService getMjdrService() {
        return mjdrService;
    }

    public static SqlService getSqlService() {
        return sqlService;
    }

    public static MissionService getMissionService() {
        return missionService;
    }

    public static NewPokerService getNewPokerService() { return newpokerService; }

    public static DdzService getDdzService() { return ddzService;}

    }
