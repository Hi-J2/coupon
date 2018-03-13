package com.kakao.pay.coupon.entity;


import com.kakao.pay.coupon.common.StatusEnum;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="Coupon")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Column(unique = true)
	private String email;

//	@Column(unique = true)
	private String code;

	@Enumerated(EnumType.STRING)
	@Column
	private StatusEnum status = StatusEnum.N;

	@Column
	private Date useDate;

	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@UpdateTimestamp
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;


}
