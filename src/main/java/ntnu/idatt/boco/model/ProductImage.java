package ntnu.idatt.boco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Blob;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return imgId == that.imgId && productId == that.productId && Objects.equals(imgName, that.imgName) && img64.equals(that.img64) && Arrays.equals(imgBlob, that.imgBlob);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(imgId, imgName, img64, productId);
        result = 31 * result + Arrays.hashCode(imgBlob);
        return result;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "imgId=" + imgId +
                ", imgName='" + imgName + '\'' +
                ", img64='" + img64 + '\'' +
                ", imgBlob=" + Arrays.toString(imgBlob) +
                ", productId=" + productId +
                '}';
    }
}
