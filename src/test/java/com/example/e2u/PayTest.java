package com.example.e2u;

import com.example.e2u.domain.Card;
import com.example.e2u.domain.Pay;
import com.example.e2u.domain.PayReceipt;
import com.example.e2u.domain.Product;
import com.example.e2u.service.ManualPay;
import com.example.e2u.service.Refund;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PayTest {

  @DisplayName("수기 결제 (비인증)")
  @Test
  void pay_without_auth() throws Exception {
    String payKey = "test_pay_key"; // TODO: 온라인 결제 키
    Pay pay = new Pay();

    pay.trxType = "ONTR";
    pay.tmnId = "test_terminal"; // TODO: 터미널 아이디
    pay.trackId = "20220808_1234"; // TODO: 주문번호
    pay.amount = "1000";
    pay.udf1 = "User Define Field 1";
    pay.udf2 = "User Define Field 2";
    pay.payerName = "홍길동";
    pay.payerEmail = "test@e2u.kr";
    pay.payerTel = "01012341234";

    Card card = new Card();
    card.number = "";  // TODO: 카드 번호
    card.expiry = "";  // TODO: 카드 유효기간 (yyMM)
    card.installment = 0;  // TODO: 할부 개월

    pay.card = card;

    Product product = new Product();
    product.name = "결제 테스트 상품";
    product.qty = 1;
    product.price = 1000;
    product.desc = "이투유 테스트 상품";

    pay.productList.add(product);

    String result = new ManualPay()
        .approval(payKey, pay);

    System.out.println(result);
  }

  @DisplayName("결제 취소")
  @Test
  void refund() throws Exception {
    String payKey = "test_pay_key"; // TODO: 온라인 결제 키
    PayReceipt payReceipt = new PayReceipt();

    payReceipt.trxType = "ONTR";
    payReceipt.tmnId = "";
    payReceipt.trackId = "TEST_ORDER_NUMBER"; // TODO: 주문 번호

    payReceipt.amount = 1000;
    payReceipt.rootTrxId = "T220808429614"; // TODO: 결제 거래 번호

    String result = new Refund()
        .request(payKey, payReceipt);

    System.out.println(result);
  }

  @Test
  void response() {
    Map<String, Map<String, Object>> map = new HashMap<>();

    Map<String, Map<String, Object>> responseToMap = new Gson().fromJson(
        "{\"result\": {\"resultCd\": \"0000\",\"resultMsg\": \"정상\",\"advanceMsg\": \"정상승인\",\"create\": \"20220808170815\"},\"pay\": {\"authCd\": \"00000000\",\"card\": {\"cardId\": \"1111111111111\",\"installment\": 0,\"bin\": \"4123123\",\"last4\": \"1111\",\"issuer\": \"신한카드\",\"cardType\": \"신용\",\"acquirer\": \"신한\"},\"trxDate\": \"20220808170814\",\"trxId\": \"T220808429614\",\"trxType\": \"ONTR\",\"tmnId\": \"test_terminal\",\"trackId\": \"20220808_1234\",\"amount\": 1000,\"udf1\": \"User Define Field 1\",\"udf2\": \"User Define Field 2\"}}",
        map.getClass());

    Map<String, Object> result = responseToMap.get("result");
    Map<String, Object> pay = responseToMap.get("pay");

    System.out.println(result.get("resultCd"));
    System.out.println(result.get("resultMsg"));

    System.out.println(pay.get("trxId"));
  }
}
