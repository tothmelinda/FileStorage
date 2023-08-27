package storage.service;

import storage.entity.Wishlist;

import java.util.List;

public interface WishlistService {

    Wishlist addToWishlist(Wishlist wishlistItem);

    void removeFromWishlist(Long itemId);

    List<Wishlist> getWishlist();
}
