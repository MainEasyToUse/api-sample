package com.example.e2u.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pay {
  public String payerName;
  public String payerEmail;
  public String payerTel;

  public Card card;

  public List<Product> productList = new ArrayList<>();

  public String tmnId;
  public String trxType;
  public String trackId;
  public String amount;
  public String udf1;
  public String udf2;

  public Map<String, String> metadata = new HashMap<>();
}
