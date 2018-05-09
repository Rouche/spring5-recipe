delete from ingredient;
delete from recipe_category;
delete from category;
delete from unit_of_measure;
update notes set recipe_id = null;
delete from recipe;
delete from notes;
