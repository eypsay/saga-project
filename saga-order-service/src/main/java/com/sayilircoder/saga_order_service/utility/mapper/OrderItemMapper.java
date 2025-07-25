package com.sayilircoder.saga_order_service.utility.mapper;

import com.sayilircoder.saga_order_service.dto.OrderItemDto;
import com.sayilircoder.saga_order_service.entity.OrderItem;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OrderItemMapper {

    public static List<OrderItem> toEntityList(List<OrderItemDto> dtoList) {
        return dtoList.stream()
                .map(dto -> {
                    OrderItem orderItemEntity = new OrderItem();
                    orderItemEntity.setProductId(dto.productId());
                    orderItemEntity.setQuantity(dto.quantity());
                    return orderItemEntity;
                }).collect(Collectors.toList());
    }

    public static List<OrderItemDto> toDtoList(List<OrderItem> entityList) {
        return entityList.stream()
                .map(entity -> new OrderItemDto(entity.getProductId(), entity.getQuantity()))
                .collect(Collectors.toList());
    }
}
