package to.kit.personal.making;

public interface InfoMaker<T> {
	public T next(String... conditions);
	public T current();
}
