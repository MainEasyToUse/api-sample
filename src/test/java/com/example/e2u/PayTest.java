package com.example.e2u;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PayTest {

  @DisplayName("수기 결제 (비인증)")
  @Test
  void pay() throws Exception {
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

    new ManualPay()
        .approval(payKey, pay);
  }
}
