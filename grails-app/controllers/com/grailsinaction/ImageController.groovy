package com.grailsinaction

import org.codehaus.groovy.grails.core.io.ResourceLocator
import org.springframework.core.io.Resource

class PhotoUploadCommand {
    byte[] photo
    String loginId
}

class ImageController {

    ResourceLocator grailsResourceLocator
    ImageService imageService

    /**
     * POST image/upload
     */
    def upload(PhotoUploadCommand puc) {
        def user = User.findByLoginId(puc.loginId)
        if (user && user.profile) {
            user.profile.photo = puc.photo
            redirect controller: "user", action: "profile", id: puc.loginId 
        } else {
            //no matching user or user has no profile
            flash.message = "Missing user or profile. Please create."
            redirect controller: "image", action: "create"}
    }

    /*
     * An example of how to upload a file and store it directly to the 
     * file system. This is not currently used and here as an example
     * only. See the Burning Image plugin for more.
     */
    def rawUpload() {
        // a Spring MultipartFile
        def mpf = request.getFile('photo')
        if (!mpf?.empty && mpf.size < 1024*200) {
            mpf.transferTo(new File("/hubbub/images/${params.loginId}/profile.gif"))
        }
    }

    /**
     * GET image/create
     *
     * An example image upload form. Not intended for user access.
     */
    def create() {
        // pass through to upload form
        [userList: User.list()]
    }

    /*
     * These actions send content to the browser by writing bytes directly
     * to the response's output stream.
     */
    def renderImage(String id) {
        def image = imageService.profileImageForUser(id)
        response.setContentLength(image.size())
        response.outputStream.write(image)
    }

    def thumbnail(String id) {
        def image = imageService.getThumbnail(id)
        response.setContentLength(image.size())
        response.outputStream.write(image)
    }

    def tinyThumbnail(String id) {
        def image = imageService.getTinyThumbnail(id)
        response.setContentLength(image.size())
        response.outputStream.write(image)
    }
}
