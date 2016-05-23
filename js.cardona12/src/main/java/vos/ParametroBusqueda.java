package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ParametroBusqueda {
	@JsonProperty(value="where")
	private List<String> where;
	
	@JsonProperty(value="group")
	private List<String> group;
	
	@JsonProperty(value="order")
	private List<String> order;

	public ParametroBusqueda(@JsonProperty(value="where") List<String> where,
			@JsonProperty(value="group") List<String> group,
			@JsonProperty(value="order") List<String> order) {
		super();
		this.where = where;
		this.group = group;
		this.order = order;
	}

	public List<String> getWhere() {
		return where;
	}

	public void setWhere(List<String> where) {
		this.where = where;
	}

	public List<String> getGroup() {
		return group;
	}

	public void setGroup(List<String> group) {
		this.group = group;
	}

	public List<String> getOrder() {
		return order;
	}

	public void setOrder(List<String> order) {
		this.order = order;
	}


}
