package ru.yarka.skinjson.skin;

import cn.nukkit.entity.data.Skin;
import cn.nukkit.utils.PersonaPiece;
import cn.nukkit.utils.PersonaPieceTint;
import cn.nukkit.utils.SerializedImage;
import cn.nukkit.utils.SkinAnimation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

public class SkinJsonUtils {

    /*
                ENCODER
     */
    public static void imageToJson(JsonObject object, SerializedImage image, String keyPrefix) {
        object.addProperty(keyPrefix + "Data", Base64.getEncoder().encodeToString(image.data));
        object.addProperty(keyPrefix + "ImageHeight", image.height);
        object.addProperty(keyPrefix + "ImageWidth", image.width);
    }

    public static JsonObject animationToJson(SkinAnimation animation) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("Frames", animation.frames);
        jsonObject.addProperty("Type", animation.type);
        jsonObject.addProperty("Image", Base64.getEncoder().encodeToString(animation.image.data));
        jsonObject.addProperty("ImageWidth", animation.image.width);
        jsonObject.addProperty("ImageHeight", animation.image.height);

        return jsonObject;
    }

    public static JsonObject personaPieceToJson(PersonaPiece piece) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("PieceId", piece.id);
        jsonObject.addProperty("PieceType", piece.type);
        jsonObject.addProperty("PackId", piece.packId);
        jsonObject.addProperty("IsDefault", piece.isDefault);
        jsonObject.addProperty("ProductId", piece.productId);

        return jsonObject;
    }

    public static JsonObject tintToJson(PersonaPieceTint tint) {
        JsonObject jsonObject = new JsonObject();
        JsonArray colors = new JsonArray();

        tint.colors.forEach(color -> {
            colors.add(color);
        });

        jsonObject.addProperty("PieceType", tint.pieceType);
        jsonObject.add("Colors", colors);

        return jsonObject;
    }

    public static JsonArray getAnimatedImageData(Skin skin) {
        JsonArray jsonArray = new JsonArray();

        skin.getAnimations().forEach(animation -> {
            jsonArray.add(animationToJson(animation));
        });

        return jsonArray;
    }

    public static JsonArray getPersonaPieces(Skin skin) {
        JsonArray jsonArray = new JsonArray();

        skin.getPersonaPieces().forEach(personaPiece -> {
            jsonArray.add(personaPieceToJson(personaPiece));
        });

        return jsonArray;
    }

    public static JsonArray getPieceTintColors(Skin skin) {
        JsonArray jsonArray = new JsonArray();

        skin.getTintColors().forEach(personaPieceTint -> {
            jsonArray.add(tintToJson(personaPieceTint));
        });

        return jsonArray;
    }


    /*
                DECODER
     */
    public static void setSkinAnimations(Skin skin, JsonArray jsonArray) {
        Iterator<JsonElement> iterator = jsonArray.iterator();

        while(iterator.hasNext()) {
            skin.getAnimations().add(getAnimation(iterator.next().getAsJsonObject()));
        }
    }

    public static void setPersonaPieces(Skin skin, JsonArray jsonArray) {
        Iterator<JsonElement> iterator = jsonArray.iterator();

        while(iterator.hasNext()) {
            skin.getPersonaPieces().add(getPersonaPiece(iterator.next().getAsJsonObject()));
        }
    }

    public static void setPieceTintColors(Skin skin, JsonArray jsonArray) {
        Iterator<JsonElement> iterator = jsonArray.iterator();

        while(iterator.hasNext()) {
            skin.getTintColors().add(getTint(iterator.next().getAsJsonObject()));
        }
    }

    private static SkinAnimation getAnimation(JsonObject element) {
        float frames = element.get("Frames").getAsFloat();
        int type = element.get("Type").getAsInt();
        byte[] data = Base64.getDecoder().decode(element.get("Image").getAsString());
        int width = element.get("ImageWidth").getAsInt();
        int height = element.get("ImageHeight").getAsInt();
        return new SkinAnimation(new SerializedImage(width, height, data), type, frames);
    }

    public static SerializedImage getImage(JsonObject token, String name) {
        if (token.has(name + "Data")) {
            byte[] skinImage = Base64.getDecoder().decode(token.get(name + "Data").getAsString());
            if (token.has(name + "ImageHeight") && token.has(name + "ImageWidth")) {
                int width = token.get(name + "ImageWidth").getAsInt();
                int height = token.get(name + "ImageHeight").getAsInt();
                return new SerializedImage(width, height, skinImage);
            } else {
                return SerializedImage.fromLegacy(skinImage);
            }
        } else {
            return SerializedImage.EMPTY;
        }
    }

    private static PersonaPiece getPersonaPiece(JsonObject object) {
        String pieceId = object.get("PieceId").getAsString();
        String pieceType = object.get("PieceType").getAsString();
        String packId = object.get("PackId").getAsString();
        boolean isDefault = object.get("IsDefault").getAsBoolean();
        String productId = object.get("ProductId").getAsString();
        return new PersonaPiece(pieceId, pieceType, packId, isDefault, productId);
    }

    public static PersonaPieceTint getTint(JsonObject object) {
        String pieceType = object.get("PieceType").getAsString();
        List<String> colors = new ArrayList();
        Iterator var3 = object.get("Colors").getAsJsonArray().iterator();

        while(var3.hasNext()) {
            JsonElement element = (JsonElement)var3.next();
            colors.add(element.getAsString());
        }

        return new PersonaPieceTint(pieceType, colors);
    }
}
