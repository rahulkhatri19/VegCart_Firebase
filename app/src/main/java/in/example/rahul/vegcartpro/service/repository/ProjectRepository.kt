package `in`.example.rahul.vegcartpro.service.repository

import `in`.example.rahul.vegcartpro.service.model.CartModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProjectRepository {
//     public LiveData<List<Project>> getProjectList(String userId) {
//        final MutableLiveData<List<Project>> data = new MutableLiveData<>();
//
//        gitHubService.getProjectList(userId).enqueue(new Callback<List<Project>>() {
//            @Override
//            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
//                data.setValue(response.body());
//            }
//
//            // Error handling will be explained in the next article …
//        });
//
//        return data;
//    }
    fun getProjectList(uerId:String): LiveData<List<CartModel>>{
    val data = MutableLiveData<List<CartModel>>()

    return data
}
}