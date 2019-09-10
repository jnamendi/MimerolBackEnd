Alter table menu_item add COLUMN avai_mon boolean;
Alter table menu_item add COLUMN avai_tue boolean;
Alter table menu_item add COLUMN avai_wed boolean;
Alter table menu_item add COLUMN avai_thu boolean;
Alter table menu_item add COLUMN avai_fri boolean;
Alter table menu_item add COLUMN avai_sat boolean;
Alter table menu_item add COLUMN avai_sun boolean;
Alter table menu_item add COLUMN out_of_stock boolean;

UPDATE `menu_item` SET `avai_monday` = 1, `avai_tue` = 1, `avai_wed` = 1, `avai_thu` = 1, `avai_fri` = 1, `avai_sat` = 1, `avai_sun` = 1, `out_of_stock` = 0;
