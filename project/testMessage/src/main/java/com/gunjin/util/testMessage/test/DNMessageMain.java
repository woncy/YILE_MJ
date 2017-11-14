package com.gunjin.util.testMessage.test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

import com.gunjin.util.testMessage.core.Client;
import com.gunjin.util.testMessage.handler.DNHandler;

public class DNMessageMain {
	public static void main(String[] args) throws URISyntaxException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
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
		DNHandler handler = new DNHandler();
		Client client = new Client(handler);
		handler.setClient(client);
		handler.login();
	}
}
