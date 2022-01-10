package com.zigzag.auction.controller;

import com.zigzag.auction.dto.response.EagerLotResponseDto;
import com.zigzag.auction.dto.response.LotResponseDto;
import com.zigzag.auction.exception.AuctionException;
import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.exception.RequestValidationException;
import com.zigzag.auction.lib.ApiPageable;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.LotService;
import com.zigzag.auction.service.ProductService;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.EagerLotMapper;
import com.zigzag.auction.service.mapper.LotMapper;
import com.zigzag.auction.util.DateTimeUtil;
import io.swagger.annotations.ApiOperation;
import java.math.BigInteger;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lots")
public class LotController {
    private final UserService userService;
    private final LotService lotService;
    private final ProductService productService;
    private final LotMapper mapper;
    private final EagerLotMapper eagerLotMapper;

    public LotController(UserService userService, LotService lotService,
                         ProductService productService, LotMapper mapper,
                         EagerLotMapper eagerLotMapper) {
        this.userService = userService;
        this.lotService = lotService;
        this.productService = productService;
        this.mapper = mapper;
        this.eagerLotMapper = eagerLotMapper;
    }

    @GetMapping
    @ApiOperation(value = "Returns all lots with pagination.")
    @ApiPageable
    public Page<LotResponseDto> getAll(Pageable pageable) {
        return lotService.getAllWithPagination(pageable).map(mapper::mapToDto);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns a lot by id with bids.")
    public EagerLotResponseDto get(@PathVariable Long id) {
        return eagerLotMapper.mapToDto(lotService.get(id));
    }

    @PostMapping
    @ApiOperation(value = "Creates a lot by product id and start price.",
            notes = "User have to be authenticated.")
    public LotResponseDto createLot(Authentication auth, @RequestParam Long productId,
                                    @RequestParam BigInteger startPrice) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.findByEmail(details.getUsername());

        Product product = productService.get(productId);
        if (product.getOwner() == null || !product.getOwner().getEmail().equals(user.getEmail())) {
            throw new RequestValidationException("User don't have product with id: " + productId);
        }
        product.setOwner(null);
        productService.update(product);
        LocalDateTime now = DateTimeUtil.getCurrentUtcLocalDateTime();
        Lot lot = new Lot(user, product, now,
                now.plusDays(DateTimeUtil.DEFAULT_LOT_DURATION_DAYS),
                startPrice, startPrice, true);
        return mapper.mapToDto(lotService.create(lot));
    }

    @PostMapping("/like")
    @ApiOperation(value = "Add a lot to liked lots of a user.",
            notes = "User have to be authenticated.")
    public void createLot(Authentication auth, @RequestParam Long lotId) throws AuctionException {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.findFullUserInfoByEmail(details.getUsername());

        try {
            Lot lot = lotService.get(lotId);
            user.getLikedLots().add(lot);
            userService.update(user);
        } catch (DataProcessingException e) {
            throw new AuctionException(String.format("Can't find lot with id: %s", lotId));
        }
    }
}
