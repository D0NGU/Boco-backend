package ntnu.idatt.boco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Blob;
import java.util.Base64;

public class ProductImage {
    private int imgId;
    private String imgName;
    private String img64;
    private byte[] imgBlob;
    private int productId;

    public ProductImage() {
    }

    public ProductImage(int imgId, String imgName, byte[] imgBlob, int productId) {
        this.imgId = imgId;
        this.imgName = imgName;
        this.img64 = Base64.getEncoder().encodeToString(imgBlob);
        this.imgBlob = imgBlob;
        this.productId = productId;
    }

    public ProductImage(int imgId, String imgName, String img64, int productId) {
        this.imgId = imgId;
        this.imgName = imgName;
        this.img64 = img64;
        this.imgBlob = Base64.getDecoder().decode(img64);
        this.productId = productId;
    }

    public int getImgId() {
        return imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public String getImg64() {
        return img64;
    }

    @JsonIgnore
    public byte[] getImgBlob() {
        return imgBlob;
    }

    public int getProductId() {
        return productId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
        this.imgBlob = Base64.getDecoder().decode(img64);
    }

    public void setImgBlob(byte[] imgBlob) {
        this.imgBlob = imgBlob;
        this.img64 = Base64.getEncoder().encodeToString(imgBlob);
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
