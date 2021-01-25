package com.demo.resource.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class ResourceController {
	
	ArrayList<ResourceEntity> list;
	
	@PostConstruct
	public void init() {
		list = new ArrayList<ResourceEntity>();
		list.add(new ResourceEntity(1, "aaaa"));
		list.add(new ResourceEntity(2, "bbbb"));
		list.add(new ResourceEntity(3, "cccc"));
		list.add(new ResourceEntity(4, "dddd"));
	}
	
	@GetMapping(value = "/{id}")
	public ResourceEntity findOne(@PathVariable int id) {
		return list.get(id);
	}
	
	@GetMapping
	public List<ResourceEntity> findAll(){
		return list;
	}
	
	@PostMapping
	public void create(@RequestBody ResourceEntity resource) {
		list.add(resource);
	}

}
