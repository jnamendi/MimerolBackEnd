package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.MenuItemTimeAvailableDao;
import bmbsoft.orderfoodonline.entities.CloseOpen;
import bmbsoft.orderfoodonline.entities.MenuItemTimeAvailable;
import bmbsoft.orderfoodonline.model.shared.MenuItemTimeAvailableModel;
import bmbsoft.orderfoodonline.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuItemTimeAvailableService {
    public static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    @Autowired
    private MenuItemTimeAvailableDao menuItemTimeAvailableDao;

    public MenuItemTimeAvailable findById(final long id) {
        return menuItemTimeAvailableDao.findById(id);
    }

    public Boolean save(MenuItemTimeAvailable menuItemTimeAvailable) {
        return menuItemTimeAvailableDao.save(menuItemTimeAvailable);
    }

    public Boolean deleteByMenuId(long menuId) {
        return menuItemTimeAvailableDao.deleteById(menuId);
    }

    public Boolean deleteById(long id) {
        return menuItemTimeAvailableDao.delete(id);
    }

    public List<MenuItemTimeAvailableModel> getByMenutId(long menuItemId) {
        List<MenuItemTimeAvailable> list = menuItemTimeAvailableDao.getMenuTimeAvailable(menuItemId);
        ArrayList<MenuItemTimeAvailableModel> mn = new ArrayList<>();
        if(!list.isEmpty()) {
            mn.add( new MenuItemTimeAvailableModel(Constant.Weekday.MON.toString()));
            mn.add( new MenuItemTimeAvailableModel(Constant.Weekday.TUE.toString()));
            mn.add( new MenuItemTimeAvailableModel(Constant.Weekday.WED.toString()));
            mn.add( new MenuItemTimeAvailableModel(Constant.Weekday.THU.toString()));
            mn.add( new MenuItemTimeAvailableModel(Constant.Weekday.FRI.toString()));
            mn.add( new MenuItemTimeAvailableModel(Constant.Weekday.SAT.toString()));
            mn.add( new MenuItemTimeAvailableModel(Constant.Weekday.SUN.toString()));
            int size = list.size();
            for (int i = 0 ; i < size ; i++){
                switch (list.get(i).getWeekday()){
                    case "MON":
                        mn.get(0).setMenuItemTimeAvailableId(menuItemId);
                        mn.get(0).getList().add(new CloseOpen(list.get(i).getEndTime(),list.get(i).getStartTime()));
                        break;
                    case "TUE":
                        mn.get(1).setMenuItemTimeAvailableId(menuItemId);
                        mn.get(1).getList().add(new CloseOpen(list.get(i).getEndTime(),list.get(i).getStartTime()));
                        break;
                    case "WED":
                        mn.get(2).setMenuItemTimeAvailableId(menuItemId);
                        mn.get(2).getList().add(new CloseOpen(list.get(i).getEndTime(),list.get(i).getStartTime()));
                        break;
                    case "THU":
                        mn.get(3).setMenuItemTimeAvailableId(menuItemId);
                        mn.get(3).getList().add(new CloseOpen(list.get(i).getEndTime(),list.get(i).getStartTime()));
                        break;
                    case "FRI":
                        mn.get(4).setMenuItemTimeAvailableId(menuItemId);
                        mn.get(4).getList().add(new CloseOpen(list.get(i).getEndTime(),list.get(i).getStartTime()));
                        break;
                    case "SAT":
                        mn.get(5).setMenuItemTimeAvailableId(menuItemId);
                        mn.get(5).getList().add(new CloseOpen(list.get(i).getEndTime(),list.get(i).getStartTime()));
                        break;
                    case "SUN":
                        mn.get(6).setMenuItemTimeAvailableId(menuItemId);
                        mn.get(6).getList().add(new CloseOpen(list.get(i).getEndTime(),list.get(i).getStartTime()));
                        break;
                }
            }
        }
        return mn;
    }
}
