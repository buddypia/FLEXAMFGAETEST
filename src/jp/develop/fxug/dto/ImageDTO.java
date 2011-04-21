package jp.develop.fxug.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ImageDTO implements Serializable {

	private String imageId;

	private String title;
	private String author;

	private byte[] image;
	private byte[] thumbnail;

	private Integer width;
	private Integer height;

	private Date createTime;


	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
