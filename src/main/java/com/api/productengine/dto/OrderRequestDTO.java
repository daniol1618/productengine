package com.api.productengine.dto;

import com.api.productengine.model.Product;


public class OrderRequestDTO {
    
        private Product product;    
        private int qty;

        public Product getProduct() {
            return product;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }


}
