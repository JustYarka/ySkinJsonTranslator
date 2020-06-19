package ru.yarka.skinjson.skin;

import cn.nukkit.entity.data.Skin;
import cn.nukkit.utils.SerializedImage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SkinJsonDecoder {

    private JsonObject jsonObject;

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

    public SkinJsonDecoder(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public SkinJsonDecoder deserialize() {
        if(jsonObject.has("SkinId")) skinId = jsonObject.get("SkinId").getAsString();
        if(jsonObject.has("CapeId")) capeId = jsonObject.get("CapeId").getAsString();
        if(jsonObject.has("PremiumSkin")) premiumSkin = jsonObject.get("PremiumSkin").getAsBoolean();
        if(jsonObject.has("PersonaSkin")) personaSkin = jsonObject.get("PersonaSkin").getAsBoolean();
        if(jsonObject.has("CapeOnClassicSkin")) capeOnClassicSkin = jsonObject.get("CapeOnClassicSkin").getAsBoolean();
        if(jsonObject.has("SkinResourcePatch")) skinResourcePatch = new String(Base64.getDecoder().decode(jsonObject.get("SkinResourcePatch").getAsString()), StandardCharsets.UTF_8);
        if(jsonObject.has("SkinGeometryData")) skinGeometryData = new String(Base64.getDecoder().decode(jsonObject.get("SkinGeometryData").getAsString()), StandardCharsets.UTF_8);
        if(jsonObject.has("AnimationData")) animationData = new String(Base64.getDecoder().decode(jsonObject.get("AnimationData").getAsString()), StandardCharsets.UTF_8);
        if(jsonObject.has("AnimatedImageData")) animatedImageData = jsonObject.get("AnimatedImageData").getAsJsonArray();
        if(jsonObject.has("SkinColor")) skinColor = jsonObject.get("SkinColor").getAsString();
        if(jsonObject.has("ArmSize")) armSize = jsonObject.get("ArmSize").getAsString();
        if(jsonObject.has("PersonaPieces")) personaPieces = jsonObject.get("PersonaPieces").getAsJsonArray();
        if(jsonObject.has("PieceTintColors")) pieceTintColors = jsonObject.get("PieceTintColors").getAsJsonArray();
        if(jsonObject.has("SkinData")) skinData = SkinJsonUtils.getImage(jsonObject, "Skin");
        if(jsonObject.has("CapeData")) capeData = SkinJsonUtils.getImage(jsonObject, "Cape");

        return this;
    }

    public Skin build() {
        Skin skin = new Skin();

        skin.setSkinId(skinId);
        skin.setCapeId(capeId);
        skin.setPremium(premiumSkin);
        skin.setPersona(personaSkin);
        skin.setCapeOnClassic(capeOnClassicSkin);
        skin.setSkinResourcePatch(skinResourcePatch);
        skin.setGeometryData(skinGeometryData);
        skin.setAnimationData(animationData);
        skin.setSkinColor(skinColor);
        skin.setArmSize(armSize);
        skin.setSkinData(skinData);
        skin.setCapeData(capeData);

        SkinJsonUtils.setSkinAnimations(skin, animatedImageData);
        SkinJsonUtils.setPersonaPieces(skin, personaPieces);
        SkinJsonUtils.setPieceTintColors(skin, pieceTintColors);

        return skin;
    }
}
