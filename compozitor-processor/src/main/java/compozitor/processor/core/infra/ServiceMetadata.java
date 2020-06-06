package compozitor.processor.core.infra;

import compozitor.processor.core.interfaces.AnnotationModel;
import compozitor.processor.core.interfaces.Service;
import compozitor.processor.core.interfaces.TargetService;
import compozitor.processor.core.interfaces.TraversalStrategy;
import compozitor.processor.core.interfaces.TypeModel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create", access = AccessLevel.PRIVATE)
public class ServiceMetadata {
  private final TypeModel targetType;

  private final TargetService target;

  private final TraversalStrategy strategy;

  public static ServiceMetadata create(TypeModel typeModel) {
    AnnotationModel serviceAnnotation = typeModel.getAnnotations().getBy(Service.class).get();

    TargetService target = TargetService.valueOf(serviceAnnotation.enumValue("target"));

    TraversalStrategy strategy = TraversalStrategy.valueOf(serviceAnnotation.enumValue("strategy"));

    return ServiceMetadata.create(typeModel, target, strategy);
  }

  public Iterable<TypeModel> targetInterfaces() {
    if (TargetService.INTERFACE.equals(this.target)) {
      return strategy.interfaces(this.targetType);
    }

    return strategy.superClasses(this.targetType);
  }
}
