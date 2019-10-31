package bmbsoft.orderfoodonline.model;

import java.util.List;

public class DeliveryArea {
    private Long deliveryAreaId;
    private Double deliveryCost;
    private List<Long> deliveryZoneId;

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Long getDeliveryAreaId() {
        return deliveryAreaId;
    }

    public void setDeliveryAreaId(Long deliveryAreaId) {
        this.deliveryAreaId = deliveryAreaId;
    }

    public List<Long> getDeliveryZoneId() {
        return deliveryZoneId;
    }

    public void setDeliveryZoneId(List<Long> deliveryZoneId) {
        this.deliveryZoneId = deliveryZoneId;
    }
}
