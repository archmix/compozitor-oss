package compozitor.template.core.interfaces;

import toolbox.resources.interfaces.StringInputStream;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class TemplateBuilder {
  private final org.apache.velocity.Template target;

  private TemplateBuilder(String name, TemplateEngine templateEngine) {
    this.target = new org.apache.velocity.Template();
    this.target.setRuntimeServices(templateEngine.getRuntimeServices());
    this.target.setName(name);
  }

  public static TemplateBuilder create(String templateName) {
    return new TemplateBuilder(templateName, TemplateEngineBuilder.create().build());
  }

  public static TemplateBuilder create(TemplateEngine templateEngine, String templateName) {
    return new TemplateBuilder(templateName, templateEngine);
  }

  public TemplateBuilder withResourceLoader(File file) {
    try {
      byte[] fileBytes = Files.readAllBytes(file.toPath());
      InputStream inputStream = new ByteArrayInputStream(fileBytes);
      return this.withResourceLoader(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public TemplateBuilder withResourceLoader(String loader) {
    return withResourceLoader(new StringInputStream(loader));
  }

  public TemplateBuilder withResourceLoader(byte[] bytes) {
    return withResourceLoader(new ByteArrayInputStream(bytes));
  }

  public TemplateBuilder withResourceLoader(InputStream inputStream) {
    this.target.setResourceLoader(new InputStreamLoader(inputStream));
    return this;
  }

  public Template build() {
    try {
      this.target.process();
      return new Template(this.target);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  class InputStreamLoader extends org.apache.velocity.runtime.resource.loader.ResourceLoader {
    private InputStream input;

    public InputStreamLoader(InputStream input) {
      this.input = input;
    }

    @Override
    public long getLastModified(Resource resource) {
      return 0;
    }

    @Override
    public InputStream getResourceStream(String resource) throws ResourceNotFoundException {
      return this.input;
    }

    @Override
    public void init(ExtendedProperties properties) {
      return;
    }

    @Override
    public boolean isSourceModified(Resource resource) {
      return false;
    }
  }
}
