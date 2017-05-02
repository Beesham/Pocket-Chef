package com.beesham.pocketchef.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Beesham on 5/1/2017.
 */

public class Columns {

    public static class SavedRecipesColumn{
        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        public static final String _ID = "_id";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String RECIPE_ID = "recipe_id";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String TITLE = "title";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String IMAGE_URL = "image_url";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String PREP_TIME = "prep_time";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String INSTRUCTIONS = "instructions";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String INGREDIENTS = "ingredients";
    }

    public static class SearchedRecipesColumn{
        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        public static final String _ID = "_id";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String RECIPE_ID = "recipe_id";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String TITLE = "title";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String IMAGE_URL = "image_url";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String PREP_TIME = "prep_time";
    }

}
