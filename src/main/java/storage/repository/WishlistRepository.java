package storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import storage.entity.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

}
