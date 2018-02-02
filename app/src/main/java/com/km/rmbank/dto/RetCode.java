package com.km.rmbank.dto;

public enum RetCode {
	
//	SUCCESS("成功", "1"),
	SUCCESS("成功", "00000000"),

	MOBILE_PHONE_IS_NULL("手机号不可为空", "00000001"),
	
	SMS_SEND_OUT_OF_LIMIT("短信一分钟内只可发送一次", "00000002"),
	
	SMS_CODE_IS_NULL("短信验证码为空", "00000003"),
	
	LOGIN_PWD_IS_NULL("登录密码为空", "00000004"),
	
	SMS_CODE_EXPIRED("短信验证码已过期", "00000005"),
	
	SMS_CODE_IS_ERROR("短信验证码错误", "00000006"),
	
	LONGITUDE_IS_NULL("经度不可为空", "00000007"),
	
	LATITUDE_IS_NULL("纬度不可为空", "00000008"),
	
	MOBILE_PHONE_NOT_EXIST("手机号不存在", "00000009"),
	
	LOGIN_PWD_ERROR("登录密码错误", "00000010"),
	
	BANNER_TYPE_IS_NULL("bannerType不可为空", "00000011"),
	
	BANNER_TYPE_IS_ERROR("bannerType错误", "00000012"),
	
	USER_ID_IS_NULL("用戶id不可为空", "00000013"),
	
	USER_IS_NOT_LOGIN("用戶尚未登录", "00000014"),
	
	PAGE_NO_IS_NULL("页号不可为空", "00000015"),
	
	PAGE_SIZE_IS_NULL("每页大小不可为空", "00000016"),
	
	CITY_NAME_IS_NULL("城市名称不可为空", "00000017"),
	
	MOBILE_PHONE_ALREADY_REGISTER("手机号已经注册", "00000018"),
	
	CITY_PARENT_ID_IS_NULL("城市的parentId不可为空", "00000019"),
	
	UPLOAD_FILE_IS_NULL("上传图片为空", "00000020"),
	
	CREATE_FLODER_FAIL("上传图片失败", "00000021"),
	
	UPLOAD_FILE_OPTION_TYPE_IS_NULL("上传图片的操作类型为空", "00000022"),
	
	UPLOAD_FILE_OPTION_TYPE_IS_ERROR("上传图片的操作类型错误", "00000023"),
	
	ALREADY_JOIN_SHOP("您已经申请过注册商家", "00000024"),
	
	PRODUCT_NO_IS_NULL("商品编号不可为空", "00000025"),
	
	PRODUCT_ALREADY_KEEP("该商品已经收藏", "00000026"),
	
	PRODUCT_ALREADY_ADD_SHOP_CAR("该商品已经加入购物车", "00000027"),
	
	RECEIVE_PERSON_IS_NULL("收货人不可为空", "00000028"),
	
	RECEIVE_PERSON_PHONE_IS_NULL("收货人手机号不可为空", "00000029"),
	
	RECEIVE_ADDRESS_IN_NULL("收货地址不可为空", "00000030"),
	
	RECEIVE_ADDRESS_ID_IN_NULL("收货地址id不可为空", "00000031"),
	
	PRODUCT_ALREADY_UNDER_THE_SHELF("该商品已经下架", "00000032"),
	
	PRODUCT_COUNT_IS_NULL("商品数量不可为空", "00000033"),
	
	RECEIVE_ADDRESS_ID_IS_NULL("收货地址id不可为空", "00000034"),
	
	START_RECEIVE_DATE_ID_IS_NULL("收货开始时间不可为空", "00000035"),
	
	END_RECEIVE_DATE_IS_NULL("收货结束时间不可为空", "00000036"),
	
	RECEIVE_ADDRESS_ID_ERROR("收货地址id错误", "00000037"),
	
	PRODUCT_NO_ERROR("商品编号错误", "00000038"),
	
	ORDER_NO_IS_NULL("订单编号不可为空", "00000039"),
	
	ORDER_NO_ERROR("订单编号错误", "00000040"),
	
	PRODUCT_STOCK_COUNT_NOT_ENOUGH("商品库存量不足", "00000041"),
	
	CITY_ID_IS_NULL("城市id不可为空", "00000042"),
	
	CITY_ID_ERROR("城市id错误", "00000043"),
	
	NOTICE_ID_IS_NULL("消息id为空", "00000044"),
	
	SHOP_CAR_OPTION_TYPE_IS_NULL("购物车操作类型不可为空", "00000045"),
	
	SHOP_CAR_OPTION_TYPE_IS_ERROR("购物车操作类型错误", "00000046"),
	
	IS_USER_SELF_PICK_IS_NULL("是否自提不可为空", "00000047"),
	
	USER_IS_FIRST_BUY("用户第一次购买", "00000048"),
	
	SYS_ERROR("系统异常", "10000000");
	
	private String message;
	
	private String status;
	
	private RetCode(String message, String status){
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
	
