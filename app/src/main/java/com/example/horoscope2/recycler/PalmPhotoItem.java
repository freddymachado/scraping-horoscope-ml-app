package com.example.horoscope2.recycler;

import android.net.Uri;

public class PalmPhotoItem {

    private Uri foto;

    public PalmPhotoItem(){


    }

    public PalmPhotoItem(Uri foto) {
        this.foto = foto;
    }

    public void setFoto(Uri foto) {
        this.foto = foto;
    }

    public Uri getFoto() {
        return foto;
    }
}
