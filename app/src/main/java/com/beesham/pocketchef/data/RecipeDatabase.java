package com.beesham.pocketchef.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Beesham on 5/1/2017.
 */

@Database(version=RecipeDatabase.VERSION)
public class RecipeDatabase {
    public static final int VERSION = 1;

    @Table(Columns.SavedRecipesColumn.class) public static final String SAVED_RECIPES = "savedRecipes";
    @Table(Columns.SearchedRecipesColumn.class) public static final String SEARCHED_RECIPES = "searchedRecipes";
}
