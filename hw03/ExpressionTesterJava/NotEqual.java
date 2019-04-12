
public class NotEqual implements Comp {
	@Override
	public Bool compare(Number value1, Number value2) {
		return new Bool(value1.getValue() != value2.getValue());
	}
}
