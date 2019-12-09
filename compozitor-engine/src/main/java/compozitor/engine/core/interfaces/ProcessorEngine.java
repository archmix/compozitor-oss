package compozitor.engine.core.interfaces;

import com.google.common.io.CharStreams;
import compozitor.generator.core.interfaces.CodeGenerationCategory;
import compozitor.generator.core.interfaces.CodeGenerationCategoryContext;
import compozitor.generator.core.interfaces.CodeGenerationCategoryEngine;
import compozitor.generator.core.interfaces.GeneratedCode;
import compozitor.generator.core.interfaces.MetaModelRepository;
import compozitor.generator.core.interfaces.TemplateRepository;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;

import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Set;

public abstract class ProcessorEngine<T extends TemplateContextData<T>> extends AnnotationProcessor {
  private final PluginRepository pluginRepository;
  private final MetaModelRepository<T> metaModelRepository;
  private SourceCodeListener generatorListener;

  public ProcessorEngine() {
    this.pluginRepository = PluginRepository.create();
    this.metaModelRepository = new MetaModelRepository<T>();
    this.init();
  }

  private void init() {
    this.pluginRepository.load(this.category());
    this.generatorListener = ((sourceCode) -> {
    });
  }

  @Override
  protected final void process(FieldModel fieldModel) {
    this.metaModelRepository.add(this.pluginRepository.getMetaModel(fieldModel));
  }

  @Override
  protected final void process(MethodModel methodModel) {
    this.metaModelRepository.add(this.pluginRepository.getMetaModel(methodModel));
  }

  @Override
  protected final void process(TypeModel typeModel) {
    this.metaModelRepository.add(this.pluginRepository.getMetaModel(typeModel));
  }

  @Override
  protected void postProcess() {
    CodeGenerationCategoryContext<T> engineContext = CodeGenerationCategoryContext.create();
    engineContext.add(this.category(), this.metaModelRepository);

    TemplateEngine templateEngine = this.pluginRepository.templateEngine();

    TemplateRepository templates = this.pluginRepository.templates(templateEngine);
    templates.forEach(template -> engineContext.add(this.category(), template));

    CodeGenerationCategoryEngine<T> engine = CodeGenerationCategoryEngine.create();
    engine.generate(templateEngine, engineContext, code -> this.write(code));
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
      e.printStackTrace();
    }
  }

  private FileObject createFile(GeneratedCode code) throws IOException {
    if (code.getResource()) {
      return this.context.createResource(StandardLocation.SOURCE_OUTPUT, code.getNamespace(), code.getFileName());
    }

    return this.context.createSourceFile(code.getQualifiedName());
  }

  protected final void listen(SourceCodeListener generatorListener) {
    this.generatorListener = generatorListener;
  }

  @Override
  public final Set<String> getSupportedAnnotationTypes() {
    return this.pluginRepository.targetAnnotations(this.category()).values();
  }

  protected abstract CodeGenerationCategory category();
}
