package compozitor.template.core.interfaces;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public abstract class Directive extends org.apache.velocity.runtime.directive.Directive {
  public static final String DIRECTIVE_FILE_EXTENSION = ".cdf";

  private TemplateEngine engine;

  @Override
  public String getName() {
    return StringUtils.uncapitalize(this.getClass().getSimpleName());
  }

  @Override
  public void init(RuntimeServices rs, InternalContextAdapter context, Node node)
    throws TemplateInitException {
    super.init(rs, context, node);
    this.engine = new TemplateEngine(rs);
  }

  @Override
  public final boolean render(InternalContextAdapter context, Writer writer, Node node)
    throws ResourceNotFoundException {
    try {
      List<Variable> variables = new ArrayList<Variable>();

      for (int i = 0; i < node.jjtGetNumChildren(); i++) {
        Variable variable =
          new Variable(node.jjtGetChild(i).literal(), node.jjtGetChild(i).value(context));
        variables.add(variable);
      }

      String generated = this.doRender(this.engine, variables);
      writer.write(generated);

      return true;
    } catch (Exception e) {
      throw new ResourceNotFoundException(e);
    }
  }

  protected abstract String doRender(TemplateEngine engine, List<Variable> variables);

  @SuppressWarnings("unchecked")
  public static class Variable {
    private String name;

    private Object value;

    public Variable(String name, Object value) {
      super();
      this.name = name.replace("$", "").replace("{", "").replace("}", "");
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public <T> T getValue() {
      return (T) value;
    }
  }
}
