package compozitor.engine.processor.interfaces;

import compozitor.engine.core.interfaces.CodeEngine;
import compozitor.engine.core.interfaces.EngineCategory;
import compozitor.engine.core.interfaces.TemplateRepository;
import compozitor.template.core.interfaces.TemplateContextData;

abstract class ModularProcessorEngine<T extends TemplateContextData<T>> extends AbstractProcessorEngine<T> {
  private final PluginRepository pluginRepository;
  private final CodeEngine<T> engine;
  private final EngineCategory engineType;

  public ModularProcessorEngine() {
    this.pluginRepository = new PluginRepository();
    this.engineType = this.engineType();
    this.engine = CodeEngine.create();
  }

  @Override
  protected final void postProcess() {
    TemplateRepository templates = new TemplateRepository();
    templates.forEach(template -> {
      this.engineContext.add(this.engineType, template);
    });

    this.engine.generate(this.templateEngine, this.engineContext, code -> {
      this.write(code);
    });
  }

  protected abstract EngineCategory engineType();
}
