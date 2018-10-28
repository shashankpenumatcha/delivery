package io.shashank.penumatcha.delivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.shashank.penumatcha.delivery.domain.InventoryLog;
import io.shashank.penumatcha.delivery.domain.Product;
import io.shashank.penumatcha.delivery.domain.UserProfile;
import io.shashank.penumatcha.delivery.repository.InventoryLogRepository;
import io.shashank.penumatcha.delivery.repository.ProductRepository;
import io.shashank.penumatcha.delivery.repository.UserProfileRepository;
import io.shashank.penumatcha.delivery.web.rest.errors.BadRequestAlertException;
import io.shashank.penumatcha.delivery.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "product";

    private final ProductRepository productRepository;
    private final UserProfileRepository userProfileRepository;
    private final InventoryLogRepository inventoryLogRepository;

    public ProductResource(ProductRepository productRepository,UserProfileRepository userProfileRepository,InventoryLogRepository inventoryLogRepository) {
        this.productRepository = productRepository;
        this.userProfileRepository=userProfileRepository;
        this.inventoryLogRepository=inventoryLogRepository;
    }

    /**
     * POST  /products : Create a new product.
     *
     * @param product the product to create
     * @return the ResponseEntity with status 201 (Created) and with body the new product, or with status 400 (Bad Request) if the product has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/products")
    @Timed
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws URISyntaxException {
        log.debug("REST request to save Product : {}", product);
        if (product.getId() != null) {
            throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Product result = productRepository.save(product);
        if(null!=product.getQuantity()&&product.getQuantity()>0){
            InventoryLog inventoryLog = new InventoryLog();
            inventoryLog.setAdded(true);
            inventoryLog.setDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
            inventoryLog.setProduct(product);
            inventoryLog.setQuantity(product.getQuantity());
            String login = getCurrentUserLogin();
            UserProfile userProfile = userProfileRepository.findUserProfileByLogin(login);
            if(userProfile!=null){
                inventoryLog.setUserProfile(userProfile);
            }
            inventoryLogRepository.save(inventoryLog);
        }
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    public String getCurrentUserLogin() {
        org.springframework.security.core.context.SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String login = null;
        if (authentication != null)
            if (authentication.getPrincipal() instanceof UserDetails)
                login = ((UserDetails) authentication.getPrincipal()).getUsername();
            else if (authentication.getPrincipal() instanceof String)
                login = (String) authentication.getPrincipal();

        return login;
    }
    /**
     * PUT  /products : Updates an existing product.
     *
     * @param product the product to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated product,
     * or with status 400 (Bad Request) if the product is not valid,
     * or with status 500 (Internal Server Error) if the product couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/products")
    @Timed
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws URISyntaxException {
        log.debug("REST request to update Product : {}", product);
        if (product.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<Product> oldProduct = productRepository.findById(product.getId());
        Float oldQuantity = oldProduct.get().getQuantity();
        Product result = productRepository.save(product);
        if(Float.compare(product.getQuantity(),oldQuantity)!=0){
            InventoryLog inventoryLog = new InventoryLog();
            inventoryLog.setDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
            inventoryLog.setProduct(product);
            String login = getCurrentUserLogin();
            UserProfile userProfile = userProfileRepository.findUserProfileByLogin(login);
            if(userProfile!=null){
                inventoryLog.setUserProfile(userProfile);
            }
            if(Float.compare(product.getQuantity(),oldQuantity)>0){
                inventoryLog.setAdded(true);
                inventoryLog.setQuantity(product.getQuantity()-oldQuantity);
            }else if(Float.compare(product.getQuantity(),oldQuantity)<0){
                inventoryLog.setRemoved(true);
                inventoryLog.setQuantity(oldQuantity-product.getQuantity());
            }

            inventoryLogRepository.save(inventoryLog);
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, product.getId().toString()))
            .body(result);
    }

    /**
     * GET  /products : get all the active products.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of active products in body
     */
    @GetMapping("/products")
    @Timed
    public List<Product> getAllProducts() {
        log.debug("REST request to get all Products");
        return productRepository.findAll();
    }



    /**
     * GET  /products/:id : get the "id" product.
     *
     * @param id the id of the product to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the product, or with status 404 (Not Found)
     */
    @GetMapping("/products/{id}")
    @Timed
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        Optional<Product> product = productRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(product);
    }

    /**
     * DELETE  /products/:id : delete the "id" product.
     *
     * @param id the id of the product to delete
     * @return the ResponseEntity with status 200 (OK)
     */
/*    @DeleteMapping("/products/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);

        productRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }*/
}
