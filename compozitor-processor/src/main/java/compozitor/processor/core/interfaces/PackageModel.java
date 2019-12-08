package compozitor.processor.core.interfaces;

import lombok.Getter;

import javax.lang.model.element.PackageElement;

@Getter
public class PackageModel extends Model<PackageElement> {
  private final String name;

  private final Boolean unnamed;

  public PackageModel(ProcessingContext context, PackageElement element) {
    super(context, element);
    this.name = element.getQualifiedName().toString();
    this.unnamed = element.isUnnamed();
  }
}
