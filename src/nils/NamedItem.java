package nils;

public class NamedItem {
	private String name;

	public NamedItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLowercaseName() {
		return this.name.toLowerCase();
	}
	
	public String getCamelcaseName() {
		String[] parts = this.name.split("\\s+");
		StringBuilder buffer = new StringBuilder();
		for (String s : parts) {
			buffer.append(s.substring(0, 1).toUpperCase()).append(s.substring(1).toLowerCase());
		}
		return buffer.toString();
	}
	
	@Override
	public String toString() {
		return name;
	}
}
