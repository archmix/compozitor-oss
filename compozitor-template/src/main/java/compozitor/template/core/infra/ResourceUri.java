package compozitor.template.core.infra;

import java.io.File;

public class ResourceUri {
  private File file;

  public ResourceUri(String resource) {
    this.file = new File(resource);
  }

  public ResourceUri(String parent, String child) {
    this.file = new File(parent, child);
  }

  public String toUri(String child) {
    return new ResourceUri(this.file.toString(), child).toString();
  }

  @Override
  public String toString() {
    String uri = this.file.toString().replace("\\", "/");
    if (uri.startsWith("/")) {
      return uri.substring(1);
    }

    return uri;
  }
}
