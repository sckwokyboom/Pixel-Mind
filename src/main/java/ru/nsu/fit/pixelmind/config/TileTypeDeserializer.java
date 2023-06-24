package ru.nsu.fit.pixelmind.config;

import com.google.gson.*;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;

import java.lang.reflect.Type;

public class TileTypeDeserializer implements JsonDeserializer<TileType[][]> {

    @Override
    public TileType[][] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        int numRows = jsonArray.size();
        int numCols = jsonArray.get(0).getAsJsonArray().size();

        TileType[][] tileArray = new TileType[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            JsonArray rowArray = jsonArray.get(i).getAsJsonArray();
            for (int j = 0; j < numCols; j++) {
                int tileValue = rowArray.get(j).getAsInt();
                tileArray[i][j] = TileType.valueOf(tileValue);
            }
        }

        return tileArray;
    }
}
