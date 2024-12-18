package com.sheryians.major.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Category {
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private int id;
    private String name;
}
