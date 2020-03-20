package compozitor.generator.core.interfaces;

import compozitor.template.core.infra.StringInputStream;
import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateBuilder;
import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@RequiredArgsConstructor
public class Code implements TemplateContextData<Code> {
  private final TemplateMetadata metadata;

  private final TemplateContext context;

  private final TemplateEngine engine;

  private InputStream content;

  public String getResourceName() {
    String fileName = this.toFileName().toString();

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
    Namespace namespace = this.namespace();
    Filename filename = this.toFileName();

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

  private Namespace namespace() {
    context.add(this.metadata).add(this);
    String namespaceValue = this.getTemplate(this.metadata.getNamespace().toString()).mergeToString(context);
    return Namespace.create(namespaceValue);
  }

  @Override
  public String key() {
    return "Code";
  }

  private Filename toFileName() {
    context.add(this.metadata).add(this);
    String filenameValue = this.getTemplate(this.metadata.getFileName().toString()).mergeToString(context);
    return Filename.create(filenameValue);
  }

  private Template getTemplate(String attribute) {
    return TemplateBuilder.create(this.engine, "Code").withResourceLoader(new StringInputStream(attribute))
      .build();
  }
}
