package com.digmaweb.salim.myatelier.ui.Model;

import android.net.Uri;

public class AtelierResponse {

    private String Id ;
    private String image_Url;

    public AtelierResponse(String image_Url , String id) {
        this.image_Url = image_Url;
        this.Id = id ;
    }

    public AtelierResponse() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Uri getImage_Url() {
        return Uri.parse(image_Url);
    }

    public void setImage_Url(String image_Url) {
        this.image_Url = image_Url;
    }
}
