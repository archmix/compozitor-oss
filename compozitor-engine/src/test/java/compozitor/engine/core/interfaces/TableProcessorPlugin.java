package compozitor.engine.core.interfaces;

import compozitor.generator.core.interfaces.CodeGenerationCategory;
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
    templateMetadata.setNamespace("compozitor.engine.core.infra");
    templateMetadata.setTemplate(engine.getTemplate("processorEngineTest/InsertCommand.tf"));
    templateMetadata.setFileName("${Table.Name}InsertCommand");
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
