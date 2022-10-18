
package com.crio.warmup.stock.quotes;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;
import com.crio.warmup.stock.dto.AlphavantageCandle;
import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;

import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class AlphavantageService implements StockQuotesService {

  // // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  // //  Implement the StockQuoteService interface as per the contracts. Call Alphavantage service
  // //  to fetch daily adjusted data for last 20 years.
  // //  Refer to documentation here: https://www.alphavantage.co/documentation/
  // //  --
  // //  The implementation of this functions will be doing following tasks:
  // //    1. Build the appropriate url to communicate with third-party.
  // //       The url should consider startDate and endDate if it is supported by the provider.
  // //    2. Perform third-party communication with the url prepared in step#1
  // //    3. Map the response and convert the same to List<Candle>
  // //    4. If the provider does not support startDate and endDate, then the implementation
  // //       should also filter the dates based on startDate and endDate. Make sure that
  // //       result contains the records for for startDate and endDate after filtering.
  // //    5. Return a sorted List<Candle> sorted ascending based on Candle#getDate
  // //  IMP: Do remember to write readable and maintainable code, There will be few functions like
  // //    Checking if given date falls within provided date range, etc.
  // //    Make sure that you write Unit tests for all such functions.
  // //  Note:
  // //  1. Make sure you use {RestTemplate#getForObject(URI, String)} else the test will fail.
  // //  2. Run the tests using command below and make sure it passes:
  // //    ./gradlew test --tests AlphavantageServiceTest
  // //CHECKSTYLE:OFF
  //   //CHECKSTYLE:ON
  // // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  // //  1. Write a method to create appropriate url to call Alphavantage service. The method should
  // //     be using configurations provided in the {@link @application.properties}.
  // //  2. Use this method in #getStockQuote.
  // private RestTemplate restTemplate;
  // private ObjectMapper objectMapper = getObjectMapper();

  

  // @Override
  // public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
  //     throws StockQuoteServiceException {
  //   // String checkPremiumResponse = "{\n"
  //   // + " \"Note\": \"Thank you for using Alpha Vantage! Our standard API call frequency is 5 calls
  //   // per minute and 500 calls per day. Please visit https://www.alphavantage.co/premium/ if you
  //   // would like to target a higher API call frequency.\"\n"
  //   // + "}";
  //   // String responseString;
  //   // do{
  //   // responseString = restTemplate.getForObject(buildURL(symbol), String.class);
  //   // }while(responseString.length()== checkPremiumResponse.length());
  //   String responseString = restTemplate.getForObject(buildUri(symbol), String.class);

  //   AlphavantageDailyResponse alphavantageDailyResponse;
  //   try {
  //     alphavantageDailyResponse =
  //         getObjectMapper().readValue(responseString, AlphavantageDailyResponse.class);
  //     if (alphavantageDailyResponse.getCandles() == null || responseString == null)
  //       throw new StockQuoteServiceException("Invalid Response Found");
  //   } 
  //   catch (JsonProcessingException e) {
  //     throw new StockQuoteServiceException(e.getMessage());
  //   }
  //   List<Candle> alphavantageCandles = new ArrayList<>();
  //   Map<LocalDate, AlphavantageCandle> mapOFDateAndAlphavantageCandle =
  //       alphavantageDailyResponse.getCandles();
  //   for (LocalDate localDate : mapOFDateAndAlphavantageCandle.keySet()) {
  //     if (localDate.isAfter(from.minusDays(1)) && localDate.isBefore(to.plusDays(1))) {
  //       AlphavantageCandle alphavantageCandle =
  //           alphavantageDailyResponse.getCandles().get(localDate);
  //       alphavantageCandle.setDate(localDate);
  //       alphavantageCandles.add(alphavantageCandle);
  //     }
  //   }
  //   return alphavantageCandles.stream().sorted(Comparator.comparing(Candle::getDate))
  //       .collect(Collectors.toList());
  // }

  // // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  // //  Inplement the StockQuoteService interface as per the contracts.
  // //  The implementation of this functions will be doing following tasks
  // //  1. Build the appropriate url to communicate with thirdparty.
  // //  The url should consider startDate and endDate if it is supported by the provider.
  // //  2. Perform thirdparty communication with the Url prepared in step#1
  // //  3. Map the response and convert the same to List<Candle>
  // //  4. If the provider does not support startDate and endDate, then the implementation
  // //  should also filter the dates based on startDate and endDate.
  // //  Make sure that result contains the records for for startDate and endDate after filtering.
  // //  5. return a sorted List<Candle> sorted ascending based on Candle#getDate
  // //  Call alphavantage service to fetch daily adjusted data for last 20 years. Refer to
  // //  documentation here - https://www.alphavantage.co/documentation/
  // //  Make sure you use {RestTemplate#getForObject(URI, String)} else the test will fail.
  // //  Run the tests using command below and make sure it passes
  // //  ./gradlew test --tests AlphavantageServiceTest
  // //CHECKSTYLE:OFF

  // private static ObjectMapper getObjectMapper() {
  //   ObjectMapper objectMapper = new ObjectMapper();
  //   objectMapper.registerModule(new JavaTimeModule());
  //   return objectMapper;
  // }
  
  // //CHECKSTYLE:ON
  // //TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  // // Write a method to create appropriate url to call alphavantage service. Method should
  // // be using configurations provided in the {@link @application.properties}.
  // // Use thie method in #getStockQuote.
  // protected String buildUri(String symbol) {
  //   String uriTemplate = "https://www.alphavantage.co/" 
  //       + "query?function=TIME_SERIES_DAILY&apikey=YFFMVPCJQDDRJJFZ&outputsize=full&symbol=" 
  //       + symbol;
  //   return uriTemplate;  
  // }

  // public AlphavantageService(RestTemplate restTemplate) {
  //   this.restTemplate = restTemplate;
  // }


  private RestTemplate restTemplate;

  protected AlphavantageService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to) 
      throws JsonProcessingException, StockQuoteServiceException, RuntimeException {
        // List<Candle> stockStartToEndDate = new ArrayList<>();
        // if (from.compareTo(to) >= 0) {
        //   throw new RuntimeException();
        // }
        // String url = buildUri(symbol, from, to);
        // // Map<LocalDate, AlphavantageCandle> dailyResponses;
        // try {
        //   String stocks = restTemplate.getForObject(url, String.class);
        //   ObjectMapper objectMapper = new ObjectMapper();
        //   objectMapper.registerModule(new JavaTimeModule());
        //   TiingoCandle[] stocksStartToEndDateArray = objectMapper.readValue(stocks, TiingoCandle[].class);
        //   stockStartToEndDate = Arrays.asList(stocksStartToEndDateArray);
        // }
        // catch (NullPointerException e) {
        //   throw new StockQuoteServiceException("Error occurred when requesting response from Tiingo API", e.getCause());
        // }

        //   // String response = restTemplate.getForObject(buildURL(symbol), String.class);
        //   // ObjectMapper objectMapper = new ObjectMapper();
        //   // objectMapper.registerModule(new JavaTimeModule());
        //   // dailyResponses = objectMapper.readValue(response, AlphavantageDailyResponse.class).getCandles();
        //   // for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
        //   //   AlphavantageCandle candle = dailyResponses.get(date);
        //   //   if (candle != null) {
        //   //     candle.setDate(date);
        //   //     stocks.add(candle);
        //   //   }
        //   // }
        // // }
        // // catch(NullPointerException e) {
        // //   throw new StockQuoteServiceException("Alphavantage returned invalid responce",  e);
        // // }
        // return stockStartToEndDate;
    try {
      ObjectMapper om = getObjectMapper();
      String url = buildURL(symbol);
      String result = restTemplate.getForObject(url, String.class);
      if (result == null || result.isEmpty()) {
        throw new StockQuoteServiceException("No response");
      }
      System.out.println(result);
      AlphavantageDailyResponse alphavantageDailyResponse = om
          .readValue(result, AlphavantageDailyResponse.class);
      Map<LocalDate, AlphavantageCandle> candles = alphavantageDailyResponse.getCandles();
      Map<LocalDate, AlphavantageCandle> filteredCandles = candles.entrySet().stream()
          .filter(x -> x.getKey().compareTo(from) >= 0 && x
            .getKey().compareTo(to) <= 0).sorted((a, b) -> {
              return a.getKey().compareTo(b.getKey());
            }).collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
      filteredCandles.forEach((k, v) -> v.setDate(k));
      List<Candle> answer = new ArrayList<Candle>(filteredCandles.values());
      Collections.reverse(answer);
      return answer;
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }

  protected String buildURL(String symbol) {
    String uriTemplate = "https://www.alphavantage.co/" 
        + "query?function=TIME_SERIES_DAILY&apikey=YFFMVPCJQDDRJJFZ&outputsize=full&symbol=" 
        + symbol;
    return uriTemplate; 
  }
  // @Override
  // public String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
  //   // String API_KEY = "YFFMVPCJQDDRJJFZ";
  //   String uriTemplate = "https://www.alphavantage.co/" 
  //       + "query?function=TIME_SERIES_DAILY&apikey=YFFMVPCJQDDRJJFZ&outputsize=full&symbol=" 
  //       + symbol;
  //   return uriTemplate;
  // }
}

