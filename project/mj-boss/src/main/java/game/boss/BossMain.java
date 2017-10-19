package game.boss;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
/**
 * 
* @ClassName: BossMain
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 13323659953@163.com   
* @date 2017年10月18日 下午2:50:54
*
 */
public class BossMain {

	public static final void main(String... args) {  
        try {
            File dir = new File("lib");
            URLClassLoader cl = (URLClassLoader) ClassLoader.getSystemClassLoader();
            if (dir.exists()) {
                File[] fileArray = dir.listFiles();

                Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addURL.setAccessible(true);
                for (File file : fileArray) {
                    addURL.invoke(cl, file.toURI().toURL());
                }
            }
            StartWarp.start(args);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
}
