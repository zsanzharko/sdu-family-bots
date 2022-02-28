package kz.sdu.service;

import kz.sdu.entity.LostAbstractItem;
import kz.sdu.repository.LostItemRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public final class LostItemService {
    private final LostItemRepository repository;

    @Autowired
    public LostItemService(LostItemRepository repository) {
        this.repository = repository;
    }

    public LostAbstractItem findById(Long id) {
        return repository.getById(id);
    }

    public List<LostAbstractItem> findAll() {
        return repository.findAll();
    }

    public LostAbstractItem saveLostItem(LostAbstractItem item) {
        return repository.save(item);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
