package itf;

import model.InvokeBuyObject;
import model.InvokeResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrderInvokeService {
    @Headers({"Content-Type: application/json"})
    @POST("channels/mychannel/chaincodes/ccv4")
    Call<InvokeResponseModel> invokeOrder(@Body InvokeBuyObject invokeBuyObject);
}
