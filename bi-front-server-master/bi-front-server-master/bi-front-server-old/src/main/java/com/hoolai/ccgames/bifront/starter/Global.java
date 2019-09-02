package com.hoolai.ccgames.bifront.starter;

import com.hoolai.ccgames.bifront.item.ItemRegistry;
import com.hoolai.ccgames.bigate.client.service.BiService;
import com.hoolai.ccgames.skeleton.dispatch.CommandRegistry;
import org.springframework.context.ApplicationContext;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class Global {

    public static final String servicePackage = "com.hoolai.ccgames.bifront.service";
    public static ApplicationContext context;
    public static CommandRegistry commandRegistry = new CommandRegistry();
    public static ItemRegistry itemRegistry = new ItemRegistry();

    public static BiService biService = new BiService();
    public static ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor( 1 );

    //    启动以后多久开始执行 间隔 单位
    //    long now = System.currentTimeMillis();
    //        Global.timer.scheduleAtFixedRate( () -> Global.quickAuthenticator.batchClear(),
    //                10, 10, TimeUnit.SECONDS );



}
