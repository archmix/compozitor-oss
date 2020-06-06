package compozitor.template.core.infra;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CompositeClassLoader extends ClassLoader {
  private final Set<ClassLoader> classLoaders;

  public CompositeClassLoader() {
    this.classLoaders = new HashSet<>();
    this.classLoaders.add(Thread.currentThread().getContextClassLoader());
  }

  public static CompositeClassLoader create() {
    return new CompositeClassLoader();
  }

  public CompositeClassLoader join(ClassLoader classLoader) {
    if (classLoader.equals(this)) {
      return this;
    }
    this.classLoaders.add(classLoader);
    return this;
  }

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    Optional<Class<?>> clazz = Classes.find(name);
    if (clazz.isPresent()) {
      return clazz.get();
    }

    this.join(Thread.currentThread().getContextClassLoader());
    this.join(this.getClass().getClassLoader());

    for (ClassLoader classLoader : this.classLoaders) {
      Optional<Class<?>> foundClass = loadClass(classLoader, name);
      if (foundClass.isPresent()) {
        return foundClass.get();
      }
    }

    throw new ClassNotFoundException("Class not found:" + name);
  }

  private Optional<Class<?>> loadClass(ClassLoader classLoader, String name) {
    try {
      return Optional.of(classLoader.loadClass(name));
    } catch (ClassNotFoundException e) {
      return Optional.empty();
    }
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
