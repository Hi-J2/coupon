package com.kakao.pay.coupon.controller.api;

import com.kakao.pay.coupon.entity.Coupon;
import com.kakao.pay.coupon.service.CouponService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponApiController {

	@Autowired
	CouponService couponService;

	@GetMapping("/")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
					value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
					value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
					value = "Sorting criteria in the format: property(,asc|desc). " +
							"Default sort order is ascending. " +
							"Multiple sort criteria are supported.")
	})
	public Page<Coupon> findAll(@PageableDefault(size=10, sort="id",direction = Sort.Direction.DESC) Pageable pageable) {
		return couponService.findAll(pageable);
	}


	@GetMapping("/{id}")
	public Coupon findOne(@PathVariable Long id) {
		return couponService.findOne(id);
	}

	@PostMapping("/")
	public Coupon create(@RequestBody Coupon coupon) {
		return couponService.create(coupon);
	}

	@PutMapping("/use/{code}")
	public Coupon use(@PathVariable String code) {
		return couponService.useCoupon(code);
	}

}
