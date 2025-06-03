package org.ecommerce.ecommerceapi.inventory.service;

import org.ecommerce.ecommerceapi.inventory.dto.InventoryResponseDTO;
import org.ecommerce.ecommerceapi.inventory.dto.UpdateStockDTO;
import org.ecommerce.ecommerceapi.inventory.model.Inventory;
import org.ecommerce.ecommerceapi.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class inventoryService {

    @Autowired
    private InventoryRepository repository;

    public InventoryResponseDTO getStockByProductId(Long productId) {
        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no estoque"));

        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setProductId(inventory.getProductId());
        dto.setQuantity(inventory.getQuantity());

        return dto;
    }

    public void updateStock(UpdateStockDTO dto) {
        Inventory inventory = repository.findByProductId(dto.getProductId())
                .orElseGet(() -> {
                    Inventory newInventory = new Inventory();
                    newInventory.setProductId(dto.getProductId());
                    newInventory.setQuantity(0);
                    return newInventory;
                });

        int currentQuantity = inventory.getQuantity() == null ? 0 : inventory.getQuantity();
        int quantityChange = dto.getQuantityChange() == null ? 0 : dto.getQuantityChange();
        int newQuantity = currentQuantity + quantityChange;

        if (newQuantity < 0) {
            throw new RuntimeException("Estoque insuficiente");
        }

        inventory.setQuantity(newQuantity);
        repository.save(inventory);
    }

    public void updateStock(Long productId, int quantityChange) {
        UpdateStockDTO dto = new UpdateStockDTO();
        dto.setProductId(productId);
        dto.setQuantityChange(quantityChange);
        updateStock(dto);
    }
}