package compozitor.engine.core.interfaces;

import compozitor.generator.core.interfaces.CodeGenerationCategory;
import compozitor.generator.core.interfaces.TemplateRepository;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;
import compozitor.template.core.interfaces.TemplateEngineBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Consumer;

class PluginRepository {
  private final Collection<TemplateEnginePlugin> templateEnginePlugins;
  private final Collection<TypeModelPlugin<?>> typeModelPlugins;
  private final Collection<FieldModelPlugin<?>> fieldModelPlugins;
  private final Collection<MethodModelPlugin<?>> methodModelPlugins;
  private final Collection<TemplatePlugin> templatePlugins;
  private final Collection<TargetAnnnotationsPlugin> targetAnnnotationsPlugins;

  PluginRepository() {
    this.templateEnginePlugins = new ArrayList<>();
    this.typeModelPlugins = new ArrayList<>();
    this.fieldModelPlugins = new ArrayList<>();
    this.methodModelPlugins = new ArrayList<>();
    this.templatePlugins = new ArrayList<>();
    this.targetAnnnotationsPlugins = new ArrayList<>();
  }

  static PluginRepository create() {
    return new PluginRepository();
  }

  void load(CodeGenerationCategory category) {
    ServiceLoader.load(TemplateEnginePlugin.class).forEach(plugin -> {
      this.accept(plugin, category, (accepted) -> {
        this.templateEnginePlugins.add(plugin);
      });
    });

    ServiceLoader.load(TypeModelPlugin.class).forEach(plugin -> {
      this.accept(plugin, category, (accepted) -> {
        this.typeModelPlugins.add(plugin);
      });
    });

    ServiceLoader.load(FieldModelPlugin.class).forEach(plugin -> {
      this.accept(plugin, category, (accepted) -> {
        this.fieldModelPlugins.add(plugin);
      });
    });

    ServiceLoader.load(MethodModelPlugin.class).forEach(plugin -> {
      this.accept(plugin, category, (accepted) -> {
        this.methodModelPlugins.add(plugin);
      });
    });

    ServiceLoader.load(TemplatePlugin.class).forEach(plugin -> {
      this.accept(plugin, category, (accepted) -> {
        this.templatePlugins.add(plugin);
      });
    });

    ServiceLoader.load(TargetAnnnotationsPlugin.class).forEach(plugin -> {
      this.accept(plugin, category, (accepted) -> {
        this.targetAnnnotationsPlugins.add(plugin);
      });
    });
  }

  private void accept(CodeGenerationCategoryPlugin plugin, CodeGenerationCategory category, Consumer<CodeGenerationCategoryPlugin> accepted) {
    if (plugin.category().equals(category)) {
      accepted.accept(plugin);
    }
  }

  public TemplateEngine templateEngine() {
    TemplateEngineBuilder builder = TemplateEngineBuilder.create().addClassLoader(this.getClass().getClassLoader()).withClasspathTemplateLoader();

    this.templateEnginePlugins.forEach(plugin -> {
      plugin.accept(builder);
    });

    return builder.build();
  }

  public TemplateRepository templates(TemplateEngine templateEngine) {
    TemplateRepository templateRepository = TemplateRepository.create();
    this.templatePlugins.forEach(plugin -> {
      plugin.accept(templateEngine, templateRepository);
    });

    return templateRepository;
  }

  public <T extends TemplateContextData<T>> Collection<T> getMetaModel(TypeModel model) {
    List<T> metaModelList = new ArrayList<>();

    this.typeModelPlugins.forEach(plugin -> {
      metaModelList.add((T) plugin.accept(model));
    });

    return metaModelList;
  }

  public <T extends TemplateContextData<T>> Collection<T> getMetaModel(FieldModel model) {
    List<T> metaModelList = new ArrayList<>();

    this.fieldModelPlugins.forEach(plugin -> {
      metaModelList.add((T) plugin.accept(model));
    });

    return metaModelList;
  }

  public <T extends TemplateContextData<T>> Collection<T> getMetaModel(MethodModel model) {
    List<T> metaModelList = new ArrayList<>();

    this.methodModelPlugins.forEach(plugin -> {
      metaModelList.add((T) plugin.accept(model));
    });

    return metaModelList;
  }

  public TargetAnnotations targetAnnotations(CodeGenerationCategory category) {
    TargetAnnotations targetAnnotations = TargetAnnotations.create();
    this.targetAnnnotationsPlugins.forEach(plugin -> {
      targetAnnotations.add(plugin.targetAnnotations());
    });

    return targetAnnotations;
  }
}
