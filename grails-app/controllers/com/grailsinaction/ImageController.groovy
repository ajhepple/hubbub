package com.grailsinaction

class PhotoUploadCommand {
    byte[] photo
    String loginId
}

class ImageController {

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

    def rawUpload() {
        // a Spring MultipartFile
        def mpf = request.getFile('photo')
        if (!mpf?.empty && mpf.size < 1024*200) {
            mpf.transferTo(new File("/hubbub/images/${params.loginId}/profile.gif"))
        }
    }

    def create() {
        // pass through to upload form
        [userList: User.list()]
    }

    /**
     * This action sends content to the browser by writing bytes directly
     * to the response's output stream.
     */
    def renderImage(String id) {
        def user = User.findByLoginId(id)
        if (user?.profile?.photo) {
            response.setContentLength(user.profile.photo.size())
            response.outputStream.write(user.profile.photo)
        } else {
            response.sendError(404)
        }
    }
}
