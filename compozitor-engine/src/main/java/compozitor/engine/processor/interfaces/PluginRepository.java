package compozitor.engine.processor.interfaces;

import compozitor.engine.core.interfaces.EngineCategory;
import compozitor.generator.core.interfaces.MetamodelRepository;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;
import compozitor.template.core.interfaces.TemplateEngineBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

class PluginRepository {
  private final Map<EngineCategory, Collection<TemplateEnginePlugin>> templateEnginePlugins;

  private final Map<EngineCategory, Collection<TypeModelPlugin<?>>> metamodelRepositoryPlugins;

  PluginRepository() {
    this.templateEnginePlugins = new HashMap<>();
    this.metamodelRepositoryPlugins = new HashMap<>();
  }

  void load(){
    ServiceLoader.load(TemplateEnginePlugin.class).forEach(plugin ->{
      Collection<TemplateEnginePlugin> plugins = this.templateEnginePlugins.get(plugin.engineType());
      if(plugins == null){
        plugins = new ArrayList<>();
        this.templateEnginePlugins.put(plugin.engineType(), plugins);
      }
      plugins.add(plugin);
    });

    ServiceLoader.load(TypeModelPlugin.class).forEach(plugin ->{
      Collection<TypeModelPlugin<?>> plugins = this.metamodelRepositoryPlugins.get(plugin.engineType());
      if(plugins == null){
        plugins = new ArrayList<>();
        this.metamodelRepositoryPlugins.put(plugin.engineType(), plugins);
      }
      plugins.add(plugin);
    });
  }

  public TemplateEngine getTemplateEngine(EngineCategory engineType){
    TemplateEngineBuilder builder = TemplateEngineBuilder.create().addClassLoader(this.getClass().getClassLoader()).withClasspathTemplateLoader();

    Collection<TemplateEnginePlugin> plugins = this.templateEnginePlugins.get(engineType);
    if(plugins != null){
      plugins.forEach(plugin ->{
        plugin.accept(builder);
      });
    }

    return builder.build();
  }

  public <T extends TemplateContextData<T>> MetamodelRepository<T> getMetamodelRepository(EngineCategory engineType){
    MetamodelRepository repository = new MetamodelRepository();

    Collection<TypeModelPlugin<?>> plugins = this.metamodelRepositoryPlugins.get(engineType);
    if(plugins != null){
      plugins.forEach(plugin ->{
        plugin.accept(repository);
      });
    }

    return repository;
  }
}
