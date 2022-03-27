package compozitor.engine.core.interfaces;

import com.google.common.io.CharStreams;
import compozitor.generator.core.interfaces.CodeGenerationCategory;
import compozitor.generator.core.interfaces.CodeGenerationCategoryContext;
import compozitor.generator.core.interfaces.CodeGenerationCategoryEngine;
import compozitor.generator.core.interfaces.GeneratedCode;
import compozitor.generator.core.interfaces.MetaModelRepository;
import compozitor.generator.core.interfaces.TemplateRepository;
import compozitor.processor.core.interfaces.*;
import toolbox.classloader.interfaces.CompositeClassLoader;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;

import javax.tools.FileObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ProcessorEngine<T extends TemplateContextData<T>> extends AnnotationProcessor {
  private final PluginRepository pluginRepository;
  private final MetaModelRepository<T> metaModelRepository;
  private final CompositeClassLoader classLoader;
  private SourceCodeListener generatorListener;

  public ProcessorEngine() {
    this.pluginRepository = PluginRepository.create();
    this.metaModelRepository = MetaModelRepository.<T>create();
    this.classLoader = CompositeClassLoader.create().join(this.getClass().getClassLoader());
  }

  @Override
  protected void init(ProcessingContext context) {
    this.joinClassLoader(this.classLoader);
    this.pluginRepository.load(this.classLoader, this.category(), context);
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
  protected final void postProcess() {
    CodeGenerationCategoryContext<T> engineContext = CodeGenerationCategoryContext.create();
    engineContext.add(this.category(), this.metaModelRepository);

    TemplateEngine templateEngine = this.pluginRepository.templateEngine();

    TemplateRepository templates = this.pluginRepository.templates(templateEngine);
    templates.forEach(template -> engineContext.add(this.category(), template));

    CodeGenerationCategoryEngine<T> engine = CodeGenerationCategoryEngine.create();
    engine.generate(templateEngine, engineContext, code -> this.write(code));

    this.postGeneration(this.metaModelRepository);
    this.metaModelRepository.clear();
  }

  protected void postGeneration(MetaModelRepository<T> repository) {
    return;
  }

  @Override
  protected void processOver() {
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
      context.warning("Was not possible to write generated code with message {0}", e.getMessage());
    }
  }

  private FileObject createFile(GeneratedCode code) throws IOException {
    if (code.getResource()) {
      Path path = Paths.get(code.getNamespace().toPath().toString(), code.getFileName().toString());
      Resource.ResourceName resourceName = Resource.ResourceName.create(path.toString());
      JavaResource javaResource = JavaResources.create(resourceName);
      return this.context.getJavaFiles().resourceFile(javaResource);
    }

    PackageName packageName = PackageName.create(code.getNamespace().toString());
    JavaFileName javaFileName = JavaFileName.create(code.getFileName().toString());
    JavaResource javaResource = JavaResources.create(packageName, javaFileName);
    return this.context.getJavaFiles().sourceFile(javaResource);
  }

  public final void listen(SourceCodeListener generatorListener) {
    this.generatorListener = generatorListener;
  }

  protected void joinClassLoader(CompositeClassLoader classLoader) {
    return;
  }

  protected abstract CodeGenerationCategory category();
}
