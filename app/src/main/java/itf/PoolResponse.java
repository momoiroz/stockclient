package itf;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import model.HolderQueryResponseModel;

public interface PoolResponse {
    void invokeBuyPoolResponse(boolean success, JsonElement element);
    void invokeSellPoolResponse(boolean success, JsonElement element);
    void invokeMatchPoolResponse(boolean success, JsonElement element);
}
