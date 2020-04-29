package compozitor.processor.core.interfaces;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.google.testing.compile.Compiler.*;

public class CompilationBuilder {
  private final List<Processor> processors;

  private final List<JavaFileObject> javaSource;

  public CompilationBuilder() {
    this.processors = new ArrayList<>();
    this.javaSource = new ArrayList<>();
  }

  public static CompilationBuilder create() {
    return new CompilationBuilder();
  }

  public CompilationBuilder withProcessors(Processor... processors) {
    Objects.requireNonNull(processors);
    this.processors.addAll(Arrays.asList(processors));
    return this;
  }

  public CompilationBuilder withJavaSource(String resourceName) {
    return this.withJavaSources(resourceName);
  }

  public CompilationBuilder withJavaSources(String... resourceName) {
    Objects.requireNonNull(resourceName);
    Arrays.asList(resourceName).forEach(javaSourceName ->{
      this.javaSource.add(JavaFileObjects.forResource(javaSourceName));
    });

    return this;
  }

  public CompileAssertion build() {
    final Compilation compilation = javac().withProcessors(this.processors).compile(this.javaSource);
    compilation.diagnostics().forEach(System.out::println);
    compilation.errors().forEach(System.out::println);

    return CompileAssertion.create(compilation);
  }
}
