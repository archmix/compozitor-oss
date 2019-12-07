package compozitor.engine.processor.interfaces;

import compozitor.engine.core.interfaces.CodeEngine;
import compozitor.engine.core.interfaces.EngineContext;
import compozitor.engine.core.interfaces.EngineCategory;
import compozitor.engine.core.interfaces.TemplateRepository;
import compozitor.generator.core.interfaces.MetamodelRepository;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;
import compozitor.template.core.interfaces.TemplateEngineBuilder;

abstract class MultiProcessorEngine<T extends TemplateContextData<T>> extends AbstractProcessorEngine<T> {
  private final MetamodelRepository<T> repository;
  private final CodeEngine<T> engine;
  private final EngineCategory engineType;
  private final EngineContext<T> engineContext;
  private TemplateEngine templateEngine;

  public MultiProcessorEngine() {
    this.repository = new MetamodelRepository<>();

    this.engine = CodeEngine.create();
    this.engineType = this.engineType();
    this.engineContext = EngineContext.create();
    this.engineContext.add(this.engineType, this.repository);
  }

  @Override
  protected final void preProcess() {
    this.templateEngine = this.init(TemplateEngineBuilder.create().addClassLoader(this.getClass().getClassLoader()).withClasspathTemplateLoader());
  }

  protected TemplateEngine init(TemplateEngineBuilder builder) {
    return builder.build();
  }

  private void add(T metadata) {
    this.repository.add(metadata);
  }

  @Override
  protected final void process(TypeModel model) {
    this.add(this.toMetadata(model));
  }

  @Override
  protected final void process(FieldModel model) {
    this.add(this.toMetadata(model));
  }

  @Override
  protected final void process(MethodModel model) {
    this.add(this.toMetadata(model));
  }

  protected T toMetadata(TypeModel model) {
    throw new IllegalStateException("You should implement this method in order to process Type annotation");
  }

  protected T toMetadata(FieldModel model) {
    throw new IllegalStateException("You should implement this method in order to process Field annotation");
  }

  protected T toMetadata(MethodModel model) {
    throw new IllegalStateException("You should implement this method in order to process Method annotation");
  }

  @Override
  protected final void postProcess() {
    TemplateRepository templates = new TemplateRepository();
    this.loadTemplates(templates);
    templates.forEach(template -> {
      this.engineContext.add(this.engineType, template);
    });

    this.engine.generate(this.templateEngine, this.engineContext, code -> {
      this.write(code);
    });
  }

  protected final Template getTemplate(String resourceName) {
    return this.templateEngine.getTemplate(resourceName);
  }

  protected abstract EngineCategory engineType();

  protected abstract void loadTemplates(TemplateRepository templates);
}
