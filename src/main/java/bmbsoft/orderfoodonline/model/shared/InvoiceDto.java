package bmbsoft.orderfoodonline.model.shared;

public class InvoiceDto {
    private String date;
    private String orderCode;
    private String price;

    public InvoiceDto() {
    }

    public InvoiceDto(String date, String orderCode, String price) {
        this.date = date;
        this.orderCode = orderCode;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
