## 接口文档

### 交易记录保存

* 接口名: addTransInfo
* 说明： 点击确认，交易结果

##### 请求：
```
{
  "terminalInfo": {
    "androidId": "17d60d0d4d962170",
    "imei": "863817038805027",
    "macAddress": "c4:0b:cb:85:38:93",
    "manufacturer": "Xiaomi",
    "model": "MI5s",
    "phoneType": 1,
    "serial": "afc7c493"
  },
  "locationInfo": {
    "address": "中国浙江省杭州市江干区香樟街",
    "city": "杭州市",
    "cityCode": "179",
    "country": "中国",
    "countryCode": "0",
    "district": "江干区",
    "latitude": 30.240849,
    "longitude": 120.21069,
    "province": "浙江省",
    "street": "香樟街",
    "streetNumber": ""
  },
  "request": {
    "transType": 101,
    "amount": 100000
  },
  "response": {
    
  },
  "orderId": "2019123123123123"
  "payType":1 //1刷卡 2支付宝 3微信
}
```

##### 响应


### 查询交易信息

* 接口名: queryTransInfo
* 说明： 交易信息明细查询

##### 请求

```
merchantNo: 12312313
```

##### 响应

```
{
 orderStatus: 1 //1成功 2失败 3已撤回
}
```

