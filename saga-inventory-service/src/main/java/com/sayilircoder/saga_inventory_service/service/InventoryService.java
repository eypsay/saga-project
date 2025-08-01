package com.sayilircoder.saga_inventory_service.service;

import com.sayilircoder.saga_inventory_service.dto.StartInventoryCheckEvent;

public interface InventoryService {
    void checkInventory(StartInventoryCheckEvent event);
    void reverseInventory(long orderId);
}
