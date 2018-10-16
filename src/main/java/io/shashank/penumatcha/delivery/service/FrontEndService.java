package io.shashank.penumatcha.delivery.service;

import io.shashank.penumatcha.delivery.domain.Product;
import io.shashank.penumatcha.delivery.domain.UserProfile;
import io.shashank.penumatcha.delivery.repository.CartItemsRepository;
import io.shashank.penumatcha.delivery.repository.CartRepository;
import io.shashank.penumatcha.delivery.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class FrontEndService {

    private final Logger log = LoggerFactory.getLogger(FrontEndService.class);

    private final UserService userService;

    private final UserProfileRepository userProfileRepository;

    private final CartRepository cartRepository;

    private final CartItemsRepository cartItemsRepository;

    public FrontEndService(UserService userService, UserProfileRepository userProfileRepository,
                           CartRepository cartRepository,CartItemsRepository cartItemsRepository) {
        this.userService = userService;
        this.userProfileRepository = userProfileRepository;
        this.cartRepository=cartRepository;
        this.cartItemsRepository=cartItemsRepository;
    }

    public UserProfile getCurrentUserProfile() {
        String login = userService.getCurrentUserLogin();
        UserProfile userProfile = userProfileRepository.findUserProfileByLogin(login);
        return userProfile;

    }

    //checks if product is active,and quantity requirements are met
    public Map<String, Object> checkProductAvailability(Float quantity, Product product) {
        Map response = new HashMap<String, Object>();
        if (!product.isActive()) {
            log.debug("product not active: {}");
            response.put("error", "Product not active");
        }
        if (product.getQuantity() == null || product.getQuantity() <= 0) {
            log.debug("Product not available in inventory {}");
            response.put("error", "Product not available in inventory");
        }
        log.debug("fetching products minimum quantity : {}");
        if (product.getMinimumQuantity() != null) {
            log.debug("Checking if minim quantity requirement is met: {}");
            log.debug(quantity.toString());
            log.debug(product.getMinimumQuantity().toString());
            log.debug(product.getQuantity().toString());



            if (quantity >= product.getMinimumQuantity()
                && quantity <= product.getQuantity()) {
                log.debug("Minimum quantity requirement met: {}");
                response.put("quantity", quantity);
            } else {
                response.put("error", "Please try increasing or decreasing the quantity");
            }
        } else {
            log.debug("product doesn't have minimum quantity set, hence checking inventory if product is available: {}");
            if (quantity <= product.getQuantity()) {
                log.debug("product available : {}");
                response.put("quantity", quantity);
            } else {
                log.debug("Quantity requirements not met to add to cart for login, reduce quantity: {}");
                response.put("error", "Please try reducing the quantity");
            }
        }
        return response;
    }


}
