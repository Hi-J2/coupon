package com.kakao.pay.coupon.service;

import com.kakao.pay.coupon.common.ErrorCode;
import com.kakao.pay.coupon.common.StatusEnum;
import com.kakao.pay.coupon.entity.Coupon;
import com.kakao.pay.coupon.exception.ApiException;
import com.kakao.pay.coupon.lib.CommonLib;
import com.kakao.pay.coupon.lib.CouponLib;
import com.kakao.pay.coupon.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	CouponRepository couponRepository;

	@Override
	public Page<Coupon> findAll(Pageable pageable) {
		return couponRepository.findAll(pageable);
	}

	@Override
	public Coupon findOne(Long id) {
		return couponRepository.findOne(id);
	}

	@Override
	@Transactional
	public Coupon create(Coupon coupon) {
		validEmail(coupon.getEmail());
		validDuplicateEmail(coupon.getEmail());

		Coupon generateCoupon = couponRepository.save(coupon);
		String code = CouponLib.generateCode(generateCoupon.getId().intValue());
		coupon.setCode(code);

		return couponRepository.save(coupon);
	}

	@Override
	@Transactional
	public Coupon useCoupon(String code) {

		// 쿠폰 유효성 체크
		invalidCode(code);

		Long id = Long.valueOf(CouponLib.restoreSeq(code));
		Coupon coupon = this.findOne(id);

		// 쿠폰 validation
		useCouponValidate(coupon, code);


		coupon.setStatus(StatusEnum.Y);
		return couponRepository.save(coupon);
	}

	private Coupon findOneByEmail(String email) {
		return couponRepository.findOneByEmail(email);
	}


	private void validEmail(String email) {
		if(!CommonLib.isEmailFormat(email)){
			throw new ApiException(ErrorCode.COUPON_DUPLICATION_EMAIL);
		}
	}

	private void validDuplicateEmail(String email) {
		Coupon coupon = couponRepository.findOneByEmail(email);
		if (coupon != null) {
			throw new ApiException(ErrorCode.COUPON_DUPLICATION_EMAIL);
		}
	}

	private void invalidCode(String code){
		if (code == null || code.length() != 16 || !CouponLib.isInvalidCode(code)) {
			throw new ApiException(ErrorCode.COUPON_INVALID_CODE);
		}
	}

	private void useCouponValidate(Coupon coupon, String code) {
		if(coupon == null){
			throw new ApiException(ErrorCode.COUPON_NOT_FOUND);
		} else if(!code.equals(coupon.getCode())){
			throw new ApiException(ErrorCode.COUPON_INVALID_CODE);
		} else if(coupon.getStatus() == StatusEnum.Y){
			throw new ApiException(ErrorCode.COUPON_ALREADY_USE_COUPON);
		}
	}
}
