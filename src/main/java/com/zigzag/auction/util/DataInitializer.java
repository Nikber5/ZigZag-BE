package com.zigzag.auction.util;

import com.zigzag.auction.exception.AuctionException;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.Role;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.AuthenticationService;
import com.zigzag.auction.service.BidService;
import com.zigzag.auction.service.LotService;
import com.zigzag.auction.service.ProductService;
import com.zigzag.auction.service.RoleService;
import com.zigzag.auction.service.UserService;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;
    private final ProductService productService;
    private final LotService lotService;
    private final AuthenticationService authenticationService;
    private final BidService bidService;

    public DataInitializer(UserService userService, RoleService roleService,
                           ProductService productService, LotService lotService,
                           AuthenticationService authenticationService, BidService bidService) {
        this.userService = userService;
        this.roleService = roleService;
        this.productService = productService;
        this.lotService = lotService;
        this.authenticationService = authenticationService;
        this.bidService = bidService;
    }

    @PostConstruct
    public void init() throws AuctionException {
        // Adding all roles
        roleService.add(new Role(Role.RoleName.ROLE_ADMIN));
        roleService.add(new Role(Role.RoleName.ROLE_USER));

        // creating mock users
        User bob = new User("Bob", "bob@gmail.com", "12345");
        bob.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_USER)));

        User alice = new User("Alice", "alice@gmail.com", "12345");
        alice.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_ADMIN)));

        userService.create(bob);
        userService.create(alice);

        User john = authenticationService.register(new User("John", "John@gmail.com", "12345678"));

        for (int i = 1; i < 41; i++) {
            Product product = createProduct("New product # " + i, "Description for product #" + i, bob);
            if (i < 6) {
                User owner = product.getOwner();
                product.setOwner(null);
                productService.update(product);
                BigInteger price = BigInteger.valueOf(1000);
                LocalDateTime now = DateTimeUtil.getCurrentUtcLocalDateTime();
                Lot lot = new Lot(owner, product, now,
                        now.plusDays(DateTimeUtil.DEFAULT_LOT_DURATION_DAYS),
                        price, price, true);
                lotService.create(lot);
                if (i < 5) {
                    makeABet(lot, john);
                }
            }
        }
    }

    private Product createProduct(String name, String description, User owner) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setOwner(owner);
        return productService.create(product);
    }

    public void makeABet(Lot lot, User user) throws AuctionException {
        BigInteger bidSum = BigInteger.valueOf(1100);
        bidService.makeABet(user, lot, bidSum);
        lot.setHighestPrice(bidSum);
        lotService.update(lot);
    }
}
