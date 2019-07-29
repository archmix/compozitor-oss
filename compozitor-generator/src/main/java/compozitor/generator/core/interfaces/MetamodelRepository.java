package compozitor.generator.core.interfaces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import compozitor.template.core.interfaces.TemplateContextData;

public abstract class MetamodelRepository<T> implements Iterable<T> {
	private final List<T> dataList;
	
	public MetamodelRepository() {
		this.dataList = new ArrayList<>();
	}
	
	public void add(T data) {
		this.dataList.add(data);
	}
	
	@Override
	public Iterator<T> iterator() {
		return this.dataList.iterator();
	}
	
	public Stream<T> stream(){
		return this.dataList.stream();
	}
	
	public abstract TemplateContextData toTemplateContextData(T value);
}
