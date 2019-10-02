package bmbsoft.orderfoodonline.model.shared;

import bmbsoft.orderfoodonline.entities.CloseOpen;

import java.util.ArrayList;
import java.util.List;

public class MenuItemTimeAvailableModel {
    private Long menuItemTimeAvailableId;
    private String weekDay;
    private List<CloseOpen> list = new ArrayList<>();

    public MenuItemTimeAvailableModel(String weekDay, List<CloseOpen> list) {
        this.weekDay = weekDay;
        this.list = list;
    }

    public MenuItemTimeAvailableModel(String weekDay) {
        this.weekDay = weekDay;
    }

    public MenuItemTimeAvailableModel(List<CloseOpen> list) {
        this.list = list;
    }

    public Long getMenuItemTimeAvailableId() {
        return menuItemTimeAvailableId;
    }

    public void setMenuItemTimeAvailableId(Long menuItemTimeAvailableId) {
        this.menuItemTimeAvailableId = menuItemTimeAvailableId;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public List<CloseOpen> getList() {
        return list;
    }

    public void setList(List<CloseOpen> list) {
        this.list = list;
    }
}
