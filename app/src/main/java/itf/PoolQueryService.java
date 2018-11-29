package itf;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import model.HolderQueryResponseModel;
import model.QueryObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PoolQueryService {
    @Headers({"Content-Type: application/json"})
    @POST("channels/mychannel/chaincodes/ccv4/q")
    Call<JsonElement> getPool(@Body QueryObject queryObject);
}
