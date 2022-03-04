package com.br.gutjahr.despesas.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CrudBaseModel<T> implements Serializable {
    
    private static final long serialVersionUID = 1772391833044299961L;

    public void afterSave() {}
	
	public void beforeSave() {}

    @Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		CrudBaseModel<T> other = (CrudBaseModel<T>) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

    public abstract T getId();

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

    @JsonIgnore
	public boolean isEmpty() {
		return getId() == null;
	}

    @JsonIgnore
	public boolean isNotEmpty() {
		return !isEmpty();
	}

    @JsonIgnore
	public boolean isNew() {
		return getId() == null || (getId() instanceof Number && 
				((Number) getId()).doubleValue() <= 0);
	}

    @JsonIgnore
	public boolean isNotNew() {
		return !isNew();
	}
}