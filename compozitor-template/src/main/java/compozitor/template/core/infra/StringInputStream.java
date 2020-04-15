package compozitor.template.core.infra;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringInputStream extends InputStream {
  private final byte[] value;

  private int index;

  public StringInputStream(String value){
    this(value, StandardCharsets.UTF_8);
  }

  public StringInputStream(String value, Charset charset) {
    this.index = 0;
    this.value = value.getBytes(charset);
  }

  public int available() {
    return this.value.length;
  }

  public int read() {
    if (this.index == this.value.length) {
      return -1;
    }

    return this.value[this.index++];
  }

  public int read(byte buf[]) {
    return read(buf, 0, buf.length);
  }

  public int read(byte buf[], int offset, int len) {
    if (this.index == this.value.length) {
      return -1;
    }

    len = Math.min(len, available());
    for (int i = 0; i < len; i++) {
      buf[i + offset] = this.value[this.index++];
    }

    return len;
  }
}
