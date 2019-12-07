package compozitor.engine.processor.interfaces;

import com.google.common.io.CharStreams;
import compozitor.generator.core.interfaces.GeneratedCode;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.template.core.interfaces.TemplateContextData;

import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

abstract class AbstractProcessorEngine<T extends TemplateContextData<T>> extends AnnotationProcessor {
  private GeneratorListener generatorListener;

  public AbstractProcessorEngine() {
    this.generatorListener = ((sourceCode) -> {
    });
  }

  final void write(GeneratedCode code) {
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

  public void setGeneratorListener(GeneratorListener generatorListener) {
    this.generatorListener = generatorListener;
  }
}
