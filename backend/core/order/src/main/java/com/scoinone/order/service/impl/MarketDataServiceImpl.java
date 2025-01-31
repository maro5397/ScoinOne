package com.scoinone.order.service.impl;

import com.scoinone.order.common.status.IntervalType;
import com.scoinone.order.dto.response.marketdata.GetCandleStickResponseDto;
import com.scoinone.order.dto.response.marketdata.GetAskingPriceResponseDto;
import com.scoinone.order.dto.response.marketdata.GetTradeSummaryResponseDto;
import com.scoinone.order.entity.TradeEntity;
import com.scoinone.order.repository.TradeRepository;
import com.scoinone.order.service.MarketDataService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MarketDataServiceImpl implements MarketDataService {
    private final TradeRepository tradeRepository;

    @Override
    public List<GetCandleStickResponseDto> getCandleSticks(
            String virtualAssetId,
            IntervalType intervalType,
            LocalDateTime start,
            LocalDateTime end
    ) {
        List<TradeEntity> trades = tradeRepository.findByVirtualAssetIdAndOptionalCreatedAt(virtualAssetId, start, end);
        if (trades.isEmpty()) {
            return Collections.emptyList();
        }

        Map<LocalDateTime, List<TradeEntity>> groupedTrades = trades.stream().collect(Collectors.groupingBy(
                        trade -> alignToInterval(trade.getCreatedAt(), intervalType),
                        TreeMap::new,
                        Collectors.toList()
        ));

        return groupedTrades.entrySet().stream()
                .map(entry -> createCandleStick(entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetTradeSummaryResponseDto> getTradeSummaries() {
        // get virtualAsset with Feign Client (virtualAssetId)
        List<String> virtualAssetIds = new ArrayList<>();
        return virtualAssetIds.stream()
                .map(this::createTradeSummaryDto)
                .collect(Collectors.toList());
    }

    @Override
    public GetAskingPriceResponseDto getAskingPrice(String virtualAssetId) {
        GetAskingPriceResponseDto dto = new GetAskingPriceResponseDto();

        tradeRepository.findVolumePowerByVirtualAssetId(virtualAssetId).ifPresent(dto::setVolumePower);

        tradeRepository.findPreviousCloseByVirtualAssetId(virtualAssetId).ifPresent(dto::setPreviousClose);
        tradeRepository.findHighPriceByVirtualAssetId(virtualAssetId).ifPresent(dto::setHighPrice);
        tradeRepository.findLowPriceByVirtualAssetId(virtualAssetId).ifPresent(dto::setLowPrice);

        tradeRepository.findTotalVolumeByVirtualAssetId(virtualAssetId).ifPresent(dto::setTotalVolume);
        tradeRepository.findTotalMoneyVolumeByVirtualAssetId(virtualAssetId).ifPresent(dto::setTotalMoneyVolume);

        return dto;
    }

    private LocalDateTime alignToInterval(LocalDateTime dateTime, IntervalType intervalType) {
        return dateTime.truncatedTo(ChronoUnit.MINUTES)
                .withSecond(0)
                .withNano(0)
                .minusMinutes(dateTime.getMinute() % intervalType.getAmount())
                .truncatedTo(intervalType.getUnit());
    }

    private GetCandleStickResponseDto createCandleStick(List<TradeEntity> groupedTrades, LocalDateTime dateTime) {
        BigDecimal open = groupedTrades.getFirst().getPrice();
        BigDecimal close = groupedTrades.getLast().getPrice();
        BigDecimal high = groupedTrades.stream().map(TradeEntity::getPrice)
                .max(BigDecimal::compareTo).orElse(open);
        BigDecimal low = groupedTrades.stream().map(TradeEntity::getPrice)
                .min(BigDecimal::compareTo).orElse(open);
        BigDecimal volume = groupedTrades.stream()
                .map(TradeEntity::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        GetCandleStickResponseDto candleStick = new GetCandleStickResponseDto();
        candleStick.setOpen(open);
        candleStick.setClose(close);
        candleStick.setHigh(high);
        candleStick.setLow(low);
        candleStick.setVolume(volume);
        candleStick.setDateTime(dateTime);
        return candleStick;
    }

    private GetTradeSummaryResponseDto createTradeSummaryDto(String virtualAssetId) {
        Optional<BigDecimal> yesterdayClosePrice = tradeRepository.findPreviousCloseByVirtualAssetId(virtualAssetId);

        BigDecimal totalMoneyVolume = tradeRepository.findTotalMoneyVolumeByVirtualAssetId(virtualAssetId)
                .orElse(BigDecimal.ZERO);

        Optional<TradeEntity> latestTrade = tradeRepository
                .findFirstByVirtualAssetIdOrderByCreatedAtDesc(virtualAssetId);

        GetTradeSummaryResponseDto response = new GetTradeSummaryResponseDto();
        response.setVirtualAssetId(virtualAssetId);
        // response.setName("Virtual Asset Name");
        // response.setSymbol("VAI");
        response.getSummary().setPrice(BigDecimal.ZERO);
        response.getSummary().setPriceChangeRate(BigDecimal.ZERO);
        response.getSummary().setTotalMoneyVolume(totalMoneyVolume);

        if (latestTrade.isPresent()) {
            BigDecimal latestPrice = latestTrade.get().getPrice();
            response.getSummary().setPrice(latestPrice);
            yesterdayClosePrice.ifPresent(tradeEntity -> response.getSummary().setPriceChangeRate(
                    getPriceChangeRate(latestTrade.get().getPrice(), tradeEntity)
            ));
        }

        return response;
    }

    private BigDecimal getPriceChangeRate(BigDecimal latestPrice, BigDecimal yesterdayClosePrice) {
        return yesterdayClosePrice.compareTo(BigDecimal.ZERO) > 0
                ? latestPrice.subtract(yesterdayClosePrice)
                .divide(yesterdayClosePrice, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;
    }
}
