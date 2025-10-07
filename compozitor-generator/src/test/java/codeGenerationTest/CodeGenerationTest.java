package codeGenerationTest;

import compozitor.generator.core.interfaces.CodeGenerationCategoryContext;
import compozitor.generator.core.interfaces.CodeGenerationCategoryEngine;
import compozitor.generator.core.interfaces.Filename;
import compozitor.generator.core.interfaces.GeneratedCode;
import compozitor.generator.core.interfaces.MetaModelRepository;
import compozitor.generator.core.interfaces.Namespace;
import compozitor.generator.core.interfaces.TemplateMetadata;
import compozitor.template.core.interfaces.TemplateEngine;
import compozitor.template.core.interfaces.TemplateEngineBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CodeGenerationTest {
  @Test
  public void givenUTF8StringWhenGenerateThenEncodingIsOk() {
    TemplateEngine templateEngine = TemplateEngineBuilder.create().withClasspathTemplateLoader().build();

    TemplateMetadata template = TemplateMetadata.regularTemplate();
    template.setTemplate(templateEngine.getTemplate("codeGenerationTest/Encoded.ctf"));
    template.setFileName(Filename.create(""));
    template.setNamespace(Namespace.root());

    MetaModelRepository<Dictionary> repository = MetaModelRepository.create();
    String expectedMessage = "Uma sentença contendo acentos será produzida no código final com sucesso.";
    repository.add(Dictionary.create(expectedMessage));

    CodeGenerationCategoryContext<Dictionary> context = CodeGenerationCategoryContext.create();
    context.add(DictionaryCategory.INSTANCE, template);
    context.add(DictionaryCategory.INSTANCE, repository);

    CodeGenerationCategoryEngine.<Dictionary>create().generate(templateEngine, context, code -> {
      Assert.assertEquals(expectedMessage, toString(code));
    });
  }

  private static String toString(GeneratedCode code) {
    try {
      return new String(code.getContent().readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
