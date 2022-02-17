package kz.sdu.repository;

import kz.sdu.entity.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
    List<LostItem> findAllByAccount_ID(Long ID);
}
