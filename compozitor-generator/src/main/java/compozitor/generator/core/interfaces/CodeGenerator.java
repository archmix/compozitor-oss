package compozitor.generator.core.interfaces;

import java.util.ArrayList;
import java.util.List;
import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateBuilder;
import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateContextData;

public class CodeGenerator<T extends TemplateContextData<T>> {

  public final List<GeneratedCode> execute(GeneratorContext context,
      MetamodelRepository<T> repository) {
    TemplateMetadata templateMetadata = context.getMetadata();
    Template template = templateMetadata.getTemplate();
    TemplateContext templateContext = context.getContext();

    List<GeneratedCode> list = new ArrayList<>();
    Code code = new Code(templateMetadata, templateContext);

    for (T metamodel : repository) {
      templateContext.add(metamodel);

      if (!this.accept(templateMetadata, templateContext)) {
        continue;
      }

      templateContext.add(code);
      code.setContent(template.mergeToStream(templateContext));

      list.add(code.toGeneratedCode());
    }

    return list;
  }

  private boolean accept(TemplateMetadata metadata, TemplateContext context) {
    if (metadata.getCriteria() == null || metadata.getCriteria().isEmpty()) {
      return true;
    }

    String criteria = "#if(" + metadata.getCriteria() + ") true #else false #end";

    Template proxy = TemplateBuilder.create("criteria").withResourceLoader(criteria).build();
    String mergeToString = proxy.mergeToString(context);

    return mergeToString.trim().equals("true");
  }
}
