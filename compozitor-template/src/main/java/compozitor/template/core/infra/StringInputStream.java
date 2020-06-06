package compozitor.template.core.infra;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringInputStream extends ByteArrayInputStream {
  public StringInputStream(String value) {
    this(value, StandardCharsets.UTF_8);
  }

  public StringInputStream(String value, Charset charset) {
    super(value.getBytes(charset));
  }
}
