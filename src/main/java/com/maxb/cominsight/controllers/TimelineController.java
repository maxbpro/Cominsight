package com.maxb.cominsight.controllers;

import com.maxb.cominsight.models.essential.Photo;
import com.maxb.cominsight.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TimelineController {

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "/timeline", method = RequestMethod.GET)
    public Page<Photo> timeline(@RequestParam( "page" ) int page,
                                @RequestParam( "size" ) int size,
                                Principal principal) {
        return photoService.getPhotos(page, size);
    }
}
