package com.beesham.pocketchef.data;

import android.net.Uri;
import android.test.ActivityUnitTestCase;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

import static android.R.attr.name;
import static android.R.attr.path;

/**
 * Created by Beesham on 5/1/2017.
 */

@ContentProvider(authority = RecipeProvider.AUTHORITY, database = RecipeDatabase.class)
public class RecipeProvider {
    public static final String AUTHORITY = "com.beesham.pocketchef.data.RecipeProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    interface Path{
        String SAVED_RECIPES = "saved_recipes";
        String SEARCHED_RECIPES = "searched_recipes";
    }

    private static Uri buildUri(String... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for(String path:paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = RecipeDatabase.SAVED_RECIPES)
    public static class SavedRecipes{
        @ContentUri(
                path = Path.SAVED_RECIPES,
                type = "vdn.android.cursor.dir/saved_recipes"
        )
        public static final Uri CONTENT_URI = buildUri(Path.SAVED_RECIPES);

        @InexactContentUri(
                name = "CATEGORY",
                path = Path.SAVED_RECIPES + "/*",
                type = "vnd.android.cursor.dir/saved_recipes",
                whereColumn = Columns.SavedRecipesColumn.CATEGORY,
                pathSegment = 1
        )
        public static final Uri withCategory(String category){
            return buildUri(Path.SAVED_RECIPES, category);
        }

        @InexactContentUri(
                name = "RECIPE_ID",
                path = Path.SAVED_RECIPES + "/*",
                type = "vnd.android.cursor.item/saved_recipes",
                whereColumn = Columns.SavedRecipesColumn.RECIPE_ID,
                pathSegment = 1
        )
        public static final Uri withRecipeId(String recipeId){
            return buildUri(Path.SAVED_RECIPES, recipeId);
        }
    }

    @TableEndpoint(table = RecipeDatabase.SEARCHED_RECIPES)
    public static class SearchedRecipes{
        @ContentUri(
                path = Path.SEARCHED_RECIPES,
                type = "vdn.android.cursor.dir/searched_recipes"
        )
        public static final Uri CONTENT_URI = buildUri(Path.SEARCHED_RECIPES);

        @InexactContentUri(
                name = "CATEGORY",
                path = Path.SAVED_RECIPES + "/*",
                type = "vnd.android.cursor.dir/saved_recipes",
                whereColumn = Columns.SavedRecipesColumn.CATEGORY,
                pathSegment = 1
        )
        public static final Uri withCategory(String category){
            return buildUri(Path.SAVED_RECIPES, category);
        }

        @InexactContentUri(
                name = "RECIPE_ID",
                path = Path.SEARCHED_RECIPES + "/*",
                type = "vnd.android.cursor.item/searched_recipes",
                whereColumn = Columns.SavedRecipesColumn.RECIPE_ID,
                pathSegment = 1
        )
        public static final Uri withRecipeId(String recipeId){
            return buildUri(Path.SEARCHED_RECIPES, recipeId);
        }
    }
}
