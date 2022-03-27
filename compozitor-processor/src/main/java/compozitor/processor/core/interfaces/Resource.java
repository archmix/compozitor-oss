package compozitor.processor.core.interfaces;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

public class Resource {

    public static String path(String root, String... nodes) {
        String parent = root;
        if(parent.endsWith("/")){
            parent = parent.substring(0, parent.length() - 1);
        }

        StringBuilder path = new StringBuilder(parent);
        if(nodes != null) {
            Arrays.asList(nodes).forEach(node -> path.append("/").append(node));
        }
        return path.toString();
    }

    @RequiredArgsConstructor(staticName = "create")
    public static class ResourceName implements Name {
      private final String value;

      @Override
      public String value() {
        return this.value;
      }

      @Override
      public String toString() {
        return this.value;
      }
    }
}
