package game.scene;

import java.util.Map;

import com.isnowfox.core.IocFactory;
import com.isnowfox.core.SpringIocFactory;

public class IocFactoryProxy implements IocFactory{
	private static final IocFactoryProxy instance = new IocFactoryProxy();
	public static final IocFactoryProxy getInstance(){
		return instance;
	}
	
	private SpringIocFactory ioc;
	private IocFactoryProxy(){
		
	}
	
	public IocFactoryProxy init(){
		ioc =new SpringIocFactory("ZJHSceneContext.xml");
		return this;
	}
	
	@Override
	public void initBean(Object o) {
		ioc.initBean(o);
	}

	@Override
	public <T> T getBean(Class<T> cls) {
		return ioc.getBean(cls);
	}

	@Override
	public <T> T getBean(String name) {
		return ioc.getBean(name);
	}

	@Override
	public <T> T getBean(String name, Class<T> cls) {
		return ioc.getBean(name, cls);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> cls) {
		return ioc.getBeansOfType(cls);
	}

	@Override
	public void destroy() {
		ioc.destroy();
	}
}
