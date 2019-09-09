package compozitor.template.core.infra;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JoinableClassLoader extends ClassLoader {
  private final Set<ClassLoader> classLoaders;
  
  public JoinableClassLoader() {
    this.classLoaders = new HashSet<>();
    this.classLoaders.add(Thread.currentThread().getContextClassLoader());
    this.classLoaders.add(this.getClass().getClassLoader());
  }

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
      } catch (ClassNotFoundException e) {
      }
    }

    if (clazz == null) {
      throw new ClassNotFoundException("Class not found:" + name);
    }

    return clazz;
  }

  @Override
  public URL getResource(String name) {
    for (ClassLoader classLoader : this.classLoaders) {
      URL resource = classLoader.getResource(name);
      if (resource != null) {
        return resource;
      }
    }

    return this.getClass().getResource(name);
  }

  @Override
  protected Enumeration<URL> findResources(String name) throws IOException {
    List<URL> resources = new ArrayList<>();

    for (ClassLoader classLoader : this.classLoaders) {
      Enumeration<URL> found = classLoader.getResources(name);
      while (found.hasMoreElements()) {
        resources.add(found.nextElement());
      }
    }

    return Collections.enumeration(resources);
  }
}
