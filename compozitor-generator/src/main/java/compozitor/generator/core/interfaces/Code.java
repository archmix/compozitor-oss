package compozitor.generator.core.interfaces;

import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;

import java.io.InputStream;

public class Code implements TemplateContextData<Code> {
  private final TemplateMetadata metadata;

  private final TemplateContext context;

  private final TemplateEngine engine;

  private InputStream content;

  public Code(TemplateMetadata metadata, TemplateContext context, TemplateEngine engine) {
    context.add(metadata).add(this);
    this.metadata = metadata;
    this.context = context;
    this.engine = engine;
  }

  public String getResourceName() {
    String fileName = this.metadata.getFileName().merge(this.engine, this.context).toString();

    int dotIndex = fileName.indexOf(".");
    if (dotIndex > -1) {
      return fileName.substring(0, dotIndex);
    }

    return fileName;
  }

  public Namespace getNamespace() {
    return this.metadata.getNamespace().merge(this.engine, this.context);
  }

  public void setContent(InputStream content) {
    this.content = content;
  }

  public GeneratedCode toGeneratedCode() {
    Namespace namespace = this.getNamespace();
    Filename filename = this.metadata.getFileName().merge(this.engine, this.context);

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
