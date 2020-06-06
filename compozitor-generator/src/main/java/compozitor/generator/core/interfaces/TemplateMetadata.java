package compozitor.generator.core.interfaces;

import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateContextData;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TemplateMetadata implements TemplateContextData<TemplateMetadata> {
  @Setter
  private String criteria;
  @Setter
  private Filename fileName;
  @Setter
  private Namespace namespace;
  @Setter
  private Template template;

  private Boolean enabled;
  private Boolean resource;
  private Boolean testArtifact;

  TemplateMetadata() {
    this.criteria = "";
    this.enabled = true;
  }

  public static TemplateMetadata regularTemplate() {
    TemplateMetadata metadata = new TemplateMetadata();
    metadata.resource = false;
    metadata.testArtifact = false;
    return metadata;
  }

  public static TemplateMetadata regularResourceTemplate() {
    TemplateMetadata metadata = new TemplateMetadata();
    metadata.resource = true;
    metadata.testArtifact = false;
    return metadata;
  }

  public static TemplateMetadata testTemplate() {
    TemplateMetadata metadata = new TemplateMetadata();
    metadata.testArtifact = true;
    metadata.resource = false;
    return metadata;
  }

  public static TemplateMetadata testResourceTemplate() {
    TemplateMetadata metadata = new TemplateMetadata();
    metadata.testArtifact = true;
    metadata.resource = true;
    return metadata;
  }

  public void enable() {
    this.enabled = true;
  }

  public void disable() {
    this.enabled = false;
  }

  @Override
  public String key() {
    return "Template";
  }
}
