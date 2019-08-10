package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.PackageElement;
import lombok.Getter;

@Getter
public class PackageModel extends Model<PackageElement> {
  private final String name;

  private final Boolean unnamed;

  public PackageModel(ProcessingEnvironment environment, PackageElement element) {
    super(environment, element);
    this.name = element.getQualifiedName().toString();
    this.unnamed = element.isUnnamed();
  }
}
