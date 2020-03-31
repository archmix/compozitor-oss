package compozitor.processor.core.infra;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import compozitor.processor.core.interfaces.Processor;
import compozitor.processor.core.interfaces.Service;
import compozitor.processor.core.interfaces.TypeModel;

import java.util.Set;

@AutoService(Processor.class)
public class AutoServiceProcessor extends JavaServiceProcessor {

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Sets.newHashSet(Service.class.getName());
  }

  @Override
  protected void process(TypeModel typeModel) {
    if(typeModel.isInterface()) {
      this.context.error("Are you sure you want to register an interface as Java Service? It makes no sense. Review your implementation {0}", typeModel.getQualifiedName());
      return;
    }

    ServiceMetadata serviceMetadata = ServiceMetadata.create(typeModel);
    serviceMetadata.targetInterfaces().forEach(targetInterface ->{
      this.addService(targetInterface);
      this.registerType(typeModel, targetInterface);
    });
  }
}
