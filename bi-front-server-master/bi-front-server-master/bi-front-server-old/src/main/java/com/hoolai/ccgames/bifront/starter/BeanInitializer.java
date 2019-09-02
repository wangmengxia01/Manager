package com.hoolai.ccgames.bifront.starter;

import com.hoolai.ccgames.bifront.net.BiClientFactory;
import com.hoolai.ccgames.bifront.vo.ZhuboView;
import com.hoolai.ccgames.centergate.client.CenterRepo;
import com.hoolai.ccgames.centergate.client.CenterRepoImpl;
import com.hoolai.ccgames.skeleton.utils.PropUtil;
import com.hoolai.centersdk.sdk.ItemSdk;
import com.hoolai.centersdk.sdk.UserSdk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanInitializer implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger( BeanInitializer.class );

    @Override
    public void setApplicationContext( ApplicationContext applicationContext )
            throws BeansException {
        Global.context = applicationContext;
    }

    public void initialize() throws Exception {

        try {
            String envDir = PropUtil.getProp( "/env.properties", "envdir" );
            BiClientFactory.setConfig( "/" + envDir + "bigate.properties" );

            Global.commandRegistry.initByAnnotationWithoutService( Global.servicePackage );
            Global.itemRegistry.init( "/items.xml" );
            ItemSdk.init();
            CenterRepo centerRepo = new CenterRepoImpl( "/" + envDir + "cgate.properties" );
            UserSdk.init(centerRepo);

            Global.biService.init( "/" + envDir + "bigate.properties" );

            ZhuboView.init();
        }
        catch( Exception e ) {
            logger.error( e.getMessage(), e );
            throw new RuntimeException( "BeanInitializer fail" );
        }
    }
}
