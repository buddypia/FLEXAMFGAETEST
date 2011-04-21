package jp.develop.fxug.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.images.ImagesService.OutputEncoding;

import jp.develop.fxug.dto.ImageDTO;
import jp.develop.fxug.entity.ImageData;
import jp.develop.fxug.util.PMF;

public class AlbumService {

	public String add(ImageDTO imageDTO) {

		// 入力値チェック
		if (imageDTO.getImage() == null) {
			throw new IllegalArgumentException("image must not be null.");
		}

		// イメージの編集
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Image inputImage = ImagesServiceFactory.makeImage(imageDTO.getImage());
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		Transform resize = ImagesServiceFactory.makeResize(160, 120);
		Image thumbnailImage = imagesService.applyTransform(resize, inputImage, OutputEncoding.JPEG);

		// 保存
		PersistenceManager pm = PMF.getPersistenceManager();
		ImageData imageData;
		try {
			imageData = new ImageData();
			imageData.setTitle(imageDTO.getTitle());
			imageData.setAuthor(imageDTO.getAuthor());
			imageData.setImage(new Blob(imageDTO.getImage()));
			imageData.setWidth(width);
			imageData.setHeight(height);
			imageData.setThumbnail(new Blob(thumbnailImage.getImageData()));
			imageData.setCreateTime(new Date());
			pm.makePersistent(imageData);
		} finally {
			pm.close();
		}
		return imageData.getImageId();
	}

	@SuppressWarnings("unchecked")
	public List<ImageDTO> search() {
		List<ImageDTO> result = new ArrayList<ImageDTO>();
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query query = pm.newQuery(ImageData.class);
//			query.setFilter("title == 'Title' && author == 'wacky'");
//			query.setFilter("author == 'wacky'");
			query.setOrdering("createTime desc");
			List<ImageData> entities = (List<ImageData>) query.execute();

			int size = entities.size();
			for (int index = 0; index < size; index++) {
				ImageData entity = entities.get(index);
				ImageDTO dto = new ImageDTO();
				dto.setImageId(entity.getImageId());
				dto.setTitle(entity.getTitle());
				dto.setAuthor(entity.getAuthor());
				dto.setWidth(entity.getWidth());
				dto.setHeight(entity.getHeight());
				dto.setThumbnail(entity.getThumbnail().getBytes());
				dto.setCreateTime(entity.getCreateTime());
				result.add(dto);
			}
		} finally {
			pm.close();
		}
		return result;
	}
}
