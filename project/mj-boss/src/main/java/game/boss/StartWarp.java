package game.boss;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.boss.admin.AdminListenServer;
import game.boss.net.GatewayListenServer;
import game.boss.net.SceneListenServer;
import game.boss.net.poker.douniu.DouniuSceneListenServer;
import game.boss.net.poker.zjh.ZJHSceneListenServer;

public class StartWarp {
    public static void start(String[] args) {
        IocFactoryProxy ioc = null;
        DOMConfigurator.configure(StartWarp.class.getResource("/bossLog4j.xml"));
        Logger log = LoggerFactory.getLogger(BossMain.class);
        try {
            ioc = IocFactoryProxy.getInstance();
            ioc.init();
            GatewayListenServer gatewayListenServer = ioc.getBean("gatewayListenServer", GatewayListenServer.class);
            gatewayListenServer.start();
            
            DouniuSceneListenServer douniuSceneListenServer=ioc.getBean("douniuSceneListenServer",DouniuSceneListenServer.class);
            douniuSceneListenServer.start();
            
            ZJHSceneListenServer ZJHSceneListenServer  = ioc.getBean("ZJHSceneListenServer",ZJHSceneListenServer.class);
            ZJHSceneListenServer.start();           
                      
            SceneListenServer sceneListenServer = ioc.getBean("sceneListenServer", SceneListenServer.class);
            sceneListenServer.start();

            AdminListenServer adminListenServer = ioc.getBean("adminListenServer", AdminListenServer.class);
            adminListenServer.start();
            System.gc();
            log.info("服务器启动完毕！");
        } catch (Throwable e) {
            log.error("服务器启动错误,准备关闭", e);
            if (ioc != null) {
                ioc.destroy();
            }
        }
    }
}
