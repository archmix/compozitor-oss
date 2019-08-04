package compozitor.processor.core.interfaces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

class ModelIterable<M> implements Iterable<M> {
	private List<M> models = new ArrayList<>();

	@Override
	public Iterator<M> iterator() {
		return this.models.iterator();
	}

	public Stream<M> stream() {
		return this.models.stream();
	}

	void add(M model) {
		this.models.add(model);
	}

	public boolean contains(M model) {
		return this.models.contains(model);
	}

	public Optional<M> get(Predicate<M> predicate) {
		return this.stream().filter(predicate).findFirst();
	}
}
