package compozitor.processor.core.infra;

import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.ServiceResourceFile;
import compozitor.processor.core.interfaces.TraversalStrategy;
import compozitor.processor.core.interfaces.TypeModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class JavaServiceProcessor extends AnnotationProcessor {
  private Map<String, ServiceResourceFile> serviceFiles;

  public JavaServiceProcessor() {
    this.serviceFiles = new HashMap<>();
  }

  protected final void addService(TypeModel serviceInterface) {
    String interfaceName = serviceInterface.getQualifiedName();
    if (this.serviceFiles.containsKey(interfaceName)) {
      return;
    }
    this.serviceFiles.put(interfaceName, new ServiceResourceFile(this.context, serviceInterface));
  }

  protected final void registerType(TypeModel model, TypeModel targetInterface) {
    ServiceResourceFile resourceFile = this.resourceFile(targetInterface);
    if (resourceFile != null) {
      this.context.info("Adding {0} in service file {1}", model.getQualifiedName(),
        resourceFile.providerInterface());
      resourceFile.add(model);
    }
  }

  private ServiceResourceFile resourceFile(TypeModel targetInterface) {
    if (targetInterface == null) {
      return null;
    }

    for (TypeModel targetService : this.retrieveAncestors(targetInterface)) {
      String interfaceName = targetService.getQualifiedName();
      ServiceResourceFile resourceFile = this.serviceFiles.get(interfaceName);
      if (resourceFile != null) {
        return resourceFile;
      }
    }

    return null;
  }

  private Iterable<TypeModel> retrieveAncestors(TypeModel targetInterface) {
    Collection<TypeModel> ancestors = new ArrayList<>();
    ancestors.add(targetInterface);

    TraversalStrategy strategy = TraversalStrategy.ANCESTORS;
    ancestors.addAll((Collection) strategy.superClasses(targetInterface));
    ancestors.addAll((Collection) strategy.interfaces(targetInterface));

    return ancestors;
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
