package compozitor.generator.engine.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;

import compozitor.template.engine.interfaces.Template;
import compozitor.template.engine.interfaces.TemplateBuilder;
import compozitor.template.engine.interfaces.TemplateBuilder.TemplateProxy;
import compozitor.template.engine.interfaces.VelocityContextBuilder;

public abstract class AbstractGenerator<T> {
	
	public final List<TemplateSource> execute(Template config) {
		VelocityContext context = VelocityContextBuilder.create().add(config.toKeyValue()).build();
		this.append(context);

		TemplateProxy template = TemplateBuilder.create(config.getName()).withResourceLoader(config.getCode())
				.build();

		List<TemplateSource> list = new ArrayList<TemplateSource>();
		for (T logic : this.getCollection()) {
			context.put(this.getContextName(), logic);
			
			if(!this.accept(config, context)){
				continue;
			}
			
			TemplateSource source = this.createSource(logic, config);
			context.put("Source", source);

			source.setContent(template.mergeToStream(context));

			list.add(source);
		}

		return list;
	}

	private TemplateSource createSource(T logic, Template config) {
		return new TemplateSource(this.getName(logic), config);
	}

	protected void append(VelocityContext context) {
		return;
	}
	
	private boolean accept(Template template, VelocityContext context){
		if(template.getCriteria() == null || template.getCriteria().isEmpty()){
			return true;
		}
		
		String criteria = "#if("+ template.getCriteria() +") true #else false #end";
	
		TemplateProxy proxy = TemplateBuilder.create("criteria").withResourceLoader(criteria).build();
		String mergeToString = proxy.mergeToString(context);
		
		return mergeToString.trim().equals("true");
	}

	protected abstract String getName(T logic);

	protected abstract String getContextName();

	protected abstract Iterable<T> getCollection();
}