package com.sayilircoder.saga_orchestrator_service.service;

import com.sayilircoder.saga_orchestrator_service.dto.InventoryCheckedEvent;

public interface OrchestratorService {
    void handleInventoryChecked(InventoryCheckedEvent event);

    void handleInventoryFailed(Long orderId);
}
