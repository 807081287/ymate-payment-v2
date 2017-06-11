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
package net.ymate.payment.alipay.data;

import net.ymate.payment.alipay.IAliPayRequestData;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/12 上午1:52
 * @version 1.0
 */
public class TradeQueryData implements IAliPayRequestData {

    /**
     * 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
     */
    private String outTradeNo;

    /**
     * 支付宝交易号, 和商户订单号不能同时为空
     */
    private String tradeNo;

    public TradeQueryData(String outTradeNo, String tradeNo) {
        if (StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(tradeNo)) {
            throw new NullArgumentException("outTradeNo or tradeNo");
        }
        this.outTradeNo = outTradeNo;
        this.tradeNo = tradeNo;
    }

    public Map<String, String> buildRequestParams() {
        Map<String, String> _params = new HashMap<String, String>();
        //
        if (StringUtils.isNotBlank(outTradeNo)) {
            _params.put("out_trade_no", outTradeNo);
        }
        if (StringUtils.isNotBlank(tradeNo)) {
            _params.put("trade_no", tradeNo);
        }
        //
        return _params;
    }
}