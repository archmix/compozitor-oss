package compozitor.template.engine.interfaces;

import java.util.HashSet;
import java.util.Set;

class JoinableClassLoader extends ClassLoader {
	private Set<ClassLoader> classLoaders = new HashSet<>();

	private static final JoinableClassLoader current = new JoinableClassLoader();

	public static JoinableClassLoader current() {
		return current;
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
}
