package kz.sdu.entity.abstractBase;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractItem extends AbstractBaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public AbstractItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public AbstractItem() {
    }
}
