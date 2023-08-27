package storage.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import storage.entity.Wishlist;
import storage.service.WishlistService;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@AllArgsConstructor
public class WishlistController {

    private WishlistService wishlistService;

    @PostMapping("/add")
    public ResponseEntity<Wishlist> addToWishlist(@RequestBody Wishlist wishlistItem) {
        Wishlist addedItem = wishlistService.addToWishlist(wishlistItem);
        return ResponseEntity.ok(addedItem);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long id) {
        wishlistService.removeFromWishlist(id);
        return ResponseEntity.ok("Item removed from wishlist");
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> getWishlist() {
        List<Wishlist> wishlist = wishlistService.getWishlist();
        return ResponseEntity.ok(wishlist);
    }
}
