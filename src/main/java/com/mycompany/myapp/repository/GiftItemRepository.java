package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.GiftItem;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GiftItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GiftItemRepository extends JpaRepository<GiftItem, Long> {
    List<GiftItem> findAllByCategory(Category category);
}
