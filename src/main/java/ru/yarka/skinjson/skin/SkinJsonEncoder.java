package ru.yarka.skinjson.skin;

import cn.nukkit.entity.data.Skin;
import cn.nukkit.utils.SerializedImage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SkinJsonEncoder {

    private Skin skin;

    private String skinId = null;
    private String capeId = null;
    private boolean premiumSkin = false;
    private boolean personaSkin = false;
    private boolean capeOnClassicSkin = false;
    private String skinResourcePatch = null;
    private String skinGeometryData = null;
    private String animationData = null;
    private JsonArray animatedImageData = null;
    private String skinColor = null;
    private String armSize = null;
    private JsonArray personaPieces = null;
    private JsonArray pieceTintColors = null;
    private SerializedImage skinData = null;
    private SerializedImage capeData = null;

    public SkinJsonEncoder(Skin skin) {
        this.skin = skin;
    }

    public SkinJsonEncoder serialize() {
        skinId = skin.getSkinId();
        capeId = skin.getCapeId();
        premiumSkin = skin.isPremium();
        personaSkin = skin.isPersona();
        capeOnClassicSkin = skin.isCapeOnClassic();
        skinResourcePatch = skin.getSkinResourcePatch();
        skinGeometryData = skin.getGeometryData();
        animationData = skin.getAnimationData();
        skinColor = skin.getSkinColor();
        armSize = skin.getArmSize();
        animatedImageData = SkinJsonUtils.getAnimatedImageData(skin);
        personaPieces = SkinJsonUtils.getPersonaPieces(skin);
        pieceTintColors = SkinJsonUtils.getPieceTintColors(skin);
        skinData = skin.getSkinData();
        capeData = skin.getCapeData();

        return this;
    }

    public JsonObject build() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("SkinId", skinId);
        jsonObject.addProperty("CapeId", capeId);
        jsonObject.addProperty("PremiumSkin", premiumSkin);
        jsonObject.addProperty("PersonaSkin", personaSkin);
        jsonObject.addProperty("CapeOnClassicSkin", capeOnClassicSkin);
        jsonObject.addProperty("SkinResourcePatch", Base64.getEncoder().encodeToString(skinResourcePatch.getBytes(StandardCharsets.UTF_8)));
        jsonObject.addProperty("SkinGeometryData", Base64.getEncoder().encodeToString(skinGeometryData.getBytes(StandardCharsets.UTF_8)));
        jsonObject.addProperty("AnimationData", Base64.getEncoder().encodeToString(animationData.getBytes(StandardCharsets.UTF_8)));
        jsonObject.add("AnimatedImageData", animatedImageData);
        jsonObject.addProperty("SkinColor", skinColor);
        jsonObject.addProperty("ArmSize", armSize);
        jsonObject.add("PersonaPieces", personaPieces);
        jsonObject.add("PieceTintColors", pieceTintColors);
        SkinJsonUtils.imageToJson(jsonObject, skinData, "Skin");
        SkinJsonUtils.imageToJson(jsonObject, capeData, "Cape");

        return jsonObject;
    }
}
