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
import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;

import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ServiceLoader;

public abstract class ProcessorEngine<T extends TemplateContextData<T>> extends AnnotationProcessor {
  private final PluginRepository pluginRepository;
  private final MetaModelRepository<T> metaModelRepository;
  private SourceCodeListener generatorListener;

  public ProcessorEngine() {
    this.pluginRepository = PluginRepository.create();
    this.metaModelRepository = new MetaModelRepository<T>();
  }

  @Override
  protected void init(ProcessingContext context) {
    this.pluginRepository.load(this.getClassLoader(), this.category(), context);
    this.generatorListener = ((sourceCode) -> {
    });
  }

  @Override
  protected final void process(FieldModel fieldModel) {
    this.metaModelRepository.add(this.pluginRepository.getMetaModel(this.context, this.repository, fieldModel));
  }

  @Override
  protected final void process(MethodModel methodModel) {
    this.metaModelRepository.add(this.pluginRepository.getMetaModel(this.context, this.repository, methodModel));
  }

  @Override
  protected final void process(TypeModel typeModel) {
    this.metaModelRepository.add(this.pluginRepository.getMetaModel(this.context, this.repository, typeModel));
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

  @Override
  protected void releaseResources() {
    this.pluginRepository.releaseResources(this.context);
  }

  private void write(GeneratedCode code) {
    try {
      String sourceCode = CharStreams.toString(new InputStreamReader(code.getContent()));
      this.generatorListener.accept(sourceCode);

      FileObject sourceFile = this.createFile(code);
      this.context.info("Generating file {0}", sourceFile.getName());

      try (Writer writer = sourceFile.openWriter()) {
        writer.write(sourceCode);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private FileObject createFile(GeneratedCode code) throws IOException {
    if (code.getResource()) {
      return this.context.createResource(StandardLocation.SOURCE_OUTPUT, code.getNamespace().toString(), code.getFileName().toString());
    }

    return this.context.createSourceFile(code.getQualifiedName().toString());
  }

  public final void listen(SourceCodeListener generatorListener) {
    this.generatorListener = generatorListener;
  }

  protected ClassLoader getClassLoader(){
    return this.getClass().getClassLoader();
  }

  protected abstract CodeGenerationCategory category();
}
