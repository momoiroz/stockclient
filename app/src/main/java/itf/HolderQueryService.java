package itf;

import model.HolderQueryResponseModel;

import model.QueryObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HolderQueryService {
    @Headers({"Content-Type: application/json"})
    @POST("channels/mychannel/chaincodes/ccv4/q")
    Call<HolderQueryResponseModel> getHolder(@Body QueryObject queryObject);
}
