package compozitor.processor.core.interfaces;

import static com.google.testing.compile.Compiler.javac;

import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationBuilder {
	private Processor[] processors;
	
	private JavaFileObject javaSource;
	
	public static CompilationBuilder create() {
		return new CompilationBuilder();
	}
	
	public CompilationBuilder withProcessors(Processor... processors) {
		this.processors = processors;
		return this;
	}
	
	public CompilationBuilder withJavaSource(String resourceName) {
		this.javaSource = JavaFileObjects.forResource(resourceName);
		return this;
	}
	
	public Compilation build() {
		return javac().withProcessors(this.processors).compile(this.javaSource);
	}
}
