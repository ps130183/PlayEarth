package com.km.rmbank.api;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.dto.ActionMemberNumDto;
import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.dto.ActiveGoodsDto;
import com.km.rmbank.dto.ActiveGoodsOrderDetailDto;
import com.km.rmbank.dto.ActiveGoodsOrderListDto;
import com.km.rmbank.dto.ActiveValueDetailDto;
import com.km.rmbank.dto.ActiveValueDto;
import com.km.rmbank.dto.AppVersionDto;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.CalendarActionsDto;
import com.km.rmbank.dto.CircleFriendsDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.EvaluateDto;
import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.dto.ForumInfoDto;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.dto.HomeNewRecommendDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.dto.IntegralDetailsDto;
import com.km.rmbank.dto.IntegralDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.MasterBannerDto;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.dto.MasterOrderDto;
import com.km.rmbank.dto.MasterWorkDto;
import com.km.rmbank.dto.MemberDto;
import com.km.rmbank.dto.MemberTypeDto;
import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.dto.MyFriendsDto;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.dto.NearbyVipDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.RecommendPersonalDto;
import com.km.rmbank.dto.ScenicServiceDto;
import com.km.rmbank.dto.ServiceDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.dto.SignInDto;
import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.dto.TicketUseRecordDto;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.retrofit.Response;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;
//import rx.Observable;

/**
 * Created by kamangkeji on 17/2/10.
 */

public interface ApiService {



    /**
     * 登录
     *
     * @param mobilePhone
     * @param smsCode
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/user/registerAndLogin/send")
    Observable<Response<UserLoginDto>> login(@Field("mobilePhone") String mobilePhone,
                                             @Field("smsCode") String smsCode);

    /**
     * 注册
     *
     * @param mobilePhone
     * @param password
     * @param smsCode
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/user/register")
    Observable<Response<String>> userRegister(@Field("mobilePhone") String mobilePhone,
                                            @Field("loginPwd") String password,
                                            @Field("smsCode") String smsCode);

    /**
     * 获取手机验证码
     *
     * @param mobilePhone
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/sms/send/code")
    Observable<Response<String>> getPhoneCode(@Field("mobilePhone") String mobilePhone);

    /**
     * 忘记密码
     *
     * @param mobilePhone
     * @param password
     * @param smsCode
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/user/forgetLoginPwd")
    Observable<Response<String>> forgetLoginPwd(@Field("mobilePhone") String mobilePhone,
                                              @Field("pwd") String password,
                                              @Field("smsCode") String smsCode);


    /**
     * 个人信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/info")
    Observable<Response<UserInfoDto>> getUserInfo(@Field("token") String token);

    /**
     * 修改个人信息
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/update/info")
    Observable<Response<String>> updateUserInfo(@Field("token") String token,
                                              @Field("nickName") String nickName,
                                              @Field("portraitUrl") String portraitUrl,
                                              @Field("birthday") String birthday);

    /**
     * 生成个人名片
     *
     * @param token
     * @param name
     * @param mobilePhone
     * @param position
     * @param personalizedSignature
     * @param detailedAddress
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/userCard/update/info")
    Observable<Response<String>> createUserCard(@Field("token") String token,
                                              @Field("portraitUrl") String portraitUrl,
                                              @Field("name") String name,
                                              @Field("mobilePhone") String mobilePhone,
                                              @Field("position") String position,
                                              @Field("personalizedSignature") String personalizedSignature,
                                              @Field("detailedAddress") String detailedAddress);

    /**
     * 生成个人名片
     *
     * @param name
     * @param mobilePhone
     * @param position
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/userCard/update/info/send")
    Observable<Response<String>> createUserCardOnLogin(@Field("name") String name,
                                                     @Field("mobilePhone") String mobilePhone,
                                                     @Field("position") String position);

    /**
     * 获取个人名片
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/userCard/info/send")
    Observable<Response<UserCardDto>> getUserCard(@Field("token") String token);

    /**
     * 获取行业数据
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/list/industry")
    Observable<Response<List<IndustryDto>>> getIndustryList(@Field("token") String token);


    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Multipart
    @POST(ApiConstant.API_MODEL + "/file/up")
    Observable<Response<String>> imageUpload(@Part("optionType") RequestBody optionType,
                                           @Part MultipartBody.Part file);

    /**
     * 查询账户余额
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/my/account")
    Observable<Response<UserBalanceDto>> getUserAccountBalance(@Field("token") String token);

    /**
     * 查询账户明细
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/account/user/stream/list")
    Observable<Response<List<UserAccountDetailDto>>> getUserAccountDetail(@Field("token") String token,
                                                                        @Field("pageNo") int pageNo);


    /**
     * 新增提现账户
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/withdraw/add/account")
    Observable<Response<String>> createWithDrawAccount(@Field("token") String token,
                                                     @Field("name") String name,
                                                     @Field("withdrawPhone") String withdrawPhone,
                                                     @Field("typeName") String typeName,
                                                     @Field("withdrawNumber") String withdrawNumber);


    /**
     * 删除提现账户
     *
     * @param token
     * @param id
     * @param delete
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/withdraw/delete/account")
    Observable<Response<String>> deleteWithDrawAccount(@Field("token") String token,
                                                     @Field("id") String id,
                                                     @Field("delete") int delete);

    /**
     * 编辑提现账户
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/withdraw/update/account")
    Observable<Response<String>> updateWithDrawAccount(@Field("token") String token,
                                                     @Field("id") String id,
                                                     @Field("name") String name,
                                                     @Field("withdrawPhone") String withdrawPhone,
                                                     @Field("typeName") String typeName,
                                                     @Field("withdrawNumber") String withdrawNumber);

    /**
     * 获取提现账户列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/withdraw/list/account")
    Observable<Response<List<WithDrawAccountDto>>> getWithDrawAccount(@Field("token") String token);

    /**
     * 提现
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/withdraw")
    Observable<Response<String>> submitWithDraw(@Field("token") String token,
                                              @Field("accountId") String accountId,
                                              @Field("userAmount") String userAmount);


    /**
     * 获取商品列表  商城
     *
     * @param pageNo
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/product/normal/list")
    Observable<Response<List<GoodsDto>>> getGoodsListOfShopping(@Field("pageNo") int pageNo,
                                                              @Field("isInIndexActivity") String isInIndexActivity);

    /**
     * 获取商品列表  商城
     *
     * @param pageNo
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/product/normal/list")
    Observable<Response<List<GoodsDto>>> getGoodsListOfShoppingNew(@Field("pageNo") int pageNo,
                                                                 @Field("isInIndexActivity") String isInIndexActivity,
                                                                 @Field("orderBy") int orderBy,
                                                                 @Field("roleId") String roleId);

    /**
     * 获取商品列表  搜索
     *
     * @param pageNo
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/product/normal/search")
    Observable<Response<List<GoodsDto>>> getGoodsListOfSearch(@Field("pageNo") int pageNo,
                                                            @Field("name") String name);

    /**
     * 商家列表的商品列表
     *
     * @param pageNo
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/product/normal/shop/list")
    Observable<Response<List<GoodsDto>>> getGoodsListOfShop(@Field("token") String token,
                                                          @Field("pageNo") int pageNo);


    /**
     * 获取商品详情
     *
     * @param productNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/product/normal/productDetail")
    Observable<Response<GoodsDetailsDto>> getGoodsDetails(@Field("token") String token,
                                                        @Field("productNo") String productNo);

    /**
     * 关注商品
     *
     * @param productNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/keep/product")
    Observable<Response<String>> followGoods(@Field("token") String token,
                                           @Field("productNo") String productNo,
                                           @Field("clubId") String clubId);

    /**
     * 发布商品
     *
     * @param token
     * @param productName
     * @param subtitle
     * @param price
     * @param productBanner
     * @param freightInMaxCount
     * @param freightInEveryAdd
     * @param productDetail
     * @param bannerUrl
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/product/normal/add")
    Observable<Response<String>> createNewGoods(@Field("token") String token,
                                              @Field("name") String productName,
                                              @Field("subtitle") String subtitle,
                                              @Field("price") String price,
                                              @Field("productBanner") String productBanner,
                                              @Field("freightInMaxCount") String freightInMaxCount,
                                              @Field("freightInEveryAdd") String freightInEveryAdd,
                                              @Field("productDetail") String productDetail,
                                              @Field("bannerUrl") String bannerUrl,
                                              @Field("isInIndexActivity") String isInIndexActivity);

    /**
     * 商品修改
     *
     * @param token
     * @param productNo
     * @param productName
     * @param subtitle
     * @param price
     * @param productBanner
     * @param freightInMaxCount
     * @param freightInEveryAdd
     * @param productDetail
     * @param bannerUrl
     * @param isInIndexActivity
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/product/normal/edit")
    Observable<Response<String>> updateGoods(@Field("token") String token,
                                           @Field("productNo") String productNo,
                                           @Field("name") String productName,
                                           @Field("subtitle") String subtitle,
                                           @Field("price") String price,
                                           @Field("productBanner") String productBanner,
                                           @Field("freightInMaxCount") String freightInMaxCount,
                                           @Field("freightInEveryAdd") String freightInEveryAdd,
                                           @Field("productDetail") String productDetail,
                                           @Field("bannerUrl") String bannerUrl,
                                           @Field("isInIndexActivity") String isInIndexActivity);

    /**
     * 商品 管理  修改商品前 获取商品信息
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/product/normal/editView")
    Observable<Response<GoodsDetailsDto>> getGoodsInfo(@Field("token") String token,
                                                     @Field("productNo") String productNo);

    /**
     * 商品 管理  下架
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/product/normal/soldOut")
    Observable<Response<String>> goodsSoldOut(@Field("token") String token,
                                            @Field("productNo") String productNo);


    ///auth/order/buy/shop/list

    /**
     * 商品 管理  已售出
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/order/buy/shop/list")
    Observable<Response<List<OrderDto>>> getSellGoodsList(@Field("token") String token,
                                                        @Field("pageNo") int pageNo);

    /**
     * 获取会员对应类型的金额
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/member/joinMember/money")
    Observable<Response<List<MemberTypeDto>>> getMemberTypeInfo(@Field("token") String token);


    /**
     * 创建支付订单
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/order/recharge/create")
    Observable<Response<PayOrderDto>> createPayOrder(@Field("token") String token,
                                                   @Field("amount") String amount,
                                                   @Field("memberType") String memberType);


    /**
     * 获取支付宝订单信息参数
     *
     * @param payNumber
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/alipay/get/params")
    Observable<Response<String>> getAlipayParams(@Field("token") String token,
                                               @Field("payNumber") String payNumber);

    /**
     * 获取微信订单信息参数
     *
     * @param payNumber
     * @returnr
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/wx/pay/create/pre/payment/order")
    Observable<Response<WeiCharParamsDto>> getWeiChatParams(@Field("token") String token,
                                                          @Field("payNumber") String payNumber);

    /**
     * 新增收货地址
     *
     * @param token
     * @param receivePerson
     * @param receivePersonPhone
     * @param receiveAddress
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/receive/address/add")
    Observable<Response<String>> newReceiverAddress(@Field("token") String token,
                                                  @Field("receivePerson") String receivePerson,
                                                  @Field("receivePersonPhone") String receivePersonPhone,
                                                  @Field("receiveAddress") String receiveAddress);

    /**
     * 编辑收货地址
     *
     * @param token
     * @param receivePerson
     * @param receivePersonPhone
     * @param receiveAddress
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/receive/address/update")
    Observable<Response<String>> updateReceiverAddress(@Field("token") String token,
                                                     @Field("id") String id,
                                                     @Field("receivePerson") String receivePerson,
                                                     @Field("receivePersonPhone") String receivePersonPhone,
                                                     @Field("receiveAddress") String receiveAddress);

    /**
     * 获取收货地址列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/receive/address/list")
    Observable<Response<List<ReceiverAddressDto>>> getReceiverAddressList(@Field("token") String token);

    /**
     * 设置默认收货地址
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/receive/address/update/default")
    Observable<Response<String>> setDefaultReceiverAddress(@Field("token") String token,
                                                         @Field("id") String id);

    /**
     * 删除收货地址
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/receive/address/delete")
    Observable<Response<String>> deleteReceiverAddress(@Field("token") String token,
                                                     @Field("id") String id);

    /**
     * 获取默认收货地址
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/receive/address/get/default")
    Observable<Response<ReceiverAddressDto>> getDefaultReceiverAddress(@Field("token") String token);


    /**
     * 获取默认收货地址
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/shop/car/add")
    Observable<Response<String>> addShoppingCart(@Field("token") String token,
                                               @Field("productNo") String productNo,
                                               @Field("count") String count);

    /**
     * 获取购物车列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/shop/car/list")
    Observable<Response<List<ShoppingCartDto>>> getShoppingCartList(@Field("token") String token);

    /**
     * 删除购物车 商品
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/shop/car/delete")
    Observable<Response<String>> deleteShoppingCartGoods(@Field("token") String token,
                                                       @Field("productNos") String productNos);

    /**
     * 购物车 去结算 创建订单
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/go/shopList")
    Observable<Response<List<ShoppingCartDto>>> createOrder(@Field("token") String token,
                                                          @Field("productNos") String productNos);

    /**
     * 更新购物车商品的数量
     * productNo 订单编号
     * optionType 请求类型1增加2减少
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/shop/car/update/count")
    Observable<Response<String>> updateCountOnShopCartForGoods(@Field("token") String token,
                                                             @Field("productNo") String productNo,
                                                             @Field("optionType") String optionType);


    /**
     * 提交订单
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/order/buy/create")
    Observable<Response<PayOrderDto>> submitOrder(@Field("token") String token,
                                                @Field("productNos") String productNos,
                                                @Field("productCounts") String productCounts,
                                                @Field("receiveAddressId") String receiveAddressId,
                                                @Field("freight") String freight,
                                                @Field("exchange") String exchange);

    /**
     * 获取订单列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/order/buy/list")
    Observable<Response<List<OrderDto>>> getOrderList(@Field("token") String token,
                                                    @Field("status") String status,
                                                    @Field("pageNo") int pageNo);

    /**
     * 获取商品类型
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/product/normal/type")
    Observable<Response<List<GoodsTypeDto>>> getGoodsTypes(@Field("token") String token);

    /**
     * 获取  新版  商城页面  商品分类
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/productTypes/list/send")
    Observable<Response<List<HomeGoodsTypeDto>>> getGoodsType(@Field("type") String type);

    /**
     * 获取  新增商品  选择  商品分类
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/productTypes/list/send")
    Observable<Response<List<HomeGoodsTypeDto>>> getGoodsTypeForCreateGoods(@Field("type") String type);

    /**
     * 关注商品列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/keep/product/list")
    Observable<Response<List<GoodsDto>>> getAttentionGoodsList(@Field("token") String token,
                                                             @Field("pageNo") int pageNo);

    /**
     * 获取活动列表
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/activity/list")
    Observable<Response<List<ActionDto>>> getActionList(@Field("pageNo") int pageNo);

    /**
     * 获取资讯列表
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/information/list")
    Observable<Response<List<InformationDto>>> getInformationList(
            @Field("pageNo") int pageNo);

    /**
     *   获取首页 动态  平台发布的资讯
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/information/managerDynamic")
    Observable<Response<List<InformationDto>>> getDynamicInformationList(@Field("pageNo") int pageNo);

    /**
     * 获取资讯列表
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/information/detail")
    Observable<Response<String>> getInformationDetail(@Field("id") String id);

    /**
     * 获取首页 推荐 内容
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/headRecommend/list")
    Observable<Response<List<HomeRecommendDto>>> getHomeRecommend(@Field("pageNo") int pageNo);

    /**
     * 获取活动列表
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/product/recommend/list")
    Observable<Response<List<HomeNewRecommendDto>>> getHomeNewActionRecommend(@Field("pageNo") int pageNo);

    /**
     * 获取首页消息
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/notice/list")
    Observable<Response<List<MessageDto>>> getMessage(@Field("token") String token,
                                                    @Field("pageNo") int pageNo);

    /**
     * 余额支付
     *
     * @param payNumber
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/balance/pay")
    Observable<Response<String>> payBalance(@Field("token") String token,
                                          @Field("payNumber") String payNumber);

    /**
     * 我的订单  去支付
     *
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/buy/order/not/pay/go/to/pay")
    Observable<Response<PayOrderDto>> toPayOnMyOrder(@Field("token") String token,
                                                   @Field("orderNo") String orderNo);

    /**
     * 商家 发货
     *
     * @param token
     * @param orderNo
     * @param expressCompany
     * @param courierNumber
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/order/buy/delivery")
    Observable<Response<String>> sendGoods(@Field("token") String token,
                                         @Field("orderNo") String orderNo,
                                         @Field("expressCompany") String expressCompany,
                                         @Field("courierNumber") String courierNumber);

    /**
     * 获取订单详情
     *
     * @param token
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/order/buy/detail")
    Observable<Response<OrderDto>> getOrderDetails(@Field("token") String token,
                                                 @Field("orderNo") String orderNo);

    /**
     * 确认收货
     *
     * @param token
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/order/buy/receipt")
    Observable<Response<String>> confirmReceiverGoods(@Field("token") String token,
                                                    @Field("orderNo") String orderNo);

    /**
     * 发表评论
     *
     * @param token
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/comment/buy/order")
    Observable<Response<String>> evaluateGoods(@Field("token") String token,
                                             @Field("orderNo") String orderNo,
                                             @Field("content") String content);

    /**
     * 获取评论列表
     *
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/list/commet/by/productNo")
    Observable<Response<List<EvaluateDto>>> getEvaluateList(@Field("token") String token,
                                                          @Field("productNo") String productNo,
                                                          @Field("pageNo") int pageNo);

    /**
     * 获取我的积分
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/integral")
    Observable<Response<IntegralDto>> getIntegralInfo(@Field("token") String token);

    /**
     * 获取积分明细列表
     *
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/integralDetail")
    Observable<Response<List<IntegralDetailsDto>>> getIntegralDetailsList(@Field("token") String token,
                                                                        @Field("pageNo") int pageNo);

    /**
     * 扫一扫 二维码
     *
     * @param url
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST()
    Observable<Response<UserCardDto>> getUserInfoOnQRCode(@Url String url,
                                                        @Field("token") String token);

    /**
     * 申请成为好友
     *
     * @param token
     * @param friendPhone
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/add/friend")
    Observable<Response<String>> applyBecomeFriend(@Field("token") String token,
                                                 @Field("friendMobilePhone") String friendPhone);

    /**
     * 获取我的人脉列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/list/friend")
    Observable<Response<List<MyFriendsDto>>> getMyFriends(@Field("token") String token);

    /**
     * 获取分享的内容
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/member/share")
    Observable<Response<ShareDto>> getShareContent(@Field("token") String token);

    /**
     * 获取我的团队数据
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/list/getMyTeams")
    Observable<Response<List<MyTeamDto>>> getMyTeam(@Field("token") String token);

    /**
     * 通过用户的id查看名片信息
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/userCard/info/send")
    Observable<Response<UserInfoDto>> getUserCardById(@Field("token") String token,
                                                    @Field("id") String id);


    /**
     * 检测新版本
     * @param version
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/get/app/version")
    Observable<Response<AppVersionDto>> checkAppVersion(@Field("version") int version);

    /**
     * 支付回调验证
     * @param token
     * @param payNumber
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/show/pay/result")
    Observable<Response<String>> checkPayResult(@Field("token") String token,
                                              @Field("payNumber") String payNumber);

    /**
     * 获取首页banner
     * @param str
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/banner/list")
    Observable<Response<List<BannerDto>>> getHomeBanner(@Field("str") String str);

    /**
     * 获取咨询页banner
     * @param str
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/information/bannerList")
    Observable<Response<List<InformationDto>>> getInformationBanner(@Field("str") String str);


    /**
     * 获取新版首页 商品分类  一级
     * @param str
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/productTypes/firstList")
    Observable<Response<List<HomeGoodsTypeDto>>> getHomeGoodsType(@Field("defaule") String str);

    /**
     * 更新用户位置信息
     * @param token
     * @param longitude
     * @param latitude
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/GPS")
    Observable<Response<String>> updateUserLocation(@Field("token") String token,
                                                  @Field("longitude") String longitude,
                                                  @Field("latitude") String latitude);

    /**
     * 获取附近合伙人信息
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/list/near/partner")
    Observable<Response<List<NearbyVipDto>>> getNearbyVip(@Field("token") String token,
                                                        @Field("pageNo") int pageNo);

    /**
     * 更新 是否允许其他人查看 名片的状态
     * @param token
     * @param allowStutas
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/update/allowStutas")
    Observable<Response<String>> updateAllowUserCard(@Field("token") String token,
                                                   @Field("allowStutas") String allowStutas);

    /**
     * 获取俱乐部 的基本数据 logo  名称
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/type")
    Observable<Response<List<ClubDto>>> getClubInfos(@Field("type") String type);


    /**
     * 创建我的俱乐部
     * @param token
     * @param clubName
     * @param clubLogo
     * @param content
     * @param backgroundImg
     * @param clubDetailList
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/club/addImageDetail")
    Observable<Response<String>> createMyClub(@Field("token") String token,
                                            @Field("clubName") String clubName,
                                            @Field("clubLogo") String clubLogo,
                                            @Field("content") String content,
                                            @Field("backgroundImg") String backgroundImg,
                                            @Field("clubDetailList") String clubDetailList);

    /**
     * 编辑我的俱乐部
     * @param token
     * @param clubName
     * @param clubLogo
     * @param content
     * @param backgroundImg
     * @param clubDetailList
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/club/editImageDetail")
    Observable<Response<String>> editMyClub(@Field("token") String token,
                                          @Field("clubName") String clubName,
                                          @Field("clubLogo") String clubLogo,
                                          @Field("content") String content,
                                          @Field("backgroundImg") String backgroundImg,
                                          @Field("clubDetailList") String clubDetailList,
                                          @Field("id") String id);

    /**
     * 获取我的俱乐部基本信息
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auto/club/detail")
    Observable<Response<ClubDto>> getMyClubInfoBasic(@Field("token") String token,@Field("clubId") String clubId);


    /**
     * 获取我的俱乐部图文介绍信息
     * @param clubId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/ImageDetail")
    Observable<Response<List<ClubDto.ClubDetailBean>>> getMyClubInfoDetails(@Field("clubId") String clubId);

    /**
     * 俱乐部 发布活动
     * @param token
     * @param clubId
     * @param activityPictureUrl
     * @param title
     * @param address
     * @param flow
     * @param holdDate
     * @param guestList
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/club/addOrEditClubActivity")
    Observable<Response<String>> releaseActionRecent(@Field("token") String token,
                                                   @Field("clubId") String clubId,
                                                   @Field("activityPictureUrl") String activityPictureUrl,
                                                   @Field("title") String title,
                                                   @Field("address") String address,
                                                   @Field("flow") String flow,
                                                   @Field("holdDate") String holdDate,
                                                   @Field("appGuestList") String guestList,
                                                   @Field("id") String actionId);

    /**
     * 获取俱乐部 近期活动列表
     * @param clubId
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/activityList")
    Observable<Response<List<ActionDto>>> getActionRecentList(@Field("clubId") String clubId,
                                                            @Field("pageNo") int pageNo);

    /**
     * 获取为举办 活动详情信息
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/activityDetail/send")
    Observable<Response<ActionDto>> getActionRecentInfo(@Field("token") String token,
                                                      @Field("id") String id);

    /**
     * 获取活动 的参加人员
     * @param actionId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/activity/registration/listpage")
    Observable<Response<List<ActionMemberDto>>> getActionMemberList(@Field("activityId") String actionId,
                                                                  @Field("pageNo") int pageNo);

    /**
     * 俱乐部发布 往期活动
     * @param clubId
     * @param avatarUrl
     * @param title
     * @param dynamicList
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/information/add")
    Observable<Response<String>> releaseActionPast(@Field("clubId") String clubId,
                                                 @Field("avatarUrl") String avatarUrl,
                                                 @Field("title") String title,
                                                 @Field("dynamicList") String dynamicList,
                                                 @Field("id") String id);

    /**
     * 获取往期资讯 列表
     * @param clubId
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/information/list")
    Observable<Response<List<ActionPastDto>>> getActionPastList(@Field("clubId") String clubId,
                                                              @Field("pageNo") int pageNo);

    /**
     * 获取往期资讯  详情
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/information/detail/update")
    Observable<Response<ActionPastDto>> getActionPastDetail(@Field("id") String id,
                                                            @Field("activityId") String activityId);

    /**
     * 获取首页  约吗  列表  所有的未举办活动列表
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/activity/list")
    Observable<Response<List<AppointDto>>> getActionRecentList(@Field("pageNo") int pageNo,
                                                               @Field("newType") int newType);

    /**
     * 获取已报名的活动列表
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/apply/activity/list")
    Observable<Response<List<AppointDto>>> getActionAppliedList(@Field("token") String token,
                                                               @Field("pageNo") int pageNo);

    /**
     * 报名
     * @param token
     * @param activityId
     * @param registrationName
     * @param registrationPhone
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/app/activityRegistration")
    Observable<Response<String>> applyAction(@Field("token") String token,
                                           @Field("activityId") String activityId,
                                           @Field("registrationName") String registrationName,
                                           @Field("registrationPhone") String registrationPhone);

    /**
     * 获取客服信息
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/admin/service")
    Observable<Response<ServiceDto>> getServiceInfo(@Field("token") String token);

    /**
     * 获取 近期活动 报名人数
     * @param activityId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/activity/registration/number")
    Observable<Response<ActionMemberNumDto>> getActionMemberNum(@Field("activityId") String activityId);

    /**
     * 发布捡漏
     * @param token
     * @param ruleTitle
     * @param ruleContent
     * @param rulePictureUrl
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/add/rule")
    Observable<Response<String>> releaseForum(@Field("token") String token,
                                            @Field("ruleTitle") String ruleTitle,
                                            @Field("ruleContent") String ruleContent,
                                            @Field("rulePictureUrl") String rulePictureUrl);

    /**
     * 获取 捡漏列表
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/rule/list")
    Observable<Response<List<CircleFriendsDto>>> getCircleFriendsList(@Field("token") String token,
                                                                      @Field("pageNo") int pageNo);

    /**
     * 捡漏 帖子点赞
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/rule/praise")
    Observable<Response<String>> praiseForum(@Field("token") String token,
                                         @Field("id") String id);

    /**
     * 给 捡漏 帖子 发表评论
     * @param token
     * @param id
     * @param commentContent
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/add/rule/comment")
    Observable<Response<String>> addForumComment(@Field("token") String token,
                                               @Field("id") String id,
                                               @Field("ruleCommentContent") String commentContent);

    /**
     * 获取更多的  捡漏帖子 评价
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/rule/comment/list")
    Observable<Response<List<CircleFriendsDto.CircleFriendsCommentDto>>> getMoreCommentList(@Field("id") String id);

    /**
     * 获取 我的捡漏 信息  评论数  获赞数  发帖数
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/my/rule/statistics")
    Observable<Response<ForumInfoDto>> getMyForumInfos(@Field("token") String token);

    /**
     * 获取 我的捡漏专区  具体的  帖子列表
     * @param token
     * @param type
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/my/rule/details")
    Observable<Response<List<CircleFriendsDto>>> getMyForumList(@Field("token") String token,
                                                      @Field("type") String type,
                                                      @Field("pageNo") int pageNo);

    /**
     * 上传app crash 的情况到服务器
     * @param appVersion
     * @param osVersion
     * @param vendor
     * @param model
     * @param cpuabi
     * @param question
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/appquestion/save")
    Observable<Response<String>> uploadAppCrashQuestion(@Field("appVersion") String appVersion,
                                                      @Field("osVersion") String osVersion,
                                                      @Field("vendor") String vendor,
                                                      @Field("model") String model,
                                                      @Field("cpuabi") String cpuabi,
                                                      @Field("question") String question);

    /**
     * 分享 近期活动  增加活跃值
     * @param token
     * @param activityId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/add/active/value")
    Observable<Response<String>> addActiveValue(@Field("token") String token,
                                              @Field("activityId") String activityId);

    /**
     * 获取 个人的 活跃值
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/active/value/account")
    Observable<Response<ActiveValueDto>> getActiveValue(@Field("token") String token);

    /**
     * 获取 个人的 活跃值  明细
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/list/activeValue")
    Observable<Response<List<ActiveValueDetailDto>>> getActiveValueDetail(@Field("token") String token,
                                                                        @Field("pageNo") int pageNo);


    /**
     * 获取 兑换商品 列表
     * @param orderBy
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/product/active/value/list")
    Observable<Response<List<ActiveGoodsDto>>> getConvertActiveGoodsList(@Field("pageNo") int pageNo,
                                                                       @Field("orderBy") int orderBy);


    /**
     * 获取 兑换商品 详情
     * @param productNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/product/active/value/detail")
    Observable<Response<ActiveGoodsDto>> getConvertActiveGoodsDetail(@Field("productNo") String productNo);

    /**
     * 兑换商品
     * @param token
     * @param productNo
     * @param productCount
     * @param receiveAddressId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/active/value/buy/create")
    Observable<Response<String>> convertActiveGoods(@Field("token") String token,
                                                  @Field("productNo") String productNo,
                                                  @Field("productCount") String productCount,
                                                  @Field("receiveAddressId") String receiveAddressId);

    /**
     * 获取 兑换商品 清单 列表
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/buy/product/active/value/list")
    Observable<Response<List<ActiveGoodsOrderListDto>>> getActiveGoodsOrderList(@Field("token") String token,
                                                                              @Field("pageNo") int pageNo);

    /**
     * 获取兑换商品的 订单详情
     * @param token
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/active/value/buy/product/details")
    Observable<Response<ActiveGoodsOrderDetailDto>> getActiveGoodsOrderDetail(@Field("token") String token,
                                                                            @Field("orderNo") String orderNo);

    /**
     * 获取大咖信息列表
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/list/maca")
    Observable<Response<List<MasterDto>>> getMasters(@Field("pageNo") int pageNo);

    /**
     * 获取大咖的banner列表
     * @param banner
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/meca/banner")
    Observable<Response<List<MasterBannerDto>>> getMasterBanners(@Field("banner") int banner);

    /**
     * 获取大咖信息
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/maca/information")
    Observable<Response<MasterDto>> getMasterInfo(@Field("id") String id);

    /**
     * 获取大咖的相关作品列表
     * @param id
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/list/maca/works")
    Observable<Response<List<MasterWorkDto>>> getMasterWorkList(@Field("id") String id,
                                                              @Field("pageNo") int pageNo);

    /**
     * 创建预约大咖 支付订单
     * @param token
     * @param macaId
     * @param macaWorksId
     * @param money
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/maca/appointment/create")
    Observable<Response<PayOrderDto>> createOrderMaster(@Field("token") String token,
                                                      @Field("macaId") String macaId,
                                                      @Field("macaWorksId") String macaWorksId,
                                                      @Field("money") String money);

    /**
     * 获取预约大咖的列表
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/list/maca/appointment")
    Observable<Response<List<MasterOrderDto>>> getMasterSubscribeOrderList(@Field("token") String token,
                                                                         @Field("pageNo") int pageNo);

    /**
     * 获取助教 的所属的活动列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/activity/teachList")
    Observable<Response<List<ActionDto>>> getActionLists(@Field("token") String token);

    /**
     * 获取助教 嘉宾签到列表
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/activity/registrationList")
    Observable<Response<List<SignInDto>>> getSignInLists(@Field("token") String token,
                                                       @Field("id") String id);

    /**
     * 获取俱乐部列表
     * @param pageNo
     * @param isRecommend
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/list")
    Observable<Response<List<ClubDto>>> getClubList(@Field("token") String token,
                                                    @Field("pageNo") int pageNo,
                                              @Field("isRecommend") String isRecommend);

    /**
     * 获取俱乐部详情
     * @param token
     * @param clubId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/getClub")
    Observable<Response<ClubDto>> getClubInfo(@Field("token") String token,
                                              @Field("clubId") String clubId);


    /**
     * 获取会员列表
     * @param string
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/joinMember/regularList")
    Observable<Response<List<MemberDto>>> getMemberList(@Field("str") String string);

    /**
     * 获取首页banner轮播图
     * @param banner
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/banner/list")
    Observable<Response<List<BannerDto>>> getBannerList(@Field("str") String banner);

    /**
     * 获取推荐人物  首页
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/personConnectionList")
    Observable<Response<List<RecommendPersonalDto>>> getRecommendPersons(@Field("token") String token,
                                                                         @Field("pageNo") int pageNo);

    /**
     * 获取推荐人物  首页 点赞
     * @param personId
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/personLike")
    Observable<Response<String>> pariseRecommendPerson(@Field("token") String token,
                                                                        @Field("personId") String personId);


    /**
     * 根据日期获取俱乐部 活动列表
     * @param clubId
     * @param startDate
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/activityList")
    Observable<Response<List<CalendarActionsDto>>> getClubActionsByMonth(@Field("clubId") String clubId,
                                                         @Field("startDate") String startDate);

    /**
     * 获取个人券的 列表
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/user/ticket")
    Observable<Response<List<TicketDto>>> getAllTicketList(@Field("token") String token,
                                                           @Field("pageNo") int pageNo);

    /**
     * 获取地图上  所有 基地会所 数据
     * @param def
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/baseList")
    Observable<Response<List<MapMarkerDto>>> getMapMarkers(@Field("default") String def);


    /**
     * 获取地图上  所有 基地会所 数据
     * @param type  2:基地 3：会所
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/baseList")
    Observable<Response<List<MapMarkerDto>>> getScenicList(@Field("type") String type);

    /**
     * 获取基地的特色服务列表
     * @param clubId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/commodityList")
    Observable<Response<List<ScenicServiceDto>>> getCommodityList(@Field("clubId") String clubId);

    /**
     * 获取平台基地活动的特色服务列表
     * @param clubId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/commodityList")
    Observable<Response<List<ScenicServiceDto>>> getPlatformCommodityList(@Field("clubId") String clubId,
                                                                          @Field("activityId") String activityId);

    /**
     * 获取基地的 特色服务详情
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/club/getCommodity")
    Observable<Response<ScenicServiceDto>> getScenicServiceInfo(@Field("id") String id);

    /**
     * 报名基地活动
     * @param token
     * @param clubCommodityId
     * @param personNum
     * @param day
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/ticketOrder/create")
    Observable<Response<PayOrderDto>> applyScenicAction(@Field("token") String token,
                                                   @Field("clubCommodityId") String clubCommodityId,
                                                   @Field("personNum") String personNum,
                                                   @Field("startDate") String startDate,
                                                   @Field("day") String day,
                                                        @Field("price") String price,
                                                        @Field("ticketNos") String ticketNos);

    /**
     * 报名驿站活动
     * @param token
     * @param clubId
     * @param personNum
     * @param startDate
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/ticketOrder/create")
    Observable<Response<PayOrderDto>> freeTea(@Field("token") String token,
                                              @Field("clubId") String clubId,
                                              @Field("personNum") String personNum,
                                              @Field("startDate") String startDate);

    /**
     * 获取基地活动 的可使用的优惠券列表
     * @param token
     * @param clubCommodityId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/club/ticket")
    Observable<Response<List<TicketDto>>> getTicketListOfScenic(@Field("token") String token,
                                                                @Field("clubCommodityId") String clubCommodityId,
                                                                @Field("clubId") String clubId);

    /**
     * 获取平台基地活动 的可使用的优惠券列表
     * @param token
     * @param clubCommodityId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/club/ticket")
    Observable<Response<List<TicketDto>>> getPlatformTicketListOfScenic(@Field("token") String token,
                                                                @Field("clubCommodityId") String clubCommodityId,
                                                                @Field("id") String id,
                                                                @Field("clubId") String clubId,
                                                                        @Field("activityId") String activityId);

    /**
     * 获取券的使用记录
     * @param token
     * @param ticketNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.API_MODEL + "/auth/ticket/record")
    Observable<Response<List<TicketUseRecordDto>>> getTicketUseRecordList(@Field("token") String token,
                                                                          @Field("ticketNo") String ticketNo);
}
