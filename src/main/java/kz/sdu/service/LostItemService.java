package kz.sdu.service;

import kz.sdu.entity.LostItem;
import kz.sdu.repository.LostItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record LostItemService(LostItemRepository repository) {
    @Autowired
    public LostItemService {
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
