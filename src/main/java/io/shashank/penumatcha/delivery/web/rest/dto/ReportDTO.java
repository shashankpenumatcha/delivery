package io.shashank.penumatcha.delivery.web.rest.dto;

public class ReportDTO {

private Long productId;
private String productName;
private Float opening;
private Float closing;
private Float sales;
private Float wastage;
private Float currentTotal;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getOpening() {
        return opening;
    }

    public void setOpening(Float opening) {
        this.opening = opening;
    }

    public Float getClosing() {
        return closing;
    }

    public void setClosing(Float closing) {
        this.closing = closing;
    }

    public Float getSales() {
        return sales;
    }

    public void setSales(Float sales) {
        this.sales = sales;
    }

    public Float getWastage() {
        return wastage;
    }

    public void setWastage(Float wastage) {
        this.wastage = wastage;
    }

    public Float getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(Float currentTotal) {
        this.currentTotal = currentTotal;
    }
}
