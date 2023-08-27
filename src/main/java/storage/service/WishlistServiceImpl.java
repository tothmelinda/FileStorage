package storage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import storage.entity.Wishlist;
import storage.repository.WishlistRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;


    @Override
    public Wishlist addToWishlist(Wishlist wishlistItem) {
        return wishlistRepository.save(wishlistItem);
    }

    @Override
    public void removeFromWishlist(Long itemId) {
        wishlistRepository.deleteById(itemId);
    }

    @Override
    public List<Wishlist> getWishlist() {
        return wishlistRepository.findAll();
    }
}
