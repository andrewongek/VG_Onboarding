package com.onboardingassignment.oa.services;

import com.onboardingassignment.oa.model.UserFavourites;
import com.onboardingassignment.oa.repository.UserFavouritesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavouritesService {
    private final UserFavouritesRepository userFavouritesRepository;

    public UserFavouritesService(UserFavouritesRepository userFavouritesRepository) {
        this.userFavouritesRepository = userFavouritesRepository;
    }

    public boolean isFavourited(int userId, long productId) {
        return userFavouritesRepository.findByUserIdAndProductId(userId, productId).isPresent();
    }

    public void toggleFavourites(int userId, int productId, int toggle) {
        if (toggle == 1) {
            addFavourite(userId, productId);
        } else if (toggle == 0) {
            deleteUserFavourite(userId, productId);
        }
    }

    public void addFavourite(int userId, int productId) {
        if (userFavouritesRepository.findByUserIdAndProductId(userId, productId).isPresent()) {
            return; // already exists
        }

        var fav = new UserFavourites();
        fav.setUserId(userId);
        fav.setProductId(productId);
        userFavouritesRepository.save(fav);
    }

    public void deleteUserFavourite(int userId, long productId) {
        var existing = userFavouritesRepository.findByUserIdAndProductId(userId, productId);

        existing.ifPresent(userFavouritesRepository::delete);
    }

    public List<Integer> getProductIdsByUserId(int userId) {
        var userFavourites = userFavouritesRepository.findByUserId(userId);
        return userFavourites.stream().map(UserFavourites::getProductId).toList();
    }
}
