package compozitor.processor.core.interfaces;

import static com.google.testing.compile.Compiler.javac;

import javax.tools.JavaFileObject;

import org.junit.Test;

import com.google.testing.compile.JavaFileObjects;

import compozitor.processor.core.application.TypeProcessor;

public class CompozitorProcessorTest {
	@Test
	public void givenTypeModelAnnotatedWithTypeAnnotationWhenCompileThenProcessType() {
		JavaFileObject modelSource = JavaFileObjects.forResource("compozitor/processor/core/test/TypeModel.java");

		javac().withProcessors(new TypeProcessor()).compile(modelSource);
	}
}
