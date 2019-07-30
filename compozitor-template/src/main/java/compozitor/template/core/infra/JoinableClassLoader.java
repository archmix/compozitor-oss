package compozitor.template.core.infra;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class JoinableClassLoader extends ClassLoader {
	private Set<ClassLoader> classLoaders = new HashSet<>();

	public static JoinableClassLoader create() {
		return new JoinableClassLoader();
	}

	public JoinableClassLoader join(ClassLoader classLoader) {
		this.classLoaders.add(classLoader);
		return this;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class<?> clazz = null;

		for (ClassLoader classLoader : this.classLoaders) {
			try {
				clazz = classLoader.loadClass(name);
			} catch (ClassNotFoundException e) { }
		}

		if (clazz == null) {
			throw new ClassNotFoundException("Class not found:" + name);
		}

		return clazz;
	}
	
	@Override
	public URL getResource(String name) {
		for(ClassLoader classLoader : this.classLoaders) {
			URL resource = classLoader.getResource(name);
			if(resource != null) {
				return resource;
			}
		}
		
		return this.getClass().getResource(name);
	}
}
