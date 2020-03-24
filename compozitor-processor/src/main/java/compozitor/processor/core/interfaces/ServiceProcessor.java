package compozitor.processor.core.interfaces;

import java.util.HashMap;
import java.util.Map;

public abstract class ServiceProcessor extends AnnotationProcessor {
  private Map<String, ServiceResourceFile> serviceFiles;

  public ServiceProcessor() {
    this.serviceFiles = new HashMap<>();
  }

  @Override
  protected final void preProcess() {
    this.serviceClasses().forEach(serviceInterface -> {
      this.context.info("Registering Service file for Interface {0}", serviceInterface);
      String interfaceName = serviceInterface.getCanonicalName();
      TypeModel interfaceType = this.context.getJavaModel().getType(interfaceName);
      this.serviceFiles.put(interfaceName, new ServiceResourceFile(this.context, interfaceType));
    });
  }

  @Override
  protected final void process(TypeModel model) {
    this.context.info("Processing service file for type {0}", model);

    model.getInterfaces().forEach(targetInterface -> {
      registerType(model, targetInterface);
    });

    this.processAncestors(model, model.getSuperType());
  }

  private void processAncestors(TypeModel targetService, TypeModel superType){
    if (superType == null) {
      return;
    }

    registerType(targetService, superType);

    superType.getInterfaces().forEach(targetInterface ->{
      registerType(targetService, targetInterface);
    });

    this.processAncestors(targetService, superType.getSuperType());
  }

  private void registerType(TypeModel model, TypeModel targetInterface) {
    ServiceResourceFile resourceFile = this.resourceFile(targetInterface);
    if (resourceFile != null) {
      this.context.info("Registering type {0} for service interface {1}", model.getQualifiedName(),
        targetInterface.getQualifiedName());
      resourceFile.add(model);
    }
  }

  private ServiceResourceFile resourceFile(TypeModel targetInterface) {
    if (targetInterface == null) {
      return null;
    }

    String interfaceName = targetInterface.getQualifiedName();
    ServiceResourceFile resourceFile = this.serviceFiles.get(interfaceName);
    if (resourceFile == null) {
      resourceFile = this.resourceFile(targetInterface.getSuperType());
    }

    if (resourceFile != null) {
      return resourceFile;
    }

    for (TypeModel iface : targetInterface.getInterfaces()) {
      resourceFile = this.resourceFile(iface);
      if (resourceFile != null) {
        break;
      }
    }

    return resourceFile;
  }

  @Override
  protected final void postProcess() {
    this.serviceFiles.values().forEach(resourceFile -> {
      try {
        resourceFile.close();
      } catch (Exception e) {
        e.printStackTrace();
        this.context.error(e.getMessage());
      }
    });
  }

  protected abstract Iterable<Class<?>> serviceClasses();
}
