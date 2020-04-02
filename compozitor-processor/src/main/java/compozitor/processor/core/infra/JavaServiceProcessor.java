package compozitor.processor.core.infra;

import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.ServiceResourceFile;
import compozitor.processor.core.interfaces.TypeModel;

import java.util.HashMap;
import java.util.Map;

public abstract class JavaServiceProcessor extends AnnotationProcessor {
  private Map<String, ServiceResourceFile> serviceFiles;

  public JavaServiceProcessor() {
    this.serviceFiles = new HashMap<>();
  }

  protected final void addService(TypeModel serviceInterface){
    this.context.info("Initializing service interface {0}", serviceInterface.getQualifiedName());
    String interfaceName = serviceInterface.getQualifiedName();
    this.serviceFiles.put(interfaceName, new ServiceResourceFile(this.context, serviceInterface));
  }

  protected final void registerType(TypeModel model, TypeModel targetInterface) {
    ServiceResourceFile resourceFile = this.resourceFile(targetInterface);
    if (resourceFile != null) {
      this.context.info("Adding {0} in service file {1}", model.getQualifiedName(),
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
}
