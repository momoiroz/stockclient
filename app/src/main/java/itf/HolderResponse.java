package itf;

import model.HolderQueryResponseModel;

public interface HolderResponse {
    void invokeHolderResponse(boolean success, HolderQueryResponseModel hqr);
}
