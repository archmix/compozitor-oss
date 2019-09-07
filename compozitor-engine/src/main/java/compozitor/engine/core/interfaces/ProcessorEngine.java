package compozitor.engine.core.interfaces;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import com.google.common.io.CharStreams;
import compozitor.generator.core.interfaces.GeneratedCode;
import compozitor.generator.core.interfaces.MetamodelRepository;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;
import compozitor.template.core.interfaces.TemplateEngineBuilder;

public abstract class ProcessorEngine<T extends TemplateContextData<T>> extends AnnotationProcessor {
  private final CodeEngine<T> engine;
  private final EngineContext<T> engineContext;
  private final TemplateEngine templateEngine;
  private final EngineType engineType;
  private final MetamodelRepository<T> repository;
  private GeneratorListener generatorListener;

  public ProcessorEngine() {
    this.engine = CodeEngine.create();
    this.engineContext = EngineContext.create();
    this.templateEngine = this.init(TemplateEngineBuilder.create().withClasspathTemplateLoader());
    this.repository = new MetamodelRepository<>();
    this.generatorListener = ((sourceCode) -> {});
    this.engineType = EngineType.adapter(this.getTargetAnnotation().getSimpleName());
    this.engineContext.add(engineType, this.repository);
  }

  protected TemplateEngine init(TemplateEngineBuilder builder) {
    return builder.build();
  }
  
  protected void add(T metadata) {
    this.repository.add(metadata);
  }

  @Override
  protected final void postProcess() {
    TemplateRepository templates = new TemplateRepository();
    this.loadTemplates(templates);
    templates.forEach(template ->{
      this.engineContext.add(this.engineType, template);
    });
    
    this.engine.generate(engineContext, code -> {
      this.write(code);
    });
  }

  private void write(GeneratedCode code) {
    try {
      String sourceCode = CharStreams.toString(new InputStreamReader(code.getContent()));
      this.generatorListener.accept(sourceCode);
      
      FileObject sourceFile = this.createFile(code);

      try (Writer writer = sourceFile.openWriter()) {
        writer.write(sourceCode);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private FileObject createFile(GeneratedCode code) throws IOException {
    if(code.getResource()) {
      return this.context.createResource(StandardLocation.SOURCE_OUTPUT, code.getNamespace(), code.getFileName());
    }
    
    return this.context.createSourceFile(code.getQualifiedName());
  }
  
  @Override
  public final Set<String> getSupportedAnnotationTypes() {
    return new HashSet<String>(Arrays.asList(this.getTargetAnnotation().getCanonicalName()));
  }

  protected final Template getTemplate(String resourceName) {
    return this.templateEngine.getTemplate(resourceName);
  }
  
  public void setGeneratorListener(GeneratorListener generatorListener) {
    this.generatorListener = generatorListener;
  }
  
  protected abstract Class<? extends Annotation> getTargetAnnotation();

  protected abstract void loadTemplates(TemplateRepository templates);
}
