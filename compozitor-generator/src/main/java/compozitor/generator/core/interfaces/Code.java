package compozitor.generator.core.interfaces;

import compozitor.template.core.infra.StringInputStream;
import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateBuilder;
import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

public class Code implements TemplateContextData<Code> {
  private final TemplateMetadata metadata;

  private final TemplateContext context;

  private final TemplateEngine engine;

  private InputStream content;

  public Code(TemplateMetadata metadata, TemplateContext context, TemplateEngine engine) {
    this.metadata = metadata;
    this.context = context;
    this.engine = engine;
    context.add(metadata).add(this);
  }

  public String getResourceName() {
    String fileName = this.metadata.getFileName().toString();

    int dotIndex = fileName.indexOf(".");
    if (dotIndex > -1) {
      return fileName.substring(0, dotIndex);
    }

    return fileName;
  }

  public void setContent(InputStream content) {
    this.content = content;
  }

  public GeneratedCode toGeneratedCode() {
    Namespace namespace = this.metadata.getNamespace().merge(this.engine, this.context);
    Filename filename =  this.metadata.getFileName().merge(this.engine, this.context);

    QualifiedName qualifiedName = QualifiedName.create(namespace, filename);

    GeneratedCode generatedCode = new GeneratedCode();
    generatedCode.setContent(this.content);
    generatedCode.setFileName(filename);
    generatedCode.setQualifiedName(qualifiedName);
    generatedCode.setPath(namespace.toPath());
    generatedCode.setNamespace(namespace);
    generatedCode.setSimpleName(this.getResourceName());
    generatedCode.setResource(this.metadata.getResource());
    generatedCode.setTestArtifact(this.metadata.getTestArtifact());

    return generatedCode;
  }

  @Override
  public String key() {
    return "Code";
  }
}
