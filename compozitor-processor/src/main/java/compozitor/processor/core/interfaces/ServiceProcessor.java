package compozitor.processor.core.interfaces;

import compozitor.processor.core.infra.JavaServiceProcessor;

public abstract class ServiceProcessor extends JavaServiceProcessor {
  private TraversalStrategy traversalStrategy;

  protected ServiceProcessor() {
    traversalStrategy = TraversalStrategy.ONE;
  }

  public void traverseAncestors(){
    this.traversalStrategy = TraversalStrategy.ALL;
  }

  @Override
  protected final void preProcess(AnnotationRepository annotationRepository) {
    this.serviceClasses().forEach(serviceInterface -> {
      this.context.info("Initializing processor for type {0}", serviceInterface);
      String interfaceName = serviceInterface.getCanonicalName();
      TypeModel interfaceType = this.context.getJavaModel().getType(interfaceName);
      this.addService(interfaceType);
    });
  }

  @Override
  protected final void process(TypeModel typeModel) {
    if(typeModel.isInterface()) {
      this.context.error("Are you sure you want to register an interface as Java Service? It makes no sense. Review your implementation {0}", typeModel.getQualifiedName());
      return;
    }

    this.context.info("Processing service file for type {0}", typeModel);

    this.traversalStrategy.superClasses(typeModel).forEach(targetInterface ->{
      registerType(typeModel, targetInterface);
    });

    this.traversalStrategy.interfaces(typeModel).forEach(targetInterface ->{
      registerType(typeModel, targetInterface);
    });
  }

  protected abstract Iterable<Class<?>> serviceClasses();
}
