package processorEngineTest;

import compozitor.engine.core.interfaces.ProcessorPlugin;
import compozitor.engine.core.interfaces.TemplateEnginePlugin;
import compozitor.engine.core.interfaces.TemplatePlugin;
import compozitor.engine.core.interfaces.TypeModelPlugin;
import compozitor.generator.core.interfaces.CodeGenerationCategory;
import compozitor.generator.core.interfaces.Filename;
import compozitor.generator.core.interfaces.Namespace;
import compozitor.generator.core.interfaces.TemplateMetadata;
import compozitor.generator.core.interfaces.TemplateRepository;
import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateEngine;
import compozitor.template.core.interfaces.TemplateEngineBuilder;

@ProcessorPlugin
public class TableProcessorPlugin implements TypeModelPlugin<TableMetadata>, TemplateEnginePlugin, TemplatePlugin {

  @Override
  public void accept(TemplateEngineBuilder builder) {
    builder.addDirectives(InsertSQLDirective.class);
  }

  @Override
  public void accept(TemplateEngine engine, TemplateRepository templateRepository) {
    TemplateMetadata templateMetadata = templateRepository.addRegularTemplate();
    templateMetadata.setNamespace(Namespace.create("processorEngineTest"));
    templateMetadata.setTemplate(engine.getTemplate("processorEngineTest/InsertCommand.tf"));
    templateMetadata.setFileName(Filename.create("${Table.Name}InsertCommand"));
  }

  @Override
  public TableMetadata accept(ProcessingContext context, TypeModel typeModel) {
    return TableMetadata.create(typeModel);
  }

  @Override
  public CodeGenerationCategory category() {
    return TableCategory.INSTANCE;
  }

}
