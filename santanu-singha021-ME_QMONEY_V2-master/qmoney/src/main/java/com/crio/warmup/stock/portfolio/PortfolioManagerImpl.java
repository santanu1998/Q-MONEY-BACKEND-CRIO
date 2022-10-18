
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;
import java.util.*;
import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.crio.warmup.stock.quotes.StockQuotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {
  // private RestTemplate restTemplate;
  // // public static RestTemplate restTemplate = new RestTemplate();
  // // public static PortfolioManager portfolioManager = PortfolioManagerFactory.getPortfolioManager(restTemplate);
  // // private StockQuotesService stockQuotesService;



  // // Caution: Do not delete or modify the constructor, or else your build will break!
  // // This is absolutely necessary for backward compatibility
  // protected PortfolioManagerImpl(RestTemplate restTemplate, StockQuotesService stockQuotesService) {
  //   this.restTemplate = restTemplate;
  // }


  // //TODO: CRIO_TASK_MODULE_REFACTOR
  // // 1. Now we want to convert our code into a module, so we will not call it from main anymore.
  // //    Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  // //    into #calculateAnnualizedReturn function here and ensure it follows the method signature.
  // // 2. Logic to read Json file and convert them into Objects will not be required further as our
  // //    clients will take care of it, going forward.

  // // Note:
  // // Make sure to exercise the tests inside PortfolioManagerTest using command below:
  // // ./gradlew test --tests PortfolioManagerTest

  // //CHECKSTYLE:OFF




  // private Comparator<AnnualizedReturn> getComparator() {
  //   return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  // }

  // //CHECKSTYLE:OFF

  // // TODO: CRIO_TASK_MODULE_REFACTOR
  // //  Extract the logic to call Tiingo third-party APIs to a separate function.
  // //  Remember to fill out the buildUri function and use that.


  // public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
  //     throws JsonProcessingException {
  //   //  return null;
  //   // return stockQuotesService.getStockQuote(symbol, from, to);
  //   // String tiingoRestURL = buildUri(symbol, from, to);
  //   // TiingoCandle[] tiingoCandleArray =
  //   // restTemplate.getForObject(tiingoRestURL, TiingoCandle[].class);
  //   // if (tiingoCandleArray == null)
  //   // return new ArrayList<>();
  //   // return Arrays.stream(tiingoCandleArray).collect(Collectors.toList());
  //   if (from.compareTo(to) >= 0) {
  //     throw new RuntimeException();
  //   }
  //   String url = buildUri(symbol, from, to);
  //   TiingoCandle[] stockStartToEndDate = restTemplate.getForObject(url, TiingoCandle[].class);
  //   if (stockStartToEndDate == null) {
  //     return new ArrayList<Candle>();
  //   }
  //   else {
  //     List<Candle> stock = Arrays.asList(stockStartToEndDate);
  //     return stock;
  //   }
  // }

  // protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
  //   // String uriTemplate = "https://api.tiingo.com/tiingo/daily/" + symbol + "/prices?" + "startDate=" + startDate + "&endDate=" + endDate + "&token=db28d023126a8d1ee4cb8f5402fbf43ae198a5a6";
  //   // return uriTemplate; 
  //   String token = "db28d023126a8d1ee4cb8f5402fbf43ae198a5a6";
  //   String uriTemplate = "https://api.tiingo.com/tiingo/daily/$SYMBOL/prices?" + 
  //   "startDate=$STARTDATE&endDate=$ENDDATE&token=$APIKEY";
  //   String url = uriTemplate.replace("$APIKEY", token).replace("$SYMBOL", symbol)
  //   .replace("$STARTDATE", startDate.toString()).replace("$ENDDATE", endDate.toString());
  //   return url;
  // }


  // @Override
  // public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
  //     LocalDate endDate) {
  //   // TODO Auto-generated method stub
  //   // return null;
  //   AnnualizedReturn annualizedReturn;
  //   List<AnnualizedReturn> annualizedReturns = new ArrayList<AnnualizedReturn>();
  //   for (int i = 0; i < portfolioTrades.size(); i++) {
  //     annualizedReturn = getAnnualizeReturn(portfolioTrades.get(i), endDate);
  //     annualizedReturns.add(annualizedReturn);
  //   }
  //   Comparator<AnnualizedReturn> SortByAnnReturn = Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  //   Collections.sort(annualizedReturns, SortByAnnReturn);
  //   return annualizedReturns;
  // }

  // public AnnualizedReturn getAnnualizeReturn(PortfolioTrade trade, LocalDate endLocalDate) {
  //   AnnualizedReturn annualizedReturn;
  //   String symbol = trade.getSymbol();
  //   LocalDate startLocalDate = trade.getPurchaseDate();
  //   try {
  //     List<Candle> stocksStartToEndDate;
  //     stocksStartToEndDate = getStockQuote(symbol, startLocalDate, endLocalDate);
  //     Candle stockStartDate = stocksStartToEndDate.get(0);
  //     Candle stockLatest = stocksStartToEndDate.get(stocksStartToEndDate.size() - 1);
  //     Double buyPrice = stockStartDate.getOpen();
  //     Double sellPrice = stockLatest.getClose();
  //     Double totalReturn = (sellPrice - buyPrice) / buyPrice;
  //     Double numYears = (double) ChronoUnit.DAYS.between(startLocalDate, endLocalDate) / 365;
  //     Double annualizedReturns = Math.pow((1 + totalReturn), (1 / numYears)) - 1;
  //     annualizedReturn = new AnnualizedReturn(symbol, annualizedReturns, totalReturn);
  //   }
  //   catch(JsonProcessingException e) {
  //     annualizedReturn = new AnnualizedReturn(symbol, Double.NaN, Double.NaN);
  //   }
  //   return annualizedReturn;
  // }




  private RestTemplate restTemplate;
  private StockQuotesService stockQuotesService;
  private ObjectMapper objectMapper = getObjectMapper();
  private ExecutorService threadPool = null;


  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  // TODO: CRIO_TASK_MODULE_REFACTOR
  // Now we want to convert our code into a module, so we will not call it from main anymore.
  // Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  // into #calculateAnnualizedReturn function here and make sure that it
  // follows the method signature.
  // Logic to read Json file and convert them into Objects will not be required further as our
  // clients will take care of it, going forward.
  // Test your code using Junits provided.
  // Make sure that all of the tests inside PortfolioManagerTest using command below -
  // ./gradlew test --tests PortfolioManagerTest
  // This will guard you against any regressions.
  // run ./gradlew build in order to test yout code, and make sure that
  // the tests and static code quality pass.

  protected PortfolioManagerImpl(RestTemplate restTemplate, 
      StockQuotesService stockQuotesService) {
    this.stockQuotesService = stockQuotesService;
    this.restTemplate = restTemplate;
  }


  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
      LocalDate endDate) throws StockQuoteServiceException {
    List<AnnualizedReturn> annualizedReturns = new ArrayList<AnnualizedReturn>();
    for (PortfolioTrade obj : portfolioTrades) {
      List<Candle> candleList = new ArrayList<>();
      try {
        candleList = stockQuotesService.getStockQuote(obj.getSymbol(), 
            obj.getPurchaseDate(), endDate);
      } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      TiingoCandle candleObj = (TiingoCandle) candleList.get(candleList.size() - 1);
      Double buyPrice = candleList.get(0).getOpen();
      Double sellPrice = candleObj.getClose();
      Double totalReturn = (sellPrice - buyPrice) / buyPrice; 
      double totalNoOfYears = ChronoUnit.DAYS.between(obj.getPurchaseDate(),endDate) / 365.0;
      Double annualizedReturn = Math.pow((1 + totalReturn),(1.0 / totalNoOfYears)) - 1;
      AnnualizedReturn anRet =  new AnnualizedReturn(obj.getSymbol(),annualizedReturn,totalReturn);
      annualizedReturns.add(anRet);
    }
    Collections.sort(annualizedReturns,getComparator());
    return annualizedReturns;
  }
  
  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }

  //CHECKSTYLE:OFF

  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo thirdparty APIs to a separate function.
  //  It should be split into fto parts.
  //  Part#1 - Prepare the Url to call Tiingo based on a template constant,
  //  by replacing the placeholders.
  //  Constant should look like
  //  https://api.tiingo.com/tiingo/daily/<ticker>/prices?startDate=?&endDate=?&token=?
  //  Where ? are replaced with something similar to <ticker> and then actual url produced by
  //  replacing the placeholders with actual parameters.


  public List<TiingoCandle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {
      String uri = buildUri(symbol, from, to);
      String result = (restTemplate.getForObject(uri,String.class));
      List<TiingoCandle> candleList = objectMapper.readValue(result,
                        new TypeReference<List<TiingoCandle>>() {});
      return candleList;
  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    String uriTemplate = "https://api.tiingo.com/tiingo/daily/" + symbol + "/prices?"
            + "startDate=" + startDate + "&endDate=" + endDate 
            + "&token=db28d023126a8d1ee4cb8f5402fbf43ae198a5a6";
    return uriTemplate;  
  }

  public PortfolioManagerImpl(StockQuotesService stockQuotesService) {
    this.stockQuotesService = stockQuotesService;
  }


  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturnParallel(
      List<PortfolioTrade> portfolioTrades, LocalDate endDate, int numThreads)
      throws InterruptedException, StockQuoteServiceException {
    // TODO Auto-generated method stub
    // return null;
    // ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    // List<AnnualizedReturn> anreturns = new ArrayList<AnnualizedReturn>();
    // List<Future<AnnualizedReturn>> list = new ArrayList<Future<AnnualizedReturn>>();

    // for (PortfolioTrade symbol : portfolioTrades) {
    //   Callable<AnnualizedReturn> callable = new PortfolioCallable(symbol, endDate, this.stockQuotesService);
    //   Future<AnnualizedReturn> future = executor.submit(callable);
    //   list.add(future);
    // }

    // for (Future<AnnualizedReturn> fut : list) {
    //   try {
    //     anreturns.add(fut.get());
    //   } catch (ExecutionException e) {
    //     throw new StockQuoteServiceException("Execution exception");
    //   }
    // }
    // Collections.sort(anreturns, getComparator());

    // executor.shutdown();

    // return anreturns;


    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    List<AnnualizedReturn> anreturns = new ArrayList<AnnualizedReturn>();
    List<Future<AnnualizedReturn>> list = new ArrayList<Future<AnnualizedReturn>>();

    for (PortfolioTrade symbol : portfolioTrades) {
      Callable<AnnualizedReturn> callable = new PortfolioCallable(symbol,endDate,this.stockQuotesService);
      Future<AnnualizedReturn> future = executor.submit(callable);
      list.add(future);
    }

    for (Future<AnnualizedReturn> fut : list) {
      try {
        anreturns.add(fut.get());
      } catch (ExecutionException e) {
        throw new StockQuoteServiceException("Execution exception");
      }
    }
    Collections.sort(anreturns, getComparator());

    executor.shutdown();

    return anreturns;
  }


  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Modify the function #getStockQuote and start delegating to calls to
  //  stockQuoteService provided via newly added constructor of the class.
  //  You also have a liberty to completely get rid of that function itself, however, make sure
  //  that you do not delete the #getStockQuote function.
  
}
