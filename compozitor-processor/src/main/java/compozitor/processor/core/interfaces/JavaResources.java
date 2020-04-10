package compozitor.processor.core.interfaces;

public class JavaResources {
  public static JavaResource create(Name name) {
    return create(PackageName.root(), name);
  }

  public static JavaResource create(PackageName packageName, Name name) {
    return JavaResource.create(packageName, name);
  }

  public static JavaResource create(PackageName packageName, JavaFileName javaFileName) {
    return JavaResource.create(packageName, javaFileName);
  }
}
