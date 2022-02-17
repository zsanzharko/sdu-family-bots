package kz.sdu.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class Item {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Item() {
    }
}
