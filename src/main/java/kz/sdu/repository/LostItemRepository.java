package kz.sdu.repository;

import kz.sdu.entity.LostAbstractItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostItemRepository extends JpaRepository<LostAbstractItem, Long> {
//    List<LostAbstractItem> findAllBy(Long ID);
}
