/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.payment.wxpay.request;

import net.ymate.framework.commons.IHttpResponse;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseRequest;
import net.ymate.payment.wxpay.base.WxPayBaseResponse;
import net.ymate.platform.core.lang.BlurObject;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 撤销订单
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/20 下午11:59
 * @version 1.0
 */
public class WxPayReverse extends WxPayBaseRequest<WxPayReverse.Response> {

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 微信订单号
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    public WxPayReverse(WxPayAccountMeta accountMeta, String transactionId, String outTradeNo) {
        super(accountMeta);
        this.appId = accountMeta.getAppId();
        this.transactionId = transactionId;
        this.outTradeNo = outTradeNo;
    }

    public String appId() {
        return appId;
    }

    public WxPayReverse appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String transactionId() {
        return transactionId;
    }

    public WxPayReverse transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String outTradeNo() {
        return outTradeNo;
    }

    public WxPayReverse outTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.transactionId) && StringUtils.isBlank(this.outTradeNo)) {
            throw new NullArgumentException("transaction_id or out_trade_no");
        }
        //
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put("transaction_id", transactionId);
        _params.put(IWxPay.Const.OUT_TRADE_NO, outTradeNo);
        return _params;
    }

    protected String __doGetRequestURL() {
        return "secapi/pay/reverse";
    }

    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    /**
     * 撤销订单响应
     */
    public static class Response extends WxPayBaseResponse {

        /**
         * 公众账号ID
         */
        private String appId;

        private String recall;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.appId = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.APP_ID)).toStringValue();
            this.recall = BlurObject.bind(this.getResponseParams().get("recall")).toStringValue();
        }

        public String appId() {
            return appId;
        }

        public String recall() {
            return recall;
        }
    }
}
