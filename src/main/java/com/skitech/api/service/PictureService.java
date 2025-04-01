package com.skitech.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skitech.api.model.Picture;
import com.skitech.api.repository.PictureRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class PictureService {
    
    @Autowired
    private PictureRepository pictureRepository;

    /*@Transactional
    public void deletePicturesByListingId(Long listingId) {
        pictureRepository.deleteByListingId(listingId);
    }*/
    @PersistenceContext
    private EntityManager entityManager; // Inject EntityManager
    
    @Transactional
    public void deletePicturesByListingId(Long listingId) {
        pictureRepository.deleteAllByListingId(listingId);
        entityManager.flush(); // Ensure delete is executed before clearing
        entityManager.clear(); // Clear persistence context to avoid stale state issues // Avoid stale entity references
    	
    	//List<Picture> pictures = pictureRepository.findByListingId(listingId);
        //pictureRepository.deleteAllInBatch(pictures); // Uses batch delete
    	
    }
    
    public List<String> getLatestPictureUrls(Long listingId) {
        List<Picture> latestPictures = pictureRepository.findLatestVersionPictures(listingId);
        return latestPictures.stream()
                .map(Picture::getFileName) // Assuming fileName contains the picture URL
                .collect(Collectors.toList());
    }
    
    /*@Transactional
    public void deletePicturesByListingId(Long listingId) {
        List<Picture> pictures = pictureRepository.findByListingId(listingId);
        System.out.println(pictures.toString());
        pictureRepository.deleteAll(pictures);
    }*/

}
