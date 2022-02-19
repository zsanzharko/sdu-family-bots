package kz.sdu.service;

import kz.sdu.entity.LostItem;
import kz.sdu.repository.LostItemRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Getter
@Setter
public final class LostItemService {
    private final LostItemRepository repository;

    @Autowired
    public LostItemService(LostItemRepository repository) {
        this.repository = repository;
    }

    public LostItem findById(Long id) {
        return repository.getById(id);
    }

    public List<LostItem> findAll() {
        return repository.findAll();
    }

    public LostItem saveLostItem(LostItem item) {
        return repository.save(item);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
