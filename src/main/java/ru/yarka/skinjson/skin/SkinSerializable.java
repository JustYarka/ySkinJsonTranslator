package ru.yarka.skinjson.skin;

import cn.nukkit.entity.data.Skin;
import com.google.gson.JsonObject;

public class SkinSerializable {

    public static SkinJsonEncoder create(Skin skin) {
        return new SkinJsonEncoder(skin);
    }

    public static SkinJsonDecoder create(JsonObject jsonObject) {
        return new SkinJsonDecoder(jsonObject);
    }
}
