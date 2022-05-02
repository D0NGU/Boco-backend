package ntnu.idatt.boco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * This class represents a product image.
 */
public class ProductImage {
    private int imgId;
    private String imgName;
    private String img64;
    private byte[] imgBlob;
    private int productId;

    public ProductImage() {}

    /**
     * Constructor for a product image.
     * @param imgId the unique id of the product image
     * @param imgName the name of the image
     * @param imgBlob the image blob
     * @param productId the id of the product the images belong to
     */
    public ProductImage(int imgId, String imgName, byte[] imgBlob, int productId) {
        this.imgId = imgId;
        this.imgName = imgName;
        this.imgBlob = imgBlob;
        this.img64 = Base64.getEncoder().encodeToString(imgBlob);
        this.productId = productId;
    }

    public ProductImage(int imgId, String imgName, String img64, int productId) {
        this.imgId = imgId;
        this.imgName = imgName;
        this.img64 = img64;
        this.imgBlob = Base64.getDecoder().decode(img64);
        this.productId = productId;
    }

    public ProductImage(String imgName, String img64, int productId) {
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
}
