package ru.geekbrains;

public class Endpoints {
    public static final String UPLOAD_IMAGE = "/upload";
    public static final String ADD_FAVORITE = "/image/{imageHash}/favorite";
    public static final String UPDATE_IMAGE = "/image/{imageDeleteHash}";
    public static final String DELETE_IMAGE = "/account/{username}/image/{deleteHash}";
}