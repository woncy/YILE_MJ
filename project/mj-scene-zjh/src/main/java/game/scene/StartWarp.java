package game.scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isnowfox.util.ArrayExpandUtils;

import game.zjh.scene.net.BossClient;
import game.zjh.scene.net.ZJHSceneServer;

public class StartWarp {
    private static final Logger log = LoggerFactory.getLogger(ZJHSceneMain.class);

    public static void start(String[] args) {
    	IocFactoryProxy ioc = null;
        try {
            String arg1 = ArrayExpandUtils.get(args, 0);
            log.debug("数据库参数:{}", arg1);
            ioc = IocFactoryProxy.getInstance();
            ioc.init();
            ZJHSceneServer sceneServer = ioc.getBean("sceneServer", ZJHSceneServer.class);
            sceneServer.start();
            BossClient bossClient = ioc.getBean("bossClient", BossClient.class);
            bossClient.connect();
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
