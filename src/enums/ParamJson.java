select params_id, text from elempar1 where params_id in (1005, 1006, 2005,2006, 3005, 4005, 11005, 12005, 31050, 33071, 34071) union
select params_id, text from elempar2 where params_id in (1005, 1006, 2005,2006, 3005, 4005, 11005, 12005, 31050, 33071, 34071) union
select params_id, text from furnpar1 where params_id in (1005, 1006, 2005,2006, 3005, 4005, 11005, 12005, 31050, 33071, 34071) union
select params_id, text from furnpar2 where params_id in (1005, 1006, 2005,2006, 3005, 4005, 11005, 12005, 31050, 33071, 34071) union
select params_id, text from glaspar1 where params_id in (1005, 1006, 2005,2006, 3005, 4005, 11005, 12005, 31050, 33071, 34071) union
select params_id, text from glaspar2 where params_id in (1005, 1006, 2005,2006, 3005, 4005, 11005, 12005, 31050, 33071, 34071) union
select params_id, text from joinpar1 where params_id in (1005, 1006, 2005,2006, 3005, 4005, 11005, 12005, 31050, 33071, 34071) union
select params_id, text from joinpar2 where params_id in (1005, 1006, 2005,2006, 3005, 4005, 11005, 12005, 31050, 33071, 34071) order by 1